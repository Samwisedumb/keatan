package tests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.ServerCommunicator;
import shared.definitions.CatanColor;
import shared.exceptions.ServerException;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.JoinGameRequest;
import shared.transferClasses.UserCredentials;
import client.server.ClientServer;
import client.server.ServerProxy;

public class NetworkTests {
	public static boolean setupDone = false;
	public static boolean createGamesTestRun = false;
	
	private List<ServerProxy> proxyList = doTheList();
	
	public List<ServerProxy> doTheList() {
		
		List<ServerProxy> newList = new ArrayList<ServerProxy>();
		
		for(int i = 0; i < 4; i++) {
			newList.add(new ServerProxy("localhost", 8081));
		}
		
		return newList;
	}
	
	@Before
	public void setup() {
		if(!setupDone) {
			ServerCommunicator server = new ServerCommunicator(8081);
			
			//proxyList = new ArrayList<ServerProxy>();
			
			System.out.println("fe");
			
			System.out.println(proxyList.size());
			
			//ClientServer.setTargetServer(proxyList.get(0));
			setupDone = true;
		}
	}
	//Register
	@Test
	public void testRegister() {
		UserCredentials userCredentials = new UserCredentials("Test","Test");
		try {
			//ClientServer.getSingleton().register(userCredentials);
			proxyList.get(0).register(userCredentials);

			fail("Should have thrown a password too short exception");
		} catch (ServerException e) {
			boolean reasonCheck = e.getReason().equals("The password is shorter than 5 characters");
			//assert is doing weird things right here, hence the kludge.
			if(!reasonCheck){
				fail("Wrong exception thrown");
			}
		}
		
		userCredentials = new UserCredentials("Te","Test1");
		try {
			//ClientServer.getSingleton().register(userCredentials);
			proxyList.get(0).register(userCredentials);
			fail("Should have thrown a name too short exception");
		} catch (ServerException e) {
			boolean reasonCheck = e.getReason().equals("The username is not within 3 and 7 characters");
			//assert is doing weird things right here, hence the kludge.
			if(!reasonCheck) {
				fail("Wrong exception thrown");
			}
		}
		
		userCredentials = new UserCredentials("Testing1","Test1");
		try {
			//ClientServer.getSingleton().register(userCredentials);
			proxyList.get(0).register(userCredentials);
			fail("Should have thrown a name too long exception");
		} catch (ServerException e) {
			boolean reasonCheck = e.getReason().equals("The username is not within 3 and 7 characters");
			//assert is doing weird things right here, hence the kludge.
			if(!reasonCheck) {
				fail("Wrong exception thrown");
			}
		}
		
		userCredentials = new UserCredentials("Test&","Test1");
		try {
			//ClientServer.getSingleton().register(userCredentials);
			proxyList.get(0).register(userCredentials);
			fail("Should have thrown a username using invalid character exception");
		} catch (ServerException e) {
			boolean reasonCheck = e.getReason().equals("The username contains invalid characters");
			//assert is doing weird things right here, hence the kludge.
			if(!reasonCheck) {
				fail("Wrong exception thrown");
			}
		}
		
		userCredentials = new UserCredentials("Test","Test&");
		try {
			//ClientServer.getSingleton().register(userCredentials);
			proxyList.get(0).register(userCredentials);
			fail("Should have thrown a password using invalid character exception");
		} catch (ServerException e) {
			boolean reasonCheck = e.getReason().equals("The password contains invalid characters");
			//assert is doing weird things right here, hence the kludge.
			if(!reasonCheck) {
				fail("Wrong exception thrown");
			}
		}
		
		userCredentials = new UserCredentials("Test","Test1");
		try {
			//ClientServer.getSingleton().register(userCredentials);
			proxyList.get(0).register(userCredentials);
			assert(true);
		} catch (ServerException e) {
			fail("Should have passed");
		}
		
		userCredentials = new UserCredentials("Test","Test1");
		try {
			//ClientServer.getSingleton().register(userCredentials);
			proxyList.get(0).register(userCredentials);
			fail("Should have thrown a user already exists error");
		} catch (ServerException e) {
			boolean reasonCheck = e.getReason().equals("Username is already in use");
			//assert is doing weird things right here, hence the kludge.
			if(!reasonCheck) {
				fail("Wrong exception thrown");
			}
		}
	}
	
	//Login
	@Test
	public void testLogin() {
		//Set 
		try {
			//ClientServer.getSingleton().register(new UserCredentials("Pest","Pest1"));
			proxyList.get(0).register(new UserCredentials("Pest","Pest1"));
		} catch (ServerException e1) {
		}

		boolean exceptionThrown = false;
		UserCredentials userCredentials = new UserCredentials("Test","Test");
		try {
			//ClientServer.getSingleton().login(userCredentials);
			proxyList.get(0).register(userCredentials);
		} catch (ServerException e) {
			exceptionThrown = true;
		}
		assert(exceptionThrown);
		
		exceptionThrown = false;
		userCredentials = new UserCredentials("Test1","Test1");
		try {
			//ClientServer.getSingleton().login(userCredentials);
			proxyList.get(0).register(userCredentials);
		} catch (ServerException e) {
			exceptionThrown = true;
		}
		assert(exceptionThrown);
		
		exceptionThrown = false;
		userCredentials = new UserCredentials("Lest","Lest1");
		try {
			//ClientServer.getSingleton().login(userCredentials);
			proxyList.get(0).register(userCredentials);
		} catch (ServerException e) {
			exceptionThrown = true;
		}
		assert(!exceptionThrown);
	}
	
