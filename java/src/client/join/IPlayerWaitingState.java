package client.join;

/**
 * A state interface for a waiting player state.
 * @author djoshuac
 */
public interface IPlayerWaitingState {
	/**
	 * How the state should update the player waiting view.
	 */
	public void update();
}
