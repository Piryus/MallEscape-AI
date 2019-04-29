package re.legend.crowd_simulator.bodies;
import com.badlogic.gdx.math.Vector2;
import java.util.UUID;


public class DogBody extends AgentBody {

	public DogBody(float x, float y, UUID id) {
		super(x, y, id);
	}
	
	public DogBody(Vector2 position, UUID id) {
		super(position, id);
	}
	
}
