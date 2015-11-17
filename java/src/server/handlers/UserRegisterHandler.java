package server.handlers;

import java.io.IOException;

import server.facades.ServerGamesFacade;
import shared.exceptions.InvalidObjectException;
import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.UserCredentials;
import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

public class UserRegisterHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {		
		UserCredentials userCredentials = Converter.fromJson(exchange.getRequestBody(), UserCredentials.class);

		try {
			userCredentials.validate();
			
			//UserInfo user = new UserInfo(userCredentials.getUsername(), userCredentials.getPassword(), );
			
			ServerGamesFacade.getInstance().register(userCredentials.getUsername(), userCredentials.getPassword());
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			exchange.getResponseBody().write(Converter.toJson("Success").getBytes());
		}
		catch (InvalidObjectException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(Converter.toJson(e.getReason()).getBytes());
		}
		catch (ServerException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(Converter.toJson(e.getReason()).getBytes());
		}
		
		exchange.getResponseBody().close();
	}

}
