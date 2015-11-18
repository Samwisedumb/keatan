package server.facades;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.model.ServerModel;
import shared.transferClasses.Password;
import shared.transferClasses.UserInfo;
import shared.transferClasses.Username;

/**
 * A singleton for storing server side data.<br>
 * <h6>Data</h6>
 * <li>User info</li>
 * <li>Game info</li>
 * @author djoshuac
 *
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
	}
	
	// Users
	private Map<Username, UserInfo> users;
	/**
	 * @return the next unique user id
	 * @pre none
	 * @post the returned user id is a unique id
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
	 * @post see return
	 */
	public UserInfo getAddInfo(Username username, Password password) {
		if (getUserInfo(username) != null) {
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
	/**
	 * @return the next unique game id
	 * @pre none
	 * @post the returned game id is a unique id
	 */
	private int getNextGameID() {
		return games.size();
	}
}
