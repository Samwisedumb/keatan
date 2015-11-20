package client.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import shared.definitions.CatanColor;
import shared.definitions.EdgeDirection;
import shared.definitions.ResourceType;
import shared.definitions.VertexDirection;
import client.base.Observer;
import client.communication.LogEntry;
import client.data.GameInfo;
import client.data.PlayerInfo;



public class ModelFacade {
	private static final ClientModel model = new ClientModel();
	private static final List<Observer> observers = new ArrayList<Observer>();
	
	/**
	 * Updates the model if the given model's version is newer.
	 * @pre none
	 * @post If the ModelFacade's model is not yet initialized, or the given model has a higher version,
	 * the model is updated to the given model.
	 * @param model - the model to check for an update
	 */
	public static void updateModel(TransferModel lump) {
		if (getModelVersion() < lump.getVersion()) {
			model.update(lump);
			notifyObserversOfChange();
		}
	}
	
	public static void forceUpdateModel(TransferModel lump) {
		model.update(lump);
		notifyObserversOfChange();
	}
	
	public static List<Hex> getHexes() {
		return model.getDataLump().getMap().getHexes();
	}
	
	public static List<Road> getRoads() {
		//TODO
		return null; //model.getDataLump().getMap().getRoads();
	}
	
	public static List<VertexObject> getSettlements() {
		//TODO
		return null; //model.getDataLump().getMap().getSettlements();
	}
	
	public static List<VertexObject> getCities() {
		//TODO
		return null;//model.getDataLump().getMap().getCities();
	}
	
	public static List<Port> getPorts() {
		//TODO
		return null;//model.getDataLump().getMap().getPorts();
	}
	
	public static Player getThisPlayer() {
		return model.getDataLump().getPlayers().get(model.getPlayerInfo().getIndex());
	}
	
	public static Player getAPlayer(int index) {
		return model.getDataLump().getPlayers().get(index);
	}
	
	public static int getWinner() {
		return model.getDataLump().getWinner();
	}
	
	/**
	* @pre ModelFacade must be initialized and must have a current valid model
	* @param playerIndex - the index of the player in question
	* @param location - location of hex
	* @return true if the given player owns a settlement or city adjacent to that location, false if otherwise
	* @post see return
	*/
	public static boolean canProduceResource(int playerIndex, HexLocation location) {
		Hex thisHex = null;
		Iterator<Entry<HexLocation, Hex>> hexes = model.getHexes().entrySet().iterator();
		while (hexes.hasNext()) {
			Entry<HexLocation, Hex> hex = hexes.next();
			if(hex.getKey().equals(location)){
				thisHex = hex.getValue();
			}
		}
		
		int x = thisHex.getLocation().getX();
		int y = thisHex.getLocation().getY();
	
		VertexLocation west = new VertexLocation(x, y, VertexDirection.West);
		VertexLocation northwest = new VertexLocation(x, y, VertexDirection.NorthWest);
		VertexLocation northeast = new VertexLocation(x, y, VertexDirection.NorthEast);
		VertexLocation east = new VertexLocation(x, y, VertexDirection.East);
		VertexLocation southeast = new VertexLocation(x, y, VertexDirection.SouthEast);
		VertexLocation southwest = new VertexLocation(x, y, VertexDirection.SouthWest);
		
		List<VertexObject> settlements = new ArrayList<VertexObject>();
		
		settlements.add(model.getSettlements().get(west.getNormalizedLocation()));
		settlements.add(model.getSettlements().get(northwest.getNormalizedLocation()));
		settlements.add(model.getSettlements().get(northeast.getNormalizedLocation()));
		settlements.add(model.getSettlements().get(east.getNormalizedLocation()));
		settlements.add(model.getSettlements().get(southeast.getNormalizedLocation()));
		settlements.add(model.getSettlements().get(southwest.getNormalizedLocation()));
		
		List<VertexObject> cities = new ArrayList<VertexObject>();
		
		cities.add(model.getCities().get(west.getNormalizedLocation()));
		cities.add(model.getCities().get(northwest.getNormalizedLocation()));
		cities.add(model.getCities().get(northeast.getNormalizedLocation()));
		cities.add(model.getCities().get(east.getNormalizedLocation()));
		cities.add(model.getCities().get(southeast.getNormalizedLocation()));
		cities.add(model.getCities().get(southwest.getNormalizedLocation()));
		
		for(VertexObject settlement : settlements) {
			if(settlement != null) {
				if(settlement.getOwner() == playerIndex) {
					return true; //HOORAY!
				}
			}
		}
		
		for(VertexObject city : cities) {
			if(city != null) {
				if(city.getOwner() == playerIndex) {
					return true; //HOORAY!
				}
			}
		}
		
		return false; //Awwww... found no settlements or cities for you... how sad....
	}
	
