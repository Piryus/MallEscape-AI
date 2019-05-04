package re.legend.crowd_simulator.entities.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Wall extends GameObject {
	
	public static final int SIZE = 16;

	public Wall(float x, float y) {
		super(x, y);
	}
	
	public Wall(Vector2 position) {
		super(position);
	}

}
