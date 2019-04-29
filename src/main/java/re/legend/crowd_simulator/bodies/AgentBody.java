package re.legend.crowd_simulator.bodies;

import com.badlogic.gdx.math.Vector2;
import java.util.UUID;


public abstract class AgentBody extends SimulationObject {
	
	// Body agent's ID
	private UUID agentId;
	
	/**
	 * Constructor with body's position (two floats) and UUID
	 */
	public AgentBody(float x, float y, UUID id) {
		super(x, y);
		this.agentId = id;
	}
	
	/**
	 * Constructor with body's position (vector2) and UUID
	 */
	public AgentBody(Vector2 position, UUID id) {
		super(position);
		this.agentId = id;
	}
	
	/**
	 * @return the uuid of the agent
	 */
	public UUID getUuid() {
		return this.agentId;
	}
}
