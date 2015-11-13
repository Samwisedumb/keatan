package client.devcards.states;

import shared.definitions.ResourceType;
import client.model.EdgeLocation;
import client.model.HexLocation;

public interface DevCardControllerState {

	void startBuyCard();
	
	void cancelBuyCard();
	
	void buyCard();
	
	void startPlayCard();
	
	void cancelPlayCard();
	
	void playMonopolyCard(ResourceType resource);
	
	void playMonumentCard();
	
	void playRoadBuildCard(EdgeLocation roadOne, EdgeLocation roadTwo);
	
	void playSoldierCard(int victimIndex, HexLocation location);
	
	void playYearOfPlentyCard(ResourceType resourceOne, ResourceType resourceTwo);
	
}
