package server;

import java.net.InetSocketAddress;

import server.handlers.UserLoginHandler;

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
		
		server.createContext("/user/login", new UserLoginHandler());
		
		server.start();
	}
}
