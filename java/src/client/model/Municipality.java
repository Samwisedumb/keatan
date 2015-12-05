package client.model;

/**
 * An interface for municipality objects.
 * A municipality should have an owner index, a VertexLocation, and a number of resources produced per roll
 * @author djoshuac
 */
public interface Municipality {
	/**
	 * @return the index of the player who owns the municipality
	 */
	int getOwnerIndex();
	
	/**
	 * @return a normalized vertex location of where this municipality is
	 */
	VertexLocation getLocation();
	
	/**
	 * @return the number of resources this municipality produces when one of it's hexes is rolled
	 */
	int getResourcesProducedPerRoll();
}
