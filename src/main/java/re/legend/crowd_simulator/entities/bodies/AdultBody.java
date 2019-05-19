package re.legend.crowd_simulator.entities.bodies;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

public class AdultBody extends AgentBody {
	
	private Sex sex;
	
	public AdultBody(float x, float y, float orientation, UUID id) {
		super(x, y, orientation, id);
	}
	
	public AdultBody(Vector2 position, float orientation, UUID id) {
		super(position, orientation, id);
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
}
