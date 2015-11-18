package server.handlers.games;

import java.io.IOException;

import server.facades.ServerGamesFacade;
import server.facades.ServerMovesFacade;
import server.handlers.IHandler;
import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.UserInfo;
import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

public class GamesCreateHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		CreateGameRequest createGameRequest = Converter.fromJson(exchange.getRequestBody(), CreateGameRequest.class);
		
		try {
			UserInfo user = getUserCookie(exchange);
			ServerGamesFacade.getInstance().verifyUserInformation(user);
			
			CreateGameResponse response = ServerGamesFacade.getInstance().createGame(createGameRequest.getName());
			ServerMovesFacade.getInstance().addGame(createGameRequest);
		
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			exchange.getResponseBody().write(Converter.toJson(response).getBytes());
		}
		catch (ServerException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(Converter.toJson(e.getReason()).getBytes());
		}
		
		exchange.close();
	}

}
