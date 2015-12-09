package client.discard;

import shared.definitions.ResourceType;
import shared.exceptions.ServerException;
import shared.transferClasses.DiscardCards;
import client.base.Controller;
import client.base.MasterController;
import client.misc.IWaitView;
import client.model.ModelFacade;
import client.model.Player;
import client.model.ResourceList;
import client.model.Status;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

	private IWaitView waitView;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	
	ResourceList theList;
	
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		
		this.waitView = waitView;
		
		theList = new ResourceList(0,0,0,0,0);
		
		ModelFacade.addObserver(this);
		
		modalIsVisible = false;
		waitModalIsVisible = false;
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
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
		
		updateDiscardView();
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
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
		
		updateDiscardView();
	}

	@Override
	public void discard() {
		
		DiscardCards command = new DiscardCards(ModelFacade.getUserPlayerInfo().getIndex(), theList);
		
		try {
			MasterController.getSingleton().discardCards(command);
			ModelFacade.getUserPlayer().setDiscarded(true);
		}
		catch (ServerException e) {
			System.err.println(e.getReason());
		}
		
		closeModal();
	}

	boolean modalIsVisible;
	boolean waitModalIsVisible;
	
	public void showModal() {
		if (!modalIsVisible) {
			getDiscardView().showModal();
			modalIsVisible = true;
		}
	}
	
	public void closeModal() {
		if (modalIsVisible) {
			getDiscardView().closeModal();
			modalIsVisible = false;
		}
	}
	
	public void updateDiscardView() {
		Player user = ModelFacade.getUserPlayer();
		
		int numToDiscard = user.getResources().getTotal() / 2;
		
		getDiscardView().setDiscardButtonEnabled(numToDiscard == theList.getTotal());
		getDiscardView().setStateMessage("Discard " + numToDiscard + "/" + theList.getTotal());
		
		for (ResourceType type : ResourceType.values()) {
			int amount = user.getResourceAmount(type);
			int value = theList.getResource(type);

			getDiscardView().setResourceDiscardAmount(type, value);
			getDiscardView().setResourceMaxAmount(type, amount);
			
			getDiscardView().setResourceAmountChangeEnabled(type, value < amount && numToDiscard > theList.getTotal(),
					value != 0);
		}
	}
	
	@Override
	public void update() {
		if (MasterController.getSingleton().hasGameBegun() &&
				ModelFacade.whatStateMightItBe() == Status.Discarding &&
				!ModelFacade.getUserPlayer().hasDiscarded()) {
			Player user = ModelFacade.getUserPlayer();

			updateDiscardView();
			
			if (user.needsToDiscard()) {
				showModal();
			}
		}
		else if (ModelFacade.getUserPlayer().hasDiscarded()){
			int stillDiscardCount = 0;
			for (Player p : ModelFacade.getPlayers()) {
				if (!p.hasDiscarded()) {
					stillDiscardCount++;
				}
			}
			closeWaitModal();
			waitView.setMessage("Waiting for " + stillDiscardCount + " players to discard.");
			showWaitModal();
		}
	}
	
	public void showWaitModal() {
		if (!waitModalIsVisible) {
			getWaitView().showModal();
			waitModalIsVisible = true;
		}
	}

	public void closeWaitModal() {
		if (waitModalIsVisible) {
			getWaitView().closeModal();
			waitModalIsVisible = false;
		}
	}
}

