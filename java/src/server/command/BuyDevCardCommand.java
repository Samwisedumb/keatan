package server.command;

import server.facades.ServerMovesFacade;
import shared.transferClasses.BuyDevCard;

/**
 * BuyDevCardCommand is a command that buys a dev card for a player
 * @author djoshuac
 */
public class BuyDevCardCommand implements Command {
	
	int game;
	BuyDevCard buyCommand;
	
	/**
	 * Creates a BuyDevCardCommand to be executed
	 * @param gameID - the id of the game to buy the dev card in
	 * @param buyDev - the information for buying a dev card
	 */
	public BuyDevCardCommand(int gameID, BuyDevCard buyDev) {
		game = gameID;
		buyCommand = buyDev;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ServerMovesFacade.getInstance().buyDevCard(game, buyCommand);
	}
}
