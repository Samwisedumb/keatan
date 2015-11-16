package server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.EdgeDirection;
import shared.definitions.VertexDirection;
import client.model.Port;
import client.model.EdgeLocation;
import client.model.EdgeValue;
import client.model.Hex;
import client.model.HexLocation;
import client.model.Road;
import client.model.TransferModel;
import client.model.VertexLocation;
import client.model.VertexObject;
import client.model.VertexValue;

/**
 * A model for a Settlers of Catan&#8482 game on the the server side
 * @author djoshuac
 */
public class ServerModel {
	
	private String gameName;
	
	private HashMap<HexLocation, Hex> hexes;
	private HashMap<EdgeLocation, Road> roads;
	private HashMap<VertexLocation, VertexObject> settlements;
	private HashMap<VertexLocation, VertexObject> cities;

	private HashMap<EdgeLocation, EdgeValue> edges;
	private HashMap<VertexLocation, VertexValue> vertices;
	
	private List<Port> ports;
	
	private HexLocation robber;
	
	private int radius;
	
	//EVENTUALLY YOU MUST CHANGE THE TRANSFER MODEL!
	private TransferModel transfer;
	
	public void createMap(boolean randomHexes, boolean randomChits, boolean randomPorts, String newName) {
	
		radius = 2;
		
		gameName = newName;
		
		List<Hex> theHexes = initializeHexList(randomHexes);
			
		List<Integer> theChits = initializeChitList(randomChits);
			
		setUpMap(theHexes, theChits);
			
		setUpPorts(randomPorts);
		
	}
	
	/**
	 * Initialize the list of hexes that will be added to the map, maybe randomizing their order
	 * @pre The game must be starting
	 * @param randomHexes determines whether or not the basic grid set up is randomized
	 * @return an array list of hexes
	 * @post the hexes that will eventually be put onto the game map are initialized in an array list
	 */
	private List<Hex> initializeHexList(boolean randomHexes) {
		
		List<Hex> gameHexes = new ArrayList<Hex>();
		
		//First Row of Beginner Grid
		gameHexes.add(new Hex(HexType.WOOD));		
		gameHexes.add(new Hex(HexType.SHEEP));
		gameHexes.add(new Hex(HexType.WHEAT));
		
		//Second Row
		gameHexes.add(new Hex(HexType.BRICK));
		gameHexes.add(new Hex(HexType.ORE));
		gameHexes.add(new Hex(HexType.BRICK));
		gameHexes.add(new Hex(HexType.SHEEP));
		
		//Third Row
		gameHexes.add(new Hex(HexType.DESERT));
		gameHexes.add(new Hex(HexType.WOOD));
		gameHexes.add(new Hex(HexType.WHEAT));
		gameHexes.add(new Hex(HexType.WOOD));
		gameHexes.add(new Hex(HexType.WHEAT));
		
		//Fourth Row
		gameHexes.add(new Hex(HexType.BRICK));
		gameHexes.add(new Hex(HexType.SHEEP));
		gameHexes.add(new Hex(HexType.SHEEP));
		gameHexes.add(new Hex(HexType.ORE));
		
		//Fifth Row
		gameHexes.add(new Hex(HexType.ORE));
		gameHexes.add(new Hex(HexType.WHEAT));
		gameHexes.add(new Hex(HexType.WOOD));
		
		if(randomHexes == true) {
			long seed = System.nanoTime();
			Collections.shuffle(gameHexes, new Random(seed));
		}
		
		return gameHexes;
	}
	
