package client.domestic;

import shared.definitions.ResourceType;
import client.base.Controller;
import client.base.MasterController;
import client.misc.IWaitView;
import client.model.ModelFacade;
import client.model.Player;
import client.model.ResourceList;
import client.model.Status;


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
		theList = null;
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
			
			getTradeOverlay().showModal();
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
		
		getTradeOverlay().setResourceAmount(resource, "1");
	}

	@Override
	public void sendTradeOffer() {
		
///*		ResourceList offer = new ResourceList(0,0,0,0,0);
//		ResourceList request = new ResourceList(0,0,0,0,0);
//		
//		if(theList.getWood() >= 0) {
//			request.setWood(theList.getWood());
//		}
//		else {
//			offer.setWood(Math.abs(theList.getWood()));
//		}
//		if(theList.getBrick() >= 0) {
//			request.setBrick(theList.getBrick());
//		}
//		else {
//			offer.setBrick(Math.abs(theList.getBrick()));
//		}
//		if(theList.getSheep() >= 0) {
//			request.setSheep(theList.getSheep());
//		}
//		else {
//			offer.setSheep(Math.abs(theList.getSheep()));
//		}
//		if(theList.getWheat() >= 0) {
//			request.setWheat(theList.getWheat());
//		}
//		else  {
//			offer.setWheat(Math.abs(theList.getWheat()));
//		}
//		if(theList.getOre() >= 0) {
//			request.setOre(theList.getOre());
//		}
//		else {
//			offer.setOre(Math.abs(theList.getOre()));
//		}
//		
//		state.sendTradeOffer(offer, target, request);
//		
//		getTradeOverlay().closeModal();
////		getWaitOverlay().showModal();
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

		getAcceptOverlay().closeModal();
	}

	@Override
	public void update() {
		
	}

}

