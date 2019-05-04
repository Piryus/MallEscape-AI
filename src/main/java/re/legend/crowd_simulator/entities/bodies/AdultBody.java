package re.legend.crowd_simulator.entities.bodies;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import java.util.UUID;

public class AdultBody extends AgentBody {
	
	private Sex sex;
	public AdultBody(float x, float y, Quaternion o, UUID id) {
		super(x, y, o, id);
	}
	
	public AdultBody(Vector2 position, UUID id) {
		super(position, id);
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
}
