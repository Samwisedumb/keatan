package server;

import java.net.InetSocketAddress;

import server.handlers.games.GameModelHandler;
import server.handlers.games.GamesCreateHandler;
import server.handlers.games.GamesJoinHandler;
import server.handlers.games.GamesListHandler;
import server.handlers.moves.MovesAcceptTradeHandler;
import server.handlers.moves.MovesBuildCityHandler;
import server.handlers.moves.MovesBuildRoadHandler;
import server.handlers.moves.MovesBuildSettlementHandler;
import server.handlers.moves.MovesBuyDevCardHandler;
import server.handlers.moves.MovesDiscardCardsHandler;
import server.handlers.moves.MovesFinishTurnHandler;
import server.handlers.moves.MovesMaritimeTradeHandler;
import server.handlers.moves.MovesMonopolyHandler;
import server.handlers.moves.MovesMonumentHandler;
import server.handlers.moves.MovesOfferTradeHandler;
import server.handlers.moves.MovesRoadBuildingHandler;
import server.handlers.moves.MovesRobPlayerHandler;
import server.handlers.moves.MovesRollNumberHandler;
import server.handlers.moves.MovesSendChatHandler;
import server.handlers.moves.MovesSoldierHandler;
import server.handlers.moves.MovesYearOfPlentyHandler;
import server.handlers.user.UserLoginHandler;
import server.handlers.user.UserRegisterHandler;

import com.sun.net.httpserver.HttpServer;

/**
 * The server class for the Catan game. Use the constructor to initialize it.<br><br>
 * <strong>default host</strong> - <em>"localhost"</em><br>
 * <strong>default port</strong> - <em>8081</em>
 * @author djoshuac
 */
public class ServerCommunicator {
	private static final int MAX_WAITING_CONNECTIONS = 4;
	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 8081;
	
	private HttpServer server;
	
	/**
	 * Initializes a ServerCommunicator with given host and port<br>
	 * Fails to start server if host or port are invalid
	 * @param host - the host to use
	 * @param port - the port to use
	 */
	public ServerCommunicator(String host, int port) {
		try {
			server = HttpServer.create(new InetSocketAddress(host, port), MAX_WAITING_CONNECTIONS);
			server.setExecutor(null); //this gives us the default executer
			initializeContexts();
			server.start();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
	

	/**
	 * Initializes a ServerCommunicator with given host and default port, see Class Java doc for the default values<br>
	 * Fails to start server if host or port are invalid
	 * @param host - the host to use
	 */
	public ServerCommunicator(String host) {
		try {
			server = HttpServer.create(new InetSocketAddress(host, DEFAULT_PORT), MAX_WAITING_CONNECTIONS);
			server.setExecutor(null); //this gives us the default executer
			initializeContexts();
			server.start();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
	

	/**
	 * Initializes a ServerCommunicator with default host and given port, see Class Java doc for the default values<br>
	 * Fails to start server if host or port are invalid
	 * @param port - the port to use
	 */
	public ServerCommunicator(int portNumber) {		
		try {
			server = HttpServer.create(new InetSocketAddress(DEFAULT_HOST, portNumber), MAX_WAITING_CONNECTIONS);
			server.setExecutor(null); //this gives us the default executer
			initializeContexts();
			server.start();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * Initializes a ServerCommunicator with default host and default port, see Class Java doc for the default values<br>
	 * Fails to start server if host or port are invalid
	 */
	public ServerCommunicator() {
		try {
			server = HttpServer.create(new InetSocketAddress(DEFAULT_HOST, DEFAULT_PORT), MAX_WAITING_CONNECTIONS);
			server.setExecutor(null); //this gives us the default executer
			initializeContexts();
			server.start();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * This function initializes all the context for all the variations of
	 * ServerCommunicator constructors, which prevents duplicate code.
	 * @pre the server field must be initialized properly (not null)
	 * @post the context handlers are added
	 */
	private void initializeContexts() {
		server.createContext("/user/register", new UserRegisterHandler());
		server.createContext("/user/login", new UserLoginHandler());
		server.createContext("/games/create", new GamesCreateHandler());
		server.createContext("/games/list", new GamesListHandler());
		server.createContext("/games/join", new GamesJoinHandler());
		server.createContext("/game/model", new GameModelHandler());
		server.createContext("/moves/rollNumber", new MovesRollNumberHandler());
		server.createContext("/moves/sendChat", new MovesSendChatHandler());
		server.createContext("/moves/discardCards", new MovesDiscardCardsHandler());
		server.createContext("/moves/robPlayer", new MovesRobPlayerHandler());
		server.createContext("/moves/buyDevCard", new MovesBuyDevCardHandler());
		server.createContext("/moves/yearOfPlenty", new MovesYearOfPlentyHandler());
		server.createContext("/moves/roadBuilding", new MovesRoadBuildingHandler());
		server.createContext("/moves/soldier", new MovesSoldierHandler());
		server.createContext("/moves/monopoly", new MovesMonopolyHandler());
		server.createContext("/moves/monument", new MovesMonumentHandler());
		server.createContext("/moves/maritimeTrade", new MovesMaritimeTradeHandler());
		server.createContext("/moves/offerTrade", new MovesOfferTradeHandler());
		server.createContext("/moves/acceptTrade", new MovesAcceptTradeHandler());
		server.createContext("/moves/buildRoad", new MovesBuildRoadHandler());
		server.createContext("/moves/buildSettlement", new MovesBuildSettlementHandler());
		server.createContext("/moves/buildCity", new MovesBuildCityHandler());
		server.createContext("/moves/finishTurn", new MovesFinishTurnHandler());
	}
}
