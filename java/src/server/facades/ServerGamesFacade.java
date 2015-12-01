package server.facades;

import java.util.List;

import client.model.TransferModel;
import server.model.ServerModel;
import shared.exceptions.ServerException;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.Game;
import shared.transferClasses.GetPlayer;
import shared.transferClasses.JoinGameRequest;
import shared.transferClasses.Password;
import shared.transferClasses.UserInfo;
import shared.transferClasses.Username;

/**
 * A facade that executes commands associated with user login and registration
 * and games creation, joining, and listing.
 * @author djoshuac
 */
public class ServerGamesFacade implements IGamesFacade {
	private static ServerGamesFacade instance = null;
	
	/**
	 * Creates a singleton of ServerGamesFacade
	 * @return the singleton
	 */
	public static ServerGamesFacade getInstance() {
		if(instance == null) {
			instance = new ServerGamesFacade();
		}
		
		return instance;
	}
	
	@Override
	public void verifyUserInformation(UserInfo user) throws ServerException {
		UserInfo registeredUser = ServerData.getInstance().getUserInfo(user.getUsername());
		
		if (!registeredUser.equals(user)) {
			throw new ServerException("Invalid user information");
		}
	}
	
	@Override
	public void verifyUserIsInGame(int gameID, UserInfo user) throws ServerException {
		if (!ServerData.getInstance().getGameInfo(gameID).hasPlayer(user.getUserID())) {
			throw new ServerException("User is not in specified game");
		}
	}

	@Override
	public UserInfo loginUser(Username username, Password password) throws ServerException {
		UserInfo user = ServerData.getInstance().getUserInfo(username);
		
		if (user == null) {
			throw new ServerException("User doesn't exist");
		}
		else if (!user.getPassword().equals(password)) {
			throw new ServerException("Incorrect password");
		}
		
		return user;
	}
	
	@Override
	public void registerUser(Username username, Password password) throws ServerException {
		if (ServerData.getInstance().addUser(username, password) == null) {
			throw new ServerException("Username is already in use");
		}
	}

	@Override
	public List<Game> listGames() {
		return ServerData.getInstance().getGameInfoList();
	}

	@Override
	public CreateGameResponse createGame(CreateGameRequest gameMaker) {
		ServerModel gameModel = new ServerModel(gameMaker.isRandomTiles(),gameMaker.isRandomNumbers(),
				gameMaker.isRandomPorts(), gameMaker.getGameName());
		
		int gameID = ServerData.getInstance().addGame(gameMaker.getGameName(), gameModel);
		
		return new CreateGameResponse(gameMaker.getGameName(), gameID);
	}

	@Override
	public Game joinGame(UserInfo user, JoinGameRequest requestJoin) throws ServerException{
		Game game = ServerData.getInstance().getGameInfo(requestJoin.getGameID());
		
		if (game == null) {
			throw new ServerException("Invalid gameID");
		}
		else if (game.isFull() == true) {
			throw new ServerException("Requsted game is full");
		}
		else if(game.hasColor(requestJoin.getColor()) == true) {
			throw new ServerException("That color is already in the game");
		}
		
		
		ServerData.getInstance().addPlayer(requestJoin.getGameID(), requestJoin.getColor(), user.getUsernameString(), user.getUserID());
		
		return game;
	}

	@Override
	public TransferModel getTransferModel(int gameID) throws ServerException {
		return ServerData.getInstance().getGameModel(gameID).getTransferModel();
	}
}



