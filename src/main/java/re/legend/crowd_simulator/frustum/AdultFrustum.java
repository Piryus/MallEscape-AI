package re.legend.crowd_simulator.frustum;

import re.legend.crowd_simulator.frustum.EntityFrustum;

/**
 * Test frustum (square)
 */
public class AdultFrustum extends EntityFrustum {
	// Side length
	private final int length;
	
	/**
	 * Constructor
	 * @param length the length of the square side
	 */
	public AdultFrustum(int length) {
		this.length = length;
	}
	
	@Override
	public int getSideLength() {
		return this.length;
	}
}
