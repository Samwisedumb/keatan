package client.model;

/**
 * A dumb data holder for a vertex of a settlers of Catan map<br>
 * owner - the index of the player who owns the vertex object
 * location - the location of the vertex object : must be a valid vertex location
 * @author djoshuac
 */
public class VertexObject {
	private int owner;
	private VertexLocation location;
	
	public VertexObject(int ownerId, VertexLocation location) {
		setOwner(ownerId);
		setLocation(location);
	}
	public VertexObject() {
		
	}
	
	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public VertexLocation getLocation() {
		return location;
	}

	public void setLocation(VertexLocation location) {
		this.location = location;
	}
	
	
	public boolean validate(int numPlayers, int radius) {
		if (owner < 0 || owner > numPlayers - 1) return false;
		if (location == null || !location.validate(radius)) return false;
		return true;
	}
	
	
}
