package shared.transferClasses;

/**
 * Plays a 'Monument' card from your hand to give you a victory point
 * @param playerIndex the player to get the victory point
 * @author willvdb
 *
 */

public class Monument {

	public Monument(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	private String type = "Monument";
	private int playerIndex;

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
}