	/**
	 * Initialize the list of chits that will be added to the game map, maybe randomizing them
	 * @pre The game must be starting
	 * @param randomChits determines whether or not the chits are randomized
	 * @return an array list of integers representing the chits
	 * @post the chits that will eventually be put onto the game map are initialized in an array list
	 */
	private List<Integer> initializeChitList(boolean randomChits) {
		
		List<Integer> chits = new ArrayList<Integer>();
		
		//First Row for beginner board
		chits.add(new Integer(11));
		chits.add(new Integer(12));
		chits.add(new Integer(9));
		
		//Second Row
		chits.add(new Integer(4));
		chits.add(new Integer(6));
		chits.add(new Integer(5));
		chits.add(new Integer(10));
		
		//Third Row (Except Desert, which has no chits)
		chits.add(new Integer(3));
		chits.add(new Integer(11));
		chits.add(new Integer(4));
		chits.add(new Integer(8));
		
		//Fourth Row
		chits.add(new Integer(8));
		chits.add(new Integer(10));
		chits.add(new Integer(9));
		chits.add(new Integer(3));
		
		//Fifth Row
		chits.add(new Integer(5));
		chits.add(new Integer(2));
		chits.add(new Integer(6));
		
		if(randomChits == true) {
			long seed = System.nanoTime();
			Collections.shuffle(chits, new Random(seed));
		}
		
		return chits;
	}
	
	/**
	 * Initialize a list of ports then add them to the game map
	 * @pre The game must be starting
	 * @param randomPorts determines whether or not the port types are randomized (locations are always the same)
	 * @return nothing, simply adding the ports to the map
	 * @post the ports are added to the map
	 */
	private void setUpPorts(boolean randomPorts) {
		
		List<PortType> types = new ArrayList<PortType>();
	
		types.add(PortType.THREE);
		types.add(PortType.SHEEP);
		types.add(PortType.THREE);
		types.add(PortType.THREE);
		types.add(PortType.BRICK);
		types.add(PortType.WOOD);
		types.add(PortType.THREE);
		types.add(PortType.WHEAT);
		types.add(PortType.ORE);
		
		if(randomPorts == true) {
			long seed = System.nanoTime();
			Collections.shuffle(types, new Random(seed));
		}
		
		ports = new ArrayList<Port>();
		
		//add ports to ports array list, based on list of port types. The edge locations never change
		
		ports.add(new Port(types.remove(0), new HexLocation(0,2), EdgeDirection.North));
		
		ports.add(new Port(types.remove(0), new HexLocation(1,2), EdgeDirection.NorthEast));
		
		ports.add(new Port(types.remove(0), new HexLocation(2,1), EdgeDirection.NorthEast));
		
		ports.add(new Port(types.remove(0), new HexLocation(2,0), EdgeDirection.SouthEast));
		
		ports.add(new Port(types.remove(0), new HexLocation(1,-1), EdgeDirection.South));
		
		ports.add(new Port(types.remove(0), new HexLocation(-1,-2), EdgeDirection.South));
		
		ports.add(new Port(types.remove(0), new HexLocation(-2,-2), EdgeDirection.SouthWest));
		
		ports.add(new Port(types.remove(0), new HexLocation(-2,-1), EdgeDirection.NorthWest));
		
		ports.add(new Port(types.remove(0), new HexLocation(-2,1), EdgeDirection.NorthWest));

	}
	/**
	 * Takes the lists of hexes and chits and adds them to the game map
	 * @pre the game is being initialized
	 * @param theHexes a list of hexes
	 * @param theChits a list of chits
	 * @post all the hexes are added to the map
	 */
	private void setUpMap(List<Hex> theHexes, List<Integer> theChits) {
		hexes = new HashMap<HexLocation, Hex>();
		edges = new HashMap<EdgeLocation, EdgeValue>();
		vertices = new HashMap<VertexLocation, VertexValue>();
		
		for(int i = -2; i <= 2; i++) {
			for(int j = -2; j <= 2; j++) {
				if(((i > 0) && !(j > 0)) || (!(i > 0) && (j > 0))) { //If either i or j (but not both) is greater than 0...
					if((Math.abs(i) + Math.abs(j)) <= radius) { //If the sum of the absolute values is less than or equal to the radius...
						Hex newHex = addToMap(i, j, theHexes, theChits);
						HashMap<EdgeDirection, EdgeValue> newEdges = addEdges(i, j);
						HashMap<VertexDirection, VertexValue> newVertices = addVertices(i, j);
					}
				}
				else
				{
					Hex newHex = addToMap(i, j, theHexes, theChits);
					HashMap<EdgeDirection, EdgeValue> newEdges = addEdges(i, j);
					HashMap<VertexDirection, VertexValue> newVertices = addVertices(i, j);
				}
			}
		}
	}
	
