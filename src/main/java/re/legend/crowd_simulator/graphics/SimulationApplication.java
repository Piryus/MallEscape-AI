package re.legend.crowd_simulator.graphics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import re.legend.crowd_simulator.bodies.AdultBody;
import re.legend.crowd_simulator.bodies.AgentBody;

public class SimulationApplication extends ApplicationAdapter implements InputProcessor, MapListener {
	OrthographicCamera camera;
	Matrix4 matrix;

	private TiledMap map;
	private TmxMapLoader loader;
	private OrthogonalTiledMapRenderer renderer;
	
	// Cursor position on last click
	Vector2 lastTouch;
	
	// Agent bodies to render, updated by the update() method
	List<AgentBody> bodies;
	
	// TODO Remove below once bodies rendering is implemented
	// John's texture
	private Texture johnTex;
	private SpriteBatch spriteBatch;
	
	@Override
	public void create() {
		// Attributes instantiation
		this.bodies = new ArrayList<>();
		this.matrix = new Matrix4();
		this.lastTouch = new Vector2();
		this.spriteBatch = new SpriteBatch();
		this.johnTex = new Texture("john.png");
		
		// Loads map
		this.loader = new TmxMapLoader();
		this.map = this.loader.load("map/map.tmx");
		this.renderer = new OrthogonalTiledMapRenderer(map);
		
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/5);
		this.camera.position.set(((int) map.getProperties().get("width") * (int) map.getProperties().get("tilewidth")) / 2, ((int) map.getProperties().get("height") * (int) map.getProperties().get("tileheight")) / 2, 0);

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose() {
		this.spriteBatch.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor((float)97/255, (float)133/255, (float)248/255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.camera.update();		
				
		// Renders map
		this.renderer.setView(this.camera);
		this.renderer.render();
		
		this.spriteBatch.setProjectionMatrix(camera.combined);
		this.spriteBatch.begin();
		for (AgentBody body : this.bodies) {
			if (body instanceof AdultBody) {
				this.spriteBatch.draw(this.johnTex, body.getPosition().x, body.getPosition().y);
			}
		}
		this.spriteBatch.end();
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
	public boolean touchDragged (int x, int y, int pointer) {
		Vector2 currentPos = new Vector2(x, y);
		// Compute vector between lastTouch and current position of the cursor
		Vector2 delta = currentPos.cpy().sub(this.lastTouch);
		// Translate the camera
		this.camera.translate((float) - (delta.x * 0.005), (float) (delta.y * 0.005));
		return false;
	}

	@Override 
	public boolean touchUp(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public void update(List<AgentBody> bodies) {
		this.bodies = bodies;
	}
}
