package re.legend.crowd_simulator.bodies;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class Map {
	
	Random random;
	// TreeMap containing the bodies on the map
	TreeMap<UUID, AgentBody> bodies;
	// List of walls on the map
	List<Wall> walls;
	
	/**
	 * Default constructor
	 */
	public Map() {
		this.random = new Random();
		this.bodies = new TreeMap<>();
		this.walls = new ArrayList<>();
		//loadWalls();
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
		float x = this.random.nextInt(200);
		float y = this.random.nextInt(200);
		
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
	
	public void loadWalls() {
		TmxMapLoader loader = new TmxMapLoader();
		TiledMap map = loader.load("map/map.tmx");
		OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map);
		TiledMapTileLayer wallsLayer = (TiledMapTileLayer) renderer.getMap().getLayers().get("walls");
		Cell cell;
		for (int x = 0; x < wallsLayer.getWidth(); x++) {
			for (int y = 0; y < wallsLayer.getHeight(); y++) {
				if ((cell = wallsLayer.getCell(x, y)) != null) {
					walls.add(new Wall(x,y));
				}
			}
		}
	}
	
	public Collection<AgentBody> getBodies() {
		return this.bodies.values();
	}
	
	public void setBodyAt(AgentBody body, float x, float y) {
		body.setPosition(x, y);
	}
	 
}
