package shared.transferClasses;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private String title;
	private int id;
	private List<GetPlayer> players;
	
	public Game(String title, int id) {
		this.title = title;
		this.id = id;
		this.players = new ArrayList<GetPlayer>(4);
	}

	public List<GetPlayer> getPlayers() {
		return this.players;
	}
	
	public void setPlayers(List<GetPlayer> players) {
		this.players = players;
	}
	
	public GetPlayer getPlayer(int index) {
		return this.players.get(index);
	}
	
	public void setPlayer(int index, GetPlayer player) {
		this.players.set(index, player);
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
}
