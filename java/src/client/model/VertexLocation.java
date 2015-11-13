package client.model;

import shared.definitions.EdgeDirection;
import shared.definitions.VertexDirection;

/**
 * Represents the location of a vertex on a hex map
 */
public class VertexLocation {
	private int x;
	private int y;
	private VertexDirection direction;
	
	public VertexLocation(int x, int y, VertexDirection direction) {
		setX(x);
		setY(y);
		setDirection(direction);
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public VertexDirection getDirection() {
		return direction;
	}
	
	public void setDirection(VertexDirection direction) {
		this.direction = direction;
	}
	
	public HexLocation getHexLocation() {
		return new HexLocation(x, y);
	}
	
	/**
	 * Returns a canonical (i.e., unique) value for this vertex location. Since
	 * each vertex has three different locations on a map, this method converts
	 * a vertex location to a single canonical form. This is useful for using
	 * vertex locations as map keys.
	 * 
	 * @return Normalized vertex location
	 */
	public VertexLocation getNormalizedLocation() {
		HexLocation hex;
		switch (direction) {
			case NorthWest:
			case NorthEast:
				return this;
			case West:
				hex = (new HexLocation(x, y)).getNeighborLocation(EdgeDirection.SouthWest);
				return new VertexLocation(hex.getX(), hex.getY(), VertexDirection.NorthEast);
			case SouthWest:
				hex = (new HexLocation(x, y)).getNeighborLocation(EdgeDirection.South);
				return new VertexLocation(hex.getX(), hex.getY(), VertexDirection.NorthWest);
			case SouthEast:
				hex = (new HexLocation(x, y)).getNeighborLocation(EdgeDirection.South);
				return new VertexLocation(hex.getX(), hex.getY(), VertexDirection.NorthEast);
			case East:
				hex = (new HexLocation(x, y)).getNeighborLocation(EdgeDirection.SouthEast);
				return new VertexLocation(hex.getX(), hex.getY(), VertexDirection.NorthWest);
			default:
				assert false;
				return null;
		}
	}
	
	/**
	 * Validates a VertexLocation based on a radius criteria
	 * @pre none
	 * @param radius - the radius within which a VertexLocation is valid, note the normalized directions can equal radius on the negative side
	 * @return returns true if valid location given radius, false if otherwise
	 * @post see return
	 */
	public boolean validate(int radius) {
		assert false; //unimplemented
		return false;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null){
			return false;
		}
		if (o.getClass() != this.getClass()){
			return false;
		}
		if (((VertexLocation)o).getX() != this.getX() || ((VertexLocation)o).getY() != this.getY() || ((VertexLocation)o).getDirection() != this.getDirection()){
			return false;
		}
		return true;
	}
}

