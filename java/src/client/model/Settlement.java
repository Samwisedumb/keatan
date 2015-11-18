package client.model;

public class Settlement {
	private int owner;
	
	public Settlement(int ownerId) {
		setOwner(ownerId);
	}
	
	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public boolean validate(int numPlayers, int radius) {
		if (owner < 0 || owner > numPlayers - 1) return false;
		return true;
	}
	
}
