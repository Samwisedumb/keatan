package server.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import server.facades.ServerGamesFacade;
import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.Game;
import shared.transferClasses.UserInfo;
import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

/**
 * A hanler to handle the list games request
 * @author djoshuac
 */
public class GamesListHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			UserInfo user = getUserCookie(exchange);
			ServerGamesFacade.getInstance().verifyUserInformation(user);
			
			List<Game> games = ServerGamesFacade.getInstance().listGames();
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);			
			exchange.getResponseBody().write(Converter.toJson(games).getBytes());
		}
		catch (ServerException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(Converter.toJson(e.getReason()).getBytes());
		}
		
		exchange.close();
	}

}
