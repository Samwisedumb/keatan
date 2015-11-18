package client.model;

public class City {
	private int owner;
	
	public City(int ownerId) {
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
