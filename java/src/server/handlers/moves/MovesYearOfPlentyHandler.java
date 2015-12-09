package server.handlers.moves;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.command.YearOfPlentyCommand;
import server.facades.ServerGamesFacade;
import server.handlers.IHandler;
import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.UserInfo;
import shared.transferClasses.YearOfPlenty;
import sun.net.www.protocol.http.HttpURLConnection;

public class MovesYearOfPlentyHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		YearOfPlenty plenty  = Converter.fromJson(exchange.getRequestBody(), YearOfPlenty.class);

		try {			
			UserInfo cookieUser = getUserCookie(exchange);
			
			ServerGamesFacade.getInstance().verifyUserInformation(cookieUser);
			
			Integer cookieGame = getGameCookie(exchange);
			
			ServerGamesFacade.getInstance().verifyUserIsInGame(cookieGame, cookieUser);
			
			YearOfPlentyCommand command = new YearOfPlentyCommand(cookieGame, plenty);
			command.execute();
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			exchange.getResponseBody().write(Converter.toJson(ServerGamesFacade.getInstance().getTransferModel(cookieGame)).getBytes());
			
		} catch (ServerException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(Converter.toJson(e.getReason()).getBytes());
		}
	
		exchange.getResponseBody().close();
	}

}
