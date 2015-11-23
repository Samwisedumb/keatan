package server.command;

import server.facades.ServerMovesFacade;
import shared.transferClasses.OfferTrade;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class OfferTradeCommand implements Command {
	
	int game;
	OfferTrade tradeCommand;
	
	/**
	 * Creates a command that, when executed, will offer a trade.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public OfferTradeCommand(int gameID, OfferTrade transferObject) {
		game = gameID;
		tradeCommand = transferObject;
	}

	@Override
	public void execute() {
		ServerMovesFacade.getInstance().offerTrade(game, tradeCommand);
	}

}
