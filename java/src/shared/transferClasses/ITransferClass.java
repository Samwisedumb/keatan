package shared.transferClasses;

import shared.exceptions.InvalidObjectException;

/**
 * An interface for objects that are transfered to and from a server
 * @author djoshuac
 */
public interface ITransferClass {
	/**
	 * @pre none
	 * @post If no exception is thrown, the object is valid according to it's
	 * classes description.
	 * @throws InvalidObjectException when the transfer object is invalid
	 */
	public void validate() throws InvalidObjectException;
}
