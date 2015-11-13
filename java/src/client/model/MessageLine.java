package client.model;

/**
 * This class contains a message a player wishes to send, along with the identity of the player
 */
public class MessageLine {
	private String message;
	private String source;
	
	/**
	 * @param message The message to send.
	 * @param source The identity of the message sender.
	 * @post The stored message and source match the given params.
	 */
	public MessageLine(String message, String source) {
		this.message = message;
		this.source = source;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}
