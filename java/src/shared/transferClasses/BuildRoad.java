package shared.transferClasses;

import client.model.EdgeLocation;


/**
 * This class builds a road at the specified location
 * @param playerIndex the player building the road
 * @param roadLocation the location to build
 * @param free - whether the road si free or not
 * @author willvdb
 *
 */

public class BuildRoad {
	private String type = "buildRoad";
	private int playerIndex;
	private EdgeLocation roadLocation;
	private Boolean free;


	public BuildRoad(int playerIndex, EdgeLocation roadLocation, Boolean free) {
		this.playerIndex = playerIndex;
		this.roadLocation = roadLocation;
		this.free = free;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public EdgeLocation getRoadLocation() {
		return roadLocation;
	}
	public void setRoadLocation(EdgeLocation roadLocation) {
		this.roadLocation = roadLocation;
	}
	public Boolean getFree() {
		return free;
	}
	public void setFree(Boolean free) {
		this.free = free;
	}

}
