package shared.transferClasses;

import shared.definitions.ResourceType;

/**
 * Plays a 'Monopoly' card from your hand to monopolize the specified resource
 * @param playerIndex the player playing the card
 * @param resource the resource being monopolized
 * @author willvdb
 *
 */

public class Monopoly {

	public Monopoly(int playerIndex, ResourceType sheep) {
		this.playerIndex = playerIndex;
		this.resource = sheep;
	}

	private String type = "Monopoly";
	private int playerIndex;
	private ResourceType resource;

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public ResourceType getResource() {
		return resource;
	}

	public void setResourceOne(ResourceType resource) {
		this.resource = resource;
	}

}
