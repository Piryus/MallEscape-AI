package re.legend.crowd_simulator.objects;

import com.badlogic.gdx.math.Vector2;


public abstract class SimulationObject implements Perceivable {
	// Object's position
	private Vector2 position;
	
	public SimulationObject(float x, float y) {
		this.position = new Vector2();
		this.position.x = x;
		this.position.y = y;
	}
	
	public SimulationObject(Vector2 position) {
		this.position = new Vector2();
		this.position = position;
	}
	
	/**
	 * Sets the object's position
	 * @param x the position of the body on the x axis
	 * @param y the position of the body on the y axis
	 */
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}
	
	/**
	 * Return object's position
	 * @return the position of the body
	 */
	public Vector2 getPosition() {
		return this.position;
	}
}
