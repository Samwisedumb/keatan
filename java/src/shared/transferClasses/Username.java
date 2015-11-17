package shared.transferClasses;

import shared.exceptions.InvalidObjectException;

/**
 * A dumb data holder for a username
 * @author djoshuac
 */
public class Username implements ITransferClass {
	private String username;
	
	public Username(String username) {
		
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void validate() throws InvalidObjectException {
		if (username == null) {
			throw new InvalidObjectException("username is null");
		}
		if (username.length() < 3 || username.length() > 7) {
			throw new InvalidObjectException("username is not within 3 and 7 characters");
		}
		for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '-') {
    			throw new InvalidObjectException("username contains invalid characters");
            }
        }
	}
}
