package client.server;
import shared.exceptions.IllegalActionException;
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
 * This interface is used by a client to send requests to a Settlers of Catan Server.
 * @author willvdb
 *
 */
public interface IServer {

	/**
	 * 
	 * @param userCredentials - the credentials of the user
	 * @return 
	 * @throws ServerException if cannot connect to server or credentials are invalid
	 */
	UserInfo login(UserCredentials userCredentials)
			throws ServerException;
	/**
	 * 
	 * @param userCredentials - the credentials of the user
	 * @throws ServerException if cannot connect to server or credentials are invalid
	 */
	void register(UserCredentials userCredentials)
			throws ServerException;

	/**
	 * 
	 * @return
	 * @throws ServerException
	 */
	Game[] getGamesList()
			throws ServerException;

	/**
	 * 
	 * @param createGameRequest
	 * @return
	 * @throws ServerException
	 */
	CreateGameResponse createGame(CreateGameRequest createGameRequest)
			throws ServerException;
	
	/**
	 * 
	 * @param joinGameRequest
	 * @return 
	 * @throws ServerException
	 */
	Game joinGame(JoinGameRequest joinGameRequest) throws ServerException;

	/**
	 * 
	 * @param version
	 * @return
	 * @throws ServerException
	 */
	TransferModel getModel(int version)
			throws ServerException;
	
	/**
	 * 
	 * @param addAIRequest
	 * @throws ServerException
	 */
	void addAI(AddAIRequest addAIRequest)
			throws ServerException;

	/**
	 * 
	 * @return
	 * @throws ServerException
	 */
	String[] listAITypes()
			throws ServerException;
	
	/**
	 * 
	 * @param sendChat
	 * @return
	 * @throws ServerException
	 */
	void sendChat(SendChat sendChat)
			throws ServerException;
	
	/**
	 * 
	 * @param rollNumber
	 * @return
	 * @throws ServerException
	 */
	void rollDice(RollNumber rollNumber)
			throws ServerException;

	/**
	 * 
	 * @param robPlayer
	 * @return
	 * @throws ServerException
	 */
	void robPlayer(RobPlayer robPlayer)
			throws ServerException;

	/**
	 * 
	 * @param finishTurn
	 * @return
	 * @throws ServerException
	 */
	void finishTurn(FinishTurn finishTurn)
			throws ServerException;
	
	/**
	 * 
	 * @param buyDevCard
	 * @return
	 * @throws ServerException=
	 */
	void buyDevCard(BuyDevCard buyDevCard)
			throws ServerException;

	/**
	 * 
	 * @param yearOfPlenty
	 * @return
	 * @throws ServerException
	 */
	void yearOfPlenty(YearOfPlenty yearOfPlenty)
			throws ServerException;

	/**
	 * 
	 * @param roadBuilding
	 * @return
	 * @throws ServerException
	 */
	void roadBuilding(RoadBuilding roadBuilding)
			throws ServerException;

	/**
	 * 
	 * @param soldier
	 * @return
	 * @throws ServerException
	 */
	void soldier(Soldier soldier)
			throws ServerException;

	/**
	 * 
	 * @param monopoly
	 * @return
	 * @throws ServerException
	 */
	void monopoly(Monopoly monopoly)
			throws ServerException;

	/**
	 * 
	 * @param monument
	 * @return
	 * @throws ServerException
	 */
	void monument(Monument monument)
			throws ServerException;
	
	/**
	 * 
	 * @param buildRoad
	 * @return
	 * @throws ServerException
	 */
	void buildRoad(BuildRoad buildRoad)
			throws ServerException;

	/**
	 * 
	 * @param buildSettlement
	 * @return
	 * @throws ServerException
	 */
	void buildSettlement(BuildSettlement buildSettlement)
			throws ServerException;

	/**
	 * 
	 * @param buildCity
	 * @return
	 * @throws ServerException
	 * @throws IllegalActionException
	 */
	void buildCity(BuildCity buildCity)
			throws ServerException;

	/**
	 * 
	 * @param offer
	 * @return
	 * @throws ServerException
	 */
	void offerTrade(OfferTrade offer)
			throws ServerException;
	
	/**
	 * 
	 * @param acceptTrade
	 * @return
	 * @throws ServerException
	 */
	void respondToTrade(AcceptTrade acceptTrade)
			throws ServerException;

	/**
	 * 
	 * @param maritimeTrade
	 * @return
	 * @throws ServerException
	 */
	void maritimeTrade(MaritimeTrade maritimeTrade)
			throws ServerException;

	/**
	 * 
	 * @param discardCards
	 * @return
	 * @throws ServerException
	 */
	void discardCards(DiscardCards discardCards)
			throws ServerException;
}