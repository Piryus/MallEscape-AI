package re.legend.crowd_simulator.influence;

import org.lwjgl.util.vector.Vector2f;

import re.legend.crowd_simulator.bodies.AgentBody;


public class MotionInfluence extends Influence
{
	private AgentBody agentB;
	private Vector2f linearMotion;
	private float angularMotion;
	
	/**
	 * @param emitter is the influencer
	 * @param linearMotion is the linear motion to apply
	 * is the number of cells to traverse.
	 * @param angularMotion is the rotational motion to apply.
	 */
	public MotionInfluence(AgentBody emitter, Vector2f linearMotion, float angularMotion)
	{
		super(emitter);
		//Test
		//assert (linearMotion!=null);
		//assert (emitter !=null);
		
		this.agentB = emitter;
		this.linearMotion = linearMotion;
		this.angularMotion = angularMotion;
	}
	
	/**
	 * @return the moved body
	 */
	public AgentBody getAgentBody()
	{
		return this.agentB;
	}
	
	/**
	 * @return the linear motion to apply
	 */
	public Vector2f getLinearMotion()
	{
		return this.linearMotion;
	}
	
	/**
	 * @return the angular motion to apply
	 */
	public float getAngularMotion()
	{
		return this.angularMotion;
	}
	
	/**
	 * 
	 * @param linearMotion the linear motion
	 */
	public void setLinearMotion(Vector2f linearMotion)
	{
		this.linearMotion=linearMotion;
	}
	
	/**
	 * 
	 * @param angularMotion the rotation
	 */
	public void setLinearMotion(float angularMotion)
	{
		this.angularMotion=angularMotion;
	}
}
