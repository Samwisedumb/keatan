package client.model;

/**
 * Contains information about the current turn
 */
public class TurnTracker {
	private int currentTurn;
	private int currentPlayer;
	private Status status;
	private int longestRoad;
	private int largestArmy;

	/**
	 * @param currentTurn The current turn (usually 0)
	 * @param status
	 * @param longestRoad The size of the current longest road
	 * @param largestArmy The size of the current largest army
	 * @post The objects internal values are set to the given params
	 */
	public TurnTracker(int currentTurn, int currentPlayer, int longestRoad, int largestArmy) {
		this.currentTurn = currentTurn;
		this.currentPlayer = currentPlayer;
		this.longestRoad = longestRoad;
		this.largestArmy = largestArmy;
		this.status = Status.FirstRound;
	}
	
	/**
	 * @pre The player whose turn it is has ended their turn.
	 * @post The next player gains control; if applicable, the overall turn number increments.
	 */
	public void endPlayerTurn() {
		if(currentPlayer == 3) {
			currentPlayer = 0;
		}
		else {
			currentPlayer++;
		}
	}
	
	public int getCurrentTurn() {
		return currentTurn;
	}
	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	public int getLongestRoad() {
		return longestRoad;
	}
	public void setLongestRoad(int longestRoad) {
		this.longestRoad = longestRoad;
	}
	public int getLargestArmy() {
		return largestArmy;
	}
	public void setLargestArmy(int largestArmy) {
		this.largestArmy = largestArmy;
	}
	public void setPlayerTurn(int playerIndex) {
		currentPlayer = playerIndex;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}
