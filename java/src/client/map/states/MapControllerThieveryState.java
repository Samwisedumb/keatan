package client.map.states;

import shared.definitions.PieceType;
import shared.exceptions.ServerException;
import shared.transferClasses.RobPlayer;
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
	}

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
		System.out.println("Robber can be placed: " + ModelFacade.canPlaceRobber(hexLoc));
		return ModelFacade.canPlaceRobber(hexLoc);
	}

	@Override
	public void placeRobber(HexLocation hexLoc) {
		getMapView().placeRobber(hexLoc);
		if (canPlaceRobber(hexLoc)) {
			System.out.println("Robber");
			
			ModelFacade.moveRobber(hexLoc);
			
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
				getRobView().closeModal();
			}
		}
	}
	
	@Override
	public void robPlayer(RobPlayerInfo victim) {
		try {
			Player user = ModelFacade.getUserPlayer();
			if (victim.getNumCards() == 0) {
				MasterController.getSingleton().robPlayer(new RobPlayer(user.getIndex(),
						-1,
						ModelFacade.findRobber()));
				getRobView().closeModal();
			}
			else {
				MasterController.getSingleton().robPlayer(new RobPlayer(user.getIndex(),
						victim.getIndex(),
						ModelFacade.findRobber()));
				getRobView().closeModal();
			}
		}
		catch (ServerException e) {
			System.err.println(e.getReason());
		}
	}
}
