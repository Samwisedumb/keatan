package server.handlers.games;

import java.io.IOException;

import server.facades.ServerGamesFacade;
import server.handlers.IHandler;
import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.UserInfo;
import sun.net.www.protocol.http.HttpURLConnection;
import client.model.TransferModel;

import com.sun.net.httpserver.HttpExchange;

public class GamesModelHandler extends IHandler {
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			UserInfo user = getUserCookie(exchange);
			int gameID = getGameCookie(exchange);
			ServerGamesFacade.getInstance().verifyUserInformation(user);
			ServerGamesFacade.getInstance().verifyUserIsInGame(gameID, user);
			
			TransferModel response = ServerGamesFacade.getInstance().getTransferModel(gameID);
			
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
