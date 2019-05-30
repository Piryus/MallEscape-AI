package re.legend.crowd_simulator.entities.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class FemaleShop extends GameObject{
	public static final int SIZE = 16;

	public FemaleShop(float x, float y) {
		super(x, y, 0);
	}
	
	public FemaleShop(Vector2 position) {
		super(position, 0);
	}
}
