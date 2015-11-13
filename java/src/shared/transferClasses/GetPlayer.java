package shared.transferClasses;

import shared.definitions.CatanColor;

public class GetPlayer {
	private CatanColor color;
	private String name;
	private int id;
	
	public GetPlayer(CatanColor color, String name, int id) {
		super();
		this.color = color;
		this.name = name;
		this.id = id;
	}
	
	public CatanColor getColor() {
		return color;
	}
	
	public void setColor(CatanColor color) {
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}	
}
