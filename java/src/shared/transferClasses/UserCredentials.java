package shared.transferClasses;

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
	 * @return true if the object is valid<br>
	 * false if otherwise
	 * @pre none
	 * @post If true, the username is guaranteed to be of 3 to 7 characters in
	 * length containing only letters, numbers, underscores, and hyphens.<br>
	 * The password is guaranteed to be 5 or more characters in length containing 
	 * only letters, numbers, underscores, and hyphens.<br>
	 * If false, you are guaranteed that at least one of the above conditions is false.
	 * 3 and 7 characters
	 */
	public boolean validate() {
		if (username == null || password == null) {
			return false;
		}
		if (username.length() < 3 || username.length() > 7) {
			return false;
		}
		for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '-') {
                return false;
            }
        }
		if (password.length() < 5) {
			return false;
		}
		for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '-') {
                return false;
            }
        }
		return true;
	}
}
