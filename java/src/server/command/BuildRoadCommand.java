package server.command;

import shared.transferClasses.BuildRoad;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class BuildRoadCommand implements Command {
	/**
	 * Creates a command that, when executed, will attempt to build a settlement.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public BuildRoadCommand(int gameID, BuildRoad transferObject) {}

	@Override
	public void execute() {}

}
