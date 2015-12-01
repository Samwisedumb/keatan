package shared.transferClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import shared.definitions.CatanColor;
import shared.exceptions.InvalidObjectException;

/**
 * A TransferClass game information object
 * <li>String title - the name of the game : cannot be null</li>
 * <li>int id - the id of the game</li>
 * <li>List/<GetPlayer/> players - a list of players in the game : cannot be null, size cannot exceed...</li>
 * <li>static int MAX_PLAYERS = 4 - the maximum number of players that can be in a game</li>
 * @author djoshuac
 */
public class Game extends ValidatableObject {
	private String title;
	private int id;
	private List<GetPlayer> players;
	
	
	private Set<CatanColor> gameColors;
	
	public static final int MAX_PLAYERS = 4;
	
	public Game(String title, int id) {
		setTitle(title);
		setID(id);
		setPlayers(new ArrayList<GetPlayer>(MAX_PLAYERS));
		gameColors = new TreeSet<CatanColor>();
	}

	public List<GetPlayer> getPlayers() {
		return this.players;
	}
	public String getTitle() {
		return title;
	}
	public int getID() {
		return id;
	}

	private void setPlayers(List<GetPlayer> players) {
		this.players = players;
	}
	private void setTitle(String title) {
		this.title = title;
	}
	private void setID(int id) {
		this.id = id;
	}

	/**
	 * Sees if a game can have players added to it.
	 * @return True if number of players is less than MAX_PLAYERS.
	 * False if otherwise
	 * @pre game must be a valid game - see validate / 
	 * @post see return
	 */
	public boolean isFull() {
		if (this.players.size() >= MAX_PLAYERS) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Adds a player to the game
	 * @param player - the player to add
	 * @pre the game must not be full<br>
	 * the game must be valid
	 * @post the player is added to the game
	 */
	public void addPlayer(GetPlayer player) {
		this.players.add(player);
		this.gameColors.add(player.getColor());
	}

	/**
	 * Returns a player with the given index
	 * @param index - the index of the player to get
	 * @return the player with the given index, null if no player has that index
	 * @pre 0 &lt index &lt MAX_PLAYERS,<br>
	 * game must be valid
	 */
	public GetPlayer getPlayer(int index) {
		if (index >= players.size()) {
			return null;
		}
		return this.players.get(index);
	}
	
	/**
	 * Returns true if a player with the given ID is in this game
	 * @param playerID - the playerID in question
	 * @return true if a player in this game has the given ID<br>
	 * false if otherwise
	 * @post see return
	 * @pre none
	 */
	public boolean hasPlayer(int playerID) {
		for (GetPlayer player : players) {
			if (player.getID() == playerID) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasColor(CatanColor color) {
		return gameColors.contains(color);
	}
	
	@Override
	public String toString() {
		return "Game [title=" + title + ", id=" + id + ", players=" + players
				+ "]";
	}

	@Override
	public void validate() throws InvalidObjectException {
		if (title == null) {
			throw new InvalidObjectException("Game title is null");
		}
		if (players == null) {
			throw new InvalidObjectException("List of players is null");
		}
		if (players.size() > MAX_PLAYERS) {
			throw new InvalidObjectException("Too many players in game");
		}
	}
}
