package server.command;

import server.facades.ServerMovesFacade;
import shared.transferClasses.Monopoly;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class MonopolyCommand implements Command {
	
	int game;
	Monopoly monopolyCommand;
	
	/**
	 * Creates a command that, when executed, will attempt to play a Monopoly card.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public MonopolyCommand(int gameID, Monopoly transferObject) {
		game = gameID;
		monopolyCommand = transferObject;
	}

	@Override
	public void execute() {
		ServerMovesFacade.getInstance().monopoly(game, monopolyCommand);
	}

}
