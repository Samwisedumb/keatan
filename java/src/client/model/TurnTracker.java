package client.model;

/**
 * Contains information about the current turn
 */
public class TurnTracker {
	private int currentPlayer;
	private Status status;
	private int longestRoad;
	private int largestArmy;

	/**
	 * @param currentPlayer The index of the player who's turn it is
	 * @param status The status of the game
	 * @param longestRoad The size of the current longest road
	 * @param largestArmy The size of the current largest army
	 * @post The object's internal values are set to the given parameters
	 */
	public TurnTracker(int currentPlayer, int longestRoad, int largestArmy) {
		this.currentPlayer = currentPlayer;
		this.longestRoad = longestRoad;
		this.largestArmy = largestArmy;
		this.status = Status.FirstRound;
	}
	
	/**
	 * @pre The player whose turn it is has ended their turn.
	 * @post The next player gains control
	 */
	public void endPlayerTurn() {
		if(currentPlayer == 3) {
			currentPlayer = 0;
		}
		else {
			currentPlayer++;
		}
		
		this.status = Status.Rolling;
	}
	
	/**
	 * @return The index of the player whose turn it is
	 */
	public int getPlayerTurn() {
		return currentPlayer;
	}
	
	/**
	 * @return The length of the longest road
	 */
	public int getLongestRoadLength() {
		return longestRoad;
	}
	
	/**
	 * @param longestRoad - The length of the longest road
	 * @post the longesRoad is set
	 */
	public void setLongestRoadLength(int longestRoad) {
		this.longestRoad = longestRoad;
	}
	
	/**
	 * @return The size of the largest army
	 */
	public int getLargestArmySize() {
		return largestArmy;
	}
	
	/**
	 * @param largestArmy - The size of the largest army
	 * @post The size of the largest army is set
	 */
	public void setLargestArmySize(int largestArmy) {
		this.largestArmy = largestArmy;
	}
	
	public void setPlayerTurn(int playerIndex) {
		currentPlayer = playerIndex;
	}
	
	/**
	 * @return The status of the game
	 */
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
