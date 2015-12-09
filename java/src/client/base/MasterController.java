package client.base;

import shared.exceptions.ServerException;
import shared.transferClasses.AcceptTrade;
import shared.transferClasses.AddAIRequest;
import shared.transferClasses.BuildCity;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;
import shared.transferClasses.BuyDevCard;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.DiscardCards;
import shared.transferClasses.FinishTurn;
import shared.transferClasses.Game;
import shared.transferClasses.JoinGameRequest;
import shared.transferClasses.MaritimeTrade;
import shared.transferClasses.Monopoly;
import shared.transferClasses.Monument;
import shared.transferClasses.OfferTrade;
import shared.transferClasses.RoadBuilding;
import shared.transferClasses.RobPlayer;
import shared.transferClasses.RollNumber;
import shared.transferClasses.SendChat;
import shared.transferClasses.Soldier;
import shared.transferClasses.UserCredentials;
import shared.transferClasses.UserInfo;
import shared.transferClasses.YearOfPlenty;
import client.model.ModelFacade;
import client.model.TransferModel;
import client.server.ClientServer;
import client.server.IServer;
import client.server.ServerPoller;


//// NOTE: Only some controllers have the access to close modal dialogs.
////		And the controllers that can close modal dialogs can only close the one that is on top of the stack.
////		This is bad because the MapController cannot ensure that it doesn't cover the PlayerWaitingControllers dialog
////		before the PlayerWaitingController closes it, therefore, making the PlayerWaitingController prone to close
////		the MapController's dialog instead of it's own depending on the order that their update methods are called.
/**
 * This controller makes all the requests to the ClientServer.getSingleton() and manages the ClientServer.getSingleton() poller. It also keeps
 * track of what actions each player makes for the GameHistoryController.
 * Professor Woodfield recommenced that our ModelFacade shouldn't access the ClientServer.getSingleton() because
 * in Model-View-Presenter the controllers are the class responsible for ClientServer.getSingleton() communication.<br>
 * @author djoshuac
 */
public class MasterController implements IServer {
	private static MasterController instance;
	
	/**
	 * @return the singleton of the master controller
	 */
	public static MasterController getSingleton() {
		if (instance == null) {
			instance = new MasterController();
		}
		return instance;
	}

	private boolean gameHasBegun;
	private MasterController() {
		gameHasBegun = false;
		playedSoldierCard = false;
		playedRoadBuildingCard = false;
	}

	@Override
	public UserInfo login(UserCredentials userCredentials) throws ServerException {
		return ClientServer.getSingleton().login(userCredentials);
	}

	@Override
	public void register(UserCredentials userCredentials) throws ServerException {
		ClientServer.getSingleton().register(userCredentials);
	}

	@Override
	public Game[] getGamesList() throws ServerException {
		return ClientServer.getSingleton().getGamesList();
	}

	@Override
	public CreateGameResponse createGame(CreateGameRequest createGameRequest) throws ServerException {
		return ClientServer.getSingleton().createGame(createGameRequest);
	}

	@Override
	public Game joinGame(JoinGameRequest joinGameRequest) throws ServerException {
		return ClientServer.getSingleton().joinGame(joinGameRequest);
	}

	@Override
	public TransferModel getModel(int version) throws ServerException {
		return ClientServer.getSingleton().getModel(version);
	}

	@Override
	public void addAI(AddAIRequest addAIRequest) throws ServerException {
		// not required
	}

	@Override
	public String[] listAITypes() throws ServerException {
		// not required
		return null;
	}

	@Override
	public void sendChat(SendChat sendChat) throws ServerException {
		ClientServer.getSingleton().sendChat(sendChat);
	}

	@Override
	public void rollDice(RollNumber rollNumber) throws ServerException {
		ClientServer.getSingleton().rollDice(rollNumber);
	}

	@Override
	public void robPlayer(RobPlayer robPlayer) throws ServerException {
		ClientServer.getSingleton().robPlayer(robPlayer);
	}

