package client.data;

import client.model.Player;
import shared.definitions.CatanColor;
import shared.transferClasses.GetPlayer;

/**
 * Used to pass player information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: Player's order in the game [0-3]</li>
 * <li>Name: Player's name (non-empty string)</li>
 * <li>Color: Player's color (cannot be null)</li>
 * </ul>
 * 
 */
public class PlayerInfo
{
	
	private int id;
	private int playerIndex;
	private String name;
	private CatanColor color;
	
	public PlayerInfo()
	{
		setID(-1);
		setPlayerIndex(-1);
		setName("");
		setColor(CatanColor.WHITE);
	}
	
	public PlayerInfo(GetPlayer player, int index) {
		setID(player.getId());
		setPlayerIndex(index);
		setName(player.getName());
		setColor(player.getColor());
	}

	public PlayerInfo(Player player, int index) {
		setID(player.getID());
		setPlayerIndex(index);
		setName(player.getName());
		setColor(player.getColor());
	}

	public PlayerInfo(String username) {
		this.name = username;
	}

	public int getID()
	{
		return id;
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public int getIndex()
	{
		return playerIndex;
	}
	
	public void setPlayerIndex(int playerIndex)
	{
		this.playerIndex = playerIndex;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public CatanColor getColor()
	{
		return color;
	}
	
	public void setColor(CatanColor color)
	{
		this.color = color;
	}

	@Override
	public int hashCode()
	{
		return 31 * this.id;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final PlayerInfo other = (PlayerInfo) obj;
		
		return this.id == other.id;
	}
}

