package client.map.states;

import client.map.IMapController;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.VertexLocation;


public class MapControllerRollingDiceState extends MapControllerState {

	public MapControllerRollingDiceState(IMapController controller) {
		super(controller);
	}

	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return false;
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return false;
	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc) {
		return false;
	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		return false;
	}

}
