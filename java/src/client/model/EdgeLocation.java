package client.model;

import shared.definitions.EdgeDirection;


/**
 * Represents the location of an edge on a hex map
 */
public class EdgeLocation {
	private int x;
	private int y;
	private EdgeDirection direction;
	
	public EdgeLocation(int x, int y, EdgeDirection direction) {
		setX(x);
		setY(y);
		setDirection(direction);
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public EdgeDirection getDirection() {
		return direction;
	}

	public void setDirection(EdgeDirection direction) {
		this.direction = direction;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public HexLocation getHexLocation() {
		return new HexLocation(x, y);
	}
	
	@Override
	public String toString() {
		return "EdgeLocation [hexLoc=(" + x + "," + y + "), direction=" + direction + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		EdgeLocation other = (EdgeLocation)obj;
		other = other.getNormalizedLocation();
		EdgeLocation me = this.getNormalizedLocation();
		if(me.direction != other.direction)
			return false;
		if (me.x != other.x) {
			return false;
		}
		if (me.y != other.y) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns a canonical (i.e., unique) value for this edge location. Since
	 * each edge has two different locations on a map, this method converts a
	 * hex location to a single canonical form. This is useful for using hex
	 * locations as map keys.
	 * 
	 * @pre direction must not be null
	 * @post a normalized (the north-version edges) EdgeLocation is returned
	 * @return a normalized EdgeLocation
	 */
	public EdgeLocation getNormalizedLocation() {		
		switch (direction)
		{
			case NorthWest:
			case North:
			case NorthEast:
				return this;
			case SouthWest:
			case South:
			case SouthEast:
				HexLocation hex = (new HexLocation(x, y).getNeighborLocation(direction));
				return new EdgeLocation(hex.getX(), hex.getY(), direction.getOppositeDirection());
			default:
				assert false;
				return null;
		}
	}

	/**
	 * Converts an edge location to a gui edge location
	 * @return
	 */
	public EdgeLocation convertToGui() {
		return new EdgeLocation(x, -y, direction.getOppositeDirection());
	}

	public EdgeLocation convertToNormalLocation() {
		return new EdgeLocation(x, -y, direction.getOppositeDirection());
	}
}

