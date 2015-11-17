package shared.transferClasses;

import shared.exceptions.InvalidObjectException;

/**
 * A dumb data container for the user's password.<br>
 * <h6>Validate</h6><ul>
 * <li>A password must be at least 5 characters long</li>
 * <li>A password may contain only letters, digits, underscores, and hyphens</li>
 * </ul>
 * @author mr399
 */
public class Password implements ITransferClass {
	private String password;
	
	public Password(String password) {
		setPassword(password);
	}
	
	public String getPassword() {
		return password;
	}
	private void setPassword(String password) {
		this.password = password;
	}
	@Override
	public void validate() throws InvalidObjectException {
		if (password == null) {
			throw new InvalidObjectException("The password is null");
		}
		if (password.length() < 5) {
			throw new InvalidObjectException("The password is shorter than 5 characters");
		}
		for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '-') {
    			throw new InvalidObjectException("The password contains invalid characters");
            }
        }
	}
	@Override
	public String toString() {
		return password;
	}
}
