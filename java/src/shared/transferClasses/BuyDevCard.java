package shared.transferClasses;

/**
 * Class used to buy a dev card
 * @param playerIndex the player buying
 * @author willvdb
 *
 */

public class BuyDevCard {

	public BuyDevCard(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	private String type = "buyDevCard";
	private int playerIndex;

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}
