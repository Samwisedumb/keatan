package client.model;

import java.util.ArrayList;
import java.util.List;

public class TransferMap {
	private List<Hex> hexes;
	private List<Port> ports;
	private List<Road> roads;
	private List<VertexObject> settlements;
	private List<VertexObject> cities;
	private int radius;
	private HexLocation robber;

	public TransferMap() {
		hexes = new ArrayList<Hex>();
		ports = new ArrayList<Port>();
		settlements = new ArrayList<VertexObject>();
		cities = new ArrayList<VertexObject>();
	}
	
	public void addHex(Hex hex) {
		hexes.add(hex);
	}
	
	public void addPort(Port port) {
		ports.add(port);
	}
	
	public void addRoad(Road road) {
		roads.add(road);
	}
	
	public void addSettlement(VertexObject settlement) {
		settlements.add(settlement);
	}
	
	public void addCity(VertexObject city) {
		cities.add(city);
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

	public List<Port> getPorts() {
		return ports;
	}

	public List<Road> getRoads() {
		return roads;
	}

	public List<VertexObject> getSettlements() {
		return settlements;
	}

	public List<VertexObject> getCities() {
		return cities;
	}

	public int getRadius() {
		return radius;
	}

	public HexLocation getRobber() {
		return robber;
	}

	public boolean validate(int numPlayers) {
		if (hexes == null) return false;
		
		if (ports == null) return false;
		
		if (roads == null) return false;
		else {
			for (Road road : roads) {
				if (road == null || !road.validate(numPlayers, radius)) return false;
			}
		}
		
		if (settlements == null) return false;
		else {
			for (VertexObject vertex : settlements) {
				if (vertex == null || !vertex.validate(numPlayers, radius)) return false;
			}
		}
		
		if (cities == null) return false;
		else {
			for (VertexObject vertex : cities) {
				if (vertex == null || !vertex.validate(numPlayers, radius)) return false;
			}
		}
		
		if (radius != 3) return false;
		if (robber == null || !robber.validate(2)) return false;
		return true;
	}
}
