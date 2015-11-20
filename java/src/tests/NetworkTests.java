package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.ServerCommunicator;
import shared.exceptions.ServerException;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.UserCredentials;
import client.server.ClientServer;
import client.server.ServerProxy;

public class NetworkTests {
	public static boolean setupDone = false;
	public static boolean createGamesTestRun = false;
	@Before
	public void setup() {
		if(!setupDone) {
			ServerCommunicator server = new ServerCommunicator(8081);
			ServerProxy.initialize(new ClientServer("localhost","8081"));
			setupDone = true;
		}
	}
	//Register
	@Test
	public void testRegister() {
		UserCredentials userCredentials = new UserCredentials("Test","Test");
		try {
			ServerProxy.register(userCredentials);
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
			ServerProxy.register(userCredentials);
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
			ServerProxy.register(userCredentials);
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
			ServerProxy.register(userCredentials);
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
			ServerProxy.register(userCredentials);
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
			ServerProxy.register(userCredentials);
			assert(true);
		} catch (ServerException e) {
			fail("Should have passed");
		}
		
		userCredentials = new UserCredentials("Test","Test1");
		try {
			ServerProxy.register(userCredentials);
			fail("Should have thrown a user already exists error");
		} catch (ServerException e) {
			boolean reasonCheck = e.getReason().equals("Username already exists");
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
			ServerProxy.register(new UserCredentials("Test","Test1"));
		} catch (ServerException e1) {
		}

		boolean exceptionThrown = false;
		UserCredentials userCredentials = new UserCredentials("Test","Test");
		try {
			ServerProxy.login(userCredentials);
		} catch (ServerException e) {
			exceptionThrown = true;
		}
		assert(exceptionThrown);
		
		exceptionThrown = false;
		userCredentials = new UserCredentials("Test1","Test1");
		try {
			ServerProxy.login(userCredentials);
		} catch (ServerException e) {
			exceptionThrown = true;
		}
		assert(exceptionThrown);
		
		exceptionThrown = false;
		userCredentials = new UserCredentials("Test","Test1");
		try {
			ServerProxy.login(userCredentials);
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
			gameResponse = ServerProxy.createGame(gameRequest);
		} catch (ServerException e) {
		}
		assert(gameResponse.getTitleString().equals("test1"));
		assert(gameResponse.getID() == 0);
		
		gameRequest = new CreateGameRequest(false,false,false,"test2");
		try {
			gameResponse = ServerProxy.createGame(gameRequest);
		} catch (ServerException e) {
		}
		assert(gameResponse.getTitleString().equals("test2"));
		assert(gameResponse.getID() == 1);
		
		gameRequest = new CreateGameRequest(true,true,true,"test3");
		try {
			gameResponse = ServerProxy.createGame(gameRequest);
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
			assert(ServerProxy.getGamesList().length == existingGames);
		} 
		catch (ServerException e) {
		}
		
		CreateGameRequest gameRequest = new CreateGameRequest(false,false,false,"test");
		try {
			ServerProxy.createGame(gameRequest);
			existingGames++;
			assert(ServerProxy.getGamesList().length == existingGames);
		}
		catch (ServerException e) {
		}
	}
	
	//joinGame
	//getModel
	//addAI
	//listAITypes
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
