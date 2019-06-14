package re.legend.crowd_simulator.entities.gameobjects;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

public class Bomb extends GameObject {

	private UUID uuid;
	
	public Bomb(float x, float y) {
		super(x, y, 0);
		this.uuid = UUID.randomUUID();
	}
	
	public Bomb(Vector2 position) {
		super(position, 0);
		this.uuid = UUID.randomUUID();
	}
	
	public UUID getUuid() {
		return this.uuid;
	}

}
