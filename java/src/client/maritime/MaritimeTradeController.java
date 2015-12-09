package client.maritime;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.ResourceType;
import shared.exceptions.ServerException;
import shared.transferClasses.MaritimeTrade;
import client.base.Controller;
import client.base.MasterController;
import client.model.ModelFacade;
import client.model.Player;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	
	private ResourceType give;
	private ResourceType get;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);
		
		give = null;
		get = null;
		
		ModelFacade.addObserver(this);
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
	
	@Override
	public void startTrade() {
		Player user = ModelFacade.getUserPlayer();
		
		List<ResourceType> getOptions = new ArrayList<ResourceType>();
		List<ResourceType> giveOptions = new ArrayList<ResourceType>();
		
		for (ResourceType r : ResourceType.values()) {
			int ratio = ModelFacade.getTradeRatio(user.getIndex(), r);
			if (user.getResources().getResource(r) >= ratio) {
				giveOptions.add(r);
			}
			if (ModelFacade.bankHasAtLeast(r, 1)) {
				getOptions.add(r);
			}
		}
		
		getTradeOverlay().showGetOptions(getOptions.toArray(new ResourceType[getOptions.size()]));
		getTradeOverlay().showGiveOptions(giveOptions.toArray(new ResourceType[giveOptions.size()]));
		getTradeOverlay().setStateMessage("Trade");
		getTradeOverlay().setCancelEnabled(true);
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {
		Player user = ModelFacade.getUserPlayer();
		int ratio = ModelFacade.getTradeRatio(user.getIndex(), give);
		
		System.out.println("Trade!");
		try {
			MasterController.getSingleton().maritimeTrade(new MaritimeTrade(user.getIndex(), ratio, give, get));
			System.out.println("Successfully traded");
		}
		catch (ServerException e) {
			System.err.println(e.getReason());
		}
		
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
		
		unsetGetValue();
		unsetGiveValue();
	}
	
	/**
	 * Enables the trade option in the gui if the trade is legal
	 */
	private void enableTradeIfShould() {
		Player user = ModelFacade.getUserPlayer();
		
		if (get != null && give != null && ModelFacade.canMaritimeTrade(user.getIndex(), give, get)) {
			getTradeOverlay().setTradeEnabled(true);
		}
		else {
			getTradeOverlay().setTradeEnabled(false);
		}
	}
		

	@Override
	public void setGetResource(ResourceType resource) {
		get = resource;
		getTradeOverlay().selectGetOption(resource, 1);
		enableTradeIfShould();
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		give = resource;
		Player user = ModelFacade.getUserPlayer();
		getTradeOverlay().selectGiveOption(resource, ModelFacade.getTradeRatio(user.getIndex(), resource));
		enableTradeIfShould();
	}

	@Override
	public void unsetGetValue() {
		get = null;

		List<ResourceType> getOptions = new ArrayList<ResourceType>();
		
		for (ResourceType r : ResourceType.values()) {
			if (ModelFacade.bankHasAtLeast(r, 1)) {
				getOptions.add(r);
			}
		}

		getTradeOverlay().showGetOptions(getOptions.toArray(new ResourceType[getOptions.size()]));
		
		enableTradeIfShould();
	}

	@Override
	public void unsetGiveValue() {
		give = null;

		Player user = ModelFacade.getUserPlayer();
		List<ResourceType> giveOptions = new ArrayList<ResourceType>();
		
		for (ResourceType r : ResourceType.values()) {
			int ratio = ModelFacade.getTradeRatio(user.getIndex(), r);
			if (user.getResources().getResource(r) >= ratio) {
				giveOptions.add(r);
			}
		}

		getTradeOverlay().showGiveOptions(giveOptions.toArray(new ResourceType[giveOptions.size()]));
		
		enableTradeIfShould();
	}

	@Override
	public void update() {
		
	}

}

