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
}
