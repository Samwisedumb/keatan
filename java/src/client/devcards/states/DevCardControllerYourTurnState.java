package client.devcards.states;

import shared.definitions.ResourceType;
import shared.exceptions.ServerException;
import shared.transferClasses.BuyDevCard;
import shared.transferClasses.Monopoly;
import shared.transferClasses.Monument;
import shared.transferClasses.RoadBuilding;
import shared.transferClasses.Soldier;
import shared.transferClasses.YearOfPlenty;
import client.base.MasterController;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.ModelFacade;

public class DevCardControllerYourTurnState implements DevCardControllerState {

	/**
	 * This the YourTurnState. You can do all of this stuff at any point on your turn. Lucky you.
	 */
	
	@Override
	public void startBuyCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelBuyCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyCard() {
		// TODO Auto-generated method stub
		BuyDevCard command = new BuyDevCard(ModelFacade.whoseTurnIsItAnyway());
		try {
			MasterController.getSingleton().buyDevCard(command);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void startPlayCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelPlayCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		// TODO Auto-generated method stub
		Monopoly command = new Monopoly(ModelFacade.whoseTurnIsItAnyway(), resource);
		try {
			MasterController.getSingleton().monopoly(command);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void playRoadBuildCard(EdgeLocation roadOne, EdgeLocation roadTwo) {
		// TODO Auto-generated method stub
		RoadBuilding command = new RoadBuilding(ModelFacade.whoseTurnIsItAnyway(), roadOne, roadTwo);
		try {
			MasterController.getSingleton().roadBuilding(command);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void playSoldierCard(int victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		Soldier command = new Soldier(ModelFacade.whoseTurnIsItAnyway(), victimIndex, location);
		try {
			MasterController.getSingleton().soldier(command);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resourceOne, ResourceType resourceTwo) {
		// TODO Auto-generated method stub
		YearOfPlenty command = new YearOfPlenty(ModelFacade.whoseTurnIsItAnyway(), resourceOne, resourceTwo);
		try {
			MasterController.getSingleton().yearOfPlenty(command);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void playMonumentCard() {
		// TODO Auto-generated method stub
		Monument commmand = new Monument(ModelFacade.whoseTurnIsItAnyway());
		try {
			MasterController.getSingleton().monument(commmand);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
