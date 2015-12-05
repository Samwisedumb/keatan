package server.command;

import server.facades.ServerMovesFacade;
import shared.exceptions.ServerException;
import shared.transferClasses.BuildCity;

/**
 * Overrides the Command's execute() method, which calls the appropriate facade method.
 * @author mr399
 *
 */
public class BuildCityCommand implements Command {
	/**
	 * Creates a command that, when executed, will attempt to build a city.
	 * @param gameID The ID of the game the command applies to
	 * @param transferObject The parameters for the command
	 */
	int game;
	BuildCity buildCommand;
	
	public BuildCityCommand(int gameID, BuildCity transferObject){
		game = gameID;
		buildCommand = transferObject;
	}

	@Override
	public void execute() throws ServerException {
			ServerMovesFacade.getInstance().buildCity(game, buildCommand);
	}

}
