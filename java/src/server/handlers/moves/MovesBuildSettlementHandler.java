package server.handlers.moves;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.command.BuildSettlementCommand;
import server.handlers.IHandler;
import shared.json.Converter;
import shared.transferClasses.BuildSettlement;
import sun.net.www.protocol.http.HttpURLConnection;

public class MovesBuildSettlementHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		BuildSettlement build = Converter.fromJson(exchange.getRequestBody(), BuildSettlement.class);
		
		//A problem. We need to make it so there's a gameID here we can see
		BuildSettlementCommand command = new BuildSettlementCommand(0, build); //need to replace 0 with the correct ID for the game
		
		command.execute();
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		exchange.getResponseBody().write(Converter.toJson("Success!").getBytes());
		
		exchange.getResponseBody().close();
	}

}
