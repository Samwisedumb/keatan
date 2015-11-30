package client.login;

import shared.exceptions.ServerException;
import shared.transferClasses.UserCredentials;
import shared.transferClasses.UserInfo;
import client.base.Controller;
import client.base.IAction;
import client.misc.IMessageView;
import client.model.ModelFacade;
import client.server.ServerProxy;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController {

	private IMessageView messageView;
	private IAction loginAction;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		
		this.messageView = messageView;
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	/**
	 * Shows an error dialog message to the user.
	 * @pre messageView cannot be null, errorMessage cannot be null
	 * @post the given errorMessage is displayed in a modal dialog
	 * @param errorMessage - the message to display in the dialog
	 */
	private void showModalError(String errorMessage) {
		messageView.setMessage(errorMessage);
		messageView.setTitle("Error");
		messageView.showModal();
	}
	
	@Override
	public void signIn() {
		String username = getLoginView().getLoginUsername();
		String password = getLoginView().getLoginPassword();
			
		try {
			UserCredentials credentials = new UserCredentials(username, password);
			UserInfo user = ServerProxy.login(credentials);

			ModelFacade.setUserInfo(user);
			getLoginView().closeModal();
			loginAction.execute();
		}
		catch (ServerException e) {
			showModalError(e.getReason());
		}
	}
	
	/**
	 * This constant is returned by validate functions if the parameters passed are valid
	 */
	private static final String VALID_CONST = "VALID";
	
	/**
	 * Checks the username based on certain conditions.
	 * <ul>
	 * <li>username must be at least 3 characters long</li>
	 * <li>username cannot exceed 7 characters</li>
	 * <li>username can only contain letters, digits, underscores, and hyphens (Underscore: '_', Hyphen: '-')</li>
	 * </ul>
	 * @pre username must not be null
	 * @param username - the username to check
	 * @post see return
	 * @return LoginController.VALID_CONST if valid, reason why invalid if otherwise.
	 */
	private String validateUsername(String username) {
		int minlength = 3;
		int maxlength = 7;
        if (username.length() < minlength) {
            return "Usernames must be at least " + minlength + " characters long";
        }
        else if (username.length() > maxlength) {
            return "Usernames cannot be longer than " + maxlength + " characters";
        }
        else {
            for (char c : username.toCharArray()) {
                if (!Character.isLetterOrDigit(c) && c != '_' && c != '-') {
                    return "Usernames may only consist letters, digits, underscores or hyphens";
                }
            }
        }
        return LoginController.VALID_CONST;
	}
	
	/**
	 * Checks the password based on certain conditions.
	 * <ul>
	 * <li>passwords musts match</li>
	 * <li>password cannot exceed 5 characters</li>
	 * <li>password can only contain letters, digits, underscores, and hyphens (Underscore: '_', Hyphen: '-')</li>
	 * </ul>
	 * @param password1 - the first password
	 * @param password2 - the password to double check
	 * @return LoginController.VALID_CONST if valid, reason why invalid if otherwise.
	 */
	private String validatePasswords(String password1, String password2) {
		int minlength = 5;
		if (!password1.equals(password2)) {
			return "Paswords don't match";
		}
		else {
			if (password1.length() < minlength) {
                return  "Passwords must be at least " + minlength + " charaters long";
            }
			else {
				for (char c : password1.toCharArray()) {
                    if (!Character.isLetterOrDigit(c) && c != '_' && c != '-') {
                        return "Passwords may only consist letters, digits, underscores or hyphens";
                    }
                }
            }
		}
		return LoginController.VALID_CONST;
	}

	
	@Override
	public void register() {
		String username = getLoginView().getRegisterUsername();
		String password1 = getLoginView().getRegisterPassword();
		String password2 = getLoginView().getRegisterPasswordRepeat();
		
		if (LoginController.VALID_CONST != validateUsername(username)) {
			showModalError(validateUsername(username));
		}
		else if (LoginController.VALID_CONST != validatePasswords(password1, password2)) {
			showModalError(validatePasswords(password1, password2));
		}
		else {
			try {
				UserCredentials credentials = new UserCredentials(username, password1);
				ServerProxy.register(credentials);
				UserInfo user = ServerProxy.login(credentials);

				ModelFacade.setUserInfo(user);
				getLoginView().closeModal();
				loginAction.execute();
			}
			catch (ServerException e) {
				showModalError(e.getReason());
			}
		}
	}

	@Override
	public void update() {
		// do nothing
	}
}

