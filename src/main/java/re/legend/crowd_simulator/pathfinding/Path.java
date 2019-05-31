package re.legend.crowd_simulator.pathfinding;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * This simple class represents a path made of nodes that an agent can follow
 */
public class Path {
	
	// List of nodes of the path
	private List<Vector2> nodes;
	
	/**
	 * Default constructor (empty path)
	 */
	public Path() {
		this.nodes = new ArrayList<>();
	}
	
	/**
	 * Constructor with existing nodes
	 * @param nodes the nodes of the path
	 */
	public Path(List<Vector2> nodes) {
		this.nodes = nodes;
	}
	
	/**
	 * Add a node to the path
	 * @param node the node to add to the path
	 */
	public void addNode(Vector2 node) {
		this.nodes.add(node);
	}
	
	/**
	 * @return the nodes of the path
	 */
	public List<Vector2> getNodes() {
		return this.nodes;
	}
	
	/**
	 * @param node the node number on the path
	 * @return the node (vector2) at the given position on the path
	 */
	public Vector2 getNode(int node) {
		return this.nodes.get(node);
	}
	
	/**
	 * @return the number of nodes in the path
	 */
	public int length() {
		return this.nodes.size();
	}
}
