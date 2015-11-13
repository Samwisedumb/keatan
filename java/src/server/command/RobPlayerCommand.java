package server.command;

import shared.transferClasses.RobPlayer;

/**
 * RobPlayerCommand is a command that robs a player based on the given RobPlayer
 * object
 * @author djoshuac
 */
public class RobPlayerCommand implements Command {
	
	/**
	 * Creates a RobPlayerCommand to be executed
	 * @param gameID - the ID of the game for the command to be executed on
	 * @param rob - an object the contains the information to rob a player
	 */
	public RobPlayerCommand(int gameID, RobPlayer rob) {
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
