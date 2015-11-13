package client.map.states;

import shared.definitions.PieceType;
import client.data.RobPlayerInfo;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.VertexLocation;

public class MapControllerDoubleWaitState implements MapControllerState {

	//DO NOTHING BECAUSE YOU ARE WAITING LIKE A GOOD LITTLE TOM!
	
	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void placeRoad(EdgeLocation edgeLoc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void placeSettlement(VertexLocation vertLoc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void placeCity(VertexLocation vertLoc) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void playRoadBuildingCard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void robPlayer(RobPlayerInfo victim) {
		// TODO Auto-generated method stub

	}

}
