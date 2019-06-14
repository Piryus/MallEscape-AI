package re.legend.crowd_simulator.entities.gameobjects;

import com.badlogic.gdx.math.Vector2;
import re.legend.crowd_simulator.entities.SimulationEntity;

public class GameObject extends SimulationEntity {
	
	public GameObject(float x, float y, float orientation) {
		super(x, y, orientation);
	}
	
	public GameObject(Vector2 position, float orientation) {
		super(position, orientation);
	}

}
