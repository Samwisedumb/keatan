package shared.transferClasses;

import client.model.VertexLocation;


/**
 * This class builds a city at the specified location
 * @param playerIndex the player building the road
 * @param vertexLocation the location to build
 * @param free whether or not the building of this is free
 * @author willvdb
 *
 */

public class BuildCity {

	public BuildCity(int playerIndex, VertexLocation vertexLocation, Boolean free) {
		this.playerIndex = playerIndex;
		this.vertexLocation = vertexLocation;
		this.free = free;
	}

	private String type = "buildCity";
	private int playerIndex;
	private VertexLocation vertexLocation;
	private Boolean free;

	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public VertexLocation getSpotOne() {
		return vertexLocation;
	}
	public void setSpotOne(VertexLocation vertexLocation) {
		this.vertexLocation = vertexLocation;
	}
	public Boolean getFree() {
		return free;
	}
	public void setFree(Boolean free) {
		this.free = free;
	}

}
