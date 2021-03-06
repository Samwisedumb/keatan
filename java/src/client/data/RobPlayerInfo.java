package client.data;

/**
 * Used to pass player information into the rob view<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: Player's order in the game [0-3]</li>
 * <li>Name: Player's name (non-empty string)</li>
 * <li>Color: Player's color (cannot be null)</li>
 * <li>NumCards: Number of resource cards the player has (>= 0)</li>
 * </ul>
 * 
 */
public class RobPlayerInfo extends PlayerInfo
{
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + numCards;
		result = prime * result + getID();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RobPlayerInfo other = (RobPlayerInfo) obj;
		if (numCards != other.numCards)
			return false;
		if (getID() != other.getID())
			return false;
		return true;
	}

	private int numCards;
	
	public RobPlayerInfo()
	{
		super();
	}
	
	public int getNumCards()
	{
		return numCards;
	}
	
	public void setNumCards(int numCards)
	{
		this.numCards = numCards;
	}
	
}

