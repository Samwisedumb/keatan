package server.command;

import shared.transferClasses.Soldier;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class SoldierCommand implements Command {
	/**
	 * Creates a command that, when executed, will attempt to play a Soldier card.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public SoldierCommand(int gameID, Soldier transferObject) {}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
