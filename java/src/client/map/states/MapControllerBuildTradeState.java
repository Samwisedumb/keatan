package client.map.states;

import shared.definitions.PieceType;
import shared.exceptions.ServerException;
import shared.transferClasses.BuildCity;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;
import shared.transferClasses.RoadBuilding;
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
		
		this.controller = controller;
	}
	
	private IMapController controller;

	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		if (MasterController.getSingleton().hasPlayedRoadBuildingCard()) {
			if (road1 == null) {
				return ModelFacade.canBuildRoad(edgeLoc, true, false);
			}
			else if (road2 == null) {
				return ModelFacade.canBuildRoad(edgeLoc, true, false) && !road1.equals(edgeLoc);
			}
			else {
				return false;
			}
		}
		else {
			return ModelFacade.canBuildRoad(edgeLoc, false, false);
		}
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
		return ModelFacade.canPlaceRobber(hexLoc) && MasterController.getSingleton().hasPlayedSoldierCard();
	}
	
	@Override
	public void placeRoad(EdgeLocation edgeLoc) {
		Player user = ModelFacade.getUserPlayer();
		if (MasterController.getSingleton().hasPlayedRoadBuildingCard()) {
			if (road1 == null) {
				if (ModelFacade.canBuildRoad(edgeLoc, true, false)) {
					road1 = edgeLoc;
					getMapView().placeRoad(edgeLoc, user.getColor());
					getMapView().startDrop(PieceType.ROAD, user.getColor(), false);
				}
			}
			else if(road2 == null) {
				if (ModelFacade.canBuildRoad(edgeLoc, true, false)) {
					road2 = edgeLoc;
					
					try {
						MasterController.getSingleton().roadBuilding(new RoadBuilding(user.getIndex(),
								road1,
								road2));
					}
					catch (ServerException e) {
						System.out.println(e.getReason());
					}
					MasterController.getSingleton().playedRoadBuildingCard(false);
				}
			}
		}
		else {
			if (ModelFacade.canBuildRoad(edgeLoc, false, false)) {
				try {
					MasterController.getSingleton().buildRoad(new BuildRoad(user.getIndex(), edgeLoc, false));
				}
				catch (ServerException e) {
					System.err.println(e.getReason());
				}
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
		case ROBBER:
			if (MasterController.getSingleton().hasPlayedSoldierCard()) {
				getMapView().startDrop(PieceType.ROBBER, user.getColor(), false);
			}
		default:
		}
	}

	@Override
	public void playSoldierCard() {
		if (MasterController.getSingleton().hasPlayedSoldierCard()) {
			if (MasterController.getSingleton().hasPlayedSoldierCard()) {
				controller.setState(new MapControllerThieveryState(controller));
				
				controller.playSoldierCard();
			}
		}
	}
	
	private EdgeLocation road1;
	private EdgeLocation road2;
	
	@Override
	public void playRoadBuildingCard() {
		Player user = ModelFacade.getUserPlayer();
		
		if (MasterController.getSingleton().hasPlayedRoadBuildingCard()) {
			getMapView().startDrop(PieceType.ROAD, user.getColor(), false);
		}
	}
	
	@Override
	public void cancelMove() {
		
	}
}
