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
	
	@Override
	public int hashCode() {
		return userID;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userID != other.userID)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
