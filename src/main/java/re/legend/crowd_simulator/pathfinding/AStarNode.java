package re.legend.crowd_simulator.pathfinding;

import com.badlogic.gdx.math.Vector2;

public class AStarNode extends Vector2 {

	// Total cost of the node
	public float f;

	// Distance between the current node and the start node
	public float g;

	// Euclidian distance between the current node and the end node (heuristic)
	public float h;

	// Parent node, useful to find the complete path
	public AStarNode parent;

	public AStarNode() {
		super();
		this.parent = null;
	}

	public AStarNode(Vector2 vec2) {
		super(vec2);
		this.parent = null;
	}

	public AStarNode(float x, float y) {
		super(x, y);
		this.parent = null;
	}

	public AStarNode(Vector2 vec2, float f, float g, float h) {
		super(vec2);
		this.f = f;
		this.g = g;
		this.h = h;
		this.parent = null;
	}

	public AStarNode(float x, float y, float f, float g, float h) {
		super(x, y);
		this.f = f;
		this.g = g;
		this.h = h;
		this.parent = null;
	}
}
