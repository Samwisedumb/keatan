package client.model;

import client.model.VertexLocation;

/**
 *  An object representing the vertex of a Hex, containing information about what is located at that hex
 */
public class VertexValue {

	private VertexLocation location;
	private Settlement settlement; //MAKE THIS A SETTLEMENT!
	private City city; //MAKE THIS A CITY!

	/**
	 * @pre owner must be in the range [-1,3], and location must be a valid EdgeLocation
	 * @param location The map location of this vertex
	 * @post The object's stored owner and location are set to the given params.
	 */
	public VertexValue(VertexLocation location) {
		
		this.location = location;
		
		this.settlement = null;
		this.city = null;
	}

	public VertexLocation getLocation() {
		return location;
	}

	public Settlement getSettlement() {
		return settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}

	public City getCity() {
		return city;
	}

	/**
	 * @pre A settlement must already exist in this location to build a city
	 * @param city The new city to place
	 * @post The old settlement is removed and the new city is placed
	 */
	public void setCity(City city) {
		this.settlement = null;
		this.city = city;
	}
	
	public String toString() {
		String string = "";
		if (this.settlement != null) {
			string += "Has settlement, ";
		}
	    if (this.city != null) {
			string += "Has city, ";
		}
		if (this.location != null) {
			string += "Location: " + location.toString();
		}
		return string;
	}
	
	/**
	 * Returns true if this VertexValue has a Settlement or City
	 * @pre none
	 * @post return
	 * @return true if has a settlement or city, false if otherwise
	 */
	public boolean hasMunicipality() {
		if (this.settlement == null && this.city == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean ownsMunicipality(int playerIndex) {
		if(this.settlement != null) {
			if(this.settlement.getOwnerIndex() == playerIndex) {
				return true;
			}
			else {
				return false;
			}
		}
		
		else if(this.city != null) {
			if(this.city.getOwnerIndex() == playerIndex) {
				return true;
			}
			else {
				return false;
			}
		}
		
		else {
			return false;
		}
	}
	
	/**
	 * @return a vertex object that corresponds to a vertex value<br>
	 * returns null if no player has a settlement or city
	 */
	public VertexObject toVertexObject() {
		if (city != null) {
			return new VertexObject(city.getOwnerIndex(), location);
		}
		else if (settlement != null) {
			return new VertexObject(settlement.getOwnerIndex(), location);
		}
		else {
			return null;
		}
	}
	
	/**
	 * Returns the playerIndex of the player who has a municipality on this VertexValue
	 * @pre none
	 * @post see return
	 * @return the index of the player who owns a municipality on this vertex, -1 if no player owns one.
	 */
	public int getPlayerIndexOfOwner() {
		if (this.settlement == null) {
			if (this.city == null) {
				return -1;
			}
			else {
				return this.city.getOwnerIndex();
			}
		}
		else {
			return this.settlement.getOwnerIndex();
		}
	}
}
