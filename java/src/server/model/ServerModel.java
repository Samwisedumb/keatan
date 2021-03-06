package server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import shared.definitions.CatanColor;
import shared.definitions.EdgeDirection;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.definitions.VertexDirection;
import client.model.City;
import client.model.DevCardList;
import client.model.EdgeLocation;
import client.model.EdgeValue;
import client.model.Hex;
import client.model.HexLocation;
import client.model.MessageLine;
import client.model.MessageList;
import client.model.Municipality;
import client.model.Player;
import client.model.Port;
import client.model.ResourceList;
import client.model.Road;
import client.model.Settlement;
import client.model.Status;
import client.model.TradeOffer;
import client.model.TransferMap;
import client.model.TransferModel;
import client.model.TurnTracker;
import client.model.VertexLocation;
import client.model.VertexValue;

/**
 * A model for a Settlers of Catan&#8482 game on the the server side
 */
public class ServerModel {
	
	//WHAT IS NEXT?
	//WORKING ON VICTORY POINT WINNING STUFF! HOORAY!
	
	private String gameName;
	
	private HashMap<HexLocation, Hex> hexes;

	private HashMap<EdgeLocation, EdgeValue> edges;
	private HashMap<VertexLocation, VertexValue> vertices;
	
	private List<Port> ports;
	private HexLocation robber;
	
	private int radius;
	
	private TransferModel transfer;
	
	public ServerModel(boolean randomHexes, boolean randomChits, boolean randomPorts, String gameName) {
		this.radius = 2;
		this.gameName = gameName;
		
		createMap(randomHexes, randomChits, randomPorts);
		
		transfer = new TransferModel();
		transfer.setDeck(new DevCardList(2, 5, 2, 14, 2));
		transfer.setBank(new ResourceList(19,19,19,19,19));
		transfer.setChat(new MessageList());
		transfer.setLog(new MessageList());
		transfer.setMap(new TransferMap(new ArrayList<Hex>(hexes.values()),
				new ArrayList<VertexValue>(vertices.values()),
				new ArrayList<EdgeValue>(edges.values()), ports, robber));
		transfer.setTurnTracker(new TurnTracker(0, 5, 3));
		transfer.setVersion(0);
		transfer.setWinner(-1);
	}
	
	public TransferModel getTransferModel() {
		return transfer;
	}	
	
	public HashMap<HexLocation, Hex> getHexes() {
		return hexes;
	}
	
	public HashMap<EdgeLocation, EdgeValue> getEdges() {
		return edges;
	}
	
	public HashMap<VertexLocation, VertexValue> getVertices() {
		return vertices;
	}
	
	public HexLocation getRobber() {
		return transfer.getMap().getRobber();
	}
	
