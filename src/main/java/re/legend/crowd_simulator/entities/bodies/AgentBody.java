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

	// Manage the linear and angular speed
	private Vector2 linearVelocity;
	private float angularVelocity;

	// Max velocity of the agent
	public static final float MAX_VELOCITY = 20;

	// Max force of the agent
	public static final float MAX_FORCE = 10;

	// Max distance at which the agent can perceive other bodies
	public static final float PERCEPTION_DISTANCE = 50;

	// Coordinates of the target to reach
	private Vector2 target;

	// Desired velocity - force towards the target
	private Vector2 desiredVelocity;

	// Steering force (desired velocity - current velocity)
	private Vector2 steering;

	// Body's perception frustum
	private EntityFrustum frustum;

	// Other bodies perceived by this body
	private List<AgentBody> perceivedBodies;

	// Objects perceived by this body
	private List<SimulationEntity> perceivedObjects;

	// Body's influences
	private List<Influence> influences;

	private Vector2 ahead;
	private Vector2 ahead2;
	private Vector2 avoidance;

	/**
	 * Constructor with body's position (two floats) and UUID
	 */
	public AgentBody(float x, float y, float orientation, UUID id) {
		super(x, y, orientation);
		this.agentId = id;
		this.influences = new ArrayList<>();
		this.linearVelocity = new Vector2();
		this.perceivedBodies = new ArrayList<>();
		this.perceivedObjects = new ArrayList<>();
	}

	/**
	 * Constructor with body's position (vector2) and UUID
	 */
	public AgentBody(Vector2 position, float orientation, UUID id) {
		this(position.x, position.y, orientation, id);
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
	public Vector2 addVector2(Vector2 position, Vector2 linearVelocity) {
		return new Vector2(position.x + linearVelocity.x, position.y + linearVelocity.y);
	}

	/**
	 * Move with the linear velocity
	 */
	public Vector2 moveWithVelocity(Vector2 linearVelocity) {
		return addVector2(this.getPosition(), linearVelocity);
		// with deltaT
		// return (addVector2(this.getPosition(), linearVelocity))/2*deltaT
	}

	/**
	 * @return the perception frustum of the body
	 */
	public EntityFrustum getFrustum() {
		return this.frustum;
	}

	/**
	 * Sets which objects and which bodies are perceived by this body
	 * 
	 * @param bodies  the other bodies perceived by the body
	 * @param objects the objects perceived by the body
	 */
	public void setPerceptions(List<AgentBody> bodies, List<SimulationEntity> objects) {
		synchronized (this.perceivedBodies) {
			this.perceivedBodies = bodies;
		}
		synchronized (this.perceivedObjects) {
			this.perceivedObjects = objects;
		}
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
		synchronized (this.influences) {
			this.influences.add(influence);
		}
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
		if (this.influences != null && !this.influences.isEmpty()) {
			synchronized (this.influences) {
				for (Influence influence : this.influences) {
					if (influence instanceof MotionInfluence) {
						motionInfluences.add((MotionInfluence) influence);
					}
				}
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

	/**
	 * @return the linearVelocity
	 */
	public Vector2 getLinearVelocity() {
		return this.linearVelocity;
	}

	/**
	 * @param linearVelocity the linearVelocity to set
	 */
	public void setLinearVelocity(Vector2 linearVelocity) {
		this.linearVelocity = linearVelocity;
	}

	/**
	 * @return the angularVelocity
	 */
	public float getAngularVelocity() {
		return this.angularVelocity;
	}

	/**
	 * @param angularVelocity the angularVelocity to set
	 */
	public void setAngularVelocity(float angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	/**
	 * @return the target
	 */
	public Vector2 getTarget() {
		return this.target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(Vector2 target) {
		this.target = target;
	}

	/**
	 * @return the desiredVelocity
	 */
	public Vector2 getDesiredVelocity() {
		return this.desiredVelocity;
	}

	/**
	 * @param desiredVelocity the desiredVelocity to set
	 */
	public void setDesiredVelocity(Vector2 desiredVelocity) {
		this.desiredVelocity = desiredVelocity;
	}

	/**
	 * @return the steering
	 */
	public Vector2 getSteering() {
		return this.steering;
	}

	/**
	 * @param steering the steering to set
	 */
	public void setSteering(Vector2 steering) {
		this.steering = steering;
	}

	public void seek() {
		float slowDownDistance = 100f;
		float stopDistance = 10f;

		// Computes the desired velocity towards the target
		this.desiredVelocity = this.target.cpy().sub(this.position);
		// Gets the distance to the target
		float distance = this.desiredVelocity.len();
		// Normalizes and scale to max velocity the desired velocity
		this.desiredVelocity.nor().scl(MAX_VELOCITY);

		// On arrival, slows down the agent
		if (distance <= slowDownDistance) {
			this.desiredVelocity.scl(distance / slowDownDistance);
		} else if (distance <= stopDistance) {
			this.desiredVelocity.scl(0);
		}

		// Computes the steering force
		this.steering = this.desiredVelocity.cpy().sub(this.linearVelocity);
		// TODO Make the force depends on the mass (sex dependent ?)
	}

	public void avoidCollisionWithBodies() {
		// The ahead vector is the velocity vector with the PERCEPTION_DISTANCE length
		float dynamicLength = this.linearVelocity.len() / MAX_VELOCITY;
		this.ahead = this.position.cpy().add(this.linearVelocity.cpy().nor()).scl(dynamicLength);
		this.ahead2 = this.ahead.cpy().scl(0.5f);

		// Find the most threatening body's position
		Vector2 bodyToAvoidPosition = findMostThreateningBody();

		// Computes the avoidance force
		this.avoidance = this.ahead.cpy();
		if (bodyToAvoidPosition != null) {
			this.avoidance.sub(bodyToAvoidPosition);
		} else {
			this.avoidance.scl(0);
		}

		// Adds the avoidance force to the steering
		this.steering.add(this.avoidance);
//		float i = MAX_FORCE / this.steering.len();
//		i = i < 1.0f ? 1.0f : i;
//		this.steering.scl(i);
	}

	public void computesVelocity() {
		// Computes the new velocity of the agent
		this.linearVelocity.add(this.steering);
//		float i = MAX_VELOCITY / this.linearVelocity.len();
//		i = i < 1.0f ? 1.0f : i;
//		this.linearVelocity.scl(i);
	}

	private boolean lineIntersectsBodyCircle(Vector2 ahead, Vector2 ahead2, Vector2 bodyPosition) {
		return distance(bodyPosition, ahead) <= 10 || distance(bodyPosition, ahead2) <= 10 || distance(bodyPosition, this.position) <= 10;
	}

	private static double distance(Vector2 obj1, Vector2 obj2) {
		return Math.sqrt((obj1.x - obj2.x) * (obj1.x - obj2.x) + (obj1.y - obj2.y) * (obj1.y - obj2.y));
	}

	private Vector2 findMostThreateningBody() {
		Vector2 mostThreateningBodyPos = null;
		synchronized (this.perceivedBodies) {
			if (this.perceivedBodies != null && !this.perceivedBodies.isEmpty()) {
				for (AgentBody body : this.perceivedBodies) {
					boolean collisionWithBody = lineIntersectsBodyCircle(this.ahead, this.ahead2, body.position);
					if (collisionWithBody && (mostThreateningBodyPos == null || distance(this.position,
							body.position) < distance(this.position, mostThreateningBodyPos))) {
						mostThreateningBodyPos = body.position;
					}
				}
			}
		}
		return mostThreateningBodyPos;
	}
}
