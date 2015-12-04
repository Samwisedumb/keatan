package client.model;

import shared.definitions.EdgeDirection;

/**
 * Represents the location of a hex on a hex map
 */
public class HexLocation {
	private int x;
	private int y;
	
	public HexLocation(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public int getX() {
		return x;
	}
	
	private void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	private void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "HexLocation [x=" + x + ", y=" + y + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		HexLocation other = (HexLocation)obj;
		if(x != other.x)
			return false;
		if(y != other.y)
			return false;
		return true;
	}
	
	public HexLocation getNeighborLocation(EdgeDirection direction) {
		switch (direction) {
			case NorthWest:
				return new HexLocation(x - 1, (y));
			case North:
				return new HexLocation(x, (y) + 1);
			case NorthEast:
				return new HexLocation(x + 1, (y) + 1);
			case SouthWest:
				return new HexLocation(x - 1, (y) - 1);
			case South:
				return new HexLocation(x, (y) - 1);
			case SouthEast:
				return new HexLocation(x + 1, (y));
			default:
				assert false;
				return null;
		}
	}
//	public HexLocation getNeighborLoc(EdgeDirection direction) //What ta's wrote it, we changed it a little
//	{
//		switch (direction)
//		{
//			case NorthWest:
//				return new HexLocation(x - 1, y);
//			case North:
//				return new HexLocation(x, y - 1);
//			case NorthEast:
//				return new HexLocation(x + 1, y - 1);
//			case SouthWest:
//				return new HexLocation(x - 1, y + 1);
//			case South:
//				return new HexLocation(x, y + 1);
//			case SouthEast:
//				return new HexLocation(x + 1, y);
//			default:
//				assert false;
//				return null;
//		}
//	}

	/**
	 * The Catan Gui was oriented a little differently than we imagined.
	 * This function returns the location as it corresponds to the gui.
	 * @pre use this only on normal hex locations
	 * @return hexLocation is gui coordinates
	 */
	public HexLocation convertToGuiCoordinates() {
		return new HexLocation(x, -y);
	}
	
	/**
	 * The Catan Gui was oriented a little differently than we imagined.
	 * This function returns the location as it corresponds to the gui.
	 * @pre use this only on locations from the gui
	 * @return hexLocation in normal coordinates
	 */
	public HexLocation convertToNormalCoordinates() {
		return new HexLocation(x, -y);
	}
}

