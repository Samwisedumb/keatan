package server.handlers.moves;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.command.BuildRoadCommand;
import server.handlers.IHandler;
import shared.json.Converter;
import shared.transferClasses.BuildRoad;
import sun.net.www.protocol.http.HttpURLConnection;

public class MovesBuildRoadHandler extends IHandler {

	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		BuildRoad build = Converter.fromJson(exchange.getRequestBody(), BuildRoad.class);
		
		//A problem. We need to make it so there's a gameID here we can see
		BuildRoadCommand command = new BuildRoadCommand(0, build); //need to replace 0 with the correct ID for the game
		
		command.execute();
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		exchange.getResponseBody().write(Converter.toJson("Success!").getBytes());
		
		exchange.getResponseBody().close();
	}
	
}
