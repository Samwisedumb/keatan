package server.command;

import server.facades.ServerMovesFacade;
import shared.exceptions.ServerException;
import shared.transferClasses.BuildSettlement;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class BuildSettlementCommand implements Command {
	
	int game;
	BuildSettlement buildCommand;
	
	/**
	 * Creates a command that, when executed, will attempt to build a settlement.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	public BuildSettlementCommand(int gameID, BuildSettlement transferObject) {
		game = gameID;
		buildCommand = transferObject;
	}

	@Override
	public void execute() throws ServerException {
			ServerMovesFacade.getInstance().buildSettlement(game, buildCommand);
	}

}