	//Create
	@Test
	public void testCreate() {
		CreateGameRequest gameRequest = new CreateGameRequest(false,false,false,"test1");
		CreateGameResponse gameResponse = null;
		try {
			//gameResponse = ClientServer.getSingleton().createGame(gameRequest);
			gameResponse = proxyList.get(0).createGame(gameRequest);
		} catch (ServerException e) {
		}
		assert(gameResponse.getTitleString().equals("test1"));
		assert(gameResponse.getID() == 0);
		
		gameRequest = new CreateGameRequest(false,false,false,"test2");
		try {
			//gameResponse = ClientServer.getSingleton().createGame(gameRequest);
			gameResponse = proxyList.get(0).createGame(gameRequest);
		} catch (ServerException e) {
		}
		assert(gameResponse.getTitleString().equals("test2"));
		assert(gameResponse.getID() == 1);
		
		gameRequest = new CreateGameRequest(true,true,true,"test3");
		try {
			//gameResponse = ClientServer.getSingleton().createGame(gameRequest);
			gameResponse = proxyList.get(0).createGame(gameRequest);
		} catch (ServerException e) {
		}
		assert(gameResponse.getTitleString().equals("test3"));
		assert(gameResponse.getID() == 2);
		
		createGamesTestRun = true;
	}
	
	//List
	@Test
	public void testList() {
		int existingGames = 0;
		if(createGamesTestRun) {
			existingGames = 3;
		}
		
		try {
			//assert(ClientServer.getSingleton().getGamesList().length == existingGames);
			assert(proxyList.get(0).getGamesList().length == existingGames);
		} 
		catch (ServerException e) {
		}
		
		CreateGameRequest gameRequest = new CreateGameRequest(false,false,false,"test");
		try {
			//ClientServer.getSingleton().createGame(gameRequest);
			proxyList.get(0).createGame(gameRequest);
			existingGames++;
			//assert(ClientServer.getSingleton().getGamesList().length == existingGames);
			assert(proxyList.get(0).getGamesList().length == existingGames);
		}
		catch (ServerException e) {
		}
	}
	
	//joinGame
	@Test
	public void testJoinGame() {
		
		//Make some more little guys to add to the game
		
		UserCredentials userCredentials = new UserCredentials("Zest", "Zest1");
		UserCredentials userCredentials1 = new UserCredentials("Rest","Rest1");
		UserCredentials userCredentials2 = new UserCredentials("Fest","Fest1");
		UserCredentials userCredentials3 = new UserCredentials("Nest","Nest1");
		
		System.out.println("he");
		
		try {
			proxyList.get(0).register(userCredentials);
			proxyList.get(0).login(userCredentials);
	
			proxyList.get(1).register(userCredentials1);
			proxyList.get(1).login(userCredentials1);
			
			proxyList.get(2).register(userCredentials2);
			proxyList.get(2).login(userCredentials2);
			
			proxyList.get(3).register(userCredentials3);
			proxyList.get(3).login(userCredentials3);
		}
		catch(ServerException e) {
			fail("Something went very wrong");
		}
		
		JoinGameRequest joinGame = new JoinGameRequest(0, CatanColor.BLUE);
		JoinGameRequest joinGame1 = new JoinGameRequest(0, CatanColor.GREEN);
		JoinGameRequest joinGame2 = new JoinGameRequest(0, CatanColor.RED);
		JoinGameRequest joinGame3 = new JoinGameRequest(0, CatanColor.PUCE);
		
		try {
			//ClientServer.getSingleton().joinGame(joinGame);
			proxyList.get(0).joinGame(joinGame);
			proxyList.get(1).joinGame(joinGame1);
			proxyList.get(2).joinGame(joinGame2);
			proxyList.get(3).joinGame(joinGame3);
		}
		catch(ServerException e) {
			fail("it was terrible");
		}
		
		boolean failed = false;
		
		try {
			proxyList.get(1).joinGame(joinGame3);
		} catch(ServerException e) {
			failed = true;
		}
		if(!failed) {
			fail("It was supposed to fail...");
		}
	}
	//getModel
	@Test
	public void testModel() {
		
	}

	//sendChat
	//rollDice
	//robPlayer
	//finishTurn
	//buyDevCard
	//yearOfPlenty
	//roadBuilding
	//soldier
	//monopoly
	//monument
	//buildRoad
	//buildSettlement
	//buildCity
	//offerTrade
	//respondToTrade
	//maritimeTrade
	//discardCards
}
