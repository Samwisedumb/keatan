package client.model;

public class Road {
	private int owner;
	private EdgeLocation location;
	
	public Road(int ownerId, EdgeLocation location) {
		setOwner(ownerId);
		setLocation(location);
	}
	public Road() {
		
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public EdgeLocation getLocation() {
		return location;
	}

	public void setLocation(EdgeLocation location) {
		this.location = location;
	}
	
	
	public boolean validate(int numPlayers, int radius) {
		if (owner < 0 || owner > numPlayers - 1) return false;
		if (location == null || !location.validate(radius)) return false;
		return true;
	}
}
