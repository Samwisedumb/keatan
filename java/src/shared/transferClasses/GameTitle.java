package shared.transferClasses;

import shared.exceptions.InvalidObjectException;

/**
 * A dumb data holder for the title of a game
 * <li>String title - the title of the game : must not be empty string, must not be null</li>
 * @author djoshuac
 */
public class GameTitle extends ValidatableObject {
	private String title;
	
	public GameTitle(String title) {
		setTitle(title);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void validate() throws InvalidObjectException {
		if (title == null) {
			throw new InvalidObjectException("Game title is null");
		}
		if (title.equals("")) {
			throw new InvalidObjectException("Game title is empty");
		}
	}
}
