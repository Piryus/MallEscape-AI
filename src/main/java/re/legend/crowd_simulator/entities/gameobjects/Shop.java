package re.legend.crowd_simulator.entities.gameobjects;

import com.badlogic.gdx.math.Polygon;

public class Shop {
	
	// The shop's ID (starting with a M for a male shop and with a F for a female shop)
	private String id;
	
	// The area of the shop
	private Polygon area;

	/**
	 * Shop constructor
	 * @param id the shop id
	 * @param areaVertices the vertices of the polygon representing the shop's area
	 */
	public Shop(String id, float[] areaVertices) {
		// Sets the ID
		this.id = id;
		
		// Initializes the area with the vertices
		this.area = new Polygon(areaVertices);
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
}