	/**
	 * Adds the specific hex to the map
	 * @pre the setUpMap function must be in a situation where adding a hex is allowed
	 * @param x the x coordinate where the hex will be placed on the grid
	 * @param y the y coordinate where the hex will be placed on the grid 
	 * @param theHexes a list of hexes
	 * @param theChits a list of chits
	 * @post A new hex is placed on the map
	 * @return
	 */
	private Hex addToMap(int x, int y, List<Hex> theHexes, List<Integer> theChits) {
		
		HexLocation coordinates = new HexLocation(x,y);
		
		theHexes.get(0).setLocation(coordinates); //Set the hex's location to the coordinates
		
		if (theHexes.get(0).getType() == HexType.DESERT) {
			robber = coordinates;
		}
		else {
			theHexes.get(0).setChitNumber(theChits.get(0).intValue());
			theChits.remove(0); //Pop off top chit as it is already used
		}
		
		hexes.put(coordinates, theHexes.get(0));
		
		return theHexes.remove(0);
	}
	
	/**
	 * Adds edges to the map of edges
	 * @pre there must be a hex newly placed on the map
	 * @param x coordinate of edge
	 * @param y coordinate of edge
	 * @return a map of edges (which will be added to a hex)
	 * @post edges are added to map
	 */
	private HashMap<EdgeDirection, EdgeValue> addEdges(int x, int y) {
		
		HashMap<EdgeDirection, EdgeValue> newEdges = new HashMap<EdgeDirection, EdgeValue>();
		
		//Create new edges in the six directions
		
		EdgeLocation northwestLocation = new EdgeLocation(x, y, EdgeDirection.NorthWest);
		EdgeValue northwestEdge = new EdgeValue(northwestLocation);
		
		EdgeLocation northLocation = new EdgeLocation(x, y, EdgeDirection.North);
		EdgeValue northEdge = new EdgeValue(northLocation);
		
		EdgeLocation northeastLocation = new EdgeLocation(x, y, EdgeDirection.NorthEast);
		EdgeValue northeastEdge = new EdgeValue(northeastLocation);
		
		EdgeLocation southeastLocation = new EdgeLocation(x, y, EdgeDirection.SouthEast);
		EdgeValue southeastEdge = new EdgeValue(southeastLocation);
		
		EdgeLocation southLocation = new EdgeLocation(x, y, EdgeDirection.South);
		EdgeValue southEdge = new EdgeValue(southLocation);
		
		EdgeLocation southwestLocation = new EdgeLocation(x, y, EdgeDirection.SouthWest);
		EdgeValue southwestEdge = new EdgeValue(southwestLocation);
		
		//Check edges map, add new edges if they don't already exist
		
		if(edges.get(northwestLocation.getNormalizedLocation()) == null) {
			edges.put(northwestLocation.getNormalizedLocation(), northwestEdge);
		}
		
		if(edges.get(northLocation.getNormalizedLocation()) == null) {
			edges.put(northLocation.getNormalizedLocation(), northEdge);
		}
		
		if(edges.get(northeastLocation.getNormalizedLocation()) == null) {
			edges.put(northeastLocation.getNormalizedLocation(), northeastEdge);
		}
		
		if(edges.get(southeastLocation.getNormalizedLocation()) == null) {
			edges.put(southeastLocation.getNormalizedLocation(), southeastEdge);
		}
		
		if(edges.get(southLocation.getNormalizedLocation()) == null) {
			edges.put(southLocation.getNormalizedLocation(), southEdge);
		}
		
		if(edges.get(southwestLocation.getNormalizedLocation()) == null) {
			edges.put(southwestLocation.getNormalizedLocation(), southwestEdge);
		}
		
		//Fill in newEdges with related edges on edges map
		
		newEdges.put(EdgeDirection.NorthWest, edges.get(northwestLocation.getNormalizedLocation()));
		newEdges.put(EdgeDirection.North, edges.get(northLocation.getNormalizedLocation()));
		newEdges.put(EdgeDirection.NorthEast, edges.get(northeastLocation.getNormalizedLocation()));
		newEdges.put(EdgeDirection.SouthEast, edges.get(southeastLocation.getNormalizedLocation()));
		newEdges.put(EdgeDirection.South, edges.get(southLocation.getNormalizedLocation()));
		newEdges.put(EdgeDirection.SouthWest, edges.get(southwestLocation.getNormalizedLocation()));
		
		return newEdges;
	}
	
