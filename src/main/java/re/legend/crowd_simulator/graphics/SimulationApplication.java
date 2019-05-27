package re.legend.crowd_simulator.graphics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import re.legend.crowd_simulator.entities.bodies.AdultBody;
import re.legend.crowd_simulator.entities.bodies.AgentBody;
import re.legend.crowd_simulator.entities.gameobjects.Wall;

public class SimulationApplication extends ApplicationAdapter implements InputProcessor, MapListener {
	private OrthographicCamera camera;

	private TiledMap map;
	private TmxMapLoader loader;
	private OrthogonalTiledMapRenderer renderer;

	// Cursor position on last click
	private Vector2 lastTouch;

	// Agent bodies to render, updated by the update() method
	private List<AgentBody> bodies;

	// Walls list, not used in this class but retrieved
	private List<Wall> walls;

	// Adult bodies textures sprite
	private Texture adultTextures;
	private TextureRegion adultStillLeft;
	private TextureRegion adultStillRight;
	private TextureRegion adultStillFace;
	private TextureRegion adultStillBack;

	// SpriteBatche for the map
	private SpriteBatch spriteBatch;

	// SpriteBatch fixed (for texts)
	SpriteBatch fixedSpriteBatch;

	// Map size
	private int mapWidth;
	private int mapHeight;

	// Timer
	private String strTimer; // display a string
	private BitmapFont fontTimer;
	private GlyphLayout timerLayout;

	// Font generator and parameter
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;

	// Shape renderer
	private ShapeRenderer shapeRenderer;

	@Override
	public void create() {
		// Attributes instantiation
		this.bodies = new ArrayList<>();
		this.lastTouch = new Vector2();
		this.spriteBatch = new SpriteBatch();
		this.fixedSpriteBatch = new SpriteBatch();
		this.walls = new ArrayList<>();
		this.shapeRenderer = new ShapeRenderer();
		
		// Loads textures
		this.adultTextures = new Texture("adult_bodies.png");
		this.adultStillLeft = new TextureRegion(this.adultTextures, 0, 0, 16, 16);
		this.adultStillFace = new TextureRegion(this.adultTextures, 16, 0, 16, 16);
		this.adultStillBack = new TextureRegion(this.adultTextures, 32, 0, 16, 16);
		this.adultStillRight = new TextureRegion(this.adultTextures, 48, 0, 16, 16);

		// Timer creation and stamp the startTimer
		this.strTimer = "Time: 0";
		this.fontTimer = new BitmapFont();
		this.timerLayout = new GlyphLayout();

		// Loads map
		this.loader = new TmxMapLoader();
		this.map = this.loader.load("map/map.tmx");
		this.renderer = new OrthogonalTiledMapRenderer(this.map);
		this.mapWidth = (int) this.map.getProperties().get("width") * (int) this.map.getProperties().get("tilewidth");
		this.mapHeight = (int) this.map.getProperties().get("height") * (int) this.map.getProperties().get("tileheight");

		// Loads walls
		TiledMapTileLayer wallsLayer = (TiledMapTileLayer) this.renderer.getMap().getLayers().get("Walls");
		Cell cell;
		for (int x = 0; x < wallsLayer.getWidth(); x++) {
			for (int y = 0; y < wallsLayer.getHeight(); y++) {
				if ((cell = wallsLayer.getCell(x, y)) != null) {
					this.walls.add(new Wall(x * Wall.SIZE, y * Wall.SIZE));
				}
			}
		}

		this.camera = new OrthographicCamera(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 5);
		this.camera.position.set(this.mapWidth / 2, this.mapHeight / 2, 0);

		// Initializes font generator and parameter for the timer text
		this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/coolvetica.ttf"));
		this.parameter = new FreeTypeFontParameter();
		this.parameter.size = 30;

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose() {
		this.spriteBatch.dispose();
		this.fixedSpriteBatch.dispose();
		this.generator.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor((float) 97 / 255, (float) 133 / 255, (float) 248 / 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		this.camera.update();

		// Renders map
		this.renderer.setView(this.camera);
		this.renderer.render();

		
		this.spriteBatch.setProjectionMatrix(this.camera.combined);
		this.spriteBatch.begin();
		for (AgentBody body : this.bodies) {
			if (body instanceof AdultBody) {
				this.spriteBatch.draw(this.johnTex, body.getPosition().x, this.mapHeight - body.getPosition().y);
			}
		}
		this.spriteBatch.end();
		
		this.shapeRenderer.setProjectionMatrix(this.camera.combined);
		this.shapeRenderer.begin(ShapeType.Line);
		for (AgentBody body : this.bodies) {
			if (body instanceof AdultBody) {
				// Agent's private circle
				this.shapeRenderer.setColor(0, 0, 1, 1); // Blue
				this.shapeRenderer.circle(body.getPosition().x, body.getPosition().y, 30);
				// Agent's ahead vector
				this.shapeRenderer.setColor(1, 1, 1, 1); // White
				this.shapeRenderer.line(body.getPosition().x, body.getPosition().y, body.getAhead().x, body.getAhead().y);
				// Agent's ahead2 vector
				//this.shapeRenderer.setColor(1, 0, 0, 1);
				//this.shapeRenderer.line(body.getPosition().x, body.getPosition().y, body.getAhead2().x, body.getAhead2().y);
				// Agent's velocity vector
				this.shapeRenderer.setColor(0, 1, 0, 1); // Green
				this.shapeRenderer.line(body.getPosition().x, body.getPosition().y, body.getPosition().x + body.getLinearVelocity().x, body.getPosition().y + body.getLinearVelocity().y);
				// Agent's avoidance vector
				this.shapeRenderer.setColor(0.5f, 0, 0.5f, 1); // Purple
				this.shapeRenderer.line(body.getPosition().x, body.getPosition().y, body.getPosition().x + body.getAvoidance().x, body.getPosition().y + body.getAvoidance().y);
				// Agent's desired velocity vector
				this.shapeRenderer.setColor(1, 0, 0, 1); // Red
				this.shapeRenderer.line(body.getPosition().x, body.getPosition().y, body.getPosition().x + body.getDesiredVelocity().x, body.getPosition().y + body.getDesiredVelocity().y);
			}
		}
		this.shapeRenderer.end();

		// Changes the parameter text to the current time
		this.parameter.characters = this.strTimer;
		// Generates the timer bitmap
		this.fontTimer = this.generator.generateFont(this.parameter);
		// Sets layout (to get width then)
		this.timerLayout.setText(this.fontTimer, this.strTimer);
		// Renders timer
		this.fixedSpriteBatch.begin();
		this.fontTimer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		float posX = (Gdx.graphics.getWidth() - this.timerLayout.width) / 2;
		float posY = this.timerLayout.height + 10;
		this.fontTimer.draw(this.fixedSpriteBatch, this.strTimer, posX, posY);
		this.fixedSpriteBatch.end();

	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// Camera zoom
		if (amount == 1 && this.camera.zoom >= 1) {
			this.camera.zoom++;
		} else if (amount == -1 && this.camera.zoom > 1) {
			this.camera.zoom--;
		}
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		this.lastTouch.set(x, y);
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		float deltaX = Gdx.input.getDeltaX();
		float deltaY = Gdx.input.getDeltaY();
		this.camera.translate(-deltaX, deltaY);
		this.camera.update();
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public void update(List<AgentBody> bodies, float time) {
		this.bodies = bodies;
		this.strTimer = "Time: " + time;
	}

	public List<Wall> getWalls() {
		return this.walls;
	}
}
