package re.legend.crowd_simulator.entities.gameobjects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Shop {
	
	// The shop's ID (starting with a M for a male shop and with a F for a female shop)
	private String id;
	
	// The area of the shop
	private Polygon area;
	
	// Entrances positions
	private List<Vector2> entrances;
	
	// Shop position
	private Vector2 position;

	/**
	 * Shop constructor
	 * @param id the shop id
	 * @param areaVertices the vertices of the polygon representing the shop's area
	 */
	public Shop(String id, float[] areaVertices, float x, float y) {
		this.entrances = new ArrayList<>();
		// Sets the ID
		this.id = id;
		// Initializes the area with the vertices
		this.area = new Polygon(areaVertices);
		this.area.setPosition(x, y);
		// Initializes position
		this.position = new Vector2(x, y);
	}
	
	/**
	 * @return the shop's ID
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * @return the shop's area
	 */
	public Polygon getArea() {
		return this.area;
	}
	
	/**
	 * Add an entrance to the shop
	 * @param x 
	 * @param y
	 */
	public void addEntrance(float x, float y) {
		this.entrances.add(new Vector2(x, y));
	}
	
	public List<Vector2> getEntrances() {
		return this.entrances;
	}
	
	public Vector2 getPosition() {
		return this.position;
	}
}
