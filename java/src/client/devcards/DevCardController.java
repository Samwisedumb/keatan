package client.devcards;

import shared.definitions.ResourceType;
import client.base.Controller;
import client.base.IAction;
import client.devcards.states.DevCardControllerNotYourTurnState;
import client.devcards.states.DevCardControllerState;
import client.devcards.states.DevCardControllerYourTurnState;
import client.model.ModelFacade;
import client.model.Status;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	
	private DevCardControllerState state;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
		
		ModelFacade.addObserver(this);
		
		setState(new DevCardControllerNotYourTurnState());
	}
	
	private void setState(DevCardControllerState state) {
		this.state = state;
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		state.startBuyCard();
	}

	@Override
	public void cancelBuyCard() {
		state.cancelBuyCard();
	}

	@Override
	public void buyCard() {
		state.buyCard();
	}

	@Override
	public void startPlayCard() {
		state.startPlayCard();
	}

	@Override
	public void cancelPlayCard() {
		state.cancelPlayCard();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		state.playMonopolyCard(resource);
	}

	@Override
	public void playMonumentCard() {
		state.playMonumentCard();
	}

	@Override
	public void playRoadBuildCard() {
		state.playRoadBuildCard();
	}

	@Override
	public void playSoldierCard() {
		state.playSoldierCard();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		state.playYearOfPlentyCard(resource1, resource2);
	}

	@Override
	public void update() {
		if (ModelFacade.whatStateMightItBe() == Status.Playing
				&& ModelFacade.whoseTurnIsItAnyway() == ModelFacade.getUserPlayer().getIndex()) {
			setState(new DevCardControllerYourTurnState(this));
		}
		else {
			setState(new DevCardControllerNotYourTurnState());
		}
	}

	public IAction getSoldierAction() {
		return soldierAction;
	}
	
	public IAction getRoadAction() {
		return roadAction;
	}
}

