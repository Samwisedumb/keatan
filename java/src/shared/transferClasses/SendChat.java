package shared.transferClasses;

/**
 * This transfer class is for sending a single message in chat.
 * @param playerIndex the player to recieve the chat
 * @param content the content of the message
 * @author willvdb
 *
 */

public class SendChat {

	public SendChat(int index, String contentIn){
		playerIndex = index;
		content = contentIn;
	}

	private String type = "sendChat";
	private int playerIndex;
	private String content;

	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
