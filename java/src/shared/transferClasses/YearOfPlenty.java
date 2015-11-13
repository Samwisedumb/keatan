package shared.transferClasses;

import shared.definitions.ResourceType;

/**
 * Plays a 'Year of Plenty' card from your hand to gain the two specified resources.
 * @param playerIndex the player playing the card
 * @param resourceOne the first resource affected
 * @parma resourceTwo the second resource affected
 * @author willvdb
 *
 */

public class YearOfPlenty {

	public YearOfPlenty(int playerIndex, ResourceType resourceOne, ResourceType resourceTwo) {
		this.playerIndex = playerIndex;
		this.resourceOne = resourceOne;
		this.resourceTwo = resourceTwo;
	}

	private String type = "Year_of_Plenty";
	private int playerIndex;
	private ResourceType resourceOne;
	private ResourceType resourceTwo;

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public ResourceType getResourceOne() {
		return resourceOne;
	}

	public void setResourceOne(ResourceType resourceOne) {
		this.resourceOne = resourceOne;
	}

	public ResourceType getResourceTwo() {
		return resourceTwo;
	}

	public void setResourceTwo(ResourceType resourceTwo) {
		this.resourceTwo = resourceTwo;
	}

}
