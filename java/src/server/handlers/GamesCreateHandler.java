package server.handlers;

import java.io.IOException;

import server.facades.ServerGamesFacade;
import shared.json.Converter;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.UserCredentials;
import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

public class GamesCreateHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		CreateGameRequest createGameRequest = Converter.fromJson(exchange.getRequestBody(), CreateGameRequest.class);
		
		CreateGameResponse response = ServerGamesFacade.getInstance().create(createGameRequest);
	
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		exchange.getResponseBody().write(Converter.toJson(response).getBytes());
		
		exchange.getResponseBody().close();
		
	}

}
