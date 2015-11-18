package shared.transferClasses;

import shared.exceptions.InvalidObjectException;

/**
 * An interface for dumb data holders.
 * @author djoshuac
 */
public abstract class ValidatableObject {
	/**
	 * Verifies that a dumb data holder is valid
	 * @pre see throws
	 * @post If no exception is thrown, the object is valid according to it's
	 * classes description.
	 * @throws InvalidObjectException when the transfer object is invalid - see class description
	 */
	public abstract void validate() throws InvalidObjectException;
}
