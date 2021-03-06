package server.handlers.user;

import java.io.IOException;

import server.facades.ServerGamesFacade;
import server.handlers.IHandler;
import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.UserCredentials;
import shared.transferClasses.UserInfo;
import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

public class UserLoginHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {		
		UserCredentials userCredentials = Converter.fromJson(exchange.getRequestBody(), UserCredentials.class);
		
		try {
			UserInfo user = ServerGamesFacade.getInstance().loginUser(userCredentials.getUsername(), userCredentials.getPassword());
			setUserCookie(exchange, user);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			exchange.getResponseBody().write(Converter.toJson("Success").getBytes());
		}
		catch (ServerException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(Converter.toJson(e.getReason()).getBytes());
		}
		
		exchange.getResponseBody().close();
	}
}
