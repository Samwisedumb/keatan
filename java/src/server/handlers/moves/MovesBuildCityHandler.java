package server.handlers.moves;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.command.BuildCityCommand;
import server.handlers.IHandler;
import shared.json.Converter;
import shared.transferClasses.BuildCity;
import sun.net.www.protocol.http.HttpURLConnection;

public class MovesBuildCityHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		BuildCity build = Converter.fromJson(exchange.getRequestBody(), BuildCity.class);
		
		//A problem. We need to make it so there's a gameID here we can see
		BuildCityCommand command = new BuildCityCommand(0, build); //need to replace 0 with the correct ID for the game
		
		command.execute();
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		exchange.getResponseBody().write(Converter.toJson("Success!").getBytes());
		
		exchange.getResponseBody().close();
	}

}
