package re.legend.crowd_simulator.bodies;

import com.badlogic.gdx.math.Vector2;
import java.util.UUID;


public class AdultBody extends AgentBody {

	public AdultBody(float x, float y, UUID id) {
		super(x, y, id);
	}
	
	public AdultBody(Vector2 position, UUID id) {
		super(position, id);
	}
	
}
