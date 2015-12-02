package server;

public class ServerMain {
	
	public static void main(String[] args) {
		int port;
		String host;
		ServerCommunicator server;
		
		switch (args.length) {
		case 0:
			server = new ServerCommunicator();
			break;
			
		case 1:
			try {
				port = Integer.parseInt(args[0]);
				server = new ServerCommunicator(port);
			}
			catch (Exception e) {
				host = args[0];
				server = new ServerCommunicator(host);
			}
			break;
			
		case 2:
			try {
				port = Integer.parseInt(args[0]);
				host = args[1];
				server = new ServerCommunicator(host, port);
			}
			catch (Exception e) {
				System.err.println("Invalid Arguments");
				return;
			}
			break;
			
		default:
			System.err.println("Invalid Arguments");
			return;
		}
	}
}
