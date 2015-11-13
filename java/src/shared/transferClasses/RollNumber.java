package shared.transferClasses;

/**
 * This class represents a roll of the dice by a given player
 * @param playerIndex the player doing the action
 * @param number the number the player rolled
 * @author willvdb
 *
 */

public class RollNumber{

	public RollNumber(int playerIndex, int number) {
		this.number = number;
		this.playerIndex = playerIndex;
	}

	private String type = "rollNumber";
	private int playerIndex;
	private int number;

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
