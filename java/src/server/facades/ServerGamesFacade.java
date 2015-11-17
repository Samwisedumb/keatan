package server.facades;

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
	private static int nextGameID;
	
	private Map<Username, UserInfo> users;
	
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
	}
	
	@Override
	public void verifyUserInformation(UserInfo user) throws ServerException {
		UserInfo registeredUser = users.get(user.getUsername());
		
		if (registeredUser.equals(user)) {
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
			users.put(username, new UserInfo(username, password, nextUserID++));
		}
	}

	@Override
	public List<Game> list() {
		// TODO Auto-generated method stub
		return ServerMovesFacade.getInstance().getGameTags();
	}

	@Override
	public CreateGameResponse create(CreateGameRequest makeGame) {
		// TODO Auto-generated method stub
		CreateGameResponse gameMade = ServerMovesFacade.getInstance().addGame(makeGame);
		
		return gameMade;
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
