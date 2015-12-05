package server.command;

import server.facades.ServerMovesFacade;
import shared.exceptions.ServerException;
import shared.transferClasses.RollNumber;

/**
 * RollNumberCommand adds resources / changes the game state to discard / steal
 * based on the number rolled.
 */
public class RollNumberCommand implements Command {
	
	int game;
	RollNumber rollCommand;
	
	/**
	 * Creates a RollNumberCommand to be executed
	 * @param gameID - the game the roll is for
	 * @param roll - the required information to execute the roll
	 */
	public RollNumberCommand(int gameID, RollNumber roll) {
		game = gameID;
		rollCommand = roll;
	}
	
	@Override
	public void execute() throws ServerException {
		// TODO Auto-generated method stub
		ServerMovesFacade.getInstance().rollNumber(game, rollCommand);
	}
}
