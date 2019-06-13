package re.legend.crowd_simulator.entities.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Bomb extends GameObject {

	public Bomb(float x, float y) {
		super(x, y, 0);
	}
	
	public Bomb(Vector2 position) {
		super(position, 0);
	}

}
