package client.model;

import shared.definitions.HexType;

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
	
	public HexLocation getLocation() {
		return this.location;
	}
	
	public HexType getType() {
		return this.resource;
	}
	
	public int getChitNumber() {
		return this.number;
	}
}
