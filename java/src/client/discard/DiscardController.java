package client.discard;

import java.util.Observable;

import shared.definitions.ResourceType;
import shared.exceptions.ServerException;
import shared.transferClasses.DiscardCards;
import client.base.Controller;
import client.misc.IWaitView;
import client.model.ModelFacade;
import client.model.ResourceList;
import client.server.ServerProxy;


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
		
		DiscardCards command = new DiscardCards(ModelFacade.getPlayerInfo().getIndex(), theList);
		
		try {
			ServerProxy.discardCards(command);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getDiscardView().closeModal();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

