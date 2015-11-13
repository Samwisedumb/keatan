package shared.transferClasses;

import shared.definitions.ResourceType;

/**
 * Used to execute a maritime trade
 * @param playerIndex the player executing the trade
 * @param ratio The ratio of the trade your doing as an integer (ie. put 3 for a 3:1 trade)
 * @param inputResource the resource provided by the player
 * @param outputResource the resource returned to the player
 * @author willvdb
 *
 */

public class MaritimeTrade {

	public MaritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output) {
		this.playerIndex = playerIndex;
		this.ratio = ratio;
		this.inputResource = input;
		this.outputResource = output;
	}

	private String type = "maritimeTrade";	
	private int playerIndex;
	private int ratio;
	private ResourceType inputResource;
	private ResourceType outputResource;

	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public int getRatio() {
		return ratio;
	}
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	public ResourceType getInputResource() {
		return inputResource;
	}
	public void setInputResource(ResourceType inputResource) {
		this.inputResource = inputResource;
	}
	public ResourceType getOutputResource() {
		return outputResource;
	}
	public void setOutputResource(ResourceType outputResource) {
		this.outputResource = outputResource;
	}

}
