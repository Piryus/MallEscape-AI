package re.legend.crowd_simulator.entities.gameobjects;

public class Shop extends GameObject {
	
	private float height;
	
	private float width;

	// "F" for female shop and "M" for Male shop
	private final String type;

	public Shop(float x, float y, float height, float width, String type) {
		super(x, y, 0);
		this.height = height;
		this.width = width;
		this.type = type;
	}

	public float y() {
		return this.position.x;
	}

	public float x() {
		return this.position.y;
	}

	public float height() {
		return this.height;
	}

	public float width() {
		return this.width;
	}
	
	public String getType() {
		return this.type;
	}
}
