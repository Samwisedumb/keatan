package shared.definitions;

import com.google.gson.annotations.SerializedName;

public enum EdgeDirection {
	
	@SerializedName("NE")
	NorthEast,
	
	@SerializedName("N")
	North,
	
	@SerializedName("NW")
	NorthWest,
	
	@SerializedName("SW")
	SouthWest,
	
	@SerializedName("S")
	South,
	
	@SerializedName("SE")
	SouthEast;
	
	private EdgeDirection opposite;
		
	static {
		NorthEast.opposite = SouthWest;
		North.opposite = South;
		NorthWest.opposite = SouthEast;
		SouthWest.opposite = NorthEast;
		South.opposite = North;
		SouthEast.opposite = NorthWest;
	}
	
	public EdgeDirection getOppositeDirection() {
		return opposite;
	}
}
