package client.exceptions;

/**
 * Happens when the client cannot connect to the server or the server has an error.
 * It has a property called reason that elaborates on why the connection could not be made.
 * We may make this so that each possible error for the server is an enum.
 * @author willvdb
 * @author djoshuac
 */
@SuppressWarnings("serial")
public class ServerException extends Throwable {
	private String reason;
	
	/**
	 * Constructs a ServerException with the given reason.
	 * @pre reason must not be null.
	 * @param reason - the reason the ServerException occurred
	 * @post A ServerExeption is created.
	 */
	public ServerException(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}
}
