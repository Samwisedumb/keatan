package server.command;

import server.facades.ServerMovesFacade;
import shared.exceptions.ServerException;
import shared.transferClasses.Monument;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class MonumentCommand implements Command {
	
	int game;
	Monument monumentCommand;
	
	/**
	 * Creates a command that, when executed, will update a player's monuments.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public MonumentCommand(int gameID, Monument transferObject) {
		game = gameID;
		monumentCommand = transferObject;
	}

	@Override
	public void execute() throws ServerException {
		ServerMovesFacade.getInstance().monument(game, monumentCommand);
	}

}
