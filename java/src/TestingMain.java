import server.ServerCommunicator;
import shared.definitions.CatanColor;
import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.Game;
import shared.transferClasses.JoinGameRequest;
import shared.transferClasses.UserCredentials;
import client.model.TransferModel;
import client.server.ClientServer;
import client.server.ServerProxy;

public class TestingMain {	
	public static void main(String[] args) {
		ServerCommunicator server = new ServerCommunicator(8081);
		ServerProxy.initialize(new ClientServer("localhost", "8081"));
		
		UserCredentials hill = new UserCredentials("Hillary", "davis");
		UserCredentials dono = new UserCredentials("Donald", "davis");
		UserCredentials burn = new UserCredentials("Bernie", "davis");
		
		try {
			ServerProxy.register(hill);
		}
		catch (ServerException e) {
			System.err.println("failed to registers: " + e.getReason());
		}
		
		try {
			ServerProxy.login(hill);
		}
		catch (ServerException e) {
			System.err.println("failed to login: " + e.getReason());
		}
		
		try {
			System.out.println("create game: " + Converter.toJson(ServerProxy.createGame(new CreateGameRequest(false, false, false, "Waldo"))));
			System.out.println("create game: " + Converter.toJson(ServerProxy.createGame(new CreateGameRequest(false, false, false, "Werry"))));
			
			System.out.println("join game: "); ServerProxy.joinGame(new JoinGameRequest(0, CatanColor.BLUE));
			
			Game[] games = ServerProxy.getGamesList();
			System.out.println(games.length);
			for (int i = 0; i < games.length; i++) {
				System.out.println(games[i].toString());
			}

			ServerProxy.register(dono);
			ServerProxy.login(dono);
			ServerProxy.joinGame(new JoinGameRequest(0, CatanColor.RED));
			
			ServerProxy.register(burn);
			ServerProxy.login(burn);
			ServerProxy.joinGame(new JoinGameRequest(0, CatanColor.WHITE));
			
			TransferModel model = ServerProxy.getModel(-1);
			System.out.println("get game -1: " + Converter.toJson(model));
			model = ServerProxy.getModel(0);
			System.out.println("get game 0: " + Converter.toJson(model));
			
			
		}
		catch (ServerException e) {
			System.err.println("failed to getGames: " + e.getReason());
		}
	}
}
