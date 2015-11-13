package server.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import shared.json.Converter;
import shared.transferClasses.UserCredentials;
import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

public class UserLoginHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Map<String, List<String>> headers = exchange.getRequestHeaders();
		System.out.println("\n\nRequest Headers");
		for (String field : headers.keySet()) {
			System.out.print(field + ": [");
			for (String value : headers.get(field)) {
				System.out.print(value + ", ");
			}
			System.out.println("]");
		}
		
		UserCredentials userCredentials = Converter.fromJson(exchange.getRequestBody(), UserCredentials.class);
		System.out.println(userCredentials.getUsername() + " : " + userCredentials.getPassword());
		
		exchange.getResponseHeaders().add("Set-cookie", "Your Cookie");
		exchange.getResponseHeaders().add("Set-cookie", "WEEEE");
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		
		exchange.getResponseBody().write(Converter.toJson("Success").getBytes());
		exchange.getResponseBody().close();
	}
}
