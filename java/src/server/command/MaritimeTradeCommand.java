package server.command;

import server.facades.ServerMovesFacade;
import shared.exceptions.ServerException;
import shared.transferClasses.MaritimeTrade;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class MaritimeTradeCommand implements Command {
	
	int game;
	MaritimeTrade tradeCommand;
	
	/**
	 * Creates a command that, when executed, will initiate a maritime trade.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public MaritimeTradeCommand(int gameID, MaritimeTrade transferObject){
		game = gameID;
		tradeCommand = transferObject;
	}
	
	@Override
	public void execute() throws ServerException {
		ServerMovesFacade.getInstance().maritimeTrade(game, tradeCommand);
	}

}
