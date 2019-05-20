package re.legend.crowd_simulator.graphics;

import java.util.List;

import re.legend.crowd_simulator.entities.bodies.AgentBody;

/**
 *
 */
public interface MapListener {
	
	/**
	 * 
	 */
	void update(List<AgentBody> bodies, float time);
}
