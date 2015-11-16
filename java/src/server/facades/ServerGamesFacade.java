package server.facades;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import client.exceptions.ServerException;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.Game;
import shared.transferClasses.JoinGameRequest;
import shared.transferClasses.UserCredentials;

/**
 * A facade that executes commands associated with user login and registration
 * and games creation, joining, and listing.
 * @author djoshuac
 */
public class ServerGamesFacade implements IGamesFacade {

	private static ServerGamesFacade instance = null;
	
	private Map<String, UserCredentials> users;
	
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
	
	public ServerGamesFacade() {
		users = new HashMap<String, UserCredentials>();
	}
	
	/**
	 * Log in to game client
	 * @param username the suggested username
	 * @param password the suggested password
	 * @
	 */
	@Override
	public boolean login(String username, String password) throws ServerException {		
		UserCredentials loginUser = users.get(username);
		
		if(loginUser == null) {
			throw new ServerException("User doesn't exist");
		}
		else if(password.equals(loginUser.getPassword())) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Register a user, adding it to the map
	 * @param newUsername the new username
	 * @param newPassword the new password
	 */
	@Override
	public void register(String username, String password) throws ServerException {
		if (users.get(username) != null) {
			throw new ServerException("User already exists");
		}
		else {
			
		}
	}

	@Override
	public List<Game> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateGameResponse create(CreateGameRequest makeGame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean joinGame(JoinGameRequest requestJoin) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
