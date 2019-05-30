package re.legend.crowd_simulator.graphics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTextButton;

import re.legend.crowd_simulator.entities.SimulationEntity;
import re.legend.crowd_simulator.entities.bodies.AdultBody;
import re.legend.crowd_simulator.entities.bodies.AgentBody;
import re.legend.crowd_simulator.entities.gameobjects.FemaleShop;
import re.legend.crowd_simulator.entities.gameobjects.MaleShop;
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
	private List<FemaleShop> femaleShop;
	private List<MaleShop> maleShop;
	
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
	private String strTimer;
	private BitmapFont fontTimer;
	private GlyphLayout timerLayout;

	// Font generator and parameter
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;

	// Shape renderer
	private ShapeRenderer shapeRenderer;
	
	// Stage
	private Stage stage;
	private VisTextButton startButton;
	
	// Input multiplexer (used to interact with both the UI and the map)
	private InputMultiplexer inputMultiplexer; 

	@Override
	public void create() {
		// Attributes instantiation
		this.bodies = new ArrayList<>();
		this.lastTouch = new Vector2();
		this.spriteBatch = new SpriteBatch();
		this.fixedSpriteBatch = new SpriteBatch();
		this.walls = new ArrayList<>();
		this.maleShop = new ArrayList();
		this.femaleShop = new ArrayList();
		this.shapeRenderer = new ShapeRenderer();

		// Loads bodies textures
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
		this.map = this.loader.load("map/map2.tmx");
		this.renderer = new OrthogonalTiledMapRenderer(map);
		this.mapWidth = (int) map.getProperties().get("width") * (int) map.getProperties().get("tilewidth");
		this.mapHeight = (int) map.getProperties().get("height") * (int) map.getProperties().get("tileheight");

		// Loads walls
		TiledMapTileLayer wallsLayer = (TiledMapTileLayer) this.renderer.getMap().getLayers().get("Walls");
		for (int x = 0; x < wallsLayer.getWidth(); x++) {
			for (int y = 0; y < wallsLayer.getHeight(); y++) {
				if ((wallsLayer.getCell(x, y)) != null) {
					this.walls.add(new Wall(x * Wall.SIZE, y * Wall.SIZE));
				}
			}
		}
		
		// Loads female shops
		TiledMapTileLayer femaleShopLayer = (TiledMapTileLayer) this.renderer.getMap().getLayers().get("FemaleShop");
		for (int x = 0; x < femaleShopLayer.getWidth(); x++) {
			for (int y = 0; y < femaleShopLayer.getHeight(); y++) {
				if ((femaleShopLayer.getCell(x, y)) != null) {
					this.femaleShop.add(new FemaleShop(x * FemaleShop.SIZE, y * FemaleShop.SIZE));
				}
			}
		}
		
		// Loads male shops
		TiledMapTileLayer maleShopLayer = (TiledMapTileLayer) this.renderer.getMap().getLayers().get("MaleShop");
		for (int x = 0; x < maleShopLayer.getWidth(); x++) {
			for (int y = 0; y < maleShopLayer.getHeight(); y++) {
				if ((maleShopLayer.getCell(x, y)) != null) {
					this.maleShop.add(new MaleShop(x * MaleShop.SIZE, y * MaleShop.SIZE));
				}
			}
		}

		// Test Access to an object
		MapLayer layer = map.getLayers().get(0);
		MapObject way = layer.getObjects().get("Way");

		// Get Access to female/male shop on map2
		/*
		 * MapLayer layer2 = map.getLayers().get("Walls");
		 * 
		 * MapObject femaleShop= layer2.getObjects().get("ShopFObject"); MapObject
		 * maleShop= layer2.getObjects().get("ShopMObject"); MapObject road =
		 * layer2.getObjects().get("Road");
		 */

		// Sets camera properties
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 5);
		this.camera.position.set(this.mapWidth / 2, this.mapHeight / 2, 0);

		// Initializes font generator and parameter for the timer text
		this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/coolvetica.ttf"));
		this.parameter = new FreeTypeFontParameter();
		this.parameter.size = 30;
		
		// Stage initialization
		VisUI.load();
		this.stage = new Stage(new ScreenViewport());	
