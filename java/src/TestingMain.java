import server.ServerCommunicator;
import shared.exceptions.ServerException;
import shared.transferClasses.UserCredentials;
import client.server.ClientServer;
import client.server.ServerProxy;

public class TestingMain {	
	public static void main(String[] args) {
		ServerCommunicator server = new ServerCommunicator(8081);
		ServerProxy.initialize(new ClientServer("localhost", "8081"));
		
		UserCredentials pig = new UserCredentials("_pigs-", "canfly-_");
		
		try {
			ServerProxy.register(pig);
		}
		catch (ServerException e) {
			System.err.println("failed to registers: " + e.getReason());
		}
		
		try {
			ServerProxy.login(pig);
		}
		catch (ServerException e) {
			System.err.println("failed to login: " + e.getReason());
		}
		
		try {
			ServerProxy.getGamesList();
		}
		catch (ServerException e) {
			System.err.println("failed to getGames: " + e.getReason());
		}
	}
}