	private void createMap(boolean randomHexes, boolean randomChits, boolean randomPorts) {		
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
		/*
		ports.add(new Port(types.remove(0), new HexLocation(0,2), EdgeDirection.North));
		
		ports.add(new Port(types.remove(0), new HexLocation(1,2), EdgeDirection.NorthEast));
		
		ports.add(new Port(types.remove(0), new HexLocation(2,1), EdgeDirection.NorthEast));
		
		ports.add(new Port(types.remove(0), new HexLocation(2,0), EdgeDirection.SouthEast));
		
		ports.add(new Port(types.remove(0), new HexLocation(1,-1), EdgeDirection.South));
		
		ports.add(new Port(types.remove(0), new HexLocation(-1,-2), EdgeDirection.South));
		
		ports.add(new Port(types.remove(0), new HexLocation(-2,-2), EdgeDirection.SouthWest));
		
		ports.add(new Port(types.remove(0), new HexLocation(-2,-1), EdgeDirection.NorthWest));
		
		ports.add(new Port(types.remove(0), new HexLocation(-2,1), EdgeDirection.NorthWest));
		*/
		
		//BLAH
		ports.add(new Port(types.remove(0), new HexLocation(0,3), EdgeDirection.South));
		
		ports.add(new Port(types.remove(0), new HexLocation(2,3), EdgeDirection.SouthWest));
		
		ports.add(new Port(types.remove(0), new HexLocation(3,2), EdgeDirection.SouthWest));
		
		ports.add(new Port(types.remove(0), new HexLocation(3,0), EdgeDirection.NorthWest));
		
		ports.add(new Port(types.remove(0), new HexLocation(1,-2), EdgeDirection.North));
		
		ports.add(new Port(types.remove(0), new HexLocation(-1,-3), EdgeDirection.North));
		
		ports.add(new Port(types.remove(0), new HexLocation(-3,-3), EdgeDirection.NorthEast));
		
		ports.add(new Port(types.remove(0), new HexLocation(-3,-1), EdgeDirection.SouthEast));
		
		ports.add(new Port(types.remove(0), new HexLocation(-2,1), EdgeDirection.SouthEast));

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
						addToMap(i, j, theHexes, theChits);
						addEdges(i, j);
						addVertices(i, j);
					}
				}
				else
				{
					addToMap(i, j, theHexes, theChits);
					addEdges(i, j);
					addVertices(i, j);
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

	/**
	 * Get resources from hexes with chits matching the number roll, or commence discarding and robbery if the player rolls a 7
	 * @pre It is the current player's turn and the game is in the rolling phase
	 * @param numberRoll - The roll of the dice
	 * @param playerIndex - The index of the current player
	 * @post If numberRoll is 7, then the game enters the discarding phase (if at least one player has more than 7 cards),
	 * 		 or the robbing phase (if no player has more than 7 cards). Otherwise, each player gets the resources they are entitled to
	 * 		 and the game enters the playing phase
	 */
	public void reapResources(int numberRoll, int playerIndex) {
		
		transfer.getLog().addLine(new MessageLine(transfer.getPlayers().get(playerIndex).getName() + " rolled a " +
				new Integer(numberRoll).toString(), playerIndex));
		
		if(numberRoll == 7) {
			if(doesAnyoneDiscard()) {
				transfer.getTurnTracker().setStatus(Status.Discarding);
			}
			else {
				transfer.getTurnTracker().setStatus(Status.Robbing);
			}
			
			transfer.incrementVersion();
			return;
		}
		
		List<ResourceList> spoils = new ArrayList<ResourceList>();
		
		ResourceList bankToll = new ResourceList(0,0,0,0,0);
		
		for(int i = 0; i < 4; i++) {
			spoils.add(new ResourceList(0,0,0,0,0));
		}

		List<HexLocation> chitLocs = new ArrayList<HexLocation>();
		
		for(Hex theHex : hexes.values()) {
			if(transfer.getMap().getRobber().equals(theHex.getLocation())) {
				//Do nothing
			}
			else if(theHex.getChitNumber() == numberRoll) {
				chitLocs.add(theHex.getLocation());
			}
		}
		
		List<VertexLocation> chitVertices = new ArrayList<VertexLocation>();
		
		for(HexLocation coordinates : chitLocs) {
			chitVertices.add(new VertexLocation(coordinates.getX(), coordinates.getY(), VertexDirection.West));
			chitVertices.add(new VertexLocation(coordinates.getX(), coordinates.getY(), VertexDirection.NorthWest));
			chitVertices.add(new VertexLocation(coordinates.getX(), coordinates.getY(), VertexDirection.NorthEast));
			chitVertices.add(new VertexLocation(coordinates.getX(), coordinates.getY(), VertexDirection.East));
			chitVertices.add(new VertexLocation(coordinates.getX(), coordinates.getY(), VertexDirection.SouthEast));
			chitVertices.add(new VertexLocation(coordinates.getX(), coordinates.getY(), VertexDirection.SouthWest));
		}
		
		for(VertexLocation vertex : chitVertices) {
			VertexValue spoilVertex = vertices.get(vertex.getNormalizedLocation());
			if(spoilVertex.hasMunicipality() == true) {
				
				ResourceType spoilResource = hexes.get(new HexLocation(vertex.getX(), vertex.getY())).getResourceType();
				
				if(spoilVertex.getCity() == null) {
					spoils.get(spoilVertex.getSettlement().getOwnerIndex()).changeResourceAmount(spoilResource, 1);
					bankToll.changeResourceAmount(spoilResource, 1);
				}
				else {
					spoils.get(spoilVertex.getCity().getOwnerIndex()).changeResourceAmount(spoilResource, 2);
					bankToll.changeResourceAmount(spoilResource, 2);
				}
			}
		}
		
		handOutSpoils(bankToll, spoils);
		
		transfer.getTurnTracker().setStatus(Status.Playing);
		transfer.incrementVersion();
	}

	/**
	 * Cycle through the players and see if any of them have more than 7 cards. If any do, the game will go to the discard phase
	 * @pre A player rolled a seven on their turn
	 * @return false if no player has more than 7 cards, true if otherwise
	 * @post The game either goes to the discarding phase or skips straight to the robbing phase. If it went to the discard phase,
	 * 		 any players with 7 or fewer cards are marked as having already discarded, thus exempting them from the discard
	 */
	private boolean doesAnyoneDiscard() {
		
		boolean someoneDiscards = false;
		
		for(Player thisPlayer : transfer.getPlayers()) {
			if(thisPlayer.getResources().getTotalCards() > 7) {
				someoneDiscards = true;
			}
			else {
				thisPlayer.setDiscarded(true);
			}
		}
		
		return someoneDiscards;
	}
	
	/**
	 * Having calculated how many resources each player is entitled to, see if the bank has enough resources to give each player
	 * those resources.
	 * @pre  During the rolling phase, the resources each player is entitled to have been calculated.
	 * @param bankToll - The total amount of resources the bank has to give out to give each player their resources
	 * @param spoils - A list of ResourceLists indicating the resources each player is entitled to from the die roll
	 * 				  (The index in the list correlates to the index of the player in the game)
	 * @post each player is gets the resources they are entitled, provided the bank has enough
	 */
	private void handOutSpoils(ResourceList bankToll, List<ResourceList> spoils) {
		
		boolean canBrick = transfer.getBank().hasResource(ResourceType.BRICK, bankToll.getBrick());
		boolean canOre = transfer.getBank().hasResource(ResourceType.ORE, bankToll.getOre());
		boolean canSheep = transfer.getBank().hasResource(ResourceType.SHEEP, bankToll.getSheep());
		boolean canWheat = transfer.getBank().hasResource(ResourceType.WHEAT, bankToll.getWheat());
		boolean canWood = transfer.getBank().hasResource(ResourceType.WOOD, bankToll.getWood());
		
		for(int i = 0; i < 4; i++) {
			if(canBrick) {transfer.getPlayers().get(i).addResource(ResourceType.BRICK, spoils.get(i).getBrick());}
			if(canOre) {transfer.getPlayers().get(i).addResource(ResourceType.ORE, spoils.get(i).getOre());}
			if(canSheep) {transfer.getPlayers().get(i).addResource(ResourceType.SHEEP, spoils.get(i).getSheep());}
			if(canWheat) {transfer.getPlayers().get(i).addResource(ResourceType.WHEAT, spoils.get(i).getWheat());}
			if(canWood) {transfer.getPlayers().get(i).addResource(ResourceType.WOOD, spoils.get(i).getWood());}
		}
		
	}
	
	/**
	 * Pay for a road the player has built
	 * @pre The player is building a road that is not free
	 * @param playerIndex - Index of the player building a road
	 * @post The player pays for the road (loses 1 wood and 1 brick, which are given to the bank)
	 */
	public void payForRoad(int playerIndex) {
		transfer.getPlayers().get(playerIndex).removeResource(ResourceType.WOOD, 1);
		transfer.getPlayers().get(playerIndex).removeResource(ResourceType.BRICK, 1);
		transfer.getBank().setWood(transfer.getBank().getWood() + 1);
		transfer.getBank().setBrick(transfer.getBank().getBrick() + 1);
	}
	
	/**
	 * The player puts a road on the game map
	 * @pre It must be the current player's turn, it must be the playing phase, and if the road is not free (e.g. it is the first
	 * 		two rounds or the player has played a roadBuilding development card), the player must have sufficient resources
	 * @param place - Where the player is building the road
	 * @param playerIndex - Index of the current player
	 * @post The player puts the road on the map, paying the resource cost if the road wasn't free
	 */
	public void placeRoad(EdgeLocation place, int playerIndex) {
		transfer.getLog().addLine(new MessageLine(transfer.getPlayers().get(playerIndex).getName() +
				" played a road", playerIndex));
		edges.get(place.getNormalizedLocation()).setRoad(new Road(playerIndex, place));
		transfer.getPlayers().get(playerIndex).playRoad();
		
		if(transfer.getPlayers().get(playerIndex).getPlacedRoads() > transfer.getTurnTracker().getLongestRoadLength()) {
			if(transfer.getLargestRoadOwnerIndex() != playerIndex) {
				transfer.getPlayers().get(transfer.getLargestRoadOwnerIndex()).loseLongestRoad();
			}
			
			transfer.setLargestRoadOwnerIndex(playerIndex);
			transfer.getPlayers().get(playerIndex).getLongestRoad();
			transfer.getTurnTracker().setLongestRoadLength(transfer.getPlayers().get(playerIndex).getPlacedRoads());
			
		}

		transfer.incrementVersion();
	}

	/**
	 * Pay for a settlement the player has built
	 * @pre The player is building a settlement that is not free
	 * @param playerIndex - Index of the player building a settlement
	 * @post The player pays for the settlement (loses 1 brick, lumber, wool and grain, which are given to the bank)
	 */
	public void payForSettlement(int playerIndex) {
		transfer.getPlayers().get(playerIndex).removeResource(ResourceType.WOOD, 1);
		transfer.getPlayers().get(playerIndex).removeResource(ResourceType.BRICK, 1);
		transfer.getPlayers().get(playerIndex).removeResource(ResourceType.SHEEP, 1);
		transfer.getPlayers().get(playerIndex).removeResource(ResourceType.WHEAT, 1);
		transfer.getBank().setWood(transfer.getBank().getWood() + 1);
		transfer.getBank().setBrick(transfer.getBank().getBrick() + 1);
		transfer.getBank().setSheep(transfer.getBank().getSheep() + 1);
		transfer.getBank().setWheat(transfer.getBank().getWheat() + 1);
	}
	
	/**
	 * The player puts a settlement on the game map
	 * @pre It must be the current player's turn, it must be the playing phase, and if it is not the first two rounds, the 
	 * 		player must have sufficient resources (if it is the second, the player also gets resources from the surrounding hexes)
	 * @param place - Where the player is building the settlement
	 * @param playerIndex - Index of the player building a settlement
	 * @post The player puts the settlement on the map, paying the resource cost if the settlement wasn't free
	 */
	public void placeSettlement(VertexLocation place, int playerIndex) {
		transfer.getLog().addLine(new MessageLine(transfer.getPlayers().get(playerIndex).getName() + 
				" played a settlement", playerIndex));
		vertices.get(place.getNormalizedLocation()).setSettlement(new Settlement(playerIndex, place));
		transfer.getPlayers().get(playerIndex).playSettlement();

		transfer.incrementVersion();
		
		if(transfer.getTurnTracker().getStatus() == Status.SecondRound) {
			secondSettlementClaim(place, playerIndex);
		}
	}
	
	/**
	 * Get the resources from the hexes around the settlement placed in the second round
	 * @pre it is the current player's turn during the secound round
	 * @param place - The location of the second settlement
	 * @param playerIndex - The player who placed it
	 * @post the player gets resources from the surrounding hexes
	 * 
	 */
	public void secondSettlementClaim(VertexLocation place, int playerIndex) {
		
		VertexDirection direction = place.getDirection();
		int x = place.getX();
		int y = place.getY();
		
		HexLocation freePlaceOne = new HexLocation(x, y);
		HexLocation freePlaceTwo = null;
		HexLocation freePlaceThree = null;
		
		switch(direction) {
		case NorthWest:
			freePlaceTwo = new HexLocation(x, y + 1);
			freePlaceThree = new HexLocation(x - 1, y);
			break;
		case NorthEast:
			freePlaceTwo = new HexLocation(x + 1, y + 1);
			freePlaceThree = new HexLocation(x, y + 1);
			break;
		case East:
			freePlaceTwo = new HexLocation(x + 1, y);
			freePlaceThree = new HexLocation(x + 1, y + 1);
			break;
		case SouthEast:
			freePlaceTwo = new HexLocation(x, y - 1);
			freePlaceThree = new HexLocation(x + 1, y);
			break;
		case SouthWest:
			freePlaceTwo = new HexLocation(x - 1, y - 1);
			freePlaceThree = new HexLocation(x, y - 1);
			break;
		case West:
			freePlaceTwo = new HexLocation(x - 1, y);
			freePlaceThree = new HexLocation(x - 1, y - 1);
			break;
		default:
			freePlaceTwo = new HexLocation(9, -9);
			freePlaceThree = new HexLocation (-9, 9);
			break;
		}
		
		if(hexes.containsKey(freePlaceOne)) {
			if((hexes.get(freePlaceOne).getType() != HexType.DESERT) && (hexes.get(freePlaceOne).getType() != HexType.WATER)) {
				transfer.getPlayers().get(playerIndex).addResource(hexes.get(freePlaceOne).getResourceType(), 1);
			}
		}
		if(hexes.containsKey(freePlaceTwo)) {
			if((hexes.get(freePlaceTwo).getType() != HexType.DESERT) && (hexes.get(freePlaceTwo).getType() != HexType.WATER)) {
				transfer.getPlayers().get(playerIndex).addResource(hexes.get(freePlaceTwo).getResourceType(), 1);
			}
		}
		if(hexes.containsKey(freePlaceThree)) {
			if((hexes.get(freePlaceThree).getType() != HexType.DESERT) && (hexes.get(freePlaceThree).getType() != HexType.WATER)) {
				transfer.getPlayers().get(playerIndex).addResource(hexes.get(freePlaceThree).getResourceType(), 1);
			}
		}
	}
	
	/**
	 * Pay for a city the player has built
	 * @pre The player is building a city
	 * @param playerIndex - Index of the player building a city
	 * @post The player pays for the city (loses 3 ore and 2 grain, which are given to the bank)
	 */
	public void payForCity(int playerIndex) {
		transfer.getPlayers().get(playerIndex).removeResource(ResourceType.WHEAT, 2);
		transfer.getPlayers().get(playerIndex).removeResource(ResourceType.ORE, 3);
		transfer.getBank().setWheat(transfer.getBank().getWheat() + 2);
		transfer.getBank().setOre(transfer.getBank().getOre() + 3);
	}
	
	/**
	 * The player puts a city on the game map
	 * @pre It must be the current player's turn, it must be the playing phase and the  player must have sufficient resources
	 * @param place - Where the player is building the city
	 * @param playerIndex - Index of the player building a city
	 * @post The player puts the city on the map, getting back the settlement they placed the city over
	 */
	public void placeCity(VertexLocation place, int playerIndex) {
		transfer.getLog().addLine(new MessageLine(transfer.getPlayers().get(playerIndex).getName() +
				"played a city", playerIndex));
		vertices.get(place.getNormalizedLocation()).setCity(new City(playerIndex, place));
		transfer.getPlayers().get(playerIndex).playCity();
	
		transfer.incrementVersion();
	}
	
	/**Pay for a dev card
	 * @pre The player must be buying a devCard
	 * @param cardBuyer - The player buying the card
	 * @post The player loses 1 Ore, 1 Sheep, and 1 Wheat, giving those resources to the banks
	 */
	public void payForDevCard(Player cardBuyer) {
		cardBuyer.removeResource(ResourceType.ORE, 1);
		cardBuyer.removeResource(ResourceType.SHEEP, 1);
		cardBuyer.removeResource(ResourceType.WHEAT, 1);
		transfer.getBank().setOre(transfer.getBank().getOre() + 1);
		transfer.getBank().setSheep(transfer.getBank().getSheep() + 1);
		transfer.getBank().setWheat(transfer.getBank().getWheat() + 1);
	}
	
	/**Buy a dev card
	 * @pre It must be the current player's turn, it must be the playing phase and they must be able to afford a dev card
	 * @param playerIndex - The player who is buying the dev card
	 * @post the player gets a dev card
	 */
	public void buyDevCard(int playerIndex) {
		Player cardBuyer = transfer.getPlayers().get(playerIndex);
		
		int draw = devDeckDraw();
		
		switch(draw) {
		case -1:
			transfer.getLog().addLine(new MessageLine("Can't buy a dev card", playerIndex));
			break; //nothing happens. No dev cards to draw
		case 0:
			payForDevCard(cardBuyer);
			transfer.getDeck().setMonopoly(transfer.getDeck().getMonopoly() - 1);
			cardBuyer.getNewDevCards().setMonopoly(cardBuyer.getNewDevCards().getMonopoly() + 1);
			transfer.getLog().addLine(new MessageLine(transfer.getPlayers().get(playerIndex).getName() +
					"bought a dev card", playerIndex));
			break;
		case 1:
			payForDevCard(cardBuyer);
			transfer.getDeck().setMonument(transfer.getDeck().getMonument() - 1);
			cardBuyer.getNewDevCards().setMonument(cardBuyer.getNewDevCards().getMonument() + 1);
			transfer.getLog().addLine(new MessageLine(transfer.getPlayers().get(playerIndex).getName() +
					"bought a dev card", playerIndex));
			break;
		case 2:
			payForDevCard(cardBuyer);
			transfer.getDeck().setRoadBuilding(transfer.getDeck().getRoadBuilding() - 1);
			cardBuyer.getNewDevCards().setRoadBuilding(cardBuyer.getNewDevCards().getRoadBuilding() + 1);
			transfer.getLog().addLine(new MessageLine(transfer.getPlayers().get(playerIndex).getName() +
					"bought a dev card", playerIndex));
			break;
		case 3:
			payForDevCard(cardBuyer);
			transfer.getDeck().setSoldier(transfer.getDeck().getSoldier() - 1);
			cardBuyer.getNewDevCards().setSoldier(cardBuyer.getNewDevCards().getSoldier() + 1);
			transfer.getLog().addLine(new MessageLine(transfer.getPlayers().get(playerIndex).getName() +
					"bought a dev card", playerIndex));
			break;
		case 4:
			payForDevCard(cardBuyer);
			transfer.getDeck().setYearOfPlenty(transfer.getDeck().getYearOfPlenty() - 1);
			cardBuyer.getNewDevCards().setYearOfPlenty(cardBuyer.getNewDevCards().getYearOfPlenty() + 1);
			transfer.getLog().addLine(new MessageLine(transfer.getPlayers().get(playerIndex).getName() +
					"bought a dev card", playerIndex));
			break;
		default:
			transfer.getLog().addLine(new MessageLine("Can't buy a dev card", playerIndex));
			break;
		}
		
		transfer.incrementVersion();
	}
	
	/**Use random number to determine what dev card type a player gets
	 * @pre the player is buying a dev card
	 * @return a random number that determines what dev card type a player gets
	 * @post The dev card is able to be drawn
	 */
	private int devDeckDraw() {
		 
		//14, 2, 2, 2, 5
		
		int firstWeight = transfer.getDeck().getMonopoly();
		int secondWeight = firstWeight + transfer.getDeck().getMonument();
		int thirdWeight = secondWeight + transfer.getDeck().getRoadBuilding();
		int fourthWeight = thirdWeight + transfer.getDeck().getSoldier();
		int finalWeight = fourthWeight + transfer.getDeck().getYearOfPlenty();
		
		if(finalWeight == 0) {
			return -1; //there are no more devcards to draw
		}
		
		long seed = System.nanoTime();
		Random random = new Random(seed);
		
		int devCardNumber = random.nextInt(finalWeight);
		
		if(devCardNumber < firstWeight) {
			return 0;
		}
		else if(devCardNumber >= firstWeight && devCardNumber < secondWeight) {
			return 1;
		}
		else if(devCardNumber >= secondWeight && devCardNumber < thirdWeight) {
			return 2;
		}
		else if(devCardNumber >= thirdWeight && devCardNumber < fourthWeight) {
			return 3;
		}
		else if(devCardNumber >= fourthWeight && devCardNumber < finalWeight) {
			return 4;
		}
		else {
			return -1;
		}
	}
	
	/**Add a player to the game
	 * @pre number of players currently in game should be less than 4
	 * @post Adds a player to the game
	 */
	public void addPlayer(CatanColor color, String name, int playerID) {
		List<Player> players = getTransferModel().getPlayers();
		players.add(new Player(name, players.size(), color, playerID));
	}
	
	/** The player discards cards
	 * @pre the player must need to discard during the Discarding phase
	 * @param playerIndex - The player discarded
	 * @param discardList - The list of cards that need discarding
	 * @post the player discards the cards, and if all players who have to discard have discarded, go to the robbing phase
	 */
	public void discardCards(int playerIndex, ResourceList discardList) {
		Player discarder = transfer.getPlayers().get(playerIndex);
		discarder.setDiscarded(true);
		
		discarder.removeResource(ResourceType.BRICK, discardList.getBrick());
		discarder.removeResource(ResourceType.WHEAT, discardList.getWheat());
		discarder.removeResource(ResourceType.SHEEP, discardList.getSheep());
		discarder.removeResource(ResourceType.WOOD, discardList.getWood());
		discarder.removeResource(ResourceType.ORE, discardList.getOre());
		
		transfer.getBank().setBrick(transfer.getBank().getBrick() + discardList.getBrick());
		transfer.getBank().setWheat(transfer.getBank().getWheat() + discardList.getWheat());
		transfer.getBank().setSheep(transfer.getBank().getSheep() + discardList.getSheep());
		transfer.getBank().setWood(transfer.getBank().getWood() + discardList.getWood());
		transfer.getBank().setOre(transfer.getBank().getOre() + discardList.getOre());
		
		if(allDiscarded() == true) {
			transfer.getTurnTracker().setStatus(Status.Robbing);
		}
		
		transfer.incrementVersion();
	}
	
	/**See if all players who need to discard cards have discarded cards
	 * @pre A player must have just discarded cards
	 * @return true if all players who need to discard have discarded, false if otherwise
	 * @post the game either waits for more players to discard or goes to the playing phase
	 */
	public boolean allDiscarded() {
		int discarders = 0;
		for(Player player : transfer.getPlayers()) {
			if(player.hasDiscarded() == true) {
				discarders++;
			}
		}
		
		if(discarders == 4) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**Rob a player
	 * @pre it must be the robbing phase
	 * @param playerIndex - the current player
	 * @param victimIndex - the player being robbed
	 * @param robberMove - the new location of the robber
	 * @post the player gets a resource from the victim, the robber is moved, and the game moves to the building phase
	 */
	public void robPlayer(int playerIndex, int victimIndex, HexLocation robberMove) {
		
		if((victimIndex == -1) || (victimIndex == playerIndex)) {
			
		}
		else {
			Player thief = transfer.getPlayers().get(playerIndex);
			Player victim = transfer.getPlayers().get(victimIndex);
		
			ResourceType claim = wheelOfSteal(victim);
		
			switch(claim) {
			case WOOD:
				victim.removeResource(ResourceType.WOOD, 1);
				thief.addResource(ResourceType.WOOD, 1);
				break;
			case WHEAT:
				victim.removeResource(ResourceType.WHEAT, 1);
				thief.addResource(ResourceType.WHEAT, 1);
				break;
			case SHEEP:
				victim.removeResource(ResourceType.SHEEP, 1);
				thief.addResource(ResourceType.SHEEP, 1);
				break;
			case ORE:
				victim.removeResource(ResourceType.ORE, 1);
				thief.addResource(ResourceType.ORE, 1);
				break;
			case BRICK:
				victim.removeResource(ResourceType.BRICK, 1);
				thief.addResource(ResourceType.BRICK, 1);
				break;
			default:
				break;
			}
		}
		
		robber = robberMove;
		transfer.getMap().setRobber(robberMove);
		transfer.getTurnTracker().setStatus(Status.Playing);
		transfer.incrementVersion();
	}
	
	/**
	 * Determines what resource will be stolen
	 * @pre somebody is stealing something
	 * @param victim - the player being stolen from
	 * @return the resource being stolen
	 * @post the thing is stolen
	 */
	public ResourceType wheelOfSteal(Player victim) {
		
		int firstWeight = victim.getResources().getWood();
		int secondWeight = firstWeight + victim.getResources().getWheat();
		int thirdWeight = secondWeight + victim.getResources().getSheep();
		int fourthWeight = thirdWeight + victim.getResources().getOre();
		int finalWeight = fourthWeight + victim.getResources().getBrick();
		
		if(finalWeight == 0) {
			return null; //empty hand
		}
		
		long seed = System.nanoTime();
		Random random = new Random(seed);
		
		int resourceCardNumber = random.nextInt(finalWeight);
		
		if(resourceCardNumber < firstWeight) {
			return ResourceType.WOOD;
		}
		else if(resourceCardNumber >= firstWeight && resourceCardNumber < secondWeight) {
			return ResourceType.WHEAT;
		}
		else if(resourceCardNumber >= secondWeight && resourceCardNumber < thirdWeight) {
			return ResourceType.SHEEP;
		}
		else if(resourceCardNumber >= thirdWeight && resourceCardNumber < fourthWeight) {
			return ResourceType.ORE;
		}
		else if(resourceCardNumber >= fourthWeight && resourceCardNumber < finalWeight) {
			return ResourceType.BRICK;
		}
		else {
			return null;
		}
	}

	/**
	 * Trade at a port
	 * @pre must be the playing phase, must have the necessary resources to get the desired resource
	 * @param playerIndex - the current player
	 * @param ratio - the trade ratio
	 * @param input - the offered resource
	 * @param output - the desired resouce
	 * @post the trade is completed
	 */
	public void maritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output) {
		transfer.getPlayers().get(playerIndex).removeResource(input, ratio);
		transfer.getPlayers().get(playerIndex).addResource(output, 1);
		transfer.getBank().changeResourceAmount(input, ratio);
		transfer.getBank().changeResourceAmount(output, -1);
		transfer.incrementVersion();
		System.out.println(playerIndex + " " + ratio + " " + input + " " + output);
	}
	
	/**Offer to trade with someone
	 * @pre it must be the playing phase for the current player's turn and
	 *      each player must have the resources for the offer to be completed
	 * @param sender - the person offering the trade
	 * @param receiver - the person to whom the trade is being offered
	 * @param offer - the offer
	 * @post the offer is established 
	 */
	public void offerTrade(int sender, int receiver, ResourceList offer) {
		transfer.setTradeOffer(new TradeOffer(sender, receiver, offer));
		transfer.incrementVersion();
	}
	
	/**Accept or refuse the trade, finishing the exchange of resources or doing nothing
	 * @pre the player must have been offered a trade 
	 * @param offerer - the index of the person offering the trade
	 * @param willAccept - if the person being offered the trade accepts
	 * @post If the trade is accepted, players exchange resources. If not, nothing happens.
	 * 		 In both cases the tradeOffer is reset to null
	 */
	public void acceptTrade(int offerer, boolean willAccept) {
		if(willAccept == false) {
			
		}
		else {
			Player sender = transfer.getPlayers().get(transfer.getTradeOffer().getSender());
			Player receiver = transfer.getPlayers().get(transfer.getTradeOffer().getReceiver());
		
			ResourceList trade = transfer.getTradeOffer().getOffer();
		
			sender.addResource(ResourceType.BRICK, trade.getBrick());
			receiver.addResource(ResourceType.BRICK, trade.getBrick() * -1);
		
			sender.addResource(ResourceType.ORE, trade.getOre());
			receiver.addResource(ResourceType.ORE, trade.getOre() * -1);
		
			sender.addResource(ResourceType.SHEEP, trade.getSheep());
			receiver.addResource(ResourceType.SHEEP, trade.getSheep() * -1);
		
			sender.addResource(ResourceType.WHEAT, trade.getWheat());
			receiver.addResource(ResourceType.WHEAT, trade.getWheat() * -1);
		
			sender.addResource(ResourceType.WOOD, trade.getWood());
			receiver.addResource(ResourceType.WOOD, trade.getWood() * -1);
		}
		
		transfer.setTradeOffer(null);
		transfer.incrementVersion();
	}
	
	/** Play a Road Building Dev Card
	 * @pre It must be current player's playing phase and they must have a road building dev card and they must not have played
	 * 		a dev card before on their turn
	 * @param playerIndex - Index of the current
	 * @param placeOne - the place of the first road
	 * @param placeTwo - the place of the second road
	 * @post the player gets two roads and loses the dev card, and can't play another dev card this turn
	 */
	public void roadBuilding(int playerIndex, EdgeLocation placeOne, EdgeLocation placeTwo) {
		transfer.getPlayers().get(playerIndex).useRoadBuildingCard();
		placeRoad(placeOne, playerIndex);
		placeRoad(placeTwo, playerIndex);
	}
	
	/** Play a soldier card
	 * @ It must be current player's playing phase and they must have a soldier dev card and they must not have played
	 * 		a dev card before on their turn
	 * @param playerIndex - the current player
	 * @param victimIndex - the player being robbed
	 * @param robberMoves - the new location for the robber
	 * @post the player gets a soldier card (winning largest army if they have the largest army) and can't play
	 * 		 another dev card this turn
	 */
	public void soldier(int playerIndex, int victimIndex, HexLocation robberMove) {
		robPlayer(playerIndex, victimIndex, robberMove);
		
		transfer.getPlayers().get(playerIndex).useSoldierCard();
		
		if(transfer.getPlayers().get(playerIndex).getNumSoldiers() > transfer.getTurnTracker().getLargestArmySize()) {
			if(transfer.getLargestArmyOwnerIndex() != playerIndex) {
				transfer.getPlayers().get(transfer.getLargestArmyOwnerIndex()).loseLargestArmy();
			}
			
			transfer.setLargestArmyOwnerIndex(playerIndex);
			transfer.getPlayers().get(playerIndex).getLargestArmy();
			transfer.getTurnTracker().setLargestArmySize(transfer.getPlayers().get(playerIndex).getNumSoldiers());
		}
		
		transfer.incrementVersion();
	}
	
	/**Play monument cards to get enough victory points to win
	 * @pre The player must have enough monument cards to win and it must be their turn
	 * @param playerIndex - The current player
	 * @post the player plays all of their monument cards and gets enough points to win the game
	 */
	public void monument(int playerIndex) {
		transfer.getPlayers().get(playerIndex).useMonumentCards();
		transfer.incrementVersion();
	}
	
	/**Play a year of plenty card
	 * @pre it must be the current player's turn and they must not have a played a dev card before
	 * @param playerIndex - the current player
	 * @param resourceOne - the first desired resource
	 * @param resourceTwo - the second desired resource
	 * @post the player plays the dev card and gets two resources (they cannot play another dev card this turn)
	 */
	public void yearOfPlenty(int playerIndex, ResourceType resourceOne, ResourceType resourceTwo) {
		Player luckyPerson = transfer.getPlayers().get(playerIndex);
		luckyPerson.addResource(resourceOne, 1);
		luckyPerson.addResource(resourceTwo, 1);
		transfer.getBank().changeResourceAmount(resourceOne, -1);
		transfer.getBank().changeResourceAmount(resourceTwo, -1);
		luckyPerson.useYearOfPlentyCard();
		transfer.incrementVersion();
	}
	
	/**Play a monopoly card
	 * @pre it must be the current player's turn and they must not have played a dev card this turn and they must have a monopoly card
	 * @param playerIndex - the current player
	 * @param mine - the resource the player wants to take from everyone
	 * @post the player plays the monopoly card and gets all of the resources of the specified type
	 */
	public void monopoly(int playerIndex, ResourceType mine) {
		List<Player> players = transfer.getPlayers();
		
		int winnings = 0;
		
		for(int i = 0; i < players.size(); i++) {
			if(i == playerIndex) {}
			else {
				winnings += players.get(i).getResourceAmount(mine);
				players.get(i).removeResource(mine, players.get(i).getResourceAmount(mine));
			}
		}
		
		players.get(playerIndex).addResource(mine, winnings);
		players.get(playerIndex).useMonopolyCard();
		transfer.incrementVersion();
	}
	
	/**Send a chat to the chat window
	 * @pre the player must be in the game
	 * @param playerIndex - the player sending the message
	 * @param message - the message
	 * @post the message is uploaded to the message log
	 */
	public void sendChat(int playerIndex, String message) {
		MessageLine chatMessage = new MessageLine(message, playerIndex);
		transfer.getChat().addLine(chatMessage);
		transfer.incrementVersion();
	}
	
	/**End the turn, and see if the current player is winning
	 * @pre the player must want to end their turn
	 * @param playerIndex - the current player
	 * @post the turn ends, passing to the next player. If the current player wins the game is over
	 */
	public void endTurn(int playerIndex) {
		transfer.getPlayers().get(playerIndex).endTurn();
		for(Player player : transfer.getPlayers()) {
			if((player.getVictoryPoints()) >= 10 && (player.getIndex() == playerIndex)) {
				transfer.setWinner(playerIndex);
			}
		}
		transfer.getTurnTracker().endPlayerTurn();

		transfer.incrementVersion();
	}

	/**Gets the two vertices touching the selected edge
	 * @pre there is an edge and vertices that exists
	 * @param checkEdge - the edge to find the vertices
	 * @return the two vertices
	 * @post 
	 */
	public List<VertexLocation> getNearbyVertices(EdgeLocation checkEdge) {
		
		int x = checkEdge.getX();
		int y = checkEdge.getY();
		
		EdgeDirection face = checkEdge.getDirection();
		
		VertexDirection pointOne = null;
		VertexDirection pointTwo = null;
		
		switch(face) {
		case NorthWest:
			pointOne = VertexDirection.West;
			pointTwo = VertexDirection.NorthWest;
			break;
		case North:
			pointOne = VertexDirection.NorthWest;
			pointTwo = VertexDirection.NorthEast;
			break;
		case NorthEast:
			pointOne = VertexDirection.NorthEast;
			pointTwo = VertexDirection.East;
			break;
		case SouthEast:
			pointOne = VertexDirection.East;
			pointTwo = VertexDirection.SouthEast;
			break;
		case South:
			pointOne = VertexDirection.SouthEast;
			pointTwo = VertexDirection.SouthWest;
			break;
		case SouthWest:
			pointOne = VertexDirection.SouthWest;
			pointTwo = VertexDirection.West;
			break;
		}
		
		List<VertexLocation> nearbyVertices = new ArrayList<VertexLocation>();
		
		VertexLocation locationOne = new VertexLocation(x, y, pointOne);
		VertexLocation locationTwo = new VertexLocation(x, y, pointTwo);
		
		nearbyVertices.add(locationOne);
		nearbyVertices.add(locationTwo);
		
		return nearbyVertices;
	}

	/**Get three edges touching the vertex
	 * @pre the vertex and edges exist
	 * @param checkVertex the vertex to check
	 * @return the edges touching the vertex
	 * @post the three edges
	 */
	public List<EdgeLocation> getNearbyEdges(VertexLocation checkVertex) {
		int x = checkVertex.getX();
		int y = checkVertex.getY();
		
		VertexDirection point = checkVertex.getDirection();
		
		EdgeDirection faceOne = null;
		EdgeDirection faceTwo = null;
		
		int newX = 0;
		int newY = 0;
		
		EdgeDirection farFace = null;
		
		switch(point) {
		case NorthWest:
			faceOne = EdgeDirection.NorthWest;
			faceTwo = EdgeDirection.North;
			//FOR THE DISTANCE!
			newX = x - 1;
			newY = y;
			farFace = EdgeDirection.NorthEast;
			break;
		case NorthEast:
			faceOne = EdgeDirection.North;
			faceTwo = EdgeDirection.NorthEast;
			//FOR THE DISTANCE!
			newX = x;
			newY = y + 1;
			farFace = EdgeDirection.SouthEast;
			break;
		case East:
			faceOne = EdgeDirection.NorthEast;
			faceTwo = EdgeDirection.SouthEast;
			//FOR THE DISTANCE!
			newX = x + 1;
			newY = y;
			farFace = EdgeDirection.North;
			break;
		case SouthEast:
			faceOne = EdgeDirection.SouthEast;
			faceTwo = EdgeDirection.South;
			//FOR THE DISTANCE!
			newX = x + 1;
			newY = y;
			farFace = EdgeDirection.SouthWest;
			break;
		case SouthWest:
			faceOne = EdgeDirection.South;
			faceTwo = EdgeDirection.SouthWest;
			//FOR THE DISTANCE!
			newX = x;
			newY = y - 1;
			farFace = EdgeDirection.NorthWest;
			break;
		case West:
			faceOne = EdgeDirection.SouthWest;
			faceTwo = EdgeDirection.NorthWest;
			//FOR THE DISTANCE!
			newX = x - 1;
			newY = y;
			farFace = EdgeDirection.South;
			break;
		}
		
		List<EdgeLocation> nearbyEdges = new ArrayList<EdgeLocation>();
		
		EdgeLocation locationOne = new EdgeLocation(x, y, faceOne);
		EdgeLocation locationTwo = new EdgeLocation(x, y, faceTwo);
		
		EdgeLocation farLocation = new EdgeLocation(newX, newY, farFace);
		
		nearbyEdges.add(locationOne);
		nearbyEdges.add(locationTwo);
		nearbyEdges.add(farLocation);
		
		
		return nearbyEdges;
	}

	public List<EdgeLocation> getAdjacentEdges(EdgeLocation checkEdge) {
		
		List<EdgeLocation> adjacentEdges = new ArrayList<EdgeLocation>();
		
		int x = checkEdge.getX();
		int y = checkEdge.getY();
		
		EdgeDirection face = checkEdge.getDirection();
		
		EdgeLocation adjacentLocationOne;
		EdgeLocation adjacentLocationTwo;
		
		switch (face) {
		case NorthWest:
			adjacentLocationOne = new EdgeLocation(x, y, EdgeDirection.SouthWest);
			adjacentLocationTwo = new EdgeLocation(x, y, EdgeDirection.North);
			break;
		case North:
			adjacentLocationOne = new EdgeLocation(x, y, EdgeDirection.NorthWest);
			adjacentLocationTwo = new EdgeLocation(x, y, EdgeDirection.NorthEast);
			break;
		case NorthEast:
			adjacentLocationOne = new EdgeLocation(x, y, EdgeDirection.North);
			adjacentLocationTwo = new EdgeLocation(x, y, EdgeDirection.SouthEast);
			break;
		case SouthEast:
			adjacentLocationOne = new EdgeLocation(x, y, EdgeDirection.NorthEast);
			adjacentLocationTwo = new EdgeLocation(x, y, EdgeDirection.South);
			break;
		case South:
			adjacentLocationOne = new EdgeLocation(x, y, EdgeDirection.SouthEast);
			adjacentLocationTwo = new EdgeLocation(x, y, EdgeDirection.SouthWest);
			break;
		case SouthWest:
			adjacentLocationOne = new EdgeLocation(x, y, EdgeDirection.South);
			adjacentLocationTwo = new EdgeLocation(x, y, EdgeDirection.NorthWest);
			break;
		default:
			adjacentLocationOne = null;
			adjacentLocationTwo = null;
			break;
		}
		
		adjacentEdges.add(adjacentLocationOne);
		adjacentEdges.add(adjacentLocationTwo);
		
		List<EdgeLocation> cousinEdges = getCousinEdges(checkEdge);
		
		if(cousinEdges != null) {
			adjacentEdges.addAll(cousinEdges);
		}
		
		return adjacentEdges;
	}
	
	private List<EdgeLocation> getCousinEdges(EdgeLocation checkEdge) {
		
		int x = checkEdge.getX();
		int y = checkEdge.getY();
		EdgeDirection face = checkEdge.getDirection();
		
		boolean NorthRim; //Whether the hex is part of the northern edge
		boolean SouthRim; //Whether the hex is part of the southern edge
		
		boolean EastRim; //Whether the hex is part of the eastern edge
		boolean WestRim; //Whether the hex is part of the western edge
		
		//RUDIMENTARY MY DEAR WATSON!
		
		if((y == 2) ||
		  ((x == -2) && (y == 0)) ||
		  ((x == -1 &&  y == 1))) {
			NorthRim = true;
		}
		else {
			NorthRim = false;
		}
		
		if((y == -2) ||
		  ((x == 2) && (y == 0)) ||
		  ((x  == 1 && y == -1))) {
			SouthRim = true;
		}
		else {
			SouthRim = false;
		}
		
		if(
		  ((x == 2) && (y == 2))  ||
		  ((x == 1) && (y == 1))  ||
		  ((x == 2) && (y == 0))  ||
		  ((x == 1) && (y == -1)) ||
		  ((x == 0) && (y == -2))) {
			
			EastRim = true;
		}
		else {
			EastRim = false;
		}
		
		if(
		  ((x == -2) && (y == 0))  ||
	      ((x == -2) && (y == -1)) ||
		  ((x == -2) && (y == 0))  ||
		  ((x == -1) && (y == 1))  ||
		  ((x == 0) && (y == 2)))   {
					
		WestRim = true;
		}
		else {
			WestRim = false;
		}
		
		
		HexLocation location = null;
		EdgeDirection directionOne = null;
		EdgeDirection directionTwo = null;
		
		switch(face) {
		case NorthWest:
			if(WestRim == false) {
				location = new HexLocation(x-1, y);
				directionOne = EdgeDirection.NorthEast;
				directionTwo = EdgeDirection.South;
			}
			else {
				if(NorthRim == true) {
					location = new HexLocation(x-1, y-1);
					directionOne = EdgeDirection.North;
				}
				else {
					location = new HexLocation(x, y+1);
					directionOne = EdgeDirection.SouthWest;
				}	
			}
			break;
		case North:
			if(NorthRim == false) {
				location = new HexLocation(x, y+1);
				directionOne = EdgeDirection.SouthEast;
				directionTwo = EdgeDirection.SouthWest;
			}
			else {
				if(WestRim == true) {
					location = new HexLocation(x+1,y+1);
					directionOne = EdgeDirection.NorthWest;
				}
				else {
					location = new HexLocation(x-1, y);
					directionOne = EdgeDirection.NorthEast;
				}
			}
			break;
		case NorthEast:
			if(
			((NorthRim == false) && (EastRim == false)) ||
			((NorthRim == true) && (WestRim == true)) ||		
			((EastRim == true) && (SouthRim == true))
			 ) {
				location = new HexLocation(x+1,y+1);
				directionOne = EdgeDirection.NorthWest;
				directionTwo = EdgeDirection.South;
			}
			else if (NorthRim == true) {
				location = new HexLocation(x+1,y);
				directionOne = EdgeDirection.North;
			}
			else if (EastRim == true) {
				location = new HexLocation(x, y+1);
				directionOne = EdgeDirection.SouthEast;
			}
			break;
		case SouthEast:
			if(EastRim == false) {
				location = new HexLocation(x+1, y);
				directionOne = EdgeDirection.North;
				directionTwo = EdgeDirection.SouthWest;
			}
			else {
				if(SouthRim == true) {
					location = new HexLocation(x+1, y+1);
					directionOne = EdgeDirection.South;
				}
				else {
					location = new HexLocation(x, y-1);
					directionOne = EdgeDirection.NorthEast;
				}
			}
			break;
		case South:
			if(SouthRim == false) {
				location = new HexLocation(x, y-1);
				directionOne = EdgeDirection.NorthWest;
				directionTwo = EdgeDirection.SouthWest;
			}
			else {
				if(EastRim == true) {
					location = new HexLocation(x-1, y-1);
					directionOne = EdgeDirection.SouthEast;
				}
				else {
					location = new HexLocation(x+1, y);
					directionOne = EdgeDirection.SouthWest;
				}
			}
			break;
		case SouthWest:
			if(
			((SouthRim == false) && (WestRim == false)) ||
			((SouthRim == true) && (EastRim == true)) ||		
			((WestRim == true) && (NorthRim == true))
			 ) {
				location = new HexLocation(x-1,y-1);
				directionOne = EdgeDirection.North;
				directionTwo = EdgeDirection.SouthEast;
			}
			else if (SouthRim == true) {
				location = new HexLocation(x-1,y);
				directionOne = EdgeDirection.South;
			}
			else if (WestRim == true) {
				location = new HexLocation(x, y-1);
				directionOne = EdgeDirection.NorthEast;
			}
			break;
		default:
			break;
		}
		
		List<EdgeLocation> cousinEdges = new ArrayList<EdgeLocation>();
		
		int newX = location.getX();
		int newY = location.getY();
		
		cousinEdges.add(new EdgeLocation(newX, newY, directionOne));
		cousinEdges.add(new EdgeLocation(newX, newY, directionTwo));
		
		return cousinEdges;
	}

	public List<VertexLocation> getAdjacentVertices(VertexLocation checkVertex) {
		
		List<VertexLocation> adjacentVertices = new ArrayList<VertexLocation>();
		
		HexLocation coordinates = checkVertex.getHexLocation();
		
		int x = coordinates.getX();
		int y = coordinates.getY();
		
		VertexDirection point = checkVertex.getDirection();
		
		VertexLocation adjacentLocationOne;
		VertexLocation adjacentLocationTwo;
		
		switch(point) {
			case NorthWest:
				adjacentLocationOne = new VertexLocation(x, y, VertexDirection.West);
				adjacentLocationTwo = new VertexLocation(x, y, VertexDirection.NorthEast);
				break;
			case NorthEast:
				adjacentLocationOne = new VertexLocation(x, y, VertexDirection.NorthWest);
				adjacentLocationTwo = new VertexLocation(x, y, VertexDirection.East);
				break;
			case East:
				adjacentLocationOne = new VertexLocation(x, y, VertexDirection.NorthEast);
				adjacentLocationTwo = new VertexLocation(x, y, VertexDirection.SouthEast);
				break;
			case SouthEast:
				adjacentLocationOne = new VertexLocation(x, y, VertexDirection.East);
				adjacentLocationTwo = new VertexLocation(x, y, VertexDirection.SouthWest);
				break;	
			case SouthWest:
				adjacentLocationOne = new VertexLocation(x, y, VertexDirection.SouthEast);
				adjacentLocationTwo = new VertexLocation(x, y, VertexDirection.West);
				break;
			case West:
				adjacentLocationOne = new VertexLocation(x, y, VertexDirection.SouthWest);
				adjacentLocationTwo = new VertexLocation(x, y, VertexDirection.NorthWest);
				break;
				
			default:
				adjacentLocationOne = null;
				adjacentLocationTwo = null;
				break;
		}
		
        VertexLocation distantCousin = getDistantCousin(checkVertex);
        
        if(distantCousin != null) {
            adjacentVertices.add(distantCousin);
        }
        
		adjacentVertices.add(adjacentLocationOne);
		adjacentVertices.add(adjacentLocationTwo);
		
		return adjacentVertices;
	}
	
	private VertexLocation getDistantCousin(VertexLocation checkVertex) {
		
		int x = checkVertex.getX();
		int y = checkVertex.getY();
		
		VertexDirection point = checkVertex.getDirection();
		
		boolean NorthEdge; //Whether the hex is not part of the northern edge
		boolean SouthEdge; //Whether the hex is not part of the southern edge
		
		//RUDIMENTARY MY DEAR WATSON!
		
		if((y == 2) ||
				  ((x == -2) && (y == 0)) ||
				  ((x == -1 &&  y == 1))) {
					NorthEdge = true;
				}
				else {
					NorthEdge = false;
				}
				
				if((y == -2) ||
				  ((x == 2) && (y == 0)) ||
				  ((x == 1 && y == -1))) {
					SouthEdge = true;
				}
				else {
					SouthEdge = false;
				}
		
		VertexDirection cousinDirection = null;
		
		int newX = 0;
		int newY = 0;
		
		switch(point) {
		
			case NorthWest:
				if(NorthEdge == false) {
					newX = x;
					newY = y + 1;
					cousinDirection = VertexDirection.West;
				}
				else {
					newX = x - 1;
					newY = y;
					cousinDirection = VertexDirection.NorthEast;					
				}
				break;
		
			case NorthEast:
				if(NorthEdge == false) {
					newX = x;
					newY = y + 1;
					cousinDirection = VertexDirection.East;
				}
				else {
					newX = x + 1;
					newY = y + 1;
					cousinDirection = VertexDirection.NorthWest;
				}
				break;
				
			case East:
				if(SouthEdge == false) {
					newX = x + 1;
					newY = y;
					cousinDirection = VertexDirection.NorthEast;
				}
				else {
					newX = x + 1;
					newY = y + 1;
					cousinDirection = VertexDirection.SouthEast;
				}
				break;

			case SouthEast:
				if(NorthEdge == false) {
					newX = x + 1;
					newY = y;
					cousinDirection = VertexDirection.SouthWest;
				}
				else {
					newX = x;
					newY = y - 1;
					cousinDirection = VertexDirection.East;
				}
				break;
				
			case SouthWest:
				if(SouthEdge == false) {
					newX = x;
					newY = y - 1;
					cousinDirection = VertexDirection.West;
				}
				else {
					newX = x - 1;
					newY = y - 1;
					cousinDirection = VertexDirection.SouthEast;
				}
				break;

			case West:
				if(NorthEdge == false) {
					newX = x - 1;
					newY = y;
					cousinDirection = VertexDirection.SouthWest;
				}
				else {
					newX = x - 1;
					newY = y - 1;
					cousinDirection = VertexDirection.NorthWest;
				}
				break;
		}
		

		
		VertexLocation cousinLocation = null;

		switch(point) {
		
			case NorthWest:
				if(((x == -2 && y == 0)) || ((x == 0) && (y == 2)) || ((x == -1) && (y == 1))) {
					cousinLocation = null;
				}
				else {
					cousinLocation = new VertexLocation(newX, newY, cousinDirection);
				}
				break;
				
			case NorthEast:
				if(((x == 0 && y == 2)) || ((x == 1) && (y == 2)) || ((x == 2) && (y == 2))) {
					cousinLocation = null;
				}
				else {
					cousinLocation = new VertexLocation(newX, newY, cousinDirection);
				}
				break;

			case East:
				if(((x == 2 && y == 2)) || ((x == 2) && (y == 1)) || ((x == 2) && (y == 0))) {
					cousinLocation = null;
				}
				else {
					cousinLocation = new VertexLocation(newX, newY, cousinDirection);
				}
				break;
				
			case SouthEast:
				if(((x == 2 && y == 0)) || ((x == 1) && (y == -1)) || ((x == 0) && (y == -2))) {
					cousinLocation = null;
				}
				else {
					cousinLocation = new VertexLocation(newX, newY, cousinDirection);
				}
				break;
				
			case SouthWest:
				if(((x == 0 && y == -2)) || ((x == -1) && (y == -2)) || ((x == -2) && (y == -2))) {
					cousinLocation = null;
				}
				else {
					cousinLocation = new VertexLocation(newX, newY, cousinDirection);
				}
				break;
				
			case West:
				if(((x == -2 && y == -2)) || ((x == -2) && (y == -1)) || ((x == -2) && (y == 0))) {
					cousinLocation = null;
				}
				else {
					cousinLocation = new VertexLocation(newX, newY, cousinDirection);
				}
				break;
		}

		
		return cousinLocation;
	}
	
	/**
	 * This works for normalized an non-normalized locations
	 * @param vertex - the vertex location to check
	 * @return true if the vertex location has a city or settlement at it<br>
	 * false if otherwise
	 * @author djoshuac
	 */
	public boolean hasMunicipality(VertexLocation vertex) {	
		return hasCity(vertex) || hasSettlement(vertex);
	}
	
	/**
	 * This works for normalized an non-normalized locations
	 * @param vertex - the vertex location to check
	 * @return true if the vertex location has a city at it<br>
	 * false if otherwise
	 * @author djoshuac
	 */
	public boolean hasCity(VertexLocation vertex) {	
		return isWithinBounds(vertex) && getVertex(vertex).getCity() != null;
	}
	
	/**
	 * This works for normalized an non-normalized locations
	 * @param vertex - the vertex location to check
	 * @return true if the vertex location has a settlement at it<br>
	 * false if otherwise
	 * @author djoshuac
	 */
	public boolean hasSettlement(VertexLocation vertex) {
		return isWithinBounds(vertex) && getVertex(vertex).getSettlement() != null;
	}
	
	/**
	 * This works for normalized an non-normalized locations
	 * @param edge - the edge location to check
	 * @return true if the edge location has a road<br>
	 * false if otherwise
	 * @author djoshuac
	 */
	public boolean hasRoad(EdgeLocation edge) {
		return isWithinBounds(edge) && getEdge(edge).hasRoad();
	}

	/**
	 * See if a vertex location is too close to another municipality for another municipality to be placed there
	 * @param vertex - the location to check
	 * @return true if the location is adjacent to a municipality
	 * false if otherwise
	 * @author djoshuac
	 */
	public boolean isTooCloseToAnotherMunicipality(VertexLocation vertex) {
		for (VertexLocation adj : getAdjacentVertices(vertex)) {
			if (hasMunicipality(adj)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets an edge from a location
	 * @param location - the location to get
	 * @return the corresponding edge value<br>
	 * null if location is out of bounds
	 */
	public EdgeValue getEdge(EdgeLocation location) {
		return edges.get(location.getNormalizedLocation());
	}

	/**
	 * Gets a vertex from a location
	 * @param location - the location to get
	 * @return the corresponding vertex value<br>
	 * null if location is out of bounds
	 */
	public VertexValue getVertex(VertexLocation location) {
		return vertices.get(location.getNormalizedLocation());
	}
	
	/**
	 * @return true is the location is within bounds<br>
	 * false if without
	 * @author djoshuac
	 */
	public boolean isWithinBounds(EdgeLocation edge) {
		return edges.get(edge.getNormalizedLocation()) != null;
	}
	
	/**
	 * @return true is the location is within bounds<br>
	 * false if without
	 * @author djoshuac
	 */
	public boolean isWithinBounds(VertexLocation vertex) {
		return vertices.get(vertex.getNormalizedLocation()) != null;
	}
	
	/**
	 * @return the player with the given index<br>
	 * null if no player corresponds to index
	 * @param index - the index of the player to get
	 * @author djoshuac
	 */
	public Player getPlayer(int index) {
		if (index < 0 || index > 3) {
			return null;
		}
		return transfer.getPlayers().get(index);
	}
}