	public static boolean canReceiveResource(int resource_amount, ResourceType resource_type) {
		return model.getDataLump().getBank().hasResource(resource_type, resource_amount);
	}
	
	/**
	 * Returns the transfer model's version, -1 if current transfer model is null
	 * @returns -1 if no model version yet, otherwise it returns the models version
	 */
	public static int getModelVersion() {
		if (model.getDataLump() == null) {
			return -1;
		}
		else if (model.getGameInfo().getPlayers().size() != 4) {
			return -1;
		}
		else {
			return model.getDataLump().getVersion();
		}
	}

	public static int whoseTurnIsItAnyway() {
		return model.getDataLump().getTurnTracker().getCurrentPlayer();
	}

	public static Status whatStateMightItBe() {
		return model.getDataLump().getTurnTracker().getStatus();
	}
	
	public static boolean canBuildSettlement(int playerIndex, VertexLocation vertLoc) {
		
		if(playerIndex != ModelFacade.whoseTurnIsItAnyway()) {
			return false;
		}
		else if(model.getSettlements().get(vertLoc.getNormalizedLocation()) != null) {
			return false;
		}
		
		else if(model.getCities().get(vertLoc.getNormalizedLocation()) != null) {
			return false;
		}
		
		List<VertexLocation> nearbyVertices = model.getAdjacentVertices(vertLoc);
		
		for(VertexLocation point : nearbyVertices) {
			if(model.getSettlements().get(point.getNormalizedLocation()) != null) {
				return false;
			}
			else if(model.getCities().get(point.getNormalizedLocation()) != null) {
				return false;
			}
		}
		
		ResourceList rList = model.getDataLump().getPlayers().get(playerIndex).getResources();
		
		if(rList.getBrick() == 0 || rList.getSheep() == 0 || rList.getWheat() == 0 || rList.getWood() == 0) {
			return false;
		}
		
		List<EdgeLocation> nearbyEdges = model.getNearbyEdges(vertLoc);
		
		for(EdgeLocation face : nearbyEdges) {
			if(model.getRoads().get(face.getNormalizedLocation()) != null) {
				if(model.getRoads().get(face.getNormalizedLocation()).getOwner() == playerIndex){
					return true;
				}
			}
		}
		
		return false;
	}

	public static boolean canBuildCity(int playerIndex, VertexLocation vertLoc) {
		// TODO Auto-generated method stub
		vertLoc = vertLoc.getNormalizedLocation();
		if(playerIndex != ModelFacade.whoseTurnIsItAnyway()) {
			return false;
		}
		
		ResourceList rList = model.getDataLump().getPlayers().get(playerIndex).getResources();
		
		if(rList.getOre() < 3 || rList.getWheat() < 2) {
			return false;
		}
		
		VertexObject x = model.getSettlements().get(vertLoc);
		if(model.getSettlements().get(vertLoc) != null) {
			if(model.getSettlements().get(vertLoc.getNormalizedLocation()).getOwner() == playerIndex) {
				return true;
			}
			
			else {
				return false;
			}
		}

		return false;
	}

	public static boolean canBuildRoad(int playerIndex, EdgeLocation edgeLoc) {
		// TODO Auto-generated method stub
		
		if(playerIndex != ModelFacade.whoseTurnIsItAnyway()) {
			return false;
		}
		
		if(model.getRoads().get(edgeLoc.getNormalizedLocation()) != null) {
			return false;
		}
		
		ResourceList rList = model.getDataLump().getPlayers().get(playerIndex).getResources();
		
		if(rList.getBrick() == 0 || rList.getWood() == 0) {
			return false;
		}
		
		List<VertexLocation> points = model.getNearbyVertices(edgeLoc);
		
		for(VertexLocation point : points) {
			if(model.getSettlements().get(point.getNormalizedLocation()) != null) {
				if(model.getSettlements().get(point.getNormalizedLocation()).getOwner() == playerIndex) {
					return true;
				}
			}
			else if(model.getCities().get(point.getNormalizedLocation()) != null) {
				if(model.getCities().get(point.getNormalizedLocation()).getOwner() == playerIndex) {
					return true;
				}
			}
		}
		
		List<EdgeLocation> nearbyEdges = model.getAdjacentEdges(edgeLoc);
		
		for(EdgeLocation nearbyEdge : nearbyEdges) {
			if(model.getRoads().get(nearbyEdge.getNormalizedLocation()) != null) {
				if(model.getRoads().get(nearbyEdge.getNormalizedLocation()).getOwner() == playerIndex) {
					return true;
				}
			}
		}
		
		return false;
	}

