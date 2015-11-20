package server.facades;

import java.util.List;

import client.model.TransferModel;
import shared.exceptions.ServerException;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.Game;
import shared.transferClasses.JoinGameRequest;
import shared.transferClasses.Password;
import shared.transferClasses.UserCredentials;
import shared.transferClasses.UserInfo;
import shared.transferClasses.Username;

/**
 * A facade that executes commands associated with user login and registration
 * and games creation, joining, and listing.
 * @author djoshuac
 */
public interface IGamesFacade {
	/**
	 * Verifies the given user information is correct
	 * @param user - the user information to verify
	 * @pre the user must be registered, the user's password must be what it is
	 * registered to be, the userID must match what it is registered to be
	 * @post The user information is verified to be correct.
	 * @throws ServerException when one of the preconditions is violated
	 */
	public void verifyUserInformation(UserInfo user) throws ServerException;
	
	/**
	 * Logs in a user
	 * @param username - the username to validate
	 * @param password - the password to validate
	 * @return 
	 * @pre The user with the given username must be registered<br>
	 * The password must be the correct password for the given username
	 * @post The preconditions are guaranteed, and the user is logged in
	 * @throws ServerException when the user is not registered or when the password is incorrect
	 */
	public UserInfo loginUser(Username username, Password password) throws ServerException;
	
	/**
	 * Registers the user so the user may login
	 * @param username - the username the user wishes to use
	 * @param password - the password the user wishes to use
	 * @pre username cannot already be in use
	 * @post the user may now use the username and password to login
	 * @throws ServerException when the username is already taken
	 */
	public void registerUser(Username username, Password password) throws ServerException;
	
//	/**
//	 * @pre the client has logged in and is looking for a game to join or create
//	 * @return a list of available games
//	 * @post the client is able to see the games on their GUI
//	 */
	public List<Game> listGames();
	
//	/**
//	 * @pre the client has logged in and wants to make a game
//	 * @param makeGame the information for the game the player wants to make (the name, whether the hexes, ports and numbers should be randomized)
//	 * @return the information for the game
//	 * @post the game is created and the client (and other players) can join it
//	 */
	public CreateGameResponse createGame(CreateGameRequest gameMaker);
	
//	/**
//	 * @pre the client has picked a game to join and what color they want to be
//	 * @param requestJoin the information for joining the game (its GameID and the requested color)
//	 * @return true if the game was successfully joined, else false
//	 * @post the client either joins the game or is returned to the choose games menu.
//	 */
	public void joinGame(UserInfo user, JoinGameRequest requestJoin) throws ServerException;

	public void verifyUserIsInGame(int gameID, UserInfo user) throws ServerException;
	
	public TransferModel getTransferModel(int gameID) throws ServerException;
}
