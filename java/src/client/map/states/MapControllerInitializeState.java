package client.map.states;

import shared.definitions.PieceType;
import shared.exceptions.ServerException;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;
import shared.transferClasses.FinishTurn;
import client.base.MasterController;
import client.map.IMapController;
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
	private IMapController controller;
	
	public MapControllerInitializeState(IMapController controller) {
		super(controller);
		this.controller = controller;

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
			Player user = ModelFacade.getUserPlayer();
			getMapView().placeRoad(edge, user.getColor());
			MasterController.getSingleton().buildRoad(new BuildRoad(user.getIndex(), edge, true));
			getMapView().startDrop(PieceType.SETTLEMENT, user.getColor(), false);
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
			Player user = ModelFacade.getUserPlayer();
			getMapView().placeSettlement(vertex, user.getColor());
			MasterController.getSingleton().buildSettlement(new BuildSettlement(user.getIndex(), vertex, true));
			MasterController.getSingleton().finishTurn(new FinishTurn(user.getIndex()));
			setState(new MapControllerNotTurnState(controller));
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



