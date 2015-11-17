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
import shared.transferClasses.YearOfPlenty;
import client.model.TransferModel;


/**
 * This class is a facade which will interperate requests recieved from a server and 
 * run commands based on the request recieved.
 * @author willvdb
 *
 */

public class ServerProxy {
	private static IServer server = null;
	
	/**
	 * Initializes this ServerProxy to target the given server.
	 * @pre targetServer must specify a valid server
	 * @param targetServer - the IServer to target
	 * @post ServerProxy will now target the given server
	 */
	public static void initialize(IServer targetServer) {
		server = targetServer;
	}

	//server functions
	public static void login(UserCredentials userCredentials) throws ServerException {
		server.login(userCredentials);
	}

	public static void register(UserCredentials userCredentials)
			throws ServerException {
		server.register(userCredentials);		
	}

	public static Game[] getGamesList() throws ServerException {
		return server.getGamesList();
	}

	public static CreateGameResponse createGame(CreateGameRequest createGameRequest)
			throws ServerException {
		return server.createGame(createGameRequest);
	}

	public static void joinGame(JoinGameRequest joinGameRequest)
			throws ServerException {
		server.joinGame(joinGameRequest);
	}

	public static TransferModel getModel(int version) throws ServerException {
		return server.getModel(version);
	}

	public static void addAI(AddAIRequest addAIRequest) throws ServerException {
		server.addAI(addAIRequest);
	}

	public static String[] listAITypes() throws ServerException {
		return server.listAITypes();
	}

	public static TransferModel sendChat(SendChat sendChat) throws ServerException {
		return server.sendChat(sendChat);
	}

	public static TransferModel rollDice(RollNumber rollNumber) throws ServerException {
		return server.rollDice(rollNumber);
	}

	public static TransferModel robPlayer(RobPlayer robPlayer) throws ServerException {
		return server.robPlayer(robPlayer);
	}

	public static TransferModel finishTurn(FinishTurn finishTurn) throws ServerException {
		return server.finishTurn(finishTurn);
	}

	public static TransferModel buyDevCard(BuyDevCard buyDevCard) throws ServerException {
		return server.buyDevCard(buyDevCard);
	}

	public static TransferModel yearOfPlenty(YearOfPlenty yearOfPlenty) throws ServerException {
		return server.yearOfPlenty(yearOfPlenty);
	}

	public static TransferModel roadBuilding(RoadBuilding roadBuilding)  throws ServerException {
		return server.roadBuilding(roadBuilding);
	}

	public static TransferModel soldier(Soldier soldier) throws ServerException {
		return server.soldier(soldier);
	}

	public static TransferModel monopoly(Monopoly monopoly) throws ServerException {
		return server.monopoly(monopoly);
	}

	public static TransferModel monument(Monument monument) throws ServerException {
		return server.monument(monument);
	}

	public static TransferModel buildRoad(BuildRoad buildRoad) throws ServerException {
		return server.buildRoad(buildRoad);
	}

	public static TransferModel buildSettlement(BuildSettlement buildSettlement)
			throws ServerException {
		return server.buildSettlement(buildSettlement);
	}

	public static TransferModel buildCity(BuildCity buildCity) throws ServerException {
		return server.buildCity(buildCity);
	}

	public static TransferModel offerTrade(OfferTrade offer) throws ServerException {
		return server.offerTrade(offer);
	}

	public static TransferModel respondToTrade(AcceptTrade acceptTrade)
			throws ServerException {
		return server.respondToTrade(acceptTrade);
	}

	public static TransferModel maritimeTrade(MaritimeTrade maritimeTrade)
			throws ServerException {
		return server.maritimeTrade(maritimeTrade);
	}

	public static TransferModel discardCards(DiscardCards discardCards)
			throws ServerException {
		return server.discardCards(discardCards);
	}
}
