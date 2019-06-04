package re.legend.crowd_simulator.behaviors;

public enum AdultState {
	/**
	 * The agent is going to a shop
	 */
	WALKING_TO_SHOP,
	
	/**
	 * The adult is getting some products.
	 */
	PICK_UP_PRODUCT,
	
	/**
	 * The adult is evacuating to the exit.
	 */
	EVACUATING,
	
	/**
	 * The adult is waiting 
	 */
	WAITING;
}
