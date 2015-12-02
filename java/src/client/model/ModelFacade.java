package client.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import shared.definitions.CatanColor;
import shared.definitions.EdgeDirection;
import shared.definitions.ResourceType;
import shared.definitions.VertexDirection;
import shared.exceptions.ServerException;
import shared.transferClasses.UserInfo;
import client.base.Observer;
import client.communication.LogEntry;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.server.ServerPoller;
import client.server.ServerProxy;



public class ModelFacade {
	private static final ClientModel model = new ClientModel();
	private static final List<Observer> observers = new ArrayList<Observer>();
	
	
	//// NOTE: Only some controllers have the access to close modal dialogs.
	////		And the controllers that can close modal dialogs can only close the one that is on top of the stack.
	////		This is bad because the MapController cannot ensure that it doesn't cover the PlayerWaitingControllers dialog
	////		before the PlayerWaitingController closes it, therefore, making the PlayerWaitingController prone to close
	////		the MapController's dialog instead of it's own depending on the order that their update methods are called.
	/**
	 * Is the game ready to begin
	 */
	private static boolean gameIsReadyToBegin = false;
	/**
	 * @return true if all players have joined and the game is ready to begin<br>
	 * false if players still need to join
	 */
	public static boolean isGameReadyToStart() {
		return gameIsReadyToBegin;
	}
	/**
	 * @post the game is now ready to begin, controllers are notified
	 */
	public static void alertThatAllPlayersHaveJoined() {
		gameIsReadyToBegin = true;
		ServerPoller.stop();
		try {
			forceUpdateModel(ServerProxy.getModel(-1));
		} catch (ServerException e) {
			e.printStackTrace();
		}
		ServerPoller.start();
	}
	/**
	 * @post the game is not ready to begin, player is waiting for players to join
	 */
	public static void alertThatPlayerIsWaitingForPlayersToJoin() {
		gameIsReadyToBegin = false;
		ServerPoller.start();
	}
	////
	
	/**
	 * Updates the model if the given model's version is newer.
	 * @pre none
	 * @post If the given model is not null and has a higher version,
	 * the model is updated to the given model.
	 * @param data - the model to check for an update
	 */
	public static void updateModel(TransferModel data) {
		if (data != null && (!gameIsReadyToBegin || getModelVersion() < data.getVersion())) {
			model.update(data);
			notifyObserversOfChange();
		}
	}
	
	public static void forceUpdateModel(TransferModel lump) {
		model.update(lump);
		notifyObserversOfChange();
	}
	
	public static List<Hex> getHexes() {
		return model.getTransferModel().getMap().getHexes();
	}
	
	public static List<Road> getRoads() {
		//TODO
		return null; //model.getTransferModel().getMap().getRoads();
	}
	
	public static List<VertexObject> getSettlements() {
		//TODO
		return null; //model.getTransferModel().getMap().getSettlements();
	}
	
	public static List<VertexObject> getCities() {
		//TODO
		return null;//model.getTransferModel().getMap().getCities();
	}
	
	public static List<Port> getPorts() {
		//TODO
		return null;//model.getTransferModel().getMap().getPorts();
	}
	
	/**
	 * @post returns the PlayerInfo for this user
	 * @pre model must not be null
	 */
	public static PlayerInfo getUserPlayerInfo() {
		return model.getPlayerInfo();
	}
	
	/**
	 * @post returns the Player object that corresponds to the user
	 * @pre model must not be null, user and game info, and transfer model must be set
	 */
	public static Player getUserPlayer() {
		return getPlayerByIndex(getUserPlayerInfo().getIndex());
	}
	
	/**
	 * @post returns the Player object that corresponds to the given index
	 * @pre model must not be null, and transfer model must be set
	 */
	public static Player getPlayerByIndex(int index) {
		return model.getTransferModel().getPlayers().get(index);
	}
	
	/**
	 * @pre game info must be set
	 * @post returns a list of the info for players who have joined the same game as the user
	 */
	public static List<PlayerInfo> getJoinedPlayersInfo() {
		return getGameInfo().getPlayers();
	}
	
	
	
