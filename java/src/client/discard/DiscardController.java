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
		
		modelIsVisible = false;
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
		getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
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
		
		getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
	}

	@Override
	public void discard() {
		
		DiscardCards command = new DiscardCards(ModelFacade.getUserPlayerInfo().getIndex(), theList);
		
		try {
			MasterController.getSingleton().discardCards(command);
		}
		catch (ServerException e) {
			System.err.println(e.getReason());
		}
		
		getDiscardView().closeModal();
	}

	boolean modelIsVisible;
	
	public void showModal() {
		if (!modelIsVisible) {
			getDiscardView().showModal();
			modelIsVisible = true;
		}
	}
	
	public void closeModel() {
		if (modelIsVisible) {
			getDiscardView().closeModal();
			modelIsVisible = false;
		}
	}
	
	@Override
	public void update() {
		if (MasterController.getSingleton().hasGameBegun() &&
				ModelFacade.whatStateMightItBe() == Status.Discarding) {
			Player user = ModelFacade.getUserPlayer();
			if (user.needsToDiscard()) {
				showModal();
			}
		}
	}

}

