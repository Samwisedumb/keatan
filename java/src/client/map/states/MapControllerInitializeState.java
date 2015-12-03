package client.map.states;

import shared.definitions.PieceType;
import client.map.IMapController;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.VertexLocation;

/**
 * The state where it is one of the user's two turns to place an initial road and settlement.
 * In this state the user is forced to place a disconnected road and a settlement for free.
 * @author djoshuac
 */
public class MapControllerInitializeState extends MapControllerState {
	
	public MapControllerInitializeState(IMapController controller) {
		super(controller);
		
		startMove(PieceType.ROAD, true, true);
		startMove(PieceType.SETTLEMENT, true, true);
	}

	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		// TODO Auto-generated method stub
		// We need a can-do that allows a player to place a road disconnected,
		// and ensure that placing the road will not force the user to be unable to place
		// a settlement adjacent to it
		System.err.println("canPlaceRoad()  for initialize state is unimplemented");
		return true;
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		// TODO Auto-generated method stub
		// We need a can-do that forces the user to place the settlement adjacent to the road
		// they just placed.  This may work automatically since if they have to place the settlement
		// adjacent to a road, they will not be able to place it on the first road since that will mean
		// the second settlement will be nearer than 2 vertices away from the first settlement
		System.err.println("canPlaceSettlement() for initialize state is unimplemented");
		return true;
	}

	/**
	 * @return false
	 */
	@Override
	public boolean canPlaceCity(VertexLocation vertLoc) {
		return false;
	}

	/**
	 * @return false
	 */
	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		return false;
	}	
	
}
