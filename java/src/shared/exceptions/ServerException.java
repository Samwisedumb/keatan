package shared.exceptions;

/**
 * Happens when the client cannot connect to the server or the server has an error.
 * It has a property called reason that elaborates on why the connection could not be made.
 * @author willvdb
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
