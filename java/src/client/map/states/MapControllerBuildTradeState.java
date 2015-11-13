package client.map.states;

import shared.definitions.PieceType;
import shared.transferClasses.BuildCity;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;
import client.data.RobPlayerInfo;
import client.exceptions.ServerException;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.ModelFacade;
import client.model.VertexLocation;
import client.server.ServerProxy;

public class MapControllerBuildTradeState implements MapControllerState {

	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		// TODO Auto-generated method stub

		//Maybe change this to the client-bound player (i.e. player index of whoever is on this machine)

		return ModelFacade.canBuildRoad(ModelFacade.whoseTurnIsItAnyway(), edgeLoc);

	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		// TODO Auto-generated method stub

		return ModelFacade.canBuildSettlement(ModelFacade.whoseTurnIsItAnyway(), vertLoc);
	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc) {
		// TODO Auto-generated method stub

		//Can canPlaceCity in ModelFacade

		return ModelFacade.canBuildCity(ModelFacade.whoseTurnIsItAnyway(), vertLoc);
	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void placeRoad(EdgeLocation edgeLoc) {
		// TODO Auto-generated method stub

		BuildRoad command = new BuildRoad(ModelFacade.whoseTurnIsItAnyway(), edgeLoc, false);

		try {
			ServerProxy.buildRoad(command);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("WHOOPS!");
		}

	}

	@Override
	public void placeSettlement(VertexLocation vertLoc) {
		// TODO Auto-generated method stub

		BuildSettlement command = new BuildSettlement(ModelFacade.whoseTurnIsItAnyway(), vertLoc, false);

		try {
			ServerProxy.buildSettlement(command);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("WHOOPS!");
		}

	}

	@Override
	public void placeCity(VertexLocation vertLoc) {
		// TODO Auto-generated method stub

		BuildCity command = new BuildCity(ModelFacade.whoseTurnIsItAnyway(), vertLoc, false);

		try {
			ServerProxy.buildCity(command);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("WHOOPS!");
		}

	}

	@Override
	public void placeRobber(HexLocation hexLoc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startMove(PieceType pieceType, boolean isFree,
			boolean allowDisconnected) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelMove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playSoldierCard() {
//		// TODO Auto-generated method stub
//		Soldier command = new Soldier(ModelFacade.getInstance().whoseTurnIsItAnyway(), victimIndex, location);
//		try {
//			ServerProxy.soldier(command);
//		} catch (ServerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void playRoadBuildingCard() {
//		// TODO Auto-generated method stub
//		RoadBuilding command = new RoadBuilding(ModelFacade.getInstance().whoseTurnIsItAnyway(), roadOne, roadTwo);
//		try {
//			ServerProxy.roadBuilding(command);
//		} catch (ServerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void robPlayer(RobPlayerInfo victim) {
		// TODO Auto-generated method stub

	}

}
