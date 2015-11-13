package server.handlers;

import java.io.IOException;

import server.facades.ServerGamesFacade;
import shared.json.Converter;
import shared.transferClasses.UserCredentials;

import com.sun.net.httpserver.HttpExchange;

public class UserRegisterHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
		UserCredentials userCredentials = Converter.fromJson(exchange.getRequestBody(), UserCredentials.class);
		
		boolean success = ServerGamesFacade.getInstance().register(userCredentials.getUsername(), userCredentials.getPassword());
		
		if(success == true) {
			exchange.getResponseBody().write(Converter.toJson("Success").getBytes());
		}
		else {
			exchange.getResponseBody().write(Converter.toJson("Failure").getBytes());
		}
		
		exchange.getResponseBody().close();
	}

}
