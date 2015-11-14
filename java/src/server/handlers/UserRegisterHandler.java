package server.handlers;

import java.io.IOException;

import server.facades.ServerGamesFacade;
import shared.json.Converter;
import shared.transferClasses.UserCredentials;
import sun.net.www.protocol.http.HttpURLConnection;
import client.exceptions.ServerException;

import com.sun.net.httpserver.HttpExchange;

public class UserRegisterHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {		
		UserCredentials userCredentials = Converter.fromJson(exchange.getRequestBody(), UserCredentials.class);

		boolean success;
		try {
			success = ServerGamesFacade.getInstance().register(userCredentials.getUsername(), userCredentials.getPassword());
			if(success == true) {
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				exchange.getResponseBody().write(Converter.toJson("Success").getBytes());
			}
			else {
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				exchange.getResponseBody().write(Converter.toJson("Failure").getBytes());
			}
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			exchange.getResponseBody().write(Converter.toJson(e.getReason()).getBytes());
		}
		
		exchange.getResponseBody().close();
	}

}
