package client.map.states;

import shared.definitions.PieceType;
import shared.exceptions.ServerException;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;
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
	
	public MapControllerInitializeState(IMapController controller) {
		super(controller);

		Player user = ModelFacade.getUserPlayer();
		
		getMapView().startDrop(PieceType.SETTLEMENT, user.getColor(), false);
		getMapView().startDrop(PieceType.ROAD, user.getColor(), false);
	}

	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		System.out.println(edgeLoc.toString());
		return ModelFacade.canBuildRoad(edgeLoc, true, true);
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		System.out.println(vertLoc.toString());
		return ModelFacade.canBuildSettlement(vertLoc.convertToNormalLocation(), true);
	}
	
	/**
	 * Tells the master controller to build a free road
	 */
	@Override
	public void placeRoad(EdgeLocation edge) {
		try {
			MasterController.getSingleton().buildRoad(new BuildRoad(ModelFacade.getUserPlayer().getIndex(), edge, true));
		}
		catch (ServerException e) {
			e.printStackTrace();
			System.err.println(e.getReason());
			// Gee. It would be nice to tell the client, don't you think?
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
			e.printStackTrace();
			System.err.println(e.getReason());
			// Gee. It would be nice to tell the client, don't you think?
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



