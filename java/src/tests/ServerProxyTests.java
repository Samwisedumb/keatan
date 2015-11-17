package tests;

import org.junit.Before;
import org.junit.Test;

import server.ServerCommunicator;
import shared.exceptions.ServerException;
import shared.transferClasses.Game;
import shared.transferClasses.UserCredentials;
import client.server.ClientServer;
import client.server.ServerProxy;

public class ServerProxyTests {
	private static final UserCredentials fox = new UserCredentials("Fox", "password");
	
	@Before
	public void setup() {
		if (!ServerProxy.isInitialized()) {
			ServerCommunicator server = new ServerCommunicator(8081);
			ServerProxy.initialize(new ClientServer("localhost", "8081"));
			try {
				ServerProxy.register(new UserCredentials("Fox", "password"));
			}
			catch (ServerException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void registerTest(UserCredentials creds, String exceptionReason) {	
		try {
			ServerProxy.register(creds);

			assert(exceptionReason.equals("none"));
		}
		catch (ServerException e) {
			assert(exceptionReason.equals(e.getReason()));
		}
	}
	
	private static void loginTest(UserCredentials creds, String exceptionReason) {	
		try {
			ServerProxy.login(creds);
			
			assert(exceptionReason.equals("none"));
		}
		catch (ServerException e) {
			assert(exceptionReason.equals(e.getReason()));
		}
	}
	
	@Test
	public void registerTests() {
		registerTest(new UserCredentials("St", "password-_"), "The username is not within 3 and 7 characters");
		registerTest(new UserCredentials("Stew1_Fish-y", "password-_"), "The username is not within 3 and 7 characters");
		registerTest(new UserCredentials("St\\/\\/", "password-_"), "The username contains invalid characters");
		registerTest(new UserCredentials("I8Stew", "pass"), "The password is shorter than 5 characters");
		registerTest(new UserCredentials("I7Stew", "pass\\/\\/ord"), "The password contains invalid characters");

		registerTest(new UserCredentials("I8StewZ", "55555"), "none");
		registerTest(new UserCredentials("333", "password"), "none");
		registerTest(new UserCredentials("7777777", "password"), "none");
		registerTest(new UserCredentials("_____", "password"), "none");
		registerTest(new UserCredentials("-----", "password"), "none");
		registerTest(new UserCredentials("I7StewZ", "HIJKLMNOPa1b2QRSc3dTUV4e5f6g7h8i9j0klmnop_qrWXYZstu-vwxy-z_ABCDEFG-_"), "none");
	}
	
	@Test
	public void loginTests() {
		loginTest(new UserCredentials("noone", "55555"), "User doesn't exist");
		
		loginTest(fox, "none");
	}
	
	@Test
	public void listGamesTests() {
		try {
			ServerProxy.login(fox);
			
			Game[] gamesList = ServerProxy.getGamesList();
			
			System.out.println(gamesList.length);
		}
		catch (ServerException e) {
			System.err.println("Failed to list games: " + e.getReason());
			assert(false);
		}
	}
}
