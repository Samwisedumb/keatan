package server.handlers;

import java.io.IOException;

import server.facades.ServerGamesFacade;
import shared.json.Converter;
import shared.transferClasses.JoinGameRequest;
import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

public class GamesJoinHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		JoinGameRequest requestJoin = Converter.fromJson(exchange.getRequestBody(), JoinGameRequest.class);
		
		boolean success = ServerGamesFacade.getInstance().joinGame(requestJoin);
		
		
		if(success == true) {	
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			exchange.getResponseBody().write(Converter.toJson("Success!").getBytes());
		}
		else {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(Converter.toJson("Game is full").getBytes());
		}
		
		exchange.getResponseBody().close();
	}
}
