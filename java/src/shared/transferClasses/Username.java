package shared.transferClasses;

import shared.exceptions.InvalidObjectException;

/**
 * A dumb data holder for a username<br>
 * <ul>
 * <li>A username must be at least 3 characters long</li>
 * <li>A username must be no longer than 7 characters</li>
 * <li>A username may contain only letters, digits, underscores, and hyphens</li>
 * </ul>
 * @author djoshuac
 */
public class Username implements ITransferClass {
	private String username;
	
	public Username(String username) {
		setUsername(username);
	}

	public String getUsername() {
		return username;
	}
	
	private void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void validate() throws InvalidObjectException {
		if (username == null) {
			throw new InvalidObjectException("The username is null");
		}
		if (username.length() < 3 || username.length() > 7) {
			throw new InvalidObjectException("The username is not within 3 and 7 characters");
		}
		for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '-') {
    			throw new InvalidObjectException("The username contains invalid characters");
            }
        }
	}
}
