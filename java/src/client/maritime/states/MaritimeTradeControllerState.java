package client.maritime.states;

import shared.definitions.ResourceType;

public interface MaritimeTradeControllerState {

	void startTrade();

	void makeTrade(ResourceType input, ResourceType output, int ratio);

	void cancelTrade();
	
	void setGetResource(ResourceType resource);

	void setGiveResource(ResourceType resource);

	void unsetGetValue();

	void unsetGiveValue();	
}
