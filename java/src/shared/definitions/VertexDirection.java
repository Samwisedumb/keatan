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
	private VertexDirection gui;
	
	static {
		West.opposite = East;
		NorthWest.opposite = SouthEast;
		NorthEast.opposite = SouthWest;
		East.opposite = West;
		SouthEast.opposite = NorthWest;
		SouthWest.opposite = NorthEast;
	}
	
	static {
		West.gui = NorthWest;
		NorthWest.gui = West;
		
		East.gui = SouthEast;
		SouthEast.gui = East;
		
		NorthEast.gui = SouthWest;
		SouthWest.gui = NorthEast;
	}
	
	public VertexDirection getOppositeDirection() {
		return opposite;
	}
	
	/**
	 * Our person who designed the model used up as positive y
	 */
	public VertexDirection getGui() {
		return gui;
	}
}
