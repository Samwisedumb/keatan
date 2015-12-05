package client.model;

public class Road {
	private int ownerIndex;
	private EdgeLocation location;
	
	public Road(int ownerIndex, EdgeLocation location) {
		setOwnerIndex(ownerIndex);
		setLocation(location);
	}
	public Road() {
		
	}

	public int getOwnerIndex() {
		return ownerIndex;
	}

	public void setOwnerIndex(int ownerIndex) {
		this.ownerIndex = ownerIndex;
	}

	public EdgeLocation getLocation() {
		return location;
	}

	public void setLocation(EdgeLocation location) {
		this.location = location;
	}
}
