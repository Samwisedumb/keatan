package shared.definitions;

import com.google.gson.annotations.SerializedName;

public enum VertexDirection {
	
	@SerializedName("NE")
	NorthEast,
	
	@SerializedName("E")
	East,
	
	@SerializedName("SE")
	SouthEast,
	
	@SerializedName("SW")
	SouthWest,
	
	@SerializedName("W")
	West,
	
	@SerializedName("NW")
	NorthWest;
	
	private VertexDirection opposite;
	
	static {
		West.opposite = East;
		NorthWest.opposite = SouthEast;
		NorthEast.opposite = SouthWest;
		East.opposite = West;
		SouthEast.opposite = NorthWest;
		SouthWest.opposite = NorthEast;
	}
	
	public VertexDirection getOppositeDirection() {
		return opposite;
	}
}
