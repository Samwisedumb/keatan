package shared.transferClasses;

import shared.definitions.CatanColor;


/**
 * This transfer object is used for the join game request to the server
 * <li>int id - the gameID</li>
 * <li>CatanColor color - the color of the player trying to join</li>
 * @author djoshuac
 */
public class JoinGameRequest {
	private int id;
	private CatanColor color;
	
	/**
	 * @param id - the game id
	 * @param color - the color of the player
	 */
	public JoinGameRequest(int id, CatanColor color) {
		setId(id);
		setColor(color);
	}
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public CatanColor getColor() {
		return this.color;
	}
	public void setColor(CatanColor color) {
		this.color = color;
	}
}
