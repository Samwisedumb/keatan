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
		
	}
}
