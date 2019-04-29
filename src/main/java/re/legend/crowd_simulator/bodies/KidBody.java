package re.legend.crowd_simulator.bodies;
import com.badlogic.gdx.math.Vector2;
import java.util.UUID;


public class KidBody extends AgentBody {

	public KidBody(float x, float y, UUID id) {
		super(x, y, id);
	}
	
	public KidBody(Vector2 position, UUID id) {
		super(position, id);
	}
	
}
