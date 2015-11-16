package server.handlers;

import java.io.IOException;

import shared.json.Converter;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.UserCredentials;

import com.sun.net.httpserver.HttpExchange;

public class GamesCreateHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		CreateGameRequest createGameRequest = Converter.fromJson(exchange.getRequestBody(), CreateGameRequest.class);
		
		
		
	}

}
