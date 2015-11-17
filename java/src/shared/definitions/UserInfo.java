package shared.definitions;

/**
 * A dumb data holder for a user
 * @author djoshuac
 */
public class UserInfo {
	private String username;
	private String password;
	private int userID;
	
	public UserInfo(String username, String password, int userID) {
		setUsername(username);
		setPassword(password);
		setUserID(userID);
	}

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public int getUserID() {
		return userID;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
}
