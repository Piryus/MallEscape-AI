package re.legend.crowd_simulator.entities;

import com.badlogic.gdx.math.Vector2;


public abstract class SimulationEntity implements Perceivable {
	
	// Entity's position
	protected Vector2 position;
	
	// Entity's orientation
	private float orientation;
	
	
	/**
	 * Constructor with position using a vector and orientation
	 * @param position the entity's position
	 */
	public SimulationEntity(Vector2 position, float orientation) {
		this.position = position;
		this.orientation = orientation;
	}
	
	/**
	 * Constructor with position and orientation using two floats
	 * @param x the entity's position on the x axis
	 * @param y the entity's position on the y axis
	 */
	public SimulationEntity(float x, float y, float orientation) {
		this(new Vector2(x, y), orientation);
	}
	
	/**
	 * Sets the entity's position
	 * @param x the position of the body on the x axis
	 * @param y the position of the body on the y axis
	 */
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}
	
	/**
	 * Return entity's position
	 * @return the position of the body
	 */
	public Vector2 getPosition() {
		return this.position;
	}
	
	/**
	 * Move the body of the passed x and y value
	 * @param x the x axis value the body should be moved of
	 * @param y the y axis value the body should be moved of
	 */
	public void moveOf(float x, float y) {
		this.setPosition(this.position.x + x, this.position.y + y);
	}
	
	/**
	 * Move the body of the passed linear motion vector values
	 * @param linearMotionVec
	 */
	public void moveOf(Vector2 linearMotionVec) {
		this.setPosition(this.position.x + linearMotionVec.x, this.position.y + linearMotionVec.y);
	}
}