//		this.startButton = new VisTextButton("Start");
//		this.startButton.setPosition((Gdx.graphics.getWidth() - this.startButton.getWidth())/2, (Gdx.graphics.getHeight() - this.startButton.getHeight())/2);
//		this.stage.addActor(this.startButton);

		// Adds input processors
		this.inputMultiplexer = new InputMultiplexer();
		this.inputMultiplexer.addProcessor(this);
		this.inputMultiplexer.addProcessor(this.stage);
		Gdx.input.setInputProcessor(this.inputMultiplexer);
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
		// Sets background color
		Gdx.gl.glClearColor((float) 97 / 255, (float) 133 / 255, (float) 248 / 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		this.camera.update();

		// Renders map
		this.renderer.setView(this.camera);
		this.renderer.render();

		// Renders the bodies on the map
		this.spriteBatch.setProjectionMatrix(this.camera.combined);
		this.spriteBatch.begin();
		for (AgentBody body : this.bodies) {
			if (body instanceof AdultBody) {
				if (body.getLinearVelocity().angle() > 315f || body.getLinearVelocity().angle() <= 45f) {
					this.spriteBatch.draw(this.adultStillRight, body.getPosition().x - 8, body.getPosition().y);
				} else if (body.getLinearVelocity().angle() > 45f && body.getLinearVelocity().angle() <= 135f) {
					this.spriteBatch.draw(this.adultStillBack, body.getPosition().x - 8, body.getPosition().y);
				} else if (body.getLinearVelocity().angle() > 135f && body.getLinearVelocity().angle() <= 225f) {
					this.spriteBatch.draw(this.adultStillLeft, body.getPosition().x - 8, body.getPosition().y);
				} else if (body.getLinearVelocity().angle() > 225f && body.getLinearVelocity().angle() <= 315f) {
					this.spriteBatch.draw(this.adultStillFace, body.getPosition().x - 8, body.getPosition().y);
				}
			}
		}
		this.spriteBatch.end();

		// Renders the forces applied on the agents
		this.shapeRenderer.setProjectionMatrix(this.camera.combined);
		this.shapeRenderer.begin(ShapeType.Line);
		for (SimulationEntity wall : this.walls)
		{
			if (wall instanceof SimulationEntity)
			{
				this.shapeRenderer.setColor(1, 1, 1, 1); // White
				//this.shapeRenderer.circle(wall.getPosition().x+8, wall.getPosition().y+8, 12);//Global
				//Bottom
				/*
				this.shapeRenderer.circle(wall.getPosition().x+4, wall.getPosition().y+5, 6);
				this.shapeRenderer.circle(wall.getPosition().x+8, wall.getPosition().y+5, 6);
				this.shapeRenderer.circle(wall.getPosition().x+12, wall.getPosition().y+5, 6);
				
				//Middle
				this.shapeRenderer.circle(wall.getPosition().x+4, wall.getPosition().y+8, 6);
				this.shapeRenderer.circle(wall.getPosition().x+8, wall.getPosition().y+8, 6);
				this.shapeRenderer.circle(wall.getPosition().x+12, wall.getPosition().y+8, 6);
				
				//Top
				this.shapeRenderer.circle(wall.getPosition().x+4, wall.getPosition().y+11, 6);
				this.shapeRenderer.circle(wall.getPosition().x+8, wall.getPosition().y+11, 6);
				this.shapeRenderer.circle(wall.getPosition().x+12, wall.getPosition().y+11, 6);*/
			}
		}
		for (AgentBody body : this.bodies) {
			if (body instanceof AdultBody) {
				/*
				// Agent's private circle
				this.shapeRenderer.setColor(0, 0, 1, 1); // Blue
				this.shapeRenderer.circle(body.getPosition().x, body.getPosition().y, 10);
				// Agent's ahead vector
				this.shapeRenderer.setColor(1, 1, 1, 1); // White
				this.shapeRenderer.line(body.getPosition().x, body.getPosition().y, body.getAhead().x,
						body.getAhead().y);
				// Agent's ahead2 vector
				// this.shapeRenderer.setColor(1, 0, 0, 1);
				// this.shapeRenderer.line(body.getPosition().x, body.getPosition().y,
				// body.getAhead2().x, body.getAhead2().y);
				// Agent's velocity vector
				this.shapeRenderer.setColor(0, 1, 0, 1); // Green
				this.shapeRenderer.line(body.getPosition().x, body.getPosition().y,
						body.getPosition().x + body.getLinearVelocity().x,
						body.getPosition().y + body.getLinearVelocity().y);
				// Agent's avoidance vector
				this.shapeRenderer.setColor(0.5f, 0, 0.5f, 1); // Purple
				this.shapeRenderer.line(body.getPosition().x, body.getPosition().y,
						body.getPosition().x + body.getAvoidance().x, body.getPosition().y + body.getAvoidance().y);
				// Agent's desired velocity vector
				this.shapeRenderer.setColor(1, 0, 0, 1); // Red
				this.shapeRenderer.line(body.getPosition().x, body.getPosition().y,
						body.getPosition().x + body.getDesiredVelocity().x,
						body.getPosition().y + body.getDesiredVelocity().y);
						*/
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
		
		// Stage rendering
		float delta = Gdx.graphics.getDeltaTime();
		this.stage.act(delta);
		this.stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		this.stage.getViewport().update(width, height, true);
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
		// Manages camera zoom
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
	
	public List<FemaleShop> getFemaleShop() {
		return this.femaleShop;
	}
	
	public List<MaleShop> getMaleShop() {
		return this.maleShop;
	}
}
