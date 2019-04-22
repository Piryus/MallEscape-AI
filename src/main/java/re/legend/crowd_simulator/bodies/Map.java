package re.legend.crowd_simulator.bodies;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;


public class Map {
	
	Random random;
	// TreeMap containing the bodies on the map
	TreeMap<UUID, AgentBody> bodies;
	
	/**
	 * Default constructor
	 */
	public Map() {
		this.random = new Random();
		this.bodies = new TreeMap<>();
	}

	/**
	 * Create a body 
	 * @param bodyType
	 * @param agentUUID
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public <T extends AgentBody> T createBody(Class<T> bodyType, UUID agentUUID) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// Body position
		float x = this.random.nextInt(2000);
		float y = this.random.nextInt(2000);
		
		// Agent's ID
		UUID id = agentUUID;
		if (agentUUID == null) {
			id = UUID.randomUUID();
		}
		
		// Create body instance
		Constructor cons = bodyType.getDeclaredConstructor(float.class, float.class, UUID.class);
		Object body = cons.newInstance(x, y, id);

		// Put the body into the tree map
		this.bodies.put(id, (AgentBody) body);

		return (T) body;
	}
	
	public Collection<AgentBody> getBodies() {
		return this.bodies.values();
	}
	
	public void setBodyAt(AgentBody body, float x, float y) {
		body.setPosition(x, y);
	}
	 
}
