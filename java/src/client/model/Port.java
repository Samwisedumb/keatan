package client.model;

import shared.definitions.EdgeDirection;
import shared.definitions.ResourceType;

public class Port {
	private ResourceType resource;
	private HexLocation location;
	private EdgeDirection direction;
	private int ratio;

	public Port(ResourceType resource, HexLocation location, EdgeDirection direction, int ratio) {
		setResource(resource);
		setLocation(location);
		setDirection(direction);
		setRatio(ratio);
	}

	public ResourceType getResource() {
		return resource;
	}

	public void setResource(ResourceType resource) {
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
