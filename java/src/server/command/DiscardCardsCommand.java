package server.command;

import server.facades.ServerMovesFacade;
import shared.exceptions.ServerException;
import shared.transferClasses.DiscardCards;

/**
 * DiscardCardsCommand overrides the Command execute() method to call the appropriate facade method. 
 * @author mr399
 *
 */
public class DiscardCardsCommand implements Command {
	
	int game;
	DiscardCards discardCommand;
	
	/**
	 * Creates a command that, when executed, will cause a player to discard cards.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public DiscardCardsCommand(int gameID, DiscardCards transferObject){
		game = gameID;
		discardCommand = transferObject;
	}
	
	@Override
	public void execute() throws ServerException {
		// TODO Auto-generated method stub
		ServerMovesFacade.getInstance().discardCards(game, discardCommand);
	}
	
}
