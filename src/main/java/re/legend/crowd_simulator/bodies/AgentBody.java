package re.legend.crowd_simulator.bodies;

import com.badlogic.gdx.math.Vector2;
import java.util.UUID;


public abstract class AgentBody {
	
	// Body agent's ID
	private UUID agentId;
	// Body's position
	private Vector2 position;
	
	/**
	 * Constructor with body's position (two floats) and UUID
	 */
	public AgentBody(float x, float y, UUID id) {
		this.position = new Vector2(x, y);
		this.agentId = id;
	}
	
	/**
	 * Constructor with body's position (vector2) and UUID
	 */
	public AgentBody(Vector2 position, UUID id) {
		this.position = position;
		this.agentId = id;
	}
	
	/**
	 * Sets the body position
	 * @param x the position of the body on the x axis
	 * @param y the position of the body on the y axis
	 */
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}
	
	/**
	 * Return body's position
	 * @return the position of the body
	 */
	public Vector2 getPosition() {
		return this.position;
	}
	
	/**
	 * @return the uuid of the agent
	 */
	public UUID getUuid() {
		return this.agentId;
	}
}
