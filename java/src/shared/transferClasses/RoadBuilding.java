package shared.transferClasses;

import client.model.EdgeLocation;


/**
 * This plays the road building card, which actually builds two roads.
 * @param playerIndex the player building the two roads
 * @param spotOne location for the first road
 * @param spotTwo the location for the second road
 * @author willvdb
 */

public class RoadBuilding {

	public RoadBuilding(int playerIndex, EdgeLocation spotOne, EdgeLocation spotTwo) {
		this.playerIndex = playerIndex;
		this.spotOne = spotOne;
		this.spotTwo = spotTwo;
	}

	private String type = "Road_Building";
	private int playerIndex;
	private EdgeLocation spotOne;
	private EdgeLocation spotTwo;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public EdgeLocation getSpotOne() {
		return spotOne;
	}
	public void setSpotOne(EdgeLocation spotOne) {
		this.spotOne = spotOne;
	}
	public EdgeLocation getSpotTwo() {
		return spotTwo;
	}
	public void setSpotTwo(EdgeLocation spotTwo) {
		this.spotTwo = spotTwo;
	}
}
