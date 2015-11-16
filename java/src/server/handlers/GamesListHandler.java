package server.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import server.facades.ServerGamesFacade;
import shared.json.Converter;
import shared.transferClasses.Game;
import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

public class GamesListHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		Map<String, List<String>> headers = exchange.getRequestHeaders();
		
		List<Game> games = ServerGamesFacade.getInstance().list();
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		exchange.getResponseBody().write(Converter.toJson(games).getBytes());
		
		exchange.getResponseBody().close();
	}

}
