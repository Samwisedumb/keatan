package server.command;

import shared.transferClasses.MaritimeTrade;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class MaritimeTradeCommand implements Command {
	/**
	 * Creates a command that, when executed, will initiate a maritime trade.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public MaritimeTradeCommand(int gameID, MaritimeTrade transferObject){}
	
	@Override
	public void execute() {}

}
