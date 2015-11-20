package server;

import java.net.InetSocketAddress;

import server.handlers.games.GamesCreateHandler;
import server.handlers.games.GamesJoinHandler;
import server.handlers.games.GamesListHandler;
import server.handlers.user.UserLoginHandler;
import server.handlers.user.UserRegisterHandler;

import com.sun.net.httpserver.HttpServer;

public class ServerCommunicator {
	private static final int MAX_WAITING_CONNECTIONS = 1;
	
	private HttpServer server;
	private int portNumber;
	
	public ServerCommunicator(int portNumber) {
		this.portNumber = portNumber;
		
		try {
			server = HttpServer.create(new InetSocketAddress(this.portNumber), MAX_WAITING_CONNECTIONS);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		server.setExecutor(null); //this gives us the default executer

		server.createContext("/user/register", new UserRegisterHandler());
		server.createContext("/user/login", new UserLoginHandler());
		server.createContext("/games/create", new GamesCreateHandler());
		server.createContext("/games/list", new GamesListHandler());
		server.createContext("/games/join", new GamesJoinHandler());
		
		server.start();
	}
}
