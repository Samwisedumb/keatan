package server.command;

import shared.transferClasses.Monument;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class MonumentCommand implements Command {
	/**
	 * Creates a command that, when executed, will update a player's monuments.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public MonumentCommand(int gameID, Monument transferObject) {}

	@Override
	public void execute() {}

}
