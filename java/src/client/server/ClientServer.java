package client.server;

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
import client.model.TransferModel;

/**
 * This class is used to work as a singleton server and allow for dependency injection for
 * a target server for the Catan game.
 * @author djoshuac
 */
public class ClientServer implements IServer {
	private static IServer server = null;
	
	/**
	 * Returns the IServer singleton. If a target server has not been initialized,
	 * a ServerProxy is assigned as default
	 */
	public static IServer getSingleton() {
		if (server == null) {
			server = new ServerProxy(); // default target server
		}
		return server;
	}
	
	private ClientServer() { // private constructor prevents other classes from using it as the target server
		
	}
	
	/**
	 * Initializes the IServer singleton to target the given server to support dependency injection.
	 * @pre targetServer must specify a valid server
	 * @param targetServer - the IServer to target
	 * @post getSingleton() will now target the given server
	 */
	public static void setTargetServer(IServer targetServer) {
		server = targetServer;
	}

	@Override
	public UserInfo login(UserCredentials userCredentials) throws ServerException {
		return server.login(userCredentials);
	}

	@Override
	public void register(UserCredentials userCredentials)
			throws ServerException {
		server.register(userCredentials);		
	}

	@Override
	public Game[] getGamesList() throws ServerException {
		return server.getGamesList();
	}

	@Override
	public CreateGameResponse createGame(CreateGameRequest createGameRequest)
			throws ServerException {
		return server.createGame(createGameRequest);
	}

	@Override
	public Game joinGame(JoinGameRequest joinGameRequest) throws ServerException {
		return server.joinGame(joinGameRequest);
	}

	@Override
	public TransferModel getModel(int version) throws ServerException {
		return server.getModel(version);
	}

	@Override
	public void addAI(AddAIRequest addAIRequest) throws ServerException {
		server.addAI(addAIRequest);
	}

	@Override
	public String[] listAITypes() throws ServerException {
		return server.listAITypes();
	}

	@Override
	public void sendChat(SendChat sendChat) throws ServerException {
		server.sendChat(sendChat);
	}

	@Override
	public void rollDice(RollNumber rollNumber) throws ServerException {
		server.rollDice(rollNumber);
	}

	@Override
	public void robPlayer(RobPlayer robPlayer) throws ServerException {
		server.robPlayer(robPlayer);
	}

	@Override
	public void finishTurn(FinishTurn finishTurn) throws ServerException {
		server.finishTurn(finishTurn);
	}

	@Override
	public void buyDevCard(BuyDevCard buyDevCard) throws ServerException {
		server.buyDevCard(buyDevCard);
	}

	@Override
	public void yearOfPlenty(YearOfPlenty yearOfPlenty) throws ServerException {
		server.yearOfPlenty(yearOfPlenty);
	}

	@Override
	public void roadBuilding(RoadBuilding roadBuilding)  throws ServerException {
		server.roadBuilding(roadBuilding);
	}

	@Override
	public void soldier(Soldier soldier) throws ServerException {
		server.soldier(soldier);
	}

	@Override
	public void monopoly(Monopoly monopoly) throws ServerException {
		server.monopoly(monopoly);
	}

	@Override
	public void monument(Monument monument) throws ServerException {
		server.monument(monument);
	}

	@Override
	public void buildRoad(BuildRoad buildRoad) throws ServerException {
		server.buildRoad(buildRoad);
	}

	@Override
	public void buildSettlement(BuildSettlement buildSettlement)
			throws ServerException {
		server.buildSettlement(buildSettlement);
	}

	@Override
	public void buildCity(BuildCity buildCity) throws ServerException {
		server.buildCity(buildCity);
	}

	@Override
	public void offerTrade(OfferTrade offer) throws ServerException {
		server.offerTrade(offer);
	}

	@Override
	public void respondToTrade(AcceptTrade acceptTrade)
			throws ServerException {
		server.respondToTrade(acceptTrade);
	}

	@Override
	public void maritimeTrade(MaritimeTrade maritimeTrade)
			throws ServerException {
		server.maritimeTrade(maritimeTrade);
	}

	@Override
	public void discardCards(DiscardCards discardCards)
			throws ServerException {
		server.discardCards(discardCards);
	}
}
