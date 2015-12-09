package client.map.states;

import shared.definitions.PieceType;
import shared.exceptions.ServerException;
import shared.transferClasses.BuildCity;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;
import shared.transferClasses.RobPlayer;
import client.base.MasterController;
import client.map.IMapController;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.ModelFacade;
import client.model.Player;
import client.model.VertexLocation;


public class MapControllerBuildTradeState extends MapControllerState {
	public MapControllerBuildTradeState(IMapController controller) {
		super(controller);
	}

	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return ModelFacade.canBuildRoad(edgeLoc, false, false);
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return ModelFacade.canBuildSettlement(vertLoc, false);
	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc) {
		return ModelFacade.canBuildCity(vertLoc);
	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		return false;
	}
	
	@Override
	public void placeRoad(EdgeLocation edgeLoc) {
		if (ModelFacade.canBuildRoad(edgeLoc, false, false)) {
			Player user = ModelFacade.getUserPlayer();
			
			try {
				MasterController.getSingleton().buildRoad(new BuildRoad(user.getIndex(), edgeLoc, false));
			}
			catch (ServerException e) {
				System.err.println(e.getReason());
			}
		}
	}

	@Override
	public void placeSettlement(VertexLocation vertLoc) {
		if (ModelFacade.canBuildSettlement(vertLoc, false)) {
			Player user = ModelFacade.getUserPlayer();
			
			try {
				MasterController.getSingleton().buildSettlement(new BuildSettlement(user.getIndex(), vertLoc, false));
			}
			catch (ServerException e) {
				System.err.println(e.getReason());
			}
		}
	}

	@Override
	public void placeCity(VertexLocation vertLoc) {
		if (ModelFacade.canBuildCity(vertLoc)) {
			Player user = ModelFacade.getUserPlayer();
			
			try {
				MasterController.getSingleton().buildCity(new BuildCity(user.getIndex(), vertLoc, false));
			}
			catch (ServerException e) {
				System.err.println(e.getReason());
			}
		}
	}

	@Override
	public void placeRobber(HexLocation hexLoc) {
		if (ModelFacade.canPlaceRobber(hexLoc)) {
			try {
				MasterController.getSingleton().robPlayer(new RobPlayer(ModelFacade.getUserPlayer().getIndex(),
						-1,
						hexLoc));
			}
			catch (ServerException e) {
				System.err.println(e.getReason());
			}
		}
	}
	
	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		if (pieceType == null) {
			return;
		}
		Player user = ModelFacade.getUserPlayer();
		switch (pieceType) {
		case ROAD:
			if (user.getResources().hasEnoughForRoad() && user.getUnplacedRoads() > 0) {
				getMapView().startDrop(PieceType.ROAD, user.getColor(), true);
			}
			break;
		case SETTLEMENT:
			if (user.getResources().hasEnoughForSettlement() & user.getUnplacedSettlements() > 0) {
				getMapView().startDrop(PieceType.SETTLEMENT, user.getColor(), true);
			}
			break;
		case CITY:
			if (user.getResources().hasEnoughForCity() & user.getUnplacedCities() > 0) {
				getMapView().startDrop(PieceType.CITY, user.getColor(), true);
			}
			break;
		default:
		}
	}

	@Override
	public void cancelMove() {
		
	}
}
