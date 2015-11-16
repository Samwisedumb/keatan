package client.model;

import shared.definitions.EdgeDirection;
import shared.definitions.PortType;
import shared.definitions.ResourceType;

public class Port {
	private PortType resource;
	private HexLocation location;
	private EdgeDirection direction;
	private int ratio;

	public Port(PortType resource, HexLocation location, EdgeDirection direction) {
		setResource(resource);
		setLocation(location);
		setDirection(direction);
		if(resource == PortType.THREE) {
			ratio = 3;
		}
		else {
			ratio = 2;
		}
	}

	public PortType getResource() {
		return resource;
	}

	public ResourceType getPortResource() {
		switch(resource) {
		case WOOD:
			return ResourceType.WOOD;
		case SHEEP:
			return ResourceType.SHEEP;
		case ORE:
			return ResourceType.ORE;
		case WHEAT:
			return ResourceType.WHEAT;
		case BRICK:
			return ResourceType.BRICK;
		default:
			return null;
		}
	}
	
	public void setResource(PortType resource) {
		this.resource = resource;
	}

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
	}

	public EdgeDirection getDirection() {
		return direction;
	}

	public void setDirection(EdgeDirection direction) {
		this.direction = direction;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
}
