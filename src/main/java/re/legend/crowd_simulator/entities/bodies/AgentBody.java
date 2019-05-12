package re.legend.crowd_simulator.entities.bodies;

import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;

import re.legend.crowd_simulator.entities.SimulationEntity;
import re.legend.crowd_simulator.frustum.EntityFrustum;
import re.legend.crowd_simulator.influence.Influence;


public abstract class AgentBody extends SimulationEntity {
	
	// Body agent's ID
	private UUID agentId;
	
	//Course p290 for agent movements
	//orientation
	private Quaternion orientation;
	
	//Manage the linear and angular speed 
	private Vector2 linearVelocity;
	private Quaternion angularVelocity;
	
	// Body's perception frustum
	private EntityFrustum frustum;
	
	// Other bodies perceived by this body
	private List<AgentBody> perceivedBodies;
	
	// Objects perceived by this body
	private List<SimulationEntity> perceivedObjects;
	
	// Body's influences
	private List<Influence> influences;
	
	/**
	 * Constructor with body's position (two floats) and UUID
	 */
	public AgentBody(float x, float y, Quaternion orientation, UUID id) {
		super(x, y);
		this.orientation = orientation;
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
	
	/**
	 * @return the orientation of the agent
	 */
	public Quaternion getOrientation()
	{
		return this.orientation;
	}
	
	/**
	 * @param orientation
	 */
	public void setOrientation(Quaternion o)
	{
		this.orientation = o;
	}
	
	/**
	 * Add 2 Vector2
	 */
	public Vector2 addVector2(Vector2 position, Vector2 linearVelocity)
	{
		return new Vector2(position.x + linearVelocity.x, position.y + linearVelocity.y);
	}
	/**
	 * Move with the linear velocity
	 */
	public Vector2 moveWithVelocity(Vector2 linearVelocity)
	{
		return addVector2(this.getPosition(), linearVelocity);
		//with deltaT
		//return (addVector2(this.getPosition(), linearVelocity))/2*deltaT
	}
	
	/**
	 * Add 2 Quaternion, maybe overload a new Quaternion class
	 */
	public Quaternion addQuaternion(Quaternion orientation, Quaternion angularVelocity)
	{
		//return new Quaternion(this.orientation.x+angularVelocity.x, this.orientation.y+angularVelocity.y this.orientation.z+angularVelocity.z this.orientation.w+angularVelocity.w);
		
		return new Quaternion(null, this.orientation.x+angularVelocity.x);
	}
	
	/**
	 * Rotate with the angular velocity
	 */
	public Quaternion rotateWithVelocity(Quaternion angularVelocity)
	{
		return addQuaternion(this.orientation, angularVelocity);
	}
	
	/**
	 * @return the perception frustum of the body
	 */
	public EntityFrustum getFrustum() {
		return this.frustum;
	}
	
	/**
	 * Sets which objects and which bodies are perceived by this body
	 * @param bodies the other bodies perceived by the body
	 * @param objects the objects perceived by the body
	 */
	public void setPerceptions(List<AgentBody> bodies, List<SimulationEntity> objects) {
		this.perceivedBodies = bodies;
		this.perceivedObjects = objects;
	}
}
