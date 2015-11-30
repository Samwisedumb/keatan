package server.command;

import server.facades.ServerMovesFacade;
import shared.transferClasses.YearOfPlenty;

/**
 * YearOfPlentyCommand is a command that gives a player two resources
 * @author djoshuac
 */
public class YearOfPlentyCommand implements Command {

	int game;
	YearOfPlenty plentyCommand;
	
	/**
	 * Creates a YearOfPlentyCommand to be executed
	 * @param gameID - the id of the game to execute the year of plenty on
	 * @param plentyCard - the information for executing the year of plenty command
	 */
	public YearOfPlentyCommand(int gameID, YearOfPlenty plentyCard) {
		game = gameID;
		plentyCommand = plentyCard;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ServerMovesFacade.getInstance().yearOfPlenty(game, plentyCommand);
	}

}
