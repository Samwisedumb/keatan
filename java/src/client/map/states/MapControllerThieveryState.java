package client.map.states;

import shared.definitions.PieceType;
import shared.exceptions.ServerException;
import shared.transferClasses.RobPlayer;
import shared.transferClasses.Soldier;
import client.base.MasterController;
import client.data.RobPlayerInfo;
import client.map.IMapController;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.ModelFacade;
import client.model.Player;
import client.model.VertexLocation;


public class MapControllerThieveryState extends MapControllerState {

	public MapControllerThieveryState(IMapController controller) {
		super(controller);
		
		if (controller.getState().getClass() != this.getClass()) {
			getMapView().startDrop(PieceType.ROBBER, ModelFacade.getUserPlayer().getColor(), false);
		}
		
		this.controller = controller;
	}
	
	private IMapController controller;

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
		return ModelFacade.canPlaceRobber(hexLoc);
	}

	@Override
	public void placeRobber(HexLocation hexLoc) {
		if (canPlaceRobber(hexLoc)) {
			ModelFacade.moveRobber(hexLoc);
			getMapView().placeRobber(hexLoc);
			
			RobPlayerInfo[] candidates = ModelFacade.getRobbablePlayerInfo();
			
			Player user = ModelFacade.getUserPlayer();
			if (candidates.length > 0) {
				getRobView().setPlayers(candidates);
				getRobView().showModal();
			}
			else {
				try {
					MasterController.getSingleton().robPlayer(new RobPlayer(user.getIndex(),
							-1,
							ModelFacade.findRobber()));
				}
				catch (ServerException e) {
					System.err.println(e.getReason());
				}
			}
		}
	}
	
	@Override
	public void robPlayer(RobPlayerInfo victim) {
		try {
			Player user = ModelFacade.getUserPlayer();
			if (victim.getNumCards() == 0) {
				if (MasterController.getSingleton().hasPlayedSoldierCard()) {
					MasterController.getSingleton().soldier(new Soldier(user.getIndex(),
							-1,
							ModelFacade.findRobber()));
					controller.setState(new MapControllerBuildTradeState(controller));
					MasterController.getSingleton().playedSoldierCard(false);
				}
				else {
					MasterController.getSingleton().robPlayer(new RobPlayer(user.getIndex(),
							-1,
							ModelFacade.findRobber()));
				}
			}
			else {
				if (MasterController.getSingleton().hasPlayedSoldierCard()) {
					MasterController.getSingleton().soldier(new Soldier(user.getIndex(),
							victim.getIndex(),
							ModelFacade.findRobber()));
					controller.setState(new MapControllerBuildTradeState(controller));
					MasterController.getSingleton().playedSoldierCard(false);
				}
				else {
					MasterController.getSingleton().robPlayer(new RobPlayer(user.getIndex(),
							victim.getIndex(),
							ModelFacade.findRobber()));
				}
			}
		}
		catch (ServerException e) {
			System.err.println(e.getReason());
		}
	}
	
	@Override
	public void playSoldierCard() {
		
	}
}
