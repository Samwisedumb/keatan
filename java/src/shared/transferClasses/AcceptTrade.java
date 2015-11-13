package shared.transferClasses;

/**
 * Used to accept or reject a trade offered to you
 * @param playerIndex the player accepting/rejecting the trade
 * @param willAccept whether or not they'll accept it
 * @author willvdb
 *
 */

public class AcceptTrade {

	public AcceptTrade(int playerIndex, Boolean willAccept) {
		this.playerIndex = playerIndex;
		this.willAccept = willAccept;
	}

	private String type = "acceptTrade";
	private int playerIndex;
	private Boolean willAccept;

	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public Boolean getWillAccept() {
		return willAccept;
	}
	public void setWillAccept(Boolean willAccept) {
		this.willAccept = willAccept;
	}

}
