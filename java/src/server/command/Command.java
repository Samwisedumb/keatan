package server.command;

import shared.exceptions.ServerException;

/**
 * An interface for the command pattern
 */
public interface Command {
	/**
	 * Executes the logic for this command
	 * @return an Object to be cast to the desired return object
	 * @throws ServerException 
	 */
	public void execute() throws ServerException;
}
