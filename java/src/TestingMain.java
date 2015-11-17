import java.util.ArrayList;
import java.util.List;

import server.ServerCommunicator;
import shared.exceptions.ServerException;
import shared.transferClasses.UserCredentials;
import client.server.ClientServer;
import client.server.ServerProxy;

public class TestingMain {
	private static void registerTest(UserCredentials creds, String exceptionReason) {	
		try {
			ServerProxy.register(creds);
			
			if (!exceptionReason.equals("none")) {
				System.err.println(exceptionReason);
			}
		}
		catch (ServerException serverSide) {
			if (!exceptionReason.equals(serverSide.getReason())) {
				System.err.println(serverSide.getReason() + " != " + exceptionReason);
			}
		}
	}
	private static void loginTest(UserCredentials creds, String exceptionReason) {	
		try {
			ServerProxy.login(creds);
			
			if (!exceptionReason.equals("none")) {
				System.err.println(exceptionReason);
			}
		}
		catch (ServerException serverSide) {
			if (!exceptionReason.equals(serverSide.getReason())) {
				System.err.println(serverSide.getReason() + " != " + exceptionReason);
			}
		}
	}
	
	public static void main(String[] args) {		
		ServerProxy.initialize(new ClientServer("localhost", "8081"));

		registerTest(new UserCredentials("Stew8", "password-_"), "failed to connect to server");
		
		ServerCommunicator server = new ServerCommunicator(8081);

		// Register Tests
		registerTest(new UserCredentials("St", "password-_"), "username is not within 3 and 7 characters");
		registerTest(new UserCredentials("Stew1_Fish-y", "password-_"), "username is not within 3 and 7 characters");
		registerTest(new UserCredentials("St\\/\\/", "password-_"), "username contains invalid characters");
		registerTest(new UserCredentials("I8Stew", "pass"), "password is shorter than 5 characters");
		registerTest(new UserCredentials("I8Stew", "pass\\/\\/ord"), "password contains invalid characters");

		registerTest(new UserCredentials("I8StewZ", "55555"), "none");
		registerTest(new UserCredentials("333", "password"), "none");
		registerTest(new UserCredentials("7777777", "password"), "none");
		registerTest(new UserCredentials("_____", "password"), "none");
		registerTest(new UserCredentials("-----", "password"), "none");
		registerTest(new UserCredentials("I8StewZ", "HIJKLMNOPa1b2QRSc3dTUV4e5f6g7h8i9j0klmnop_qrWXYZstu-vwxy-z_ABCDEFG-_"), "none");
		
		// Login Tests
		loginTest(new UserCredentials("St", "password-_"), "username is not within 3 and 7 characters");
		loginTest(new UserCredentials("Stew1_Fish-y", "password-_"), "username is not within 3 and 7 characters");
		loginTest(new UserCredentials("St\\/\\/", "password-_"), "username contains invalid characters");
		loginTest(new UserCredentials("I8Stew", "pass"), "password is shorter than 5 characters");
		loginTest(new UserCredentials("I8Stew", "pass\\/\\/ord"), "password contains invalid characters");

		loginTest(new UserCredentials("I8StewZ", "55555"), "none");
		loginTest(new UserCredentials("333", "password"), "none");
		loginTest(new UserCredentials("7777777", "password"), "none");
		loginTest(new UserCredentials("_____", "password"), "none");
		loginTest(new UserCredentials("-----", "password"), "none");
		loginTest(new UserCredentials("I8StewZ", "HIJKLMNOPa1b2QRSc3dTUV4e5f6g7h8i9j0klmnop_qrWXYZstu-vwxy-z_ABCDEFG-_"), "none");
		
		
		List<String> list = new ArrayList<String>();
		
		System.out.println(list.getClass());
		
	}
}
