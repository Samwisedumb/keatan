package server.command;

import shared.transferClasses.FinishTurn;

/**
 * FinishTurnCommand is a command that ends the turn of the specified player
 * @author djoshuac
 */
public class FinishTurnCommand implements Command {
	
	/**
	 * Creates a FinishTurn command to be executed
	 * @param gameID - the ID of the game to end the turn in
	 * @param endTurn - the information for ending a turn
	 */
	public FinishTurnCommand(int gameID, FinishTurn endTurn) {
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