	@Override
	public void finishTurn(FinishTurn finishTurn) throws ServerException {
		ClientServer.getSingleton().finishTurn(finishTurn);
	}

	@Override
	public void buyDevCard(BuyDevCard buyDevCard) throws ServerException {
		ClientServer.getSingleton().buyDevCard(buyDevCard);
	}

	@Override
	public void yearOfPlenty(YearOfPlenty yearOfPlenty) throws ServerException {
		ClientServer.getSingleton().yearOfPlenty(yearOfPlenty);
	}

	@Override
	public void roadBuilding(RoadBuilding roadBuilding) throws ServerException {
		ClientServer.getSingleton().roadBuilding(roadBuilding);
	}

	@Override
	public void soldier(Soldier soldier) throws ServerException {
		ClientServer.getSingleton().soldier(soldier);
	}

	@Override
	public void monopoly(Monopoly monopoly) throws ServerException {
		ClientServer.getSingleton().monopoly(monopoly);
	}

	@Override
	public void monument(Monument monument) throws ServerException {
		ClientServer.getSingleton().monument(monument);;
	}

	@Override
	public void buildRoad(BuildRoad buildRoad) throws ServerException {
		ClientServer.getSingleton().buildRoad(buildRoad);
	}

	@Override
	public void buildSettlement(BuildSettlement buildSettlement)
			throws ServerException {
		ClientServer.getSingleton().buildSettlement(buildSettlement);
	}

	@Override
	public void buildCity(BuildCity buildCity) throws ServerException {
		ClientServer.getSingleton().buildCity(buildCity);
	}

	@Override
	public void offerTrade(OfferTrade offer) throws ServerException {
		ClientServer.getSingleton().offerTrade(offer);
	}

	@Override
	public void respondToTrade(AcceptTrade acceptTrade) throws ServerException {
		ClientServer.getSingleton().respondToTrade(acceptTrade);
	}

	@Override
	public void maritimeTrade(MaritimeTrade maritimeTrade)
			throws ServerException {
		ClientServer.getSingleton().maritimeTrade(maritimeTrade);
	}

	@Override
	public void discardCards(DiscardCards discardCards) throws ServerException {
		ClientServer.getSingleton().discardCards(discardCards);
	}
	
	/**
	 * Starts the ServerPoller
	 */
	public void startPolling() {
		ServerPoller.setTargetServer(ClientServer.getSingleton());
		ServerPoller.start();
	}
	
	/**
	 * Stops the ServerPoller
	 */
	public void stopPolling() {
		ServerPoller.stop();
	}

	/**
	 * one last update to ensure that all controllers are alerted with the proper information
	 * and that the player waiting controller can close it's dialog before the MapController opens one
	 * @post the game is currently being played
	 */
	public void beginGame() {
		gameHasBegun = true;
		stopPolling();
		try {
			System.out.println("Game has begun");
			ModelFacade.forceUpdateModel(getModel(-1)); 
		} catch (ServerException e) {
			e.printStackTrace();
		}
		startPolling();
	}

	/**
	 * @post the game is not ready to begin, player is waiting for players to join
	 */
	public void beginWaitingForPlayersToJoin() {
		gameHasBegun = false;
		startPolling();
	}
	
	/**
	 * @return true if the game is currently being played<br>
	 * false if otherwise
	 */
	public boolean hasGameBegun() {
		return gameHasBegun;
	}

	
	private boolean playedSoldierCard;
	
	public void playedSoldierCard(boolean b) {
		playedSoldierCard = b;
	}
	
	public boolean hasPlayedSoldierCard() {
		return playedSoldierCard;
	}
	

	private boolean playedRoadBuildingCard;
	
	public void playedRoadBuildingCard(boolean b) {
		playedRoadBuildingCard = b;
	}
	
	public boolean hasPlayedRoadBuildingCard() {
		return playedRoadBuildingCard;
	}
}
