package server.facades;

import java.util.List;

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
	
	/**
	 * A private constructor for the ServerGamesFacade singleton
	 */
	private ServerGamesFacade() {
		
	}
	
	@Override
	public void verifyUserInformation(UserInfo user) throws ServerException {
		UserInfo registeredUser = ServerData.getInstance().getUserInfo(user.getUsername());
		
		if (!registeredUser.equals(user)) {
			throw new ServerException("Invalid user information");
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
		UserInfo user = ServerData.getInstance().getUserInfo(username);
		
		if (user != null) {
			throw new ServerException("Username is already in use");
		}
		else {
			ServerData.getInstance().addUser(username, password);
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
	public void joinGame(UserInfo user, JoinGameRequest requestJoin) throws ServerException{
		Game game = ServerData.getInstance().getGameInfo(requestJoin.getId());
		
		if (game == null) {
			throw new ServerException("Invalid gameID");
		}
		if (game.isFull()) {
			throw new ServerException("Requsted game is full");
		}
		
		game.addPlayer(new GetPlayer(requestJoin.getColor(), user.getUsernameString(), user.getUserID()));
	}
}
