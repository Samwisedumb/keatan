package client.map.states;

import shared.definitions.PieceType;
import shared.exceptions.ServerException;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;
import client.base.MasterController;
import client.map.IMapController;
import client.map.MapController;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.ModelFacade;
import client.model.Player;
import client.model.VertexLocation;

/**
 * The state where it is one of the user's two turns to place an initial road and settlement.
 * In this state the user is forced to place a disconnected road and a settlement for free.
 * @author djoshuac
 */
public class MapControllerInitializeState extends MapControllerState {
	
	public MapControllerInitializeState(IMapController controller) {
		super(controller);

		if (controller.getState().getClass() != this.getClass()) {
			getMapView().startDrop(PieceType.ROAD, ModelFacade.getUserPlayer().getColor(), false);
		}
	}

	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return ModelFacade.canBuildRoad(edgeLoc, true, true);
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return ModelFacade.canBuildSettlement(vertLoc, true);
	}
	
	/**
	 * Tells the master controller to build a free road
	 */
	@Override
	public void placeRoad(EdgeLocation edge) {
		try {
			MasterController.getSingleton().buildRoad(new BuildRoad(ModelFacade.getUserPlayer().getIndex(), edge, true));
			getMapView().startDrop(PieceType.SETTLEMENT, ModelFacade.getUserPlayer().getColor(), false);
		}
		catch (ServerException e) {
			System.err.println(e.getReason());
		}
	}
	
	/**
	 * Tells the master controller to build a free settlement
	 */
	@Override
	public void placeSettlement(VertexLocation vertex) {
		try {
			MasterController.getSingleton().buildSettlement(new BuildSettlement(ModelFacade.getUserPlayer().getIndex(), vertex, true));
		}
		catch (ServerException e) {
			System.err.println(e.getReason());
		}
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



