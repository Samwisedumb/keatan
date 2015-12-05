package server.command;

import server.facades.ServerMovesFacade;
import shared.exceptions.ServerException;
import shared.transferClasses.RobPlayer;

/**
 * RobPlayerCommand is a command that robs a player based on the given RobPlayer
 * object
 * @author djoshuac
 */
public class RobPlayerCommand implements Command {
	
	int game;
	RobPlayer robCommand;
	
	/**
	 * Creates a RobPlayerCommand to be executed
	 * @param gameID - the ID of the game for the command to be executed on
	 * @param rob - an object the contains the information to rob a player
	 */
	public RobPlayerCommand(int gameID, RobPlayer rob) {
		game = gameID;
		robCommand = rob;
	}

	@Override
	public void execute() throws ServerException {
		// TODO Auto-generated method stub
		ServerMovesFacade.getInstance().robPlayer(game, robCommand);
	}
}
