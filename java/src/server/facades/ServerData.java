package server.facades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.model.ServerModel;
import shared.definitions.CatanColor;
import shared.transferClasses.Game;
import shared.transferClasses.GetPlayer;
import shared.transferClasses.Password;
import shared.transferClasses.UserInfo;
import shared.transferClasses.Username;

/**
 * A singleton for storing server side data such as:<br>
 * <li>User info</li>
 * <li>Game info</li>
 */
public class ServerData {
	private static ServerData instance;
	
	public static ServerData getInstance() {
		if (instance == null) {
			instance = new ServerData();
		}
		return instance;
	}

	private ServerData() {
		users = new HashMap<Username, UserInfo>();

		games = new ArrayList<ServerModel>();
		gameTags = new ArrayList<Game>();
	}
	
	// Users
	private Map<Username, UserInfo> users;
	/**
	 * @return the next unique user id
	 * @pre none
	 * @post the returned user id is is unique from all other users
	 */
	private int getNextUserID() {
		return users.size();
	}
	
	/**
	 * Returns the UserInformation of an added User given a Username
	 * @param username - the username in question
	 * @return user info for the given username, null if user does not exist
	 * @pre to get the UserInfo, the user must first be added with addUserInfo
	 * @post see return
	 */
	public UserInfo getUserInfo(Username username) {
		return users.get(username);
	}
	
	/**
	 * Adds a User's Information to be gotten later
	 * @param username - the username for the user
	 * @param password - the password for the user
	 * @return user info for the given username, null if username is already in use
	 * @pre none
	 * @post the user is assigned a unique userID
	 */
	public UserInfo addUser(Username username, Password password) {
		if (getUserInfo(username) == null) {
			UserInfo user = new UserInfo(username, password, getNextUserID());
			users.put(username, user);
			return user;
		}
		else {
			return null;
		}
	}

	// Games
	private List<ServerModel> games;
	private List<Game> gameTags;
	
	/**
	 * @return the next unique game id
	 * @pre none
	 * @post the returned game id is unique from all other games
	 */
	private int getNextGameID() {
		return games.size();
	}

	/**
	 * Adds a game's information and model to the server data
	 * @param gameName - the name of the game to add
	 * @param gameModel - the model of the game to add
	 * @return the game id assigned to the added game
	 * @pre gameModel and gameName must not be null
	 * @post the returned gameID is a unique game id
	 */
	public int addGame(String gameName, ServerModel gameModel) {
		int gameID = getNextGameID();
		
		gameTags.add(new Game(gameName, gameID));
		games.add(gameModel);
		
		return gameID;
	}
	
	/**
	 * Returns the game information for the requested game
	 * @pre none
	 * @post see return
	 * @return The game information associated with the given gameID.
	 * Null if the given gameID is not associated with a game
	 */
	public Game getGameInfo(int gameID) {
		if (gameID < 0 || gameID > gameTags.size()) {
			return null;
		}
		return gameTags.get(gameID);
	}

	/**
	 * Returns a list of game information added to the server
	 * @return a list of game information
	 * @pre none
	 * @post see return, list can be empty
	 */
	public List<Game> getGameInfoList() {
		return gameTags;
	}
	
	/**
	 * Returns the specified game model
	 * @param gameID - the gameID of the requested game
	 * @pre none
	 * @return the game with the given ID<br>
	 * null if no game has the given ID
	 * @post see return
	 */
	public ServerModel getGameModel(int gameID) {
		if (gameID >= games.size() || gameID < 0) {
			return null;
		}
		else {
			return games.get(gameID);
		}
	}

	/**
	 * @post adds a player to a game
	 * @pre the gameID must be a valid game id
	 * @param gameID - the gameID
	 * @param color - the color of the player
	 * @param username - the username of the player
	 * @param userID - the userID
	 */
	public void addPlayer(int gameID, CatanColor color, String username, int userID) {
		games.get(gameID).addPlayer(color, username, userID);
		gameTags.get(gameID).addPlayer(new GetPlayer(color, username, userID));
	}
}
