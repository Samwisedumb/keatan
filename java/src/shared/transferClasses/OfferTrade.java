package shared.transferClasses;

import client.model.ResourceList;


/**
 * Offers a domestic trade to another player.
 * @param playerIndex the player offering the trade
 * @param offer the resources being offered
 * @param reciever the player recieving the trade
 * @author willvdb
 *
 */

public class OfferTrade {

	public OfferTrade(int playerIndex, ResourceList offer, int reciever) {
		this.playerIndex = playerIndex;
		this.offer = offer;
		this.reciever = reciever;
	}

	private String type = "offerTrade";
	private int playerIndex;
	private ResourceList offer;
	private int reciever;

	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public ResourceList getOffer() {
		return offer;
	}
	public void setOffer(ResourceList offer) {
		this.offer = offer;
	}
	public int getReciever() {
		return reciever;
	}
	public void setReciever(int reciever) {
		this.reciever = reciever;
	}


}
