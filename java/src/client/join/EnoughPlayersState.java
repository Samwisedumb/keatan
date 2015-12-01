package client.join;

/**
 * A state where enough players are in the game for the game to begin
 * @author djoshuac
 */
public class EnoughPlayersState implements IPlayerWaitingState {

	/**
	 * Does nothing since a player cannot exit a game, and when they win the join game, the player waiting state should reset
	 */
	@Override
	public void update() {
		// do nothing
	}
}
