package client.exceptions;

/**
 * An exception to be thrown for an objects validate method
 * @author djoshuac
 */
@SuppressWarnings("serial")
public class InvalidObjectException extends Throwable  {
	private String reason;
	
	/**
	 * Constructs a InvalidObjectException with the given reason.
	 * @pre reason must not be null.
	 * @param reason - the reason the InvalidObjectException occurred
	 * @post A InvalidObjectException is created.
	 */
	public InvalidObjectException(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}
}