	public static int getWinner() {
		return model.getTransferModel().getWinner();
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
		return model.getTransferModel().getBank().hasResource(resource_type, resource_amount);
	}
	
	/**
	 * Returns the transfer model's version, -1 if current transfer model is null
	 * @returns -1 if no model version yet, otherwise it returns the models version
	 */
	public static int getModelVersion() {
		if (model.getTransferModel() == null) {
			return -1;
		}
		else if (model.getGameInfo().getPlayers().size() != 4) {
			return -1;
		}
		else {
			return model.getTransferModel().getVersion();
		}
	}

	public static int whoseTurnIsItAnyway() {
		return model.getTransferModel().getTurnTracker().getCurrentPlayer();
	}

	public static Status whatStateMightItBe() {
		return model.getTransferModel().getTurnTracker().getStatus();
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
		
		ResourceList rList = model.getTransferModel().getPlayers().get(playerIndex).getResources();
		
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
		
		ResourceList rList = model.getTransferModel().getPlayers().get(playerIndex).getResources();
		
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
		
		ResourceList rList = model.getTransferModel().getPlayers().get(playerIndex).getResources();
		
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
		int currentPlayer = model.getTransferModel().getTurnTracker().getCurrentPlayer();
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
		
		ResourceList sendResources = model.getTransferModel().getPlayers().get(offer.getSender()).getResources();
		if(theOffer.getBrick() > sendResources.getBrick()
				|| theOffer.getOre() > sendResources.getOre()
				|| theOffer.getSheep() > sendResources.getSheep()
				|| theOffer.getWheat() > sendResources.getWheat()
				|| theOffer.getWood() > sendResources.getWood()) {
			return false;
		}
		
		//Check if receiver has enough resources
		
		ResourceList receiveResources = model.getTransferModel().getPlayers().get(offer.getReceiver()).getResources();
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
		Player player = model.getTransferModel().getPlayers().get(playerIndex);
		
		int tradeRatio = getTradeRatio(playerIndex, tradeResource);
		
		return player.getResources().hasResource(tradeResource, tradeRatio) && model.getTransferModel().getBank().hasResource(desiredResource, 1);
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
		ResourceList rList = model.getTransferModel().getPlayers().get(playerIndex).getResources();
		if(model.getTransferModel().getTurnTracker().getCurrentPlayer() != playerIndex) {
			return false;
		}
		else if(rList.getOre() == 0 || rList.getSheep() == 0 || rList.getWheat() == 0) {
			return false;
		}
		else if(model.getTransferModel().getDeck().getTotalCards() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean canLoseCardsFromDieRoll(int playerIndex) {
		return model.getTransferModel().getPlayers().get(playerIndex).getResources().getTotalCards() > 7;
	}
	
	public static boolean canWin(int playerIndex) {
		return model.getTransferModel().getTurnTracker().getCurrentPlayer() == playerIndex && 
				model.getTransferModel().getPlayers().get(playerIndex).getVictoryPoints() > 9;
	}
	
	/**
	 * Queries the hex location of the robber.
	 * @pre ModelFacade.updateModel() must have been called with a valid model
	 * @post see return
	 * @return the hex location of the robber
	 */
	public static HexLocation findRobber() {
		return model.getTransferModel().getMap().getRobber();
	}

	/**
	 * @post sets the user information for the user
	 * @param userInfo - the userInfo to set it to
	 * @pre model must not be null
	 */
	public static void setUserInfo(UserInfo userInfo) {
		model.setUserInfo(userInfo);
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
	/**
	 * Gets the game info for the game the user has joined or is trying to join
	 * @return the game info for the game the user has joined or is trying to join
	 * @pre model must not be null
	 * @post see return
	 */
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
		MessageList chat = model.getTransferModel().getChat();
		List<LogEntry> chatLog = new ArrayList<LogEntry>();
		
		for (MessageLine line : chat.getLines()) {
			CatanColor color = getGameInfo().getPlayerInfoByName(line.getSource()).getColor();
			chatLog.add(new LogEntry(color, line.getMessage()));
		}
		
		return chatLog;
	}
}
