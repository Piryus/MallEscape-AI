package re.legend.crowd_simulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class SimulationApplication extends ApplicationAdapter implements InputProcessor {
	Texture grassTexture;
	OrthographicCamera camera;
	SpriteBatch batch;
	Sprite[][] sprites = new Sprite[10][10];
	Matrix4 matrix = new Matrix4();
	final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();

	@Override
	public void create() {
		this.grassTexture = new Texture("grass.png");
		this.camera = new OrthographicCamera(10, 10 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
		this.camera.position.set(5, 5, 10);
		this.camera.direction.set(-1, -1, -1);
		this.camera.near = 1;
		this.camera.far = 100;
		this.matrix.setToRotation(new Vector3(1, 0, 0), 90);
		
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
		this.batch.setTransformMatrix(this.matrix);
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
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override 
	public boolean touchDragged (int x, int y, int pointer) {
		Ray pickRay = this.camera.getPickRay(x, y);
		Intersector.intersectRayPlane(pickRay, this.xzPlane, this.curr);
		
		if(!(this.last.x == -1 && this.last.y == -1 && this.last.z == -1)) {
			pickRay = this.camera.getPickRay(this.last.x, this.last.y);
			Intersector.intersectRayPlane(pickRay, this.xzPlane, this.delta);			
			this.delta.sub(this.curr);
			this.camera.position.add(this.delta.x, this.delta.y, this.delta.z);
		}
		this.last.set(x, y, 0);
		return false;
	}

	@Override 
	public boolean touchUp(int x, int y, int pointer, int button) {
		this.last.set(-1, -1, -1);
		return false;
	}
}
