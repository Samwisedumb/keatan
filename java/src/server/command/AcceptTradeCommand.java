package server.command;

import server.facades.ServerMovesFacade;
import shared.exceptions.ServerException;
import shared.transferClasses.AcceptTrade;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class AcceptTradeCommand implements Command {
	
	int game;
	AcceptTrade tradeCommand;
	
	/**
	 * Creates a command that, when executed, will accept a trade.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public AcceptTradeCommand(int gameID, AcceptTrade transferObject) {
		game = gameID;
		tradeCommand = transferObject;
	}

	@Override
	public void execute() throws ServerException {
		ServerMovesFacade.getInstance().acceptTrade(game, tradeCommand);
	}

}
