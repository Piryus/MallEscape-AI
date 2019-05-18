package re.legend.crowd_simulator.influence;

import org.lwjgl.util.vector.Vector2f;

import com.badlogic.gdx.math.Vector2;

import re.legend.crowd_simulator.entities.bodies.AgentBody;

public class MotionInfluence extends Influence {
	// The body that that emitted this influence
	private AgentBody body;
	// The linear motion the agent wants to make
	private Vector2 linearMotion;
	// The angle at which the agent wants to rotate
	private float angularMotion;

	/**
	 * Constructor with linear and angular motion
	 * @param emitter the emitter of the influence
	 * @param linearMotion the vector of the motion
	 * @param angle the angle of the motion
	 */
	public MotionInfluence(AgentBody emitter, Vector2 linearMotion, float angle) {
		super(emitter);
		this.body = emitter;
		this.linearMotion = linearMotion;
		this.angularMotion = angle;
	}


	/**
	 * Constructor for an angular motion
	 * @param emitter the emitter of the influence
	 * @param angle the angle of the motion
	 */
	public MotionInfluence(AgentBody emitter, float angle) {
		super(emitter);
		this.body = emitter;
		this.linearMotion = new Vector2();
		this.angularMotion = angle;
	}

	/**
	 * Constructor for a linear motion
	 * @param emitter the emitter of the influence
	 * @param linearMotion the vector of the motion
	 */
	public MotionInfluence(AgentBody emitter, Vector2 linearMotion) {
		super(emitter);
		this.body = emitter;
		this.linearMotion = linearMotion;
		this.angularMotion = 0f;
	}


	/**
	 * Constructor for a still 'motion'
	 * @param emitter the emitter of the influence
	 */
	public MotionInfluence(AgentBody emitter) {
		super(emitter);
		this.body = emitter;
		this.linearMotion = new Vector2();
		this.angularMotion = 0f;
	}

	/**
	 * TO DO? Constructor when an object influence translation and rotation
	 * 
	 * @param emitter
	 * @param simObject
	 * @param linearMotion
	 * @param angularMotion
	 * 
	 *                      public MotionInfluence(AgentBody emitter,
	 *                      SimulationObject simObject, Vector2f linearMotion, float
	 *                      angularMotion) { //Test //assert (linearMotion != null);
	 *                      //assert (object != null); super(emitter);
	 * 
	 *                      this.agentB = simObject; this.linearMotion =
	 *                      linearMotion; this.angularMotion = angularMotion; }
	 */

	/**
	 * @return the moved body
	 */
	public AgentBody getAgentBody() {
		return this.body;
	}

	/**
	 * @return the linear motion to apply
	 */
	public Vector2 getLinearMotion() {
		return this.linearMotion;
	}

	/**
	 * @return the angular motion to apply
	 */
	public float getAngularMotion() {
		return this.angularMotion;
	}

	/**
	 * @param linearMotion the linear motion
	 */
	public void setLinearMotion(Vector2 linearMotion) {
		this.linearMotion = linearMotion;
	}

	/**
	 * 
	 * @param angularMotion the rotation
	 */
	public void setLinearMotion(float angularMotion) {
		this.angularMotion = angularMotion;
	}

	/**
	 * @return true if the influence has a linear motion
	 */
	public boolean hasLinearMotion() {
		// lengthSquared for Vector2f value
		return this.linearMotion != null && this.linearMotion.len2() != 0f;
	}

	/**
	 * @return true if the influence has a rotation
	 */
	public boolean hasAngularMotion() {
		return this.angularMotion != 0f;
	}
}
