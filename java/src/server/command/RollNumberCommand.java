package server.command;

import shared.transferClasses.RollNumber;

/**
 * RollNumberCommand adds resources / changes the game state to discard / steal
 * based on the number rolled.
 */
public class RollNumberCommand implements Command {
	
	/**
	 * Creates a RollNumberCommand to be executed
	 * @param gameID - the game the roll is for
	 * @param roll - the required information to execute the roll
	 */
	public RollNumberCommand(int gameID, RollNumber roll) {
		
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
