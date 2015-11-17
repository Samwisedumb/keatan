package server.handlers;


import java.util.List;

import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.UserInfo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class IHandler implements HttpHandler {
	protected UserInfo getUserCookie(HttpExchange exchange) throws ServerException {
		List<String> values = exchange.getRequestHeaders().get("User");
		
		if (values == null || values.size() == 0) {
			throw new ServerException("Missing user cookie");
		}
		
		UserInfo user = Converter.fromJson(values.get(0), UserInfo.class);
		
		return user;
	}
}
