package client.map.states;

import client.map.IMapController;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.VertexLocation;

public class MapControllerDoubleWaitState extends MapControllerState {

	public MapControllerDoubleWaitState(IMapController controller) {
		super(controller);
	}

	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		// TODO Auto-generated method stub
		return false;
	}

}
