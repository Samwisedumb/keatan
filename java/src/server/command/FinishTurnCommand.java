package server.command;

import server.facades.ServerMovesFacade;
import shared.exceptions.ServerException;
import shared.transferClasses.FinishTurn;

/**
 * FinishTurnCommand is a command that ends the turn of the specified player
 * @author djoshuac
 */
public class FinishTurnCommand implements Command {
	
	int game;
	FinishTurn finishCommand;
	
	/**
	 * Creates a FinishTurn command to be executed
	 * @param gameID - the ID of the game to end the turn in
	 * @param endTurn - the information for ending a turn
	 */
	public FinishTurnCommand(int gameID, FinishTurn endTurn) {
		game = gameID;
		finishCommand = endTurn;
	}

	@Override
	public void execute() throws ServerException {
		// TODO Auto-generated method stub
		ServerMovesFacade.getInstance().finishTurn(game, finishCommand);
	}
}
