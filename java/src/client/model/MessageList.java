package client.model;

import java.util.ArrayList;
import java.util.List;
/**
 * The MessageList class is a simple container to hold MessageLine objects. 
 *
 */
public class MessageList {
	
	private List<MessageLine> lines;
	
	public MessageList() {
		lines = new ArrayList<MessageLine>();
	}

	public List<MessageLine> getLines() {
		return lines;
	}

	public void setLines(List<MessageLine> lines) {
		this.lines = lines;
	}

	/**
	 * @pre lines must not be null
	 * @param line is the MessageLine the user wishes to add
	 * @post The most recent MessageLine in MessageList.getLines() is line.
	 */
	public void addLine(MessageLine line) {
		lines.add(line);
	}	
}
