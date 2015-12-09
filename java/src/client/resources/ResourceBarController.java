package client.resources;

import java.util.HashMap;
import java.util.Map;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import client.base.Controller;
import client.base.IAction;
import client.base.MasterController;
import client.model.ModelFacade;
import client.model.Player;
import client.model.Status;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		
		elementActions = new HashMap<ResourceBarElement, IAction>();
		
		ModelFacade.addObserver(this);
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		System.out.println("Tried to build a road");
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}

	@Override
	public void update() {
		if (MasterController.getSingleton().hasGameBegun()) {
			Player user = ModelFacade.getUserPlayer();
			for (ResourceType r : ResourceType.values()) {
				getView().setElementAmount(r.getResourceBarElement(), user.getResourceAmount(r));
			}
			getView().setElementAmount(ResourceBarElement.ROAD, user.getUnplacedRoads());
			getView().setElementAmount(ResourceBarElement.SETTLEMENT, user.getUnplacedSettlements());
			getView().setElementAmount(ResourceBarElement.CITY, user.getUnplacedCities());
			getView().setElementAmount(ResourceBarElement.SOLDIERS, user.getNumPlayedSoldiers());
			
			if (ModelFacade.whatStateMightItBe() == Status.Playing) {
				getView().setElementEnabled(ResourceBarElement.ROAD, true);
				getView().setElementEnabled(ResourceBarElement.SETTLEMENT, true);
				getView().setElementEnabled(ResourceBarElement.CITY, true);
				
				getView().setElementEnabled(ResourceBarElement.BUY_CARD, user.getResources().hasEnoughForDevCard());
				getView().setElementEnabled(ResourceBarElement.PLAY_CARD, user.getNumPlayableDevCards() > 0);
			}
			else {
				getView().setElementEnabled(ResourceBarElement.ROAD, false);
				getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
				getView().setElementEnabled(ResourceBarElement.CITY, false);
			}
		}
	}

}

