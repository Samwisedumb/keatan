package client.model;

/**
 * This class contains a message a player wishes to send, along with the identity of the player
 */
public class MessageLine {
	private String message;
	private int playerIndex;
	
	/**
	 * @param message - The message to send.
	 * @param playerIndex - the index of the player who sent it
	 * @post The stored message and source match the given params.
	 */
	public MessageLine(String message, int playerIndex) {
		this.message = message;
		this.playerIndex = playerIndex;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getSource() {
		return playerIndex;
	}
	public void setSource(int playerIndex) {
		this.playerIndex = playerIndex;
	}
}