	public static boolean canDomesticTrade(TradeOffer offer) {
		int currentPlayer = model.getDataLump().getTurnTracker().getCurrentPlayer();
		if(offer.getSender() != currentPlayer && offer.getReceiver() != currentPlayer) {
			return false;
		}
		
		ResourceList theList = offer.getOffer();
		
		ResourceList theOffer = new ResourceList(0,0,0,0,0);
		ResourceList theRequest = new ResourceList(0,0,0,0,0);
		
		
		if(theList.getWood() >= 0) {
			theRequest.setWood(theList.getWood());
		}
		else {
			theOffer.setWood(Math.abs(theList.getWood()));
		}
		if(theList.getBrick() >= 0) {
			theRequest.setBrick(theList.getBrick());
		}
		else {
			theOffer.setBrick(Math.abs(theList.getBrick()));
		}
		if(theList.getSheep() >= 0) {
			theRequest.setSheep(theList.getSheep());
		}
		else {
			theOffer.setSheep(Math.abs(theList.getSheep()));
		}
		if(theList.getWheat() >= 0) {
			theRequest.setWheat(theList.getWheat());
		}
		else  {
			theOffer.setWheat(Math.abs(theList.getWheat()));
		}
		if(theList.getOre() >= 0) {
			theRequest.setOre(theList.getOre());
		}
		else {
			theOffer.setOre(Math.abs(theList.getOre()));
		}
		
		
		//Check if sender has enough resources
		
		ResourceList sendResources = model.getDataLump().getPlayers().get(offer.getSender()).getResources();
		if(theOffer.getBrick() > sendResources.getBrick()
				|| theOffer.getOre() > sendResources.getOre()
				|| theOffer.getSheep() > sendResources.getSheep()
				|| theOffer.getWheat() > sendResources.getWheat()
				|| theOffer.getWood() > sendResources.getWood()) {
			return false;
		}
		
		//Check if receiver has enough resources
		
		ResourceList receiveResources = model.getDataLump().getPlayers().get(offer.getReceiver()).getResources();
		if(theRequest.getBrick() > receiveResources.getBrick()
				|| theRequest.getOre() > receiveResources.getOre()
				|| theRequest.getSheep() > receiveResources.getSheep()
				|| theRequest.getWheat() > receiveResources.getWheat()
				|| theRequest.getWood() > receiveResources.getWood()) {
			return false;
		}
		
		//Passed all checks
		return true;
	}
	
	public static boolean canMaritimeTrade(int playerIndex, ResourceType tradeResource, ResourceType desiredResource) {
		Player player = model.getDataLump().getPlayers().get(playerIndex);
		
		int tradeRatio = getTradeRatio(playerIndex, tradeResource);
		
		return player.getResources().hasResource(tradeResource, tradeRatio) && model.getDataLump().getBank().hasResource(desiredResource, 1);
	}
	
	public static int getTradeRatio(int playerIndex, ResourceType tradeResource) {
		
		int ratio = 4;
		
		for (Port port : getPorts()) {
			
			int x = port.getLocation().getX();
			int y = port.getLocation().getY();
			
			EdgeDirection direction = port.getDirection();
			
			EdgeLocation relativeEdge = (new EdgeLocation(x, y, direction)).getNormalizedLocation();
			
			for (VertexLocation vertex : model.getNearbyVertices(relativeEdge)) {
				
				if(model.getSettlements().get(vertex.getNormalizedLocation()) != null) {
					if(model.getSettlements().get(vertex.getNormalizedLocation()).getOwner() == playerIndex) {
						if (ratio == 4 && port.getRatio() == 3) {
							ratio = 3;
						}
						else if (tradeResource == port.getPortResource()){
							return 2;
						}
					}
				}
				
				else if(model.getCities().get(vertex.getNormalizedLocation()) != null) {
					if(model.getCities().get(vertex.getNormalizedLocation()).getOwner() == playerIndex) {
						if (ratio == 4 && port.getRatio() == 3) {
							ratio = 3;
						}
						else if (tradeResource == port.getPortResource()){
							return 2;
						}
					}
				}
			}
		}
		return ratio;
	}
	
