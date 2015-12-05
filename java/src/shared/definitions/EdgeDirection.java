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
	private EdgeDirection gui;
		
	static {
		NorthEast.opposite = SouthWest;
		North.opposite = South;
		NorthWest.opposite = SouthEast;
		SouthWest.opposite = NorthEast;
		South.opposite = North;
		SouthEast.opposite = NorthWest;
	}
	
	static {
		North.gui = SouthWest;
		SouthWest.gui = North;
		
		NorthEast.gui = South;
		South.gui = NorthEast;
		
		NorthWest.gui = NorthWest;
		SouthEast.gui = SouthEast;
	}
	
	public EdgeDirection getOppositeDirection() {
		return opposite;
	}
	
	/**
	 * Our person who designed the model used up as positive y
	 */
	public EdgeDirection getGui() {
		return gui;
	}
}
