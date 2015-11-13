package client.devcards.states;

import shared.definitions.ResourceType;
import client.model.EdgeLocation;
import client.model.HexLocation;

public class DevCardControllerNotYourTurnState implements
		DevCardControllerState {

	/**
	 * This is the Not Your Turn State. You cannot do anything in this state because it's not your turn. Poor you.
	 */
	
	@Override
	public void startBuyCard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelBuyCard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void buyCard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startPlayCard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelPlayCard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playRoadBuildCard(EdgeLocation roadOne, EdgeLocation roadTwo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playSoldierCard(int victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resourceOne,
			ResourceType resourceTwo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playMonumentCard() {
		// TODO Auto-generated method stub
		
	}

}
