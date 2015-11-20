package shared.transferClasses;

import shared.definitions.CatanColor;

/**
 * A dumb data holder for a player in a Game object
 * @author djoshuac
 *
 */
public class GetPlayer {
	private CatanColor color;
	private String name;
	private int id;
	
	public GetPlayer(CatanColor color, String name, int id) {
		this.setColor(color);
		this.setName(name);
		this.setID(id);
	}
	
	public CatanColor getColor() {
		return color;
	}
	
	private void setColor(CatanColor color) {
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	public int getID() {
		return id;
	}
	
	private void setID(int id) {
		this.id = id;
	}	
}
