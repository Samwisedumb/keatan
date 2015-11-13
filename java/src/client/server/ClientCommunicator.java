package client.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

import shared.json.Converter;
import client.exceptions.ServerException;

/**
 * This is a temporary communicator class until we can take control of the server and work the cookies
 * the way we want to
 * @author djoshuac
 *
 */
public class ClientCommunicator {
	/**
	 * The url of the server to communicate with
	 */
	private String serverUrl;
	/**
	 * This is the time until the a connection is timed out
	 */
	private int timeOut;
	/**
	 * This is the default timeOut time
	 */
	private static final int DEFAULT_TIMEOUT = 5000;
	
	private String urlEncodedUserCookie;
	private String urlEncodedGameCookie;
	
	/**
	 * Constructs a ClientCommunicator object
	 * @pre severUrl must be a valid url for a server.
	 * @param serverUrl - the url of the server to send commands to
	 * @post a functioning ClientCommuicator is constructed.
	 */
	public ClientCommunicator(String serverUrl) {
		this.serverUrl = serverUrl;
		this.timeOut = DEFAULT_TIMEOUT;
	}
	
	
	// getters and setters
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	public String getServerUrl() {
		return this.serverUrl;
	}
	public int getTimeOut() {
		return this.timeOut;
	}
	public void createCookie(){
		
	}
	
	// utility functions
	 /**
	  * Sends an Http Post request to the specified server. This class encapsulates all errors
	  * into one ServerException which is useful for avoiding multi-catch blocks.
	  * The connection times out at the set timeOut time.
	  * @pre Command must be a valid command for the server. The serverUrl must be working and valid.
	  * Data must be an object that can be serialized to json and is appropriate for the specified
	  * command.
	  * @param command - the command to be executed on the server
	  * @param data - an object to be serialized to json to be sent with the request
	  * @return A String of the response body from the call to the server
	  * @post The server command is successfully executed.
	  * @throws ServerException when the ClientCommunicator cannot connect to the server for any reason
	  */
	public String send(String command, Object data) throws ServerException {
		return send(command, data, String.class);
	}
	
	 /**
	  * Sends an Http Post request to the specified server returning the data sent back
	  * from the server. This class encapsulates all errors into one ServerException which is useful
	  * for avoiding multi-catch blocks.
	  * The connection times out at the set timeOut time.
	  * @pre Command must be a valid command for the server. The serverUrl must be working and valid.
	  * Data must be an object that can be serialized to json and is appropriate for the specified
	  * command. The classOfReturnObject must be correspond to the data returned by the server.
	  * @param command - the command to be executed on the server
	  * @param data - an object to be serialized to json to be sent with the request
	  * @param classOfReturnObject - used to deserialize the data sent back from the server
	  * to the desired class using reflection
	 * @post The command is successfully executed on the server. An Object that can be cast to
	  * the given classOfReturnObject is returned.
	  * @throws ServerException when the ClientCommunicator cannot connect to the server for any reason
	  */
	public <T> T send(String command, Object data, Type classOfReturnObject) throws ServerException {
		try {
			URL url = new URL("http://" + serverUrl + command);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setConnectTimeout(timeOut);
			connection  = addHeaders(connection);
			connection.connect();
			connection.setReadTimeout(timeOut);
			OutputStream requestBody = connection.getOutputStream();
			
			
			String json = Converter.toJson(data);
			requestBody.write(json.getBytes());
			requestBody.close();
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream responseBody = connection.getInputStream();
				
//				java.util.Scanner s = new java.util.Scanner(responseBody).useDelimiter("\\A"); If you want to see SwaggerModel
//			    String joe = s.hasNext() ? s.next() : "";
//			    System.out.println(joe);
//			    
//				responseBody = new ByteArrayInputStream(joe.getBytes(StandardCharsets.UTF_8));
				
				handleCookie(command, connection);
				return Converter.fromJson(responseBody, classOfReturnObject);
			}
			else {
				try {
					InputStream responseBody = connection.getErrorStream();
					InputStreamReader reader = new InputStreamReader(responseBody);
					Scanner scan = new Scanner(reader);
					String errorMessage = "";
					while (scan.hasNext()) {
						errorMessage += scan.nextLine();
					}
					scan.close();
					throw new ServerException(errorMessage);
				}
				catch(Exception e) {
					throw new ServerException("Could not read error stream from server");
				}
			}
		}
		catch (MalformedURLException mue) {
			throw new ServerException("Url is malformed: " + serverUrl + command);
		}
		catch (SocketTimeoutException ste) {
			throw new ServerException("Connection timed out after " + timeOut + " miliseconds");
		}
		catch (IOException ioe) {
			throw new ServerException("An IOException occurred");
		}
	}

	private HttpURLConnection addHeaders(HttpURLConnection connection) {
		String cookieString = "";
		if (urlEncodedUserCookie != null){
			cookieString += "catan.user=" + urlEncodedUserCookie;
		}
		if (urlEncodedGameCookie != null){
			cookieString += "; catan.game=" + urlEncodedGameCookie;
		}
		connection.setRequestProperty("Cookie", cookieString);
		return connection;
	}
	
	private void handleCookie(String command, HttpURLConnection connection) {
		if (command.equals("/user/login")){
			urlEncodedUserCookie = connection.getHeaderField("Set-cookie");
			urlEncodedUserCookie = urlEncodedUserCookie.replace("catan.user=", "");
			urlEncodedUserCookie = urlEncodedUserCookie.replace(";Path=/;", "");
		} else if (command.equals("/games/join")) {
			urlEncodedGameCookie = connection.getHeaderField("Set-cookie");
			System.out.println(urlEncodedGameCookie);
			urlEncodedGameCookie = urlEncodedGameCookie.replace("catan.game=", "");
			urlEncodedGameCookie = urlEncodedGameCookie.replace(";Path=/;", "");
		}
		
	}
}