	//RUDIMENTARY!!
	/**
	 * Adds vertices to the map of vertices
	 * @pre there must be a hex newly placed on the map
	 * @param x coordinate of vertex
	 * @param y coordinate of vertex
	 * @return a map of vertices (which will be added to a hex)
	 * @post vertices are added to map
	 */
	private HashMap<VertexDirection, VertexValue> addVertices(int x, int y) {
		
		HashMap<VertexDirection, VertexValue> newVertices = new HashMap<VertexDirection, VertexValue>();
		
		//Create new vertices in six directions
		
		VertexLocation westLocation = new VertexLocation(x, y, VertexDirection.West);
		VertexValue westVertex = new VertexValue(westLocation);
		
		VertexLocation northwestLocation = new VertexLocation(x, y, VertexDirection.NorthWest);
		VertexValue northwestVertex = new VertexValue(northwestLocation);
		
		VertexLocation northeastLocation = new VertexLocation(x, y, VertexDirection.NorthEast);
		VertexValue northeastVertex = new VertexValue(northeastLocation);
		
		VertexLocation eastLocation = new VertexLocation(x, y, VertexDirection.East);
		VertexValue eastVertex = new VertexValue(eastLocation);
		
		VertexLocation southeastLocation = new VertexLocation(x, y, VertexDirection.SouthEast);
		VertexValue southeastVertex = new VertexValue(southeastLocation);
		
		VertexLocation southwestLocation = new VertexLocation(x, y, VertexDirection.SouthWest);
		VertexValue southwestVertex = new VertexValue(southwestLocation);
		
		//check vertices map, adding new vertices if they don't already exist
		
		if(vertices.get(westLocation.getNormalizedLocation()) == null) {
			vertices.put(westLocation.getNormalizedLocation(), westVertex);
		}
		
		if(vertices.get(northwestLocation.getNormalizedLocation()) == null) {
			vertices.put(northwestLocation.getNormalizedLocation(), northwestVertex);
		}
		
		if(vertices.get(northeastLocation.getNormalizedLocation()) == null) {
			vertices.put(northeastLocation.getNormalizedLocation(), northeastVertex);
		}
		
		if(vertices.get(eastLocation.getNormalizedLocation()) == null) {
			vertices.put(eastLocation.getNormalizedLocation(), eastVertex);
		}
		
		if(vertices.get(southeastLocation.getNormalizedLocation()) == null) {
			vertices.put(southeastLocation.getNormalizedLocation(), southeastVertex);
		}
		
		if(vertices.get(southwestLocation.getNormalizedLocation()) == null) {
			vertices.put(southwestLocation.getNormalizedLocation(), southwestVertex);
		}
		
		//Fill in newVertices with related vertices on vertices map
		newVertices.put(VertexDirection.West, vertices.get(westLocation.getNormalizedLocation()));
		newVertices.put(VertexDirection.NorthWest, vertices.get(northwestLocation.getNormalizedLocation()));
		newVertices.put(VertexDirection.NorthEast, vertices.get(northeastLocation.getNormalizedLocation()));
		newVertices.put(VertexDirection.East, vertices.get(eastLocation.getNormalizedLocation()));
		newVertices.put(VertexDirection.SouthEast, vertices.get(southeastLocation.getNormalizedLocation()));
		newVertices.put(VertexDirection.SouthWest, vertices.get(southwestLocation.getNormalizedLocation()));
		
		return newVertices;
	}
}

