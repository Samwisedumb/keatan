package client.server;

import java.util.List;

import shared.exceptions.ServerException;
import shared.json.Converter;
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

public class ClientServer implements IServer {
	private ClientCommunicator communicator;
	
	private String userCookie;
	private String gameCookie;
	
	public ClientServer (String host, String port) {
		communicator = new ClientCommunicator(host + ":" + port);
	}
	
	/**
	 * Sets the userCookie
	 * @throws ServerException when the userCookie does not exist in communicator's response headers
	 */
	private void setUserCookie() throws ServerException {
		List<String> cookie = communicator.getResponseHeadersForLastSend().get("Set-user");
		
		if (cookie == null || cookie.size() < 1) {
			throw new ServerException("Server failed to give user cookie");
		}
		else {
			userCookie = cookie.get(0);
		}
	}
	
	/**
	 * Sets the gameCookie
	 * @throws ServerException when the gameCookie does not exist in communicator's response headers
	 */
	private void setGameCookie() throws ServerException {
		List<String> cookie = communicator.getResponseHeadersForLastSend().get("Set-game");
		
		if (cookie == null || cookie.size() < 1) {
			throw new ServerException("Server failed to give game cookie");
		}
		else {
			gameCookie = cookie.get(0);
		}
	}
	
	/**
	 * Adds the userCookie to the next server request
	 * @pre userCookie must not be null
	 * @post the userCookie is set to the user header
	 * @throws ServerException if userCookie is null
	 */
	private void addUserCookieToNextRequest() throws ServerException {
		if (userCookie != null) {
			communicator.addRequestHeader("User", userCookie);
		}
		else {
			throw new ServerException("User cookie is null");
		}
	}
	
	/**
	 * Adds the gameCookie to the next server request
	 * @pre gameCookie must not be null
	 * @post the gameCookie is set to the game header
	 * @throws ServerException if gameCookie is null
	 */
	private void addGameCookieToNextRequest() throws ServerException {
		if (gameCookie != null) {
			communicator.addRequestHeader("Game", gameCookie);
		}
		else {
			throw new ServerException("Game cookie is null");
		}
	}

	@Override
	public void register(UserCredentials userCredentials) throws ServerException {
		communicator.send("/user/register", userCredentials);
	}
	
	@Override
	public UserInfo login(UserCredentials userCredentials) throws ServerException {
		communicator.send("/user/login", userCredentials);
		setUserCookie();
		return Converter.fromJson(userCookie, UserInfo.class);
	}

	@Override
	public Game[] getGamesList() throws ServerException {
		addUserCookieToNextRequest();
		return communicator.send("/games/list", null, Game[].class);
	}

	@Override
	public CreateGameResponse createGame(CreateGameRequest createGameRequest) throws ServerException {
		addUserCookieToNextRequest();
		return communicator.send("/games/create", createGameRequest, CreateGameResponse.class);
	}

	@Override
	public Game joinGame(JoinGameRequest joinGameRequest) throws ServerException {
		addUserCookieToNextRequest();
		Game game = communicator.send("/games/join", joinGameRequest, Game.class);
		setGameCookie();
		return game;
	}

	@Override
	public TransferModel getModel(int version) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/game/model", version, TransferModel.class);
	}

	@Override
	public void addAI(AddAIRequest addAIRequest) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		communicator.send("/game/addAI", addAIRequest);
	}

	@Override
	public String[] listAITypes() throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/game/listAI", null, String[].class);
	}

	@Override
	public TransferModel sendChat(SendChat sendChat) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/sendChat", sendChat, TransferModel.class);
	}

	@Override
	public TransferModel rollDice(RollNumber rollNumber) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/rollDice", rollNumber, TransferModel.class);		
	}

	@Override
	public TransferModel robPlayer(RobPlayer robPlayer) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/robPlayer", robPlayer, TransferModel.class);
	}

	@Override
	public TransferModel finishTurn(FinishTurn finishTurn) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/finishTurn", finishTurn, TransferModel.class);
	}

	@Override
	public TransferModel buyDevCard(BuyDevCard buyDevCard) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/buyDevCard", buyDevCard, TransferModel.class);
	}

	@Override
	public TransferModel yearOfPlenty(YearOfPlenty yearOfPlenty) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/yearOfPlenty", yearOfPlenty, TransferModel.class);
	}

	@Override
	public TransferModel roadBuilding(RoadBuilding roadBuilding) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/Road_Building", roadBuilding, TransferModel.class);
	}

	@Override
	public TransferModel soldier(Soldier soldier) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/Soldier", soldier, TransferModel.class);
	}

	@Override
	public TransferModel monopoly(Monopoly monopoly) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/Monopoly", monopoly, TransferModel.class);
	}

	@Override
	public TransferModel monument(Monument monument) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/Monument", monument, TransferModel.class);
	}

	@Override
	public TransferModel buildRoad(BuildRoad buildRoad) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/buildRoad", buildRoad, TransferModel.class);
	}

	@Override
	public TransferModel buildSettlement(BuildSettlement buildSettlement) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/buildSettlement", buildSettlement, TransferModel.class);
	}

	@Override
	public TransferModel buildCity(BuildCity buildCity) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/buildCity", buildCity, TransferModel.class);
	}

	@Override
	public TransferModel offerTrade(OfferTrade offer) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/offerTrade", offer, TransferModel.class);
	}

	@Override
	public TransferModel respondToTrade(AcceptTrade acceptTrade) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/acceptTrade", acceptTrade, TransferModel.class);
	}

	@Override
	public TransferModel maritimeTrade(MaritimeTrade maritimeTrade) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/maritimeTrade", maritimeTrade, TransferModel.class);
	}

	@Override
	public TransferModel discardCards(DiscardCards discardCards) throws ServerException {
		addUserCookieToNextRequest();
		addGameCookieToNextRequest();
		return communicator.send("/moves/discardCards", discardCards, TransferModel.class);
	}

}
