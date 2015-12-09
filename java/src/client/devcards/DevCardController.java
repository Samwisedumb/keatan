package client.devcards;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import client.base.Controller;
import client.base.IAction;
import client.devcards.states.DevCardControllerNotYourTurnState;
import client.devcards.states.DevCardControllerState;
import client.model.ModelFacade;
import client.model.Player;


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
		
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		
		state.buyCard();
		
		getBuyCardView().closeModal();
	}

	@Override
	public void startPlayCard() {
		getPlayCardView().reset();
		
		Player user = ModelFacade.getUserPlayer();
		
		for (DevCardType type : DevCardType.values()) {
			
		}
		
		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {
		
		getPlayCardView().closeModal();
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
		
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		
		
		//state.playSoldierCard(victimIndex, location);
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		state.playYearOfPlentyCard(resource1, resource2);
	}

	@Override
	public void update() {
		
	}

}

