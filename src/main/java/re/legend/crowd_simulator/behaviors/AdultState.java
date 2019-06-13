package re.legend.crowd_simulator.behaviors;

public enum AdultState {
	/**
	 * The agent is going to a shop
	 */
	WALKING_TO_SHOP,
	
	/**
	 * The adult is shopping the store
	 */
	SHOPPING,
	
	/**
	 * The adult is evacuating to the exit
	 */
	EVACUATING,
	
	/**
	 * The adult is evacuating the shop
	 */
	EVACUATING_SHOP,
	
	/**
	 * The adult is waiting 
	 */
	WAITING;
}
