package server.facades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shared.exceptions.ServerException;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.Game;
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

	private static int nextUserID;
	/**
	 * @return the next unique user id
	 * @pre none
	 * @post the returned user id is a unique id
	 */
	private int getNextUserID() {
		return nextUserID++;
	}
	
	private static int nextGameID;
	/**
	 * @return the next unique game id
	 * @pre none
	 * @post the returned game id is a unique id
	 */
	private int getNextGameID() {
		return nextGameID++;
	}
	
	private Map<Username, UserInfo> users;
	private List<Game> games;
	
	/**
	 * Creates a singleton of ServerGamesFacade
	 * @return the singleton
	 */
	public static ServerGamesFacade getInstance() {
		if(instance == null) {
			nextUserID = 0;
			nextGameID = 0;
			instance = new ServerGamesFacade();
		}
		
		return instance;
	}
	
	public ServerGamesFacade() {
		users = new HashMap<Username, UserInfo>();
		games = new ArrayList<Game>();
		games.add(new Game("Already Made Game", getNextGameID()));
	}
	
	@Override
	public void verifyUserInformation(UserInfo user) throws ServerException {
		UserInfo registeredUser = users.get(user.getUsername());
		
		if (!registeredUser.equals(user)) {
			throw new ServerException("Invalid user information");
		}
	}
	
	@Override
	public UserInfo loginUser(Username username, Password password) throws ServerException {
		UserInfo user = users.get(username);
		
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
		if (users.containsKey(username)) {
			throw new ServerException("Username is already in use");
		}
		else {
			users.put(username, new UserInfo(username, password, getNextUserID()));
		}
	}

	@Override
	public List<Game> listGames() {
		return games;
	}

	@Override
	public CreateGameResponse createGame(String gameName) {
		Game game = new Game(gameName, getNextGameID());
		
		games.add(game);
		
		return new CreateGameResponse(game.getTitle(), game.getId());
	}

	@Override
	public boolean joinGame(JoinGameRequest requestJoin) {
		// TODO Auto-generated method stub
		
		//GET INFO FROM COOKIES (THIS WON'T WORK)
		
		String playerName = "Stave";
		int playerID = -74;
		
		return ServerMovesFacade.getInstance().addPlayerToGame(requestJoin, playerName, playerID);
	}
	
}
