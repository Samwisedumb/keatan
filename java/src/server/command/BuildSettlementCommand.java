package server.command;

import shared.transferClasses.BuildSettlement;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class BuildSettlementCommand implements Command {
	/**
	 * Creates a command that, when executed, will attempt to build a settlement.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public BuildSettlementCommand(int gameID, BuildSettlement transferObject) {}

	@Override
	public void execute() {}

}
