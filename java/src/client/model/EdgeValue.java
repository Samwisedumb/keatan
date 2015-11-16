package client.model;

import java.util.List;

import client.model.EdgeLocation;
import client.model.Road;

/**
 * Contains data about the edges between hexes on the map.
 */
public class EdgeValue {
	
	private EdgeLocation location;
	private Road road;
	
	/**
	 * @pre playerIndex must be in the range [-1,3]
	 * @param playerIndex
	 * @param location
	 * @post This object's data is set to the given params. Road is null because there will be no road on the object when it is first constructed
	 */
	public EdgeValue(EdgeLocation location) {
		this.location = location;
		this.road = null;
	}


	/**
	 * Sets the edge's North and South locations, referring to its relative position based on the two hexes that share it
	 * @param location the location passed into the constructor
	 */

	public EdgeLocation getLocation() {
		return location;
	}
	
	public Road getRoad() {
		return road;
	}

	public void setRoad(Road road) {
		this.road = road;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {return true;}
		else if(obj == null) {return false;}
		else if(this.getClass() != obj.getClass()) {return false;}
		
		else {
			EdgeValue otherEdge = (EdgeValue) obj;
			return (this.getLocation() == otherEdge.getLocation());
		}
	}

	public boolean hasRoad() {
		if (road == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public List<EdgeValue> getAdjacentEdges() {
		// TODO Auto-generated method stub
		return null;
	}


	public List<VertexValue> getAdjacentVertices() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return "EdgeValue [location=" + location + ", road=" + road + "]";
	}
}
