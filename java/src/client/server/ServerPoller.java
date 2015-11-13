package client.server;

import java.util.Timer;
import java.util.TimerTask;

import shared.json.Converter;
import client.exceptions.ServerException;
import client.model.ModelFacade;
import client.model.TransferModel;

/**
 * This class is used to 
 * @author djoshuac
 */
public class ServerPoller {
	/**
	 * UpdateModelTask is an inner class that extends TimerTask so the ServerPoller
	 * can create a TimerTask that polls it's target server.
	 * @author djoshuac
	 */
	private static class UpdateModelTask extends TimerTask {		
		/**
		 * This function gets the latest Model from the server and
		 * updates it in the ClientFacade.
		 * 
		 * @pre ServerPoller.clientServer can't be null
		 * @post clientServer is polled, ClientFacade is updated
		 * @throw NullPointerException - if ServerPoller.clientServer is null
		 */
		@Override
		public void run() {
			TransferModel model;
			try {
				model = ServerProxy.getModel(ModelFacade.getModelVersion());
				ModelFacade.updateModel(model);
			}
			catch (ServerException e) {
				System.err.println(e.getReason());
				return;
			}
		}
	}
	
	private static final int ONE_SECOND = 1000; //in milliseconds
	private static final int NO_DELAY = 0; //in milliseconds
	private static Timer timer;
	
	/**
	 * Delays, and then starts a timer to poll the target server at regular intervals.
	 * @pre delay must nonnegative, period must be positive, System.currentTimeMillis()
	 * must be nonnegative, and the clock used for Object.wait() must be accurate
	 * @param delay - delay in milliseconds before the polling of the server should begin
	 * @param period - delay in milliseconds between each poll to the server
	 * @post After the delay, the ServerProxy will be polled periodically. This will
	 * override any previous calls to start
	 * @throw IllegalArgumentException - if delay is less than zero, period is less than one,
	 * or delay + System.currentTimeMillis() is less than zero
	 */
	public static void start(long period, long delay) {
		if (timer == null) {
			timer = new Timer();
			timer.schedule(new UpdateModelTask(), delay, period);
		}
		else {
			timer.cancel();
			timer.purge();
			timer = null;
			start(period, delay);
		}
	}
	/**
	 * Starts a timer to poll the target server at regular intervals.
	 * @pre period must be positive, System.currentTimeMillis() must be nonnegative,
	 * and the clock used for Object.wait() must be accurate
	 * @param period - delay in milliseconds between each poll to the server
	 * @post After the delay, the CLIENTSERVER will be polled at the given period. This will
	 * override any previous calls to start
	 * @throw IllegalArgumentException - if period is less than one, or 
	 * System.currentTimeMillis() is less than zero
	 */
	public static void start(long period) {
		start(period, NO_DELAY);
	}
	/**
	 * Starts a timer to poll the target server at regular intervals.
	 * @pre System.currentTimeMillis() must be nonnegative, and the clock used for
	 * Object.wait() must be accurate
	 * @post The CLIENTSERVER will be polled every second. This will override any
	 * previous calls to start
	 * @throw IllegalArgumentException - if period is less than one, or 
	 * System.currentTimeMillis() is less than zero
	 */
	public static void start() {
		start(ONE_SECOND, NO_DELAY);
	}
	
	/**
	 * Stops polling the target server. If not running nothing happens
	 * @pre none
	 * @post Polling to the target server is stopped
	 */
	public static void stop() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
			timer = null;
		}
	}
}



