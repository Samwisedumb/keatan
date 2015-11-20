package client.model;

import java.util.List;

public class TransferMap {
	private List<Hex> hexes;
	private List<VertexValue> vertices;
	private List<EdgeValue> edges;
	private int radius;
	private HexLocation robber;
	
	public void addHex(Hex hex) {
		hexes.add(hex);
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public void setRobber(HexLocation location) {
		this.robber = location;
	}

	public List<Hex> getHexes() {
		return hexes;
	}

	public List<EdgeValue> getEdges() {
		return edges;
	}

	public List<VertexValue> getVertexValues() {
		return vertices;
	}

	public int getRadius() {
		return radius;
	}

	public HexLocation getRobber() {
		return robber;
	}
}
