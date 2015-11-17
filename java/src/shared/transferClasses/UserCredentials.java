package shared.transferClasses;

import shared.exceptions.InvalidObjectException;

/**
 * A transfer object for registering and logging in a user.
 * <ul>
 * <li>username must be a valid Username</li>
 * <li>password must be a valid password</li>
 * </ul>
 * @author djoshuac
 */
public class UserCredentials {
	private Username username;
	private Password password;
	
	public UserCredentials(String username, String password) {
		setUsername(new Username(username));
		setPassword(new Password(password));
	}
	public UserCredentials(Username username, Password password) {
		setUsername(username);
		setPassword(password);
	}

	public Username getUsername() {
		return username;
	}

	private void setUsername(Username username) {
		this.username = username;
	}

	public Password getPassword() {
		return password;
	}

	private void setPassword(Password password) {
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
		username.validate();
		password.validate();
	}
}
