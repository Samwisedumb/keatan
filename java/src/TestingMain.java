import server.ServerCommunicator;
import shared.transferClasses.UserCredentials;
import client.exceptions.ServerException;
import client.server.NewClientServer;
import client.server.ServerProxy;


public class TestingMain {
	public static void main(String[] args) {		
		ServerProxy.initialize(new NewClientServer("localhost", "8081"));
		ServerCommunicator server = new ServerCommunicator(8081);
		
		UserCredentials fox = new UserCredentials("Star_Fox", "WhereYouGoing");

		try {
			ServerProxy.register(fox);
		}
		catch (ServerException e) {
			System.out.println("Fox is already registered");
		}
		try {
			ServerProxy.login(fox);
			System.out.println("Success!!");
		}
		catch (ServerException e) {
			System.out.println("Fail!!");
			System.err.println(e.getReason());
		}
//		
//		ServerProxy.initialize(new MockServer());
//		//ServerProxy.initialize(new ClientServer("localhost", "8081"));
//		
//		try {
//			TransferModel modeling = ServerProxy.getModel(-1);
//			System.out.println(Converter.toJson(modeling));
//		}
//		catch (ServerException e1) {
//			System.err.println(e1.getReason());
//		}
//		
//		
//		UserCredentials fox = new UserCredentials("Star_Fox", "WhereYouGoing");
//		UserCredentials peppy = new UserCredentials("Harey_Mentor", "DoABarrelRoll");
//		UserCredentials falco = new UserCredentials("Bird_Rival", "GeezeFox");
//		UserCredentials slippy = new UserCredentials("Frog_Mechanic", "HELPMEIAMBEINGSHOT");
//		
//		UserCredentials wolf = new UserCredentials("Wolf", "CantLetYouDoThat");
//		UserCredentials leon = new UserCredentials("Leon", "AnnoyingBirdIAmTheGreatLeon");
//		UserCredentials pigma = new UserCredentials("Pigma", "DaddyScreamedRealGood");
//		UserCredentials andrew = new UserCredentials("Andrew", "UNCLEANDROOOOOOOOSS");
//		
//		
//		try {
//			ServerProxy.register(wolf);
//			ServerProxy.register(leon);
//			ServerProxy.register(pigma);
//			ServerProxy.register(andrew);
//			ServerProxy.register(fox);
//			ServerProxy.register(peppy);
//			ServerProxy.register(falco);
//			ServerProxy.register(slippy);
//		}
//		catch (ServerException e) {
//			System.out.println("The squad is already registered.");
//		}
//		
//		try {
//			ServerProxy.login(fox);
//			ServerProxy.login(peppy);
//			ServerProxy.login(falco);
//			ServerProxy.login(slippy);
//			
//			Game[] games = ServerProxy.getGamesList();
//			CreateGameResponse cgr = ServerProxy.createGame(new CreateGameRequest(true, true, true, "Slippy's Game " + games.length));
//			System.out.println(games[0].getTitle());
//			
//			ServerProxy.joinGame(new JoinGameRequest(cgr.getId(), CatanColor.GREEN));
//			
//			TransferModel model = ServerProxy.getModel(-1);
//			System.out.println(Converter.toJson(model.getBank()));
//			System.out.println(Converter.toJson(model.getMap()));
//			
//			ServerProxy.login(wolf);
//			ServerProxy.joinGame(new JoinGameRequest(cgr.getId(), CatanColor.YELLOW));
//			ServerProxy.login(peppy);
//			ServerProxy.joinGame(new JoinGameRequest(cgr.getId(), CatanColor.RED));
//			ServerProxy.login(falco);
//			ServerProxy.joinGame(new JoinGameRequest(cgr.getId(), CatanColor.BLUE));
//			
//			model = ServerProxy.getModel(-1);
//			ModelFacade.updateModel(model);
//			System.out.println(Converter.toJson(model));
//			System.out.println(Converter.toJson(model.getBank()));
//			System.out.println(Converter.toJson(model.getMap()));
//			
//			ServerProxy.sendChat(new SendChat(0, "HEYOU"));
//			model = ServerProxy.sendChat(new SendChat(0, "Guys"));
//			
//			List<MessageLine> lines = model.getChat().getLines();
//			for (int i = 0; i < lines.size(); i++) {
//				System.out.println(lines.get(i).getSource() + lines.get(i).getMessage());
//			}
//			
//			ServerProxy.sendChat(new SendChat(1, "What Slippy!!!??"));
//			model = ServerProxy.sendChat(new SendChat(0, "I uh..."));
//			
//			lines = model.getChat().getLines();
//			for (int i = 0; i < lines.size(); i++) {
//				System.out.println(lines.get(i).getSource() + " " + lines.get(i).getMessage());
//			}
//			
//			List<Port> ports = model.getMap().getPorts();
//			for (Port p : ports) {
//				System.out.println(p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getDirection());
//			}
//			
//			
//			System.out.println(lines.size());
//			
//			
//			ServerPoller.start();
//			
//			
//			for (PlayerInfo player : ModelFacade.getJoinedPlayersInfo()) {
//				System.out.println(player.getName());
//			}
//			
//		}
//		catch (ServerException e) {
//			System.err.println("Bad stuff: " + e.getReason());
//		}
	}
}
