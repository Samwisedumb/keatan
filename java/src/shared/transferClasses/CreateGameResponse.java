package shared.transferClasses;

import java.util.List;

import shared.definitions.EmptyObject;

public class CreateGameResponse {
	private String title;
	private int id;
	private List<EmptyObject> players;
	
	public CreateGameResponse(String title, int id, List<EmptyObject> emptyPlayers) {
		setTitle(title);
		setId(id);
		setPlayers(emptyPlayers);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public List<EmptyObject> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<EmptyObject> players) {
		this.players = players;
	}
}