	public static boolean canBuyDevelopmentCard(int playerIndex) {
		ResourceList rList = model.getDataLump().getPlayers().get(playerIndex).getResources();
		if(model.getDataLump().getTurnTracker().getCurrentPlayer() != playerIndex) {
			return false;
		}
		else if(rList.getOre() == 0 || rList.getSheep() == 0 || rList.getWheat() == 0) {
			return false;
		}
		else if(model.getDataLump().getDeck().getTotalCards() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean canLoseCardsFromDieRoll(int playerIndex) {
		return model.getDataLump().getPlayers().get(playerIndex).getResources().getTotalCards() > 7;
	}
	
	public static boolean canWin(int playerIndex) {
		return model.getDataLump().getTurnTracker().getCurrentPlayer() == playerIndex && 
				model.getDataLump().getPlayers().get(playerIndex).getVictoryPoints() > 9;
	}
	
	/**
	 * Queries the hex location of the robber.
	 * @pre ModelFacade.updateModel() must have been called with a valid model
	 * @post see return
	 * @return the hex location of the robber
	 */
	public static HexLocation findRobber() {
		return model.getDataLump().getMap().getRobber();
	}

	/**
	 * This function sets the data for the game the user is interacting with, namely
	 * <ul>
	 * <li>A game the user is trying to join.
	 * <li>A game that the user is playing in.
	 * <ul>
	 * @pre model must not be null
	 * @post game info is set to given
	 * @param game - the game info to store
	 */
	public static void setGameInfo(GameInfo game) {
		model.setGameInfo(game);
	}
	public static GameInfo getGameInfo() {
		return model.getGameInfo();
	}
	
	/**
	 * This function clears the current game info the user is trying to interact with.
	 * @pre model must not be null
	 * @post game info is set to null
	 */
	public static void clearGameInfo() {
		model.setGameInfo(null);
	}
	
	/**
	 * This function sets the data for the player info of the user
	 * @pre model must not be null
	 * @post player info is set to given
	 * @param player - the player info to store
	 */
	public static void setPlayerInfo(PlayerInfo player) {
		System.out.println("Set player info: " + player.getColor());
		model.setPlayerInfo(player);
	}
	public static PlayerInfo getPlayerInfo() {
		return model.getPlayerInfo();
	}
	
	/**
	 * This function clears the current player info of the user
	 * @pre model must not be null
	 * @post player info is set to null
	 */
	public static void clearPlayerInfo() {
		model.setPlayerInfo(null);
	}
	
	/**
	 * This function returns the player info for the current game
	 * @pre model cannot be null, model.getGameInfo() cannot return null
	 * @post see return
	 * @return The player information array for the players in the current game
	 */
	public static PlayerInfo[] getJoinedPlayersInfo() {
		List<PlayerInfo> playerList = model.getGameInfo().getPlayers();
		int numPlayers = 0;
		for (PlayerInfo info : playerList) {
			if(info != null) {
				numPlayers++;
			}
		}
		
		PlayerInfo[] players = new PlayerInfo[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			players[i] = playerList.get(i);
		}
		return players;
	}
	
	/**
	 * Adds the given observer to the notify list to be notified when the model is updated
	 * @param observer - the observer to add
	 * @pre none
	 * @post the given observer will be notified when the model is updated.
	 */
	public static void addObserver(Observer observer) {
		observers.add(observer);
	}
	
	/**
	 * Notifies the list of observers that the model has changed
	 * @pre none
	 * @post each observer is notified
	 */
	private static void notifyObserversOfChange() {
		System.out.println("Notify observers");
		for (Observer observer : observers) {
			observer.update();
		}
	}
	
	public static List<LogEntry> getChatLog() {
		MessageList chat = model.getDataLump().getChat();
		List<LogEntry> chatLog = new ArrayList<LogEntry>();
		
		for (MessageLine line : chat.getLines()) {
			CatanColor color = getGameInfo().getPlayerInfo(line.getSource()).getColor();
			chatLog.add(new LogEntry(color, line.getMessage()));
		}
		
		return chatLog;
	}
	
	public static void setUsername(String username) {
		System.out.println("Set username to: " + username);
		model.setUsername(username);
	}
	public static String getUsername() {
		return model.getUsername();
	}
}
