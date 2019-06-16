package re.legend.crowd_simulator.entities.bodies;

import java.util.Random;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

public class AdultBody extends AgentBody {
	
	private Sex sex;
	
	public AdultBody(float x, float y, float orientation, UUID id) {
		// Calls super constructor
		super(x, y, orientation, id);
		
		// Randomizes the sex of the agent
		Random rand = new Random();
		if (rand.nextBoolean() == true) {
			this.sex = Sex.male;
		} else {
			this.sex = Sex.female;
		}
		
	}
	
	public AdultBody(Vector2 position, float orientation, UUID id) {
		super(position, orientation, id);
	}

	public Sex getSex() {
		return this.sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
}
