package shared.transferClasses;

import shared.exceptions.InvalidObjectException;

/**
 * A transfer class to respond to a create game request<br>
 * <li>GameTitle title - the title of the created game : must be valid</li>
 * <li>int gameID - the ID of the created game</li>
 * @author djoshuac
 *
 */
public class CreateGameResponse extends ValidatableObject {
	private GameTitle title;
	private int id;
	
	public CreateGameResponse(String title, int gameID) {
		setTitle(title);
		setID(gameID);
	}
	
	public String getTitleString() {
		return title.toString();
	}
	
	public void setTitle(String title) {
		this.title = new GameTitle(title);
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}

	@Override
	public void validate() throws InvalidObjectException {
		if (title == null) {
			throw new InvalidObjectException("Game title is null");
		}
	}
}
