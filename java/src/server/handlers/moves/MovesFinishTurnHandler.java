package server.handlers.moves;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.command.FinishTurnCommand;
import server.facades.ServerGamesFacade;
import server.handlers.IHandler;
import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.FinishTurn;
import shared.transferClasses.UserInfo;
import sun.net.www.protocol.http.HttpURLConnection;

public class MovesFinishTurnHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		FinishTurn finish = Converter.fromJson(exchange.getRequestBody(), FinishTurn.class);

		try {			
			UserInfo cookieUser = getUserCookie(exchange);
			
			ServerGamesFacade.getInstance().verifyUserInformation(cookieUser);
			
			Integer cookieGame = getGameCookie(exchange);
			
			ServerGamesFacade.getInstance().verifyUserIsInGame(cookieGame, cookieUser);
			
			FinishTurnCommand command = new FinishTurnCommand(cookieGame, finish);
			command.execute();
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			exchange.getResponseBody().write(Converter.toJson("Success!").getBytes());
			
		} catch (ServerException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(Converter.toJson(e.getReason()).getBytes());
		}
	
		exchange.close();
	}

}
