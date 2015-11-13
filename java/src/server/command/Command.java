package server.command;

/**
 * An interface for the command pattern
 */
public interface Command {
	/**
	 * Executes the logic for this command
	 * @return an Object to be cast to the desired return object
	 */
	public void execute();
}
