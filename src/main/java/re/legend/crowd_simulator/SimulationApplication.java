package re.legend.crowd_simulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import io.sarl.bootstrap.SRE;

public class SimulationApplication extends ApplicationAdapter implements InputProcessor {
	Texture grassTexture;
	OrthographicCamera camera;
	SpriteBatch batch;
	Sprite[][] sprites = new Sprite[10][10];
	Matrix4 matrix = new Matrix4();

	// Cursor position on last click
	Vector2 lastTouch = new Vector2();
	
	@Override
	public void create() {
		this.grassTexture = new Texture("grass.png");
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth()/50, Gdx.graphics.getHeight()/50);
		this.camera.position.set(5, 5, 10);
		
		for (int z = 0; z < 10; z++) {
			for (int x = 0; x < 10; x++) {
				this.sprites[x][z] = new Sprite(this.grassTexture);
				this.sprites[x][z].setPosition(x, z);
				this.sprites[x][z].setSize(1, 1);
			}
		}

		this.batch = new SpriteBatch();

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose() {
		this.batch.dispose();
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
				
		this.batch.setProjectionMatrix(this.camera.combined);
		this.batch.begin();
		for(int z = 0; z < 10; z++) {
			for(int x = 0; x < 10; x++) {
				this.sprites[x][z].draw(this.batch);
			}
		}
		this.batch.end();
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
		try {
			SRE.getBootstrap().startAgent(MapAgent.class); //Start an agent
			//SRE.getBootstrap().startAgent(AdultAgent.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
}
