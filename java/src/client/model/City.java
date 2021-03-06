package client.model;

/**
 * A class for a city object in a Settlers of Catan game.
 * @author djoshuac
 */
public class City implements Municipality {
	/**
	 * The number of resources produced when the number on one of this city's hexes is rolled
	 */
	public static final int RESOURCES_PRODUCED_PER_ROLL = 2;
	
	private int ownerIndex;
	private VertexLocation location;
	
	public City(int ownerIndex, VertexLocation location) {
		setOwnerIndex(ownerIndex);
		setLocation(location);
	}

	private void setOwnerIndex(int ownerIndex) {
		this.ownerIndex = ownerIndex;
	}
	
	private void setLocation(VertexLocation location) {
		this.location = location;
	}

	@Override
	public int getOwnerIndex() {
		return ownerIndex;
	}
	
	@Override
	public VertexLocation getLocation() {
		return location;
	}

	@Override
	public int getResourcesProducedPerRoll() {
		return RESOURCES_PRODUCED_PER_ROLL;
	}
}
