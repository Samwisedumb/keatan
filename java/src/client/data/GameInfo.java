package client.data;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.CatanColor;
import shared.transferClasses.Game;
import shared.transferClasses.GetPlayer;
import client.model.Player;
import client.model.TransferModel;

/**
 * Used to pass game information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique game ID</li>
 * <li>Title: Game title (non-empty string)</li>
 * <li>Players: List of players who have joined the game (can be empty)</li>
 * </ul>
 * 
 */
public class GameInfo
{
	private int id;
	private String title;
	private List<PlayerInfo> players;
	
	public GameInfo()
	{
		setId(-1);
		setTitle("");
		players = new ArrayList<PlayerInfo>();
	}
	
	public GameInfo(Game game) {
		setId(game.getID());
		setTitle(game.getTitle());
		List<GetPlayer> playerList = game.getPlayers();
		players = new ArrayList<PlayerInfo>();
		int i = 0;
		for (GetPlayer player : playerList) {
			if (player.getName() != null) {
				players.add(new PlayerInfo(player, i++));
			}
		}
	}

	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void addPlayer(PlayerInfo newPlayer)
	{
		players.add(newPlayer);
	}
	
	public List<PlayerInfo> getPlayers()
	{
		return players;
	}
	
	/**
	 * clears all player info from this game info object
	 */
	public void clearPlayers() {
		players.clear();
	}
	
	
	/**
	 * Updates this games information based on the retrieved data lump
	 * @param newLump
	 */
	public void update(TransferModel newLump) {
		clearPlayers();
		List<Player> players = newLump.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if (p != null) {
				addPlayer(new PlayerInfo(p, i));
			}
		}
	}

	public PlayerInfo getPlayerInfo(String name) {
		for (PlayerInfo player : players) {
			if (name.equals(player.getName())) {
				return player;
			}
		}
		return null;
	}
	
	public PlayerInfo getPlayerInfo(CatanColor color) {
		for (PlayerInfo player : players) {
			if (color == player.getColor()) {
				return player;
			}
		}
		return null;
	}
	
	public PlayerInfo getPlayerInfo(int playerIndex) {
		return players.get(playerIndex);
	}
}

