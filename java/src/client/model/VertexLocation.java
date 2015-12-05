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
				hex = (new HexLocation(x, y)).getNeighborLoc(EdgeDirection.SouthWest);
				return new VertexLocation(hex.getX(), hex.getY(), VertexDirection.NorthEast);
			case SouthWest:
				hex = (new HexLocation(x, y)).getNeighborLoc(EdgeDirection.South);
				return new VertexLocation(hex.getX(), hex.getY(), VertexDirection.NorthWest);
			case SouthEast:
				hex = (new HexLocation(x, y)).getNeighborLoc(EdgeDirection.South);
				return new VertexLocation(hex.getX(), hex.getY(), VertexDirection.NorthEast);
			case East:
				hex = (new HexLocation(x, y)).getNeighborLoc(EdgeDirection.SouthEast);
				return new VertexLocation(hex.getX(), hex.getY(), VertexDirection.NorthWest);
			default:
				assert false;
				return null;
		}
	}

	/**
	 * Our person who designed the model used up as positive y
	 */
	public VertexLocation getGuiNormalizedLocation() {
		HexLocation hex;
		switch (direction) {
			case NorthWest:
			case NorthEast:
				return this;
			case West:
				hex = (new HexLocation(x, y)).getGuiNeighbor(EdgeDirection.SouthWest);
				return new VertexLocation(hex.getX(), hex.getY(), VertexDirection.NorthEast);
			case SouthWest:
				hex = (new HexLocation(x, y)).getGuiNeighbor(EdgeDirection.South);
				return new VertexLocation(hex.getX(), hex.getY(), VertexDirection.NorthWest);
			case SouthEast:
				hex = (new HexLocation(x, y)).getGuiNeighbor(EdgeDirection.South);
				return new VertexLocation(hex.getX(), hex.getY(), VertexDirection.NorthEast);
			case East:
				hex = (new HexLocation(x, y)).getGuiNeighbor(EdgeDirection.SouthEast);
				return new VertexLocation(hex.getX(), hex.getY(), VertexDirection.NorthWest);
			default:
				assert false;
				return null;
		}
	}

	/**
	 * Our person who designed the model used up as positive y
	 */
	public VertexLocation convertFromGui() {
		return new VertexLocation(x, -y, direction);
	}

	/**
	 * Our person who designed the model used up as positive y
	 */
	public VertexLocation convertToGui() {
		return new VertexLocation(x, -y, direction);
	}

	@Override
	public String toString() {
		return "VertexLocation [x=" + x + ", y=" + y + ", direction="
				+ direction + "]";
	}

	
	@Override
	public boolean equals(Object o){
		if (o == null){
			return false;
		}
		if (o.getClass() != this.getClass()){
			return false;
		}
		
		VertexLocation other = (VertexLocation)o;
		other = other.getNormalizedLocation();
		VertexLocation me = this.getNormalizedLocation();
		
		if (me.x != other.x) {
			return false;
		}
		if (me.y != other.y) {
			return false;
		}
		if (me.direction != other.direction) {
			return false;
		}
		return true;
	}

	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		return result;
	}
}

