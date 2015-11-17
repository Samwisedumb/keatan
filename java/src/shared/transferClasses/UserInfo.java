package shared.transferClasses;


/**
 * A dumb data holder for a user
 * @author djoshuac
 */
public class UserInfo {
	private Username username;
	private Password password;
	private int userID;
	
	public UserInfo(String username, String password, int userID) {
		setUsername(new Username(username));
		setPassword(new Password(password));
		setUserID(userID);
	}
	public UserInfo(Username username, Password password, int userID) {
		setUsername(username);
		setPassword(password);
		setUserID(userID);
	}

	public Username getUsername() {
		return username;
	}
	public Password getPassword() {
		return password;
	}
	public int getUserID() {
		return userID;
	}

	private void setPassword(Password password) {
		this.password = password;
	}
	private void setUsername(Username username) {
		this.username = username;
	}
	private void setUserID(int userID) {
		this.userID = userID;
	}
}
