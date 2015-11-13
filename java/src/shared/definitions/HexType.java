package shared.definitions;

import com.google.gson.annotations.SerializedName;

public enum HexType {
	
	@SerializedName("wood")
	WOOD,

    @SerializedName("brick")
	BRICK,

    @SerializedName("sheep")
	SHEEP,

    @SerializedName("wheat")
	WHEAT,

    @SerializedName("ore")
	ORE,

    @SerializedName("DESERT")
	DESERT,

    @SerializedName("WATER")
	WATER;

	public static HexType fromString(String aString) {
		if (aString == null) return null;
		switch (aString) {
		case "wood":
			return HexType.WOOD;
		case "brick":
			return HexType.BRICK;
		case "sheep":
			return HexType.SHEEP;
		case "wheat":
			return HexType.WHEAT;
		case "ore":
			return HexType.ORE;
		case "DESERT":
			return HexType.DESERT;
		case "WATER":
			return HexType.WATER;
		default:
			return null;
		}
	}
}

