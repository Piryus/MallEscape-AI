package re.legend.crowd_simulator.bodies;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import java.util.UUID;


public abstract class AgentBody extends SimulationObject {
	
	// Body agent's ID
	private UUID agentId;
	
	//Course p290 for agent movements
	//orientation
	Quaternion orientation;
	
	//Manage the linear and angular speed 
	Vector2 linearVelocity;
	Quaternion angularVelocity;
	
	//var perception;
	//var influences;
	
	
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
}
