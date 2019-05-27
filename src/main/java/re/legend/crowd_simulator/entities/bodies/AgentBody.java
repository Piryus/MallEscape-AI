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
	public static final float MAX_VELOCITY = 15;

	// Max force of the agent
	public static final float MAX_FORCE = 30;

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
		this.ahead = new Vector2();
		this.ahead2 = new Vector2();
		this.avoidance = new Vector2();
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
	 * @return the angularVelocity
	 */
	public float getAngularVelocity() {
		return this.angularVelocity;
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

	public void seek() {
		// Distance from the target at which the agent should start slowing down
		float slowDownDistance = 100f;
		// Distance at which it should stop
		float stopDistance = 10f;

		// Computes the desired velocity towards the target
		this.desiredVelocity = this.target.cpy().sub(this.position);
		// Gets the distance to the target
		float distance = this.desiredVelocity.len();
		// Normalizes and scale to the desired velocity the maximum velocity 
		this.desiredVelocity.nor().scl(MAX_VELOCITY);

		// On arrival, slows down the agent
		if (distance <= slowDownDistance) {
			this.desiredVelocity.scl(distance / slowDownDistance);
		} else if (distance <= stopDistance) {
			// If the agent is within the "stop circle", scale the desired velocity to 0
			this.desiredVelocity.scl(0);
		}

		// Computes the steering force
		this.steering = this.desiredVelocity.cpy().sub(this.linearVelocity);
		// TODO Make the force depends on the mass (sex dependent ?)
	}

	public void avoidCollisionWithBodies() {
		// The ahead vector is the velocity vector with the PERCEPTION_DISTANCE length
		this.ahead = this.position.cpy().add(this.linearVelocity.nor().scl(PERCEPTION_DISTANCE));
		this.ahead2 = this.position.cpy().add(this.linearVelocity.nor().scl(PERCEPTION_DISTANCE * 0.5f));

		// We could also use a dynamic length as shown below
//		 float dynamicLength = this.linearVelocity.len() / MAX_VELOCITY; 
//		 this.ahead = this.position.cpy().add(this.linearVelocity.cpy().nor()).scl(dynamicLength);
//		 this.ahead2 = this.ahead.cpy().scl(0.5f);

		// Find the most threatening body's position
		Vector2 bodyToAvoidPosition = findMostThreateningBodyPosition();

		// Computes the avoidance force depending on the position of the most
		// threatening body found
		// If no body was found, the avoidance force is simply null
		if (bodyToAvoidPosition != null) {
			this.avoidance = this.ahead.cpy().sub(bodyToAvoidPosition).scl(MAX_FORCE).nor().scl(MAX_FORCE);
			// Recovers normal speed
//			avoidance.scl(1/MAX_FORCE);
		} else {
			this.avoidance.scl(0);
		}

		// Adds the avoidance force to the steering
		this.steering.add(this.avoidance);

		// TODO Inspect the code below, is it useful ?
//		float i = MAX_FORCE / this.steering.len();
//		i = i < 1.0f ? 1.0f : i;
//		this.steering.scl(i);
	}

	public void computesVelocity() {
		// Computes the new velocity of the agent
		this.linearVelocity.add(this.steering);

		// TODO Inspect the code below, is it useful ?
//		float i = MAX_VELOCITY / this.linearVelocity.len();
//		i = i < 1.0f ? 1.0f : i;
//		this.linearVelocity.scl(i);
	}

	private boolean lineIntersectsBodyCircle(Vector2 bodyPosition) {
		if (distance(bodyPosition, this.ahead) <= 50 || distance(bodyPosition, this.ahead2) <= 25
				|| distance(bodyPosition, this.position) <= 60) {
			// System.out.println("Collision found.");
			return true;
		}
		return false;
	}

	private static double distance(Vector2 obj1, Vector2 obj2) {
		return Math.sqrt((obj1.x - obj2.x) * (obj1.x - obj2.x) + (obj1.y - obj2.y) * (obj1.y - obj2.y));
	}

	private Vector2 findMostThreateningBodyPosition() {
		Vector2 mostThreateningBodyPos = null;
		synchronized (this.perceivedBodies) {
			if (this.perceivedBodies != null && !this.perceivedBodies.isEmpty()) {
				// Loop through the perceived bodies of the agent
				for (AgentBody body : this.perceivedBodies) {
					// Checks if the agent's ahead vectors collide with the perceived body
					boolean collisionWithBody = lineIntersectsBodyCircle(body.position);
					if (collisionWithBody && (mostThreateningBodyPos == null || distance(this.position,
							body.position) < distance(this.position, mostThreateningBodyPos))) {
						mostThreateningBodyPos = body.position;
					}
				}
			}
		}
		return mostThreateningBodyPos;
	}

	/* To rewrite */
	private SimulationEntity findMostThreateningBody2() {
		SimulationEntity mostThreateningBodyPos = null;
		synchronized (this.perceivedBodies) {
			if (this.perceivedBodies != null && !this.perceivedBodies.isEmpty()) {
				for (AgentBody body : this.perceivedBodies) {
					boolean collisionWithBody = lineIntersectsBodyCircle(body.position);
					if (collisionWithBody && (mostThreateningBodyPos == null || distance(this.position,
							body.position) < distance(this.position, mostThreateningBodyPos.getPosition()))) {
						mostThreateningBodyPos = body;
					}
				}
			}
		}
		return mostThreateningBodyPos;
	}

}
