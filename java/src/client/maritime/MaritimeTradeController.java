package client.maritime;

import java.util.Observable;

import shared.definitions.ResourceType;
import client.base.Controller;
import client.maritime.states.MaritimeTradeControllerState;
import client.model.ModelFacade;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	
	private MaritimeTradeControllerState state;
	
	private ResourceType give;
	private ResourceType get;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);
		
		give = null;
		get = null;
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public void set_state(MaritimeTradeControllerState newState) {
		state = newState;
	}
	
	@Override
	public void startTrade() {
		
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {

		int ratio = ModelFacade.getTradeRatio(ModelFacade.whoseTurnIsItAnyway(), get);
		
		getTradeOverlay().closeModal();
		state.makeTrade(give, get, ratio);  //WILL BE A DIFFERENT NUMBER!
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
		
		unsetGetValue();
		unsetGiveValue();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		get = resource;
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		give = resource;
	}

	@Override
	public void unsetGetValue() {
		get = null;
	}

	@Override
	public void unsetGiveValue() {
		give = null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

