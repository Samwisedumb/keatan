package client.maritime.states;

import shared.definitions.ResourceType;
import shared.exceptions.ServerException;
import shared.transferClasses.MaritimeTrade;
import client.model.ModelFacade;
import client.server.ServerProxy;

public class MaritimeTradeControllerBuildTradeState implements
		MaritimeTradeControllerState {

	@Override
	public void startTrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeTrade(ResourceType input, ResourceType output, int ratio) {
		// TODO Auto-generated method stub
		MaritimeTrade command = new MaritimeTrade(ModelFacade.whoseTurnIsItAnyway(), ratio, input, output);
		try {
			ServerProxy.maritimeTrade(command);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void cancelTrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGetResource(ResourceType resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGiveResource(ResourceType resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unsetGetValue() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unsetGiveValue() {
		// TODO Auto-generated method stub

	}

}
