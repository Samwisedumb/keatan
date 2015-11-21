package server.handlers.moves;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.command.MaritimeTradeCommand;
import server.facades.ServerGamesFacade;
import server.handlers.IHandler;
import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.MaritimeTrade;
import shared.transferClasses.UserInfo;
import sun.net.www.protocol.http.HttpURLConnection;

public class MovesMaritimeTradeHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		MaritimeTrade trade = Converter.fromJson(exchange.getRequestBody(), MaritimeTrade.class);
		
		try {			
			UserInfo cookieUser = getUserCookie(exchange);
			
			ServerGamesFacade.getInstance().verifyUserInformation(cookieUser);
			
			Integer cookieGame = getGameCookie(exchange);
			
			ServerGamesFacade.getInstance().verifyUserIsInGame(cookieGame, cookieUser);
			
			MaritimeTradeCommand command = new MaritimeTradeCommand(cookieGame, trade);
			command.execute();
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			exchange.getResponseBody().write(Converter.toJson("Success!").getBytes());
			
		} catch (ServerException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(Converter.toJson(e.getReason()).getBytes());
		}
	}
}
