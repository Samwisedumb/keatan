package client.map.states;

import client.map.IMapController;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.VertexLocation;

/**
 * The state where it is the user's turn and the game is being initialized.
 * In this state the user is to place a disconnected road and a settlement for free.
 * @author djoshuac
 */
public class MapControllerInitializeState extends MapControllerState {

	public MapControllerInitializeState(IMapController controller) {
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
