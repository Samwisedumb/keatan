package client.map.states;

import shared.definitions.PieceType;
import client.map.IMapController;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.ModelFacade;
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
		return ModelFacade.canBuildRoad(edgeLoc, true, true);
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return ModelFacade.canBuildSettlement(vertLoc.convertToNormalLocation(), true);
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
