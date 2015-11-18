package server.handlers.moves;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.command.RollNumberCommand;
import server.handlers.IHandler;
import shared.json.Converter;
import shared.transferClasses.RollNumber;
import sun.net.www.protocol.http.HttpURLConnection;

public class MovesRollNumberHandler extends IHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		RollNumber roll = Converter.fromJson(exchange.getRequestBody(), RollNumber.class);
		
		RollNumberCommand command = new RollNumberCommand(0, roll);
		
		command.execute();
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		exchange.getResponseBody().write(Converter.toJson("Success!").getBytes());
		
		exchange.getResponseBody().close();
	}

}
