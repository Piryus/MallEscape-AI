package re.legend.crowd_simulator.influence;

import org.lwjgl.util.vector.Vector2f;

import re.legend.crowd_simulator.entities.bodies.AgentBody;

public class MotionInfluence extends Influence {
	// The body to move
	private AgentBody body;
	private Vector2f linearMotion;
	private float angularMotion;

	/**
	 * Main Constructor
	 * 
	 * @param emitter       is the influencer
	 * @param linearMotion  is the linear motion to apply is the number of cells to
	 *                      traverse.
	 * @param angularMotion is the rotational motion to apply.
	 */
	public MotionInfluence(AgentBody emitter, Vector2f linearMotion, float angularMotion) {
		super(emitter);
		// Test
		// assert (linearMotion!=null);
		// assert (emitter !=null);

		this.body = emitter;
		this.linearMotion = linearMotion;
		this.angularMotion = angularMotion;
	}

	/**
	 * Constructor when the agent only rotate
	 * 
	 * @param emitter
	 * @param angularMotion
	 */
	public MotionInfluence(AgentBody emitter, float angularMotion) {
		super(emitter);
		// Test
		// assert (emitter != null);
		this.body = emitter;
		this.linearMotion = new Vector2f();
		this.angularMotion = angularMotion;
	}

	/**
	 * Constructor when the agent only go forward
	 * 
	 * @param emitter
	 * @param linearMotion
	 */
	public MotionInfluence(AgentBody emitter, Vector2f linearMotion) {
		// Test
		// assert (emitter != null);
		// asser (linearMotion != null);
		super(emitter);
		this.body = emitter;
		this.linearMotion = linearMotion;
		this.angularMotion = 0f;
	}

	/**
	 * Constructor if the agent do not move
	 * 
	 * @param emitter
	 */
	public MotionInfluence(AgentBody emitter) {
		// Test
		// assert (emitter != null);
		super(emitter);
		this.body = emitter;
		this.linearMotion = new Vector2f();
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
	public Vector2f getLinearMotion() {
		return this.linearMotion;
	}

	/**
	 * @return the angular motion to apply
	 */
	public float getAngularMotion() {
		return this.angularMotion;
	}

	/**
	 * 
	 * @param linearMotion the linear motion
	 */
	public void setLinearMotion(Vector2f linearMotion) {
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
	 * 
	 * @return true if the influence has a linear motion
	 */
	public boolean hasLinearMotion() {
		// lengthSquared for Vector2f value
		return this.linearMotion != null && this.linearMotion.lengthSquared() != 0f;
	}

	/**
	 * 
	 * @return true if the influence has a rotation
	 */
	public boolean hasAngularMotion() {
		return this.angularMotion != 0f;
	}
}
