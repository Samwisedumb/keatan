package client.domestic.states;

import shared.definitions.ResourceType;
import shared.exceptions.ServerException;
import shared.transferClasses.OfferTrade;
import client.model.ModelFacade;
import client.model.ResourceList;
import client.server.ServerProxy;

public class DomesticTradeControllerBuildTradeState implements
		DomesticTradeControllerState {

	/**
	 * YOU CAN DO EVERYTHING!
	 */
	
	@Override
	public void startTrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendTradeOffer(ResourceList offer, int receiver) {
		// TODO Auto-generated method stub

		OfferTrade command = new OfferTrade(ModelFacade.whoseTurnIsItAnyway(), offer, receiver);
		try {
			ServerProxy.offerTrade(command);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unsetResource(ResourceType resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelTrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptTrade(boolean willAccept) {
		// TODO Auto-generated method stub

	}

}
