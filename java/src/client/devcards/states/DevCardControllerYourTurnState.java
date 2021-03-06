package client.devcards.states;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.exceptions.ServerException;
import shared.transferClasses.BuyDevCard;
import shared.transferClasses.Monopoly;
import shared.transferClasses.Monument;
import shared.transferClasses.YearOfPlenty;
import client.base.MasterController;
import client.devcards.DevCardController;
import client.model.ModelFacade;
import client.model.Player;

/**
 * This the YourTurnState. You can do all of this stuff at any point on your turn. Lucky you.
 */
public class DevCardControllerYourTurnState implements DevCardControllerState {
	private DevCardController controller;
	
	public DevCardControllerYourTurnState(DevCardController devCardController) {
		controller = devCardController;
	}
	
	@Override
	public void startBuyCard() {
		controller.getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		controller.getPlayCardView().closeModal();
	}

	@Override
	public void buyCard() {
		BuyDevCard command = new BuyDevCard(ModelFacade.whoseTurnIsItAnyway());
		try {
			MasterController.getSingleton().buyDevCard(command);
		}
		catch (ServerException e) {
			System.out.println(e.getReason());
		}
		controller.getPlayCardView().closeModal();
	}

	@Override
	public void startPlayCard() {
		controller.getPlayCardView().reset();
		
		Player user = ModelFacade.getUserPlayer();
		
		for (DevCardType type : DevCardType.values()) {
			controller.getPlayCardView().setCardAmount(type, user.getTotalNumberOfDevCard(type));
			controller.getPlayCardView().setCardEnabled(type, user.getNumPlayableDevCard(type) > 0);
		}
		
		controller.getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {
		controller.getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		Monopoly command = new Monopoly(ModelFacade.whoseTurnIsItAnyway(), resource);
		try {
			MasterController.getSingleton().monopoly(command);
		}
		catch (ServerException e) {
			System.out.println(e.getReason());
		}
	}

	@Override
	public void playRoadBuildCard() {
		System.out.println("Road buld card");
		
		MasterController.getSingleton().playedRoadBuildingCard(true);
		
		controller.getRoadAction().execute();
	}

	@Override
	public void playSoldierCard() {
		System.out.println("Solder card");
		
		MasterController.getSingleton().playedSoldierCard(true);
		
		controller.getSoldierAction().execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resourceOne, ResourceType resourceTwo) {
		YearOfPlenty command = new YearOfPlenty(ModelFacade.whoseTurnIsItAnyway(), resourceOne, resourceTwo);
		try {
			MasterController.getSingleton().yearOfPlenty(command);
		} 
		catch (ServerException e) {
			System.out.println(e.getReason());
		}
	}

	@Override
	public void playMonumentCard() {
		Monument commmand = new Monument(ModelFacade.whoseTurnIsItAnyway());
		try {
			MasterController.getSingleton().monument(commmand);
		}
		catch (ServerException e) {
			System.out.println(e.getReason());
		}
	}

}
