package re.legend.crowd_simulator.influence;

import re.legend.crowd_simulator.bodies.AgentBody;



public abstract class Influence 
{
	private AgentBody emitter;
	
	/**
	 * constructor
	 * @param emitter of the influence
	 */
	public Influence(AgentBody emitter)
	{
		this.emitter = emitter;
	}
	
	/**
	 * @return the agent that emitted an influence
	 */
	public AgentBody getEmitter()
	{
		return this.emitter;
	}
	
	/**
	 * 
	 * @param emitter to set
	 */
	public void setEmitter(AgentBody emitter)
	{
		this.emitter = emitter;
	}
	
	//Return the UUID in string
	public String toString()
	{
		return this.emitter == null ? "" : this.emitter.getUuid().toString();
	}
}
