package shared.definitions;

import client.resources.ResourceBarElement;

import com.google.gson.annotations.SerializedName;

public enum ResourceType {

    @SerializedName("wood")
	WOOD,

    @SerializedName("brick")
	BRICK,

    @SerializedName("sheep")
	SHEEP,

    @SerializedName("wheat")
	WHEAT,

    @SerializedName("ore")
	ORE;

	public static ResourceType fromString(String string) {
		if (string == null) {
			return null;
		}
		switch (string) {
		case "wood":
			return ResourceType.WOOD;
		case "brick":
			return ResourceType.BRICK;
		case "sheep":
			return ResourceType.SHEEP;
		case "wheat":
			return ResourceType.WHEAT;
		case "ore":
			return ResourceType.ORE;
		default:
			return null;
		}
	}
	
	public PortType getPortType() {
		switch (this) {
		case BRICK:
			return PortType.BRICK;
		case ORE:
			return PortType.ORE;
		case SHEEP:
			return PortType.SHEEP;
		case WHEAT:
			return PortType.WHEAT;
		case WOOD:
			return PortType.WOOD;
		default:
			return PortType.THREE;
		}
	}
	
	public ResourceBarElement getResourceBarElement() {
		switch (this) {
		case BRICK:
			return ResourceBarElement.BRICK;
		case ORE:
			return ResourceBarElement.ORE;
		case SHEEP:
			return ResourceBarElement.SHEEP;
		case WHEAT:
			return ResourceBarElement.WHEAT;
		case WOOD:
			return ResourceBarElement.WOOD;
		default:
			return null;
		}
	}
}

