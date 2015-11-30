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
 * @author djoshuac and willvdb
 *
 */
public interface IServer {

	/**
	 * 
	 * @param userCredentials - the credentials of the user
	 * @return 
	 * @throws ServerException if cannot connect to server or credentials are invalid
	 */
	abstract public UserInfo login(UserCredentials userCredentials)
			throws ServerException;
	/**
	 * 
	 * @param userCredentials - the credentials of the user
	 * @throws ServerException if cannot connect to server or credentials are invalid
	 */
	abstract public void register(UserCredentials userCredentials)
			throws ServerException;

	/**
	 * 
	 * @return
	 * @throws ServerException
	 */
	abstract public Game[] getGamesList()
			throws ServerException;

	/**
	 * 
	 * @param createGameRequest
	 * @return
	 * @throws ServerException
	 */
	abstract public CreateGameResponse createGame(CreateGameRequest createGameRequest)
			throws ServerException;
	
	/**
	 * 
	 * @param joinGameRequest
	 * @return 
	 * @throws ServerException
	 */
	abstract public Game joinGame(JoinGameRequest joinGameRequest) throws ServerException;

	/**
	 * 
	 * @param version
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel getModel(int version)
			throws ServerException;
	
	/**
	 * 
	 * @param addAIRequest
	 * @throws ServerException
	 */
	abstract public void addAI(AddAIRequest addAIRequest)
			throws ServerException;

	/**
	 * 
	 * @return
	 * @throws ServerException
	 */
	abstract public String[] listAITypes()
			throws ServerException;
	
	/**
	 * 
	 * @param sendChat
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel sendChat(SendChat sendChat)
			throws ServerException;
	
	/**
	 * 
	 * @param rollNumber
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel rollDice(RollNumber rollNumber)
			throws ServerException;

	/**
	 * 
	 * @param robPlayer
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel robPlayer(RobPlayer robPlayer)
			throws ServerException;

	/**
	 * 
	 * @param finishTurn
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel finishTurn(FinishTurn finishTurn)
			throws ServerException;
	
	/**
	 * 
	 * @param buyDevCard
	 * @return
	 * @throws ServerException=
	 */
	abstract public TransferModel buyDevCard(BuyDevCard buyDevCard)
			throws ServerException;

	/**
	 * 
	 * @param yearOfPlenty
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel yearOfPlenty(YearOfPlenty yearOfPlenty)
			throws ServerException;

	/**
	 * 
	 * @param roadBuilding
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel roadBuilding(RoadBuilding roadBuilding)
			throws ServerException;

	/**
	 * 
	 * @param soldier
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel soldier(Soldier soldier)
			throws ServerException;

	/**
	 * 
	 * @param monopoly
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel monopoly(Monopoly monopoly)
			throws ServerException;

	/**
	 * 
	 * @param monument
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel monument(Monument monument)
			throws ServerException;
	
	/**
	 * 
	 * @param buildRoad
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel buildRoad(BuildRoad buildRoad)
			throws ServerException;

	/**
	 * 
	 * @param buildSettlement
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel buildSettlement(BuildSettlement buildSettlement)
			throws ServerException;

	/**
	 * 
	 * @param buildCity
	 * @return
	 * @throws ServerException
	 * @throws IllegalActionException
	 */
	abstract public TransferModel buildCity(BuildCity buildCity)
			throws ServerException;

	/**
	 * 
	 * @param offer
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel offerTrade(OfferTrade offer)
			throws ServerException;
	
	/**
	 * 
	 * @param acceptTrade
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel respondToTrade(AcceptTrade acceptTrade)
			throws ServerException;

	/**
	 * 
	 * @param maritimeTrade
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel maritimeTrade(MaritimeTrade maritimeTrade)
			throws ServerException;

	/**
	 * 
	 * @param discardCards
	 * @return
	 * @throws ServerException
	 */
	abstract public TransferModel discardCards(DiscardCards discardCards)
			throws ServerException;
}