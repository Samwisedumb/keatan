package server.facades;

import java.util.List;

import client.exceptions.ServerException;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.Game;
import shared.transferClasses.JoinGameRequest;

/**
 * A facade that executes commands associated with user login and registration
 * and games creation, joining, and listing.
 * @author djoshuac
 */
public interface IGamesFacade {

	/**
	 * @pre the application has just been started and is waiting for a login
	 * @param username presented username for verification
	 * @param password presented password to be associated with that username
	 * @return true if username and accompanying password are validated
	 * @throws ServerException 
	 * @post either the client must make another attempt to log in,
	 */
	public boolean login(String username, String password) throws ServerException;
	
	/**
	 * @pre the application has just been started and is waiting for a login or a new user registration 
	 * @param newUsername the new username
	 * @param newPassword the new password associated with that username
	 * @return true if the username and password are in acceptable format and the new user has successfully registered, false if otherwise
	 * @throws ServerException 
	 * @post either the new user is created and logged in, or the client must try a new username or password to register
	 */
	public boolean register(String newUsername, String newPassword) throws ServerException;
	
	/**
	 * @pre the client has logged in and is looking for a game to join or create
	 * @return a list of available games
	 * @post the client is able to see the games on their GUI
	 */
	public List<Game> list();
	
	/**
	 * @pre the client has logged in and wants to make a game
	 * @param makeGame the information for the game the player wants to make (the name, whether the hexes, ports and numbers should be randomized)
	 * @return the information for the game
	 * @post the game is created and the client (and other players) can join it
	 */
	public CreateGameResponse create(CreateGameRequest makeGame);
	
	/**
	 * @pre the client has picked a game to join and what color they want to be
	 * @param requestJoin the information for joining the game (its GameID and the requested color)
	 * @return true if the game was successfully joined, else false
	 * @post the client either joins the game or is returned to the choose games menu.
	 */
	public boolean joinGame(JoinGameRequest requestJoin);
}
