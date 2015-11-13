package client.model;


public class TradeOffer {
	private int sender; // The index of the person offering the trade
	private int receiver; // The index of the person the trade is offered to
	private ResourceList offer; // Positive=offered, Negative=requested

	public TradeOffer(int sender, int receiver, ResourceList offer) {
		this.sender = sender;
		this.receiver = receiver;
		this.offer = offer;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public ResourceList getOffer() {
		return offer;
	}

	public void setOffer(ResourceList offer) {
		this.offer = offer;
	}
}
