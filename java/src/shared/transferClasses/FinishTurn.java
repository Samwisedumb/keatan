package shared.transferClasses;

/**
 * Class used to finish your turn
 * @param playerIndex the player finishing his turn
 * @author willvdb
 *
 */

public class FinishTurn {

	public FinishTurn(int playerIndex){
		this.playerIndex = playerIndex;
	}

	private String type = "finishTurn";
	private int playerIndex;

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}
