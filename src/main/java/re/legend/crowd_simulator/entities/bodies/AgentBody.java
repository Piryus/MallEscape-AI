package re.legend.crowd_simulator.entities.bodies;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

import re.legend.crowd_simulator.entities.SimulationEntity;
import re.legend.crowd_simulator.frustum.EntityFrustum;
import re.legend.crowd_simulator.influence.Influence;
import re.legend.crowd_simulator.influence.MotionInfluence;


public abstract class AgentBody extends SimulationEntity {
	
	// Body agent's ID
	private UUID agentId;
	
	//Manage the linear and angular speed 
	private Vector2 linearVelocity;
	private float angularVelocity;
	
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
	public AgentBody(float x, float y, float orientation, UUID id) {
		super(x, y, orientation);
		this.agentId = id;
		this.influences = new ArrayList<>();
	}
	
	/**
	 * Constructor with body's position (vector2) and UUID
	 */
	public AgentBody(Vector2 position, float orientation, UUID id) {
		super(position, orientation);
		this.agentId = id;
	}
	
	/**
	 * @return the uuid of the agent
	 */
	public UUID getUuid() {
		return this.agentId;
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
	
	/**
	 * @return the objects perceived by the body
	 */
	public List<SimulationEntity> getPerceivedObjects() {
		return this.perceivedObjects;
	}
	
	/**
	 * @return the bodies perceived by this body
	 */
	public List<AgentBody> getPerceivedBodies() {
		return this.perceivedBodies;
	}
	
	/**
	 * @param influence the influence to add to the body
	 */
	public void addInfluence(Influence influence) {
		this.influences.add(influence);
	}
	
	/**
	 * @return all the influences of the body
	 */
	public List<Influence> getInfluences() {
		return this.influences;
	}
	
	/**
	 * @return all the motion influences of the body
	 */
	public List<MotionInfluence> getMotionInfluences() {
		List<MotionInfluence> motionInfluences = new ArrayList<>();
		for (Influence influence : this.influences) {
			if (influence instanceof MotionInfluence) {
				motionInfluences.add((MotionInfluence) influence);
			}
		}
		return motionInfluences;
	}
	
	/**
	 * Clear the list of influences
	 */
	public void clearInfluences() {
		this.influences.clear();
	}
}
