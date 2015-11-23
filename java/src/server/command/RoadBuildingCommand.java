package server.command;

import server.facades.ServerMovesFacade;
import shared.transferClasses.RoadBuilding;

/**
 * RoadBuildingCommand is a command that builds two roads for a user
 * @author djoshuac
 */
public class RoadBuildingCommand implements Command {
	
	int game;
	RoadBuilding buildCommand;
	
	/**
	 * Creates a RoadBuildingCommand to be executed
	 * @param gameID - the ID of the game to build the roads in
	 * @param roadCard - the information for the road building command
	 */
	public RoadBuildingCommand(int gameID, RoadBuilding roadCard) {
		game = gameID;
		buildCommand = roadCard;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ServerMovesFacade.getInstance().roadBuilding(game, buildCommand);
	}
}
