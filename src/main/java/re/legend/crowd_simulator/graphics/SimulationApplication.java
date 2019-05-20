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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
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

	// TODO Remove below once bodies rendering is implemented
	// John's texture
	private Texture johnTex;

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
	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;

	@Override
	public void create() {
		// Attributes instantiation
		this.bodies = new ArrayList<>();
		this.lastTouch = new Vector2();
		this.spriteBatch = new SpriteBatch();
		this.fixedSpriteBatch = new SpriteBatch();
		this.johnTex = new Texture("john.png");
		this.walls = new ArrayList<>();

		// Timer creation and stamp the startTimer
		this.strTimer = "Time: 0";
		this.fontTimer = new BitmapFont();
		this.timerLayout = new GlyphLayout();

		// Loads map
		this.loader = new TmxMapLoader();
		this.map = this.loader.load("map/map.tmx");
		this.renderer = new OrthogonalTiledMapRenderer(map);
		this.mapWidth = (int) map.getProperties().get("width") * (int) map.getProperties().get("tilewidth");
		this.mapHeight = (int) map.getProperties().get("height") * (int) map.getProperties().get("tileheight");

		// Loads walls
		TiledMapTileLayer wallsLayer = (TiledMapTileLayer) renderer.getMap().getLayers().get("Walls");
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
		camera.translate(-deltaX, deltaY);
		camera.update();
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
