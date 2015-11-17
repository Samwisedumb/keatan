package shared.transferClasses;

import shared.exceptions.InvalidObjectException;

/**
 * A transfer object for registering and logging in a user.
 * @author djoshuac
 */
public class UserCredentials {
	private String username;
	private String password;
	
	public UserCredentials(String username, String password) {
		setUsername(username);
		setPassword(password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Validates a UserCredentials object
	 * @post An InvalidObjectException will be thrown if the preconditions are not met.
	 * An English description of which precondition was not met is given in the exception's reason.
	 * @pre The username must be 3 to 7 characters in length containing only letters,
	 * numbers, underscores, and hyphens.<br>
	 * The password must 5 or more characters in length containing only letters, numbers,
	 * underscores, and hyphens.
	 * @throws InvalidObjectException is thrown if one of the preconditions is violated.
	 */
	public void validate() throws InvalidObjectException {
		if (username == null || password == null) {
			throw new InvalidObjectException("username or password were null");
		}
		if (username.length() < 3 || username.length() > 7) {
			throw new InvalidObjectException("username is not within 3 and 7 characters");
		}
		for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '-') {
    			throw new InvalidObjectException("username contains invalid characters");
            }
        }
		if (password.length() < 5) {
			throw new InvalidObjectException("password is shorter than 5 characters");
		}
		for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '-') {
    			throw new InvalidObjectException("password contains invalid characters");
            }
        }
	}
}
