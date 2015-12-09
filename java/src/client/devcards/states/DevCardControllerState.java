package client.devcards.states;

import shared.definitions.ResourceType;

public interface DevCardControllerState {

	void startBuyCard();
	
	void cancelBuyCard();
	
	void buyCard();
	
	void startPlayCard();
	
	void cancelPlayCard();
	
	void playMonopolyCard(ResourceType resource);
	
	void playMonumentCard();
	
	void playRoadBuildCard();
	
	void playSoldierCard();
	
	void playYearOfPlentyCard(ResourceType resourceOne, ResourceType resourceTwo);
	
}
