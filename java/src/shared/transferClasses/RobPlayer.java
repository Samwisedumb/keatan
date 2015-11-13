package shared.transferClasses;

import client.model.HexLocation;

/**
 * This class moves the robber, selecting the new robber position and player to rob.
 * @param playerIndex the player moving the robber
 * @param victimIndex the person to steal from
 * @param location the location to move the robber to
 * @author willvdb
 *
 */

public class RobPlayer {

	public RobPlayer(int playerIndex, int victimIndex, HexLocation location){
		this.playerIndex = playerIndex;
		this.victimIndex = victimIndex;
		this.location = location;
	}

	private String type = "robPlayer";
	private int playerIndex;
	private int victimIndex;
	private HexLocation location;

	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public int getVictimIndex() {
		return victimIndex;
	}
	public void setVictimIndex(int victimIndex) {
		this.victimIndex = victimIndex;
	}
	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}
}
