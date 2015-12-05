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
		//ServerCommunicator server = new ServerCommunicator(); // uses default localhost:8081
		ClientServer.setTargetServer(new ServerProxy());
		
		UserCredentials hill = new UserCredentials("Hillary", "davis");
		UserCredentials dono = new UserCredentials("Donald", "davis");
		UserCredentials burn = new UserCredentials("Bernie", "davis");
		
		try {
			ClientServer.getSingleton().register(hill);
		}
		catch (ServerException e) {
			System.err.println("failed to registers: " + e.getReason());
		}
		
		try {
			ClientServer.getSingleton().login(hill);
		}
		catch (ServerException e) {
			System.err.println("failed to login: " + e.getReason());
		}
		
		try {
			System.out.println("create game: " + Converter.toJson(ClientServer.getSingleton().createGame(
					new CreateGameRequest(false, false, false, "Waldo"))));
			System.out.println("create game: " + Converter.toJson(ClientServer.getSingleton().createGame(
					new CreateGameRequest(false, false, false, "Werry"))));
			
			System.out.println("join game: "); ClientServer.getSingleton().joinGame(new JoinGameRequest(0, CatanColor.BLUE));
			
			Game[] games = ClientServer.getSingleton().getGamesList();
			System.out.println(games.length);
			for (int i = 0; i < games.length; i++) {
				System.out.println(games[i].toString());
			}

			ClientServer.getSingleton().register(dono);
			ClientServer.getSingleton().login(dono);
			ClientServer.getSingleton().joinGame(new JoinGameRequest(0, CatanColor.RED));
			
			ClientServer.getSingleton().register(burn);
			ClientServer.getSingleton().login(burn);
			ClientServer.getSingleton().joinGame(new JoinGameRequest(0, CatanColor.WHITE));
			
			TransferModel model = ClientServer.getSingleton().getModel(-1);
			System.out.println("get game -1: " + Converter.toJson(model));
			model = ClientServer.getSingleton().getModel(0);
			System.out.println("get game 0: " + Converter.toJson(model));
			
			
		}
		catch (ServerException e) {
			System.err.println("failed to getGames: " + e.getReason());
		}
	}
}
