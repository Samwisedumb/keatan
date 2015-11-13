package server.command;

import shared.transferClasses.OfferTrade;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class OfferTradeCommand implements Command {
	/**
	 * Creates a command that, when executed, will offer a trade.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	OfferTradeCommand(int gameID, OfferTrade transferObject){}

	@Override
	public void execute() {}

}
