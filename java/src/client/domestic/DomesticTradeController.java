package client.domestic;

import shared.definitions.ResourceType;
import shared.exceptions.ServerException;
import shared.transferClasses.AcceptTrade;
import shared.transferClasses.OfferTrade;
import client.base.Controller;
import client.base.MasterController;
import client.misc.IWaitView;
import client.model.ModelFacade;
import client.model.Player;
import client.model.ResourceList;
import client.model.Status;
import client.model.TradeOffer;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

	//Trade Offer Thing
	private int target;
	private ResourceList theList;
	private boolean waitingForAccept;
	
	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
		
		//TOT
		
		target = -1;
		theList = new ResourceList(0, 0, 0, 0, 0);
		waitingForAccept = false;
		
		ModelFacade.addObserver(this);
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	@Override
	public void startTrade() {
		Player user = ModelFacade.getUserPlayer();
		if (ModelFacade.whatStateMightItBe() == Status.Playing &&
				ModelFacade.whoseTurnIsItAnyway() == user.getIndex()) {

			theList = new ResourceList(0,0,0,0,0);
			
			getTradeOverlay().setCancelEnabled(true);
			getTradeOverlay().setStateMessage("Trade");
			
			getTradeOverlay().setPlayers(ModelFacade.getPlayersInfo());
			
			for (Player p : ModelFacade.getPlayers()) {
				getTradeOverlay().setPlayerSelectionEnabled(p.getResources().getTotal() > 0);
			}
			
			updateAmounts();
			
			getTradeOverlay().showModal();
		}
	}
	
	private void updateAmounts() {
		Player user = ModelFacade.getUserPlayer();
		for (ResourceType r : ResourceType.values()) {
			getTradeOverlay().setResourceAmountChangeEnabled(r,
					theList.getResource(r) < user.getResourceAmount(r),
					theList.getResource(r) > 0);
		}
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		switch(resource) {
		case WOOD:
			theList.setWood(theList.getWood() - 1);
			break;
		case BRICK:
			theList.setBrick(theList.getBrick() - 1);
			break;
		case SHEEP:
			theList.setSheep(theList.getSheep() - 1);
			break;
		case WHEAT:
			theList.setWheat(theList.getWheat() - 1);
			break;
		case ORE:
			theList.setOre(theList.getOre() - 1);
			break;
		}

		updateAmounts();
		getTradeOverlay().setResourceAmount(resource, "-1");
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		switch(resource) {
		case WOOD:
			theList.setWood(theList.getWood() + 1);
			break;
		case BRICK:
			theList.setBrick(theList.getBrick() + 1);
			break;
		case SHEEP:
			theList.setSheep(theList.getSheep() + 1);
			break;
		case WHEAT:
			theList.setWheat(theList.getWheat() + 1);
			break;
		case ORE:
			theList.setOre(theList.getOre() + 1);
			break;
		}

		updateAmounts();
		getTradeOverlay().setResourceAmount(resource, "1");
	}
	
	@Override
	public void sendTradeOffer() {
		Player user = ModelFacade.getUserPlayer();
		OfferTrade offer = new OfferTrade(user.getIndex(), theList, target);
		
		try {
			MasterController.getSingleton().offerTrade(offer);
			getTradeOverlay().closeModal();
			getWaitOverlay().showModal();
			waitingForAccept = true;
		}
		catch (ServerException e) {
			System.out.println(e.getReason());
			getTradeOverlay().closeModal();
		}
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		target = playerIndex;
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		switch(resource) {
		case WOOD:
			theList.setWood(1);
			break;
		case BRICK:
			theList.setBrick(1);
			break;
		case SHEEP:
			theList.setSheep(1);
			break;
		case WHEAT:
			theList.setWheat(1);
			break;
		case ORE:
			theList.setOre(1);
			break;
		}
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		switch(resource) {
		case WOOD:
			theList.setWood(-1);
			break;
		case BRICK:
			theList.setBrick(-1);
			break;
		case SHEEP:
			theList.setSheep(-1);
			break;
		case WHEAT:
			theList.setWheat(-1);
			break;
		case ORE:
			theList.setOre(-1);
			break;
		}
	}

	@Override
	public void unsetResource(ResourceType resource) {
		switch(resource) {
		case WOOD:
			theList.setWood(0);
			break;
		case BRICK:
			theList.setBrick(0);
			break;
		case SHEEP:
			theList.setSheep(0);
			break;
		case WHEAT:
			theList.setWheat(0);
			break;
		case ORE:
			theList.setOre(0);
			break;
		}
	}

	@Override
	public void cancelTrade() {
		target = -1;
		theList = null;
		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		TradeOffer offer = ModelFacade.getTradeOffer();
		
		int target = offer.getSender();
		
		if (willAccept) {
			try {
				MasterController.getSingleton().respondToTrade(new AcceptTrade(target, willAccept));
			}
			catch (ServerException e) {
				System.out.println(e.getReason());
			}
		}
		else {
			try {
				MasterController.getSingleton().respondToTrade(new AcceptTrade(target, false));
			}
			catch (ServerException e) {
				System.out.println(e.getReason());
			}
		}
		getAcceptOverlay().closeModal();
	}

	@Override
	public void update() {
		if (MasterController.getSingleton().hasGameBegun()) {
			if (ModelFacade.whatStateMightItBe() == Status.Playing) {
				Player user = ModelFacade.getUserPlayer();
				TradeOffer offer = ModelFacade.getTradeOffer();
				
				if (ModelFacade.whoseTurnIsItAnyway() == user.getIndex()) {
					if (waitingForAccept && offer == null) {
						getWaitOverlay().closeModal();
						waitingForAccept = false;
					}
				}
				else {
					if (offer != null && offer.getReceiver() == user.getIndex()) {
						if (!getAcceptOverlay().isModalShowing()) {
							Player trader = ModelFacade.getPlayer(offer.getSender());
							getAcceptOverlay().setPlayerName(trader.getName());

							boolean hasEnough = false;
							for (ResourceType r : ResourceType.values()) {
								int offerAmount = offer.getOffer().getResource(r);
								hasEnough &= user.getResourceAmount(r) + offerAmount > 0;
								if (offerAmount < 0) {
									getAcceptOverlay().addGetResource(r, -offerAmount);
								}
								else {
									getAcceptOverlay().addGiveResource(r, offerAmount);
								}
							}
							
							getAcceptOverlay().setAcceptEnabled(hasEnough);
							getAcceptOverlay().showModal();
						}
					}
				}
			}
		}
	}

}

