package server.command;

import shared.transferClasses.DiscardCards;

/**
 * DiscardCardsCommand overrides the Command execute() method to call the appropriate facade method. 
 * @author mr399
 *
 */
public class DiscardCardsCommand implements Command {
	/**
	 * Creates a command that, when executed, will cause a player to discard cards.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public DiscardCardsCommand(int gameID, DiscardCards transferObject){}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
}
