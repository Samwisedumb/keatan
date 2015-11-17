package server.handlers;

import java.io.IOException;

import server.facades.ServerGamesFacade;
import shared.definitions.UserInfo;
import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.UserCredentials;
import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

public class UserLoginHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {		
		UserCredentials userCredentials = Converter.fromJson(exchange.getRequestBody(), UserCredentials.class);
		System.out.println(userCredentials.getUsername() + " : " + userCredentials.getPassword());
		
		//UserInfo user = //
		//exchange.getResponseHeaders().add("Set-cookie", Converter.toJson(user));
		
		boolean success;
		try {
			success = ServerGamesFacade.getInstance().login(userCredentials.getUsername(), userCredentials.getPassword());
			if(success == true) {
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				exchange.getResponseBody().write(Converter.toJson("Success").getBytes());
			}
			else {
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				exchange.getResponseBody().write(Converter.toJson("Wrong Password").getBytes());
			}
		} catch (ServerException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(Converter.toJson(e.getReason()).getBytes());
		}
		
		exchange.getResponseBody().close();
	}
}
