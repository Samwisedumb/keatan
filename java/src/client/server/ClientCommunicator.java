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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import shared.exceptions.ServerException;
import shared.json.Converter;

/**
 * This class sends commands to a server specified by the server
 * url.
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
	
	/**
	 * Constructs a ClientCommunicator object
	 * @pre severUrl must be a valid url for a server.
	 * @param serverUrl - the url of the server to send commands to
	 * @post a functioning ClientCommuicator is constructed.
	 */
	public ClientCommunicator(String serverUrl) {
		this.serverUrl = serverUrl;
		this.timeOut = DEFAULT_TIMEOUT;
		this.requestHeaders = new HashMap<String, List<String>>();
		this.lastResponseHeaders = new HashMap<String, List<String>>();
	}
	
	/**
	 * The headers added to requests in future send calls
	 */
	private Map<String, List<String>> requestHeaders;
	
	/**
	 * The headers retrieved from the response in the last send call
	 */
	private Map<String, List<String>> lastResponseHeaders;
	
	/**
	 * Adds a request header to the server request in the next send call
	 * @param header - the name of the header to add
	 * @param value - the value of the header to add
	 * @pre none
	 * @post The given header is added to the server request.
	 * If the given header was a previously set header, then the value
	 * is added as a another property in that header
	 */
	public void addRequestHeader(String header, String value) {
		if (requestHeaders.get(header) == null) {
			requestHeaders.put(header, new ArrayList<String>());
		}
		requestHeaders.get(header).add(value);
	}
	
	/**
	 * Retrieves the last response headers from the previous send operation
	 * @return A mapping of response headers to response values. This will
	 * return an empty map if no response headers were received or no send
	 * operation was called.
	 * @pre send must have been called, the serverUrl must be for a valid working server
	 * @post see return
	 */
	public Map<String, List<String>> getResponseHeadersForLastSend() {
		return lastResponseHeaders;
	}
	
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
			
			addRequestHeaders(connection);
			
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
				
				setLastResponseHeaders(connection.getHeaderFields());
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
					throw new ServerException(errorMessage.substring(1, errorMessage.length() -1 ));
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
			throw new ServerException("failed to connect to server");
		}
	}
	
	/**
	 * Adds the set request headers to the connection
	 * @pre call this right before calling connection.conect
	 * @post the headers are added to the request in connection
	 * @param connection - the connection to add the request headers to
	 */
	private void addRequestHeaders(HttpURLConnection connection) {
		for (String header : requestHeaders.keySet()) {
			for (String value : requestHeaders.get(header)) {
				connection.setRequestProperty(header, value);
			}
		}
	}
	
	/**
	 * Sets the last response headers for the last send request.
	 */
	private void setLastResponseHeaders(Map<String, List<String>> headers) {
		lastResponseHeaders = headers;
	}
}