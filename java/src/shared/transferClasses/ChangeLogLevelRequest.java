package shared.transferClasses;

public class ChangeLogLevelRequest {

	public ChangeLogLevelRequest(String logLevel) {
		this.logLevel = logLevel;
	}
	
	/**
	 * The server's new log level. The following values are allowed: ALL, SEVERE, WARNING ,INFO, CONFIG, FINE, FINER, FINEST, OFF
	 */
	private String logLevel;
	
	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

}
