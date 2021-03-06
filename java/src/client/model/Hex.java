package client.model;

import shared.definitions.HexType;
import shared.definitions.ResourceType;

/**
 * This interface is used so that the Map's list of hexes can include the DesertHex which
 * has no chit-number or resource String
 * @author djoshuac
 */
public class Hex {
	private HexLocation location;
	private HexType resource;
	private int number;
	
	public Hex(HexType type) {
		this.resource = type;
		this.number = -1; //For desert
	}
	
	public Hex(HexLocation hexLoc, HexType type, int chitNumber) {
		setLocation(hexLoc);
		setType(type);
		setChitNumber(chitNumber);
	}

	public void setType(HexType type) {
		this.resource = type;
	}
	
	public void setChitNumber(int chitNumber) {
		this.number = chitNumber;
	}

	public void setLocation(HexLocation newLocation) {
		this.location = newLocation;
	}
	
	/**
	 * The Catan Gui was oriented a little differently than we imagined.
	 * This function returns the location as it corresponds to the gui.
	 * @pre give this to the gui
	 * @return hexLocation is gui coordinates
	 */
	public HexLocation getGuiLocation() {
		return location.convertToGui();
	}
	
	/**
	 * @return hexLocation in conventional coordinates
	 */
	public HexLocation getLocation() {
		return this.location;
	}
	
	public HexType getType() {
		return this.resource;
	}
	
	public int getChitNumber() {
		return this.number;
	}

	public ResourceType getResourceType() {
		switch(resource) {
		case WOOD:
			return ResourceType.WOOD;
		case WHEAT:
			return ResourceType.WHEAT;
		case ORE:
			return ResourceType.ORE;
		case BRICK:
			return ResourceType.BRICK;
		case SHEEP:
			return ResourceType.SHEEP;
		default:
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "Location: " + getLocation() + ", Type: " + getType().name() + ", Number: " + getChitNumber();
	}

	/**
	 * See if the hex is at the given location
	 * @param hexLoc - the location to check
	 * @return true if the hex is at the given location<br>
	 * false if otherwise
	 * @author djoshuac
	 */
	public boolean isLocatedAt(HexLocation hexLoc) {
		if (getLocation().equals(hexLoc)) {
			return true;
		}
		return false;
	}

	/**
	 * See if a hex would produce on the given roll
	 * @param roll - the number rolled
	 * @return true if hex would produce<br>
	 * false if otherwise
	 */
	public boolean isProductive(int roll) {
		if (getType() == HexType.DESERT || getType() == HexType.WATER) {
			return false;
		}
		else if (getChitNumber() != number) {
			return false;
		}
		else {
			return true;
		}
	}
}
