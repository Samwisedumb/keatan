package server.handlers.games;

import java.io.IOException;

import server.facades.ServerGamesFacade;
import server.facades.ServerMovesFacade;
import server.handlers.IHandler;
import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.JoinGameRequest;
import shared.transferClasses.UserInfo;
import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

public class GamesJoinHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		JoinGameRequest requestJoin = Converter.fromJson(exchange.getRequestBody(), JoinGameRequest.class);
		
		UserInfo user;
		try {
			user = getUserCookie(exchange);
			
			ServerGamesFacade.getInstance().verifyUserInformation(user);
			
			ServerGamesFacade.getInstance().joinGame(user, requestJoin);
			
			boolean success = ServerMovesFacade.getInstance().addPlayerToGame(requestJoin, user.getUsernameString(), user.getUserID());
			
			if(success == true) {	
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				exchange.getResponseBody().write(Converter.toJson("Success!").getBytes());
			}
			else {
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				exchange.getResponseBody().write(Converter.toJson("Game is full").getBytes());
			}
		}
		catch (ServerException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(Converter.toJson(e.getReason()).getBytes());
		}
		
		
		
		exchange.getResponseBody().close();
	}
}
