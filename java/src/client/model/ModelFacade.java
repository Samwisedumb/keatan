package client.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import server.facades.ServerData;
import server.model.ServerModel;
import shared.definitions.EdgeDirection;
import shared.definitions.ResourceType;
import shared.definitions.VertexDirection;
import shared.transferClasses.UserInfo;
import client.base.MasterController;
import client.base.Observer;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.data.RobPlayerInfo;


public class ModelFacade {
	private static final ClientModel model = new ClientModel();
	private static final List<Observer> observers = new ArrayList<Observer>();

	
	/**
	 * Updates the model if the given model's version is newer.
	 * @pre none
	 * @post If the given model is not null and has a higher version,
	 * the model is updated to the given model.
	 * @param data - the model to check for an update
	 */
	public static void updateModel(TransferModel data) {
		if (data != null &&
				(MasterController.getSingleton().hasGameBegun() || getModelVersion() < data.getVersion())) {
			model.update(data);
			notifyObserversOfChange();
		}
	}
	
	public static void forceUpdateModel(TransferModel lump) {
		model.update(lump);
		notifyObserversOfChange();
	}
	
	public static Collection<Hex> getHexes() {
		return model.getTransferModel().getMap().getHexes();
	}
	
	public static Collection<Road> getRoads() {		
		return model.getRoads().values();
	}
	
	/**
	 * Get the player with the given index
	 * @param index - the index to get
	 * @return the player at the given index<br>
	 * null if the index is out of range
	 */
	public static Player getPlayer(int index) {
		if (index < 0 || index > 4) {
			return null;
		}
		
		return model.getTransferModel().getPlayers().get(index);
	}
	
	public static Collection<Settlement> getSettlements() {
		return model.getSettlements().values();
	}
	
	public static Collection<City> getCities() {
		return model.getCities().values();
	}
	
	public static Collection<Port> getPorts() {
		return model.getTransferModel().getMap().getPorts();
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
	
	
	/**
	 * @return The index of the winning player if a player has won<br>
	 * -1 if no player has won yet
	 */
	public static int getWinner() {
		return model.getTransferModel().getWinner();
	}
	
	/**
	 * Returns the transfer model's version, -1 if current transfer model is null
	 * @returns -1 if no model version yet, otherwise it returns the models version
	 * @author djoshuac
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

	/**
	 * @return The index of the player whose turn it is
	 * @author djoshuac
	 */
	public static int whoseTurnIsItAnyway() {
		return model.getTransferModel().getTurnTracker().getPlayerTurn();
	}

	public static Status whatStateMightItBe() {
		return model.getTransferModel().getTurnTracker().getStatus();
	}
	
	
	/**
	 * See if the user can build a settlement
	 * @param vertLoc - the vertex location to build at
	 * @param isFree - does the settlement cost resources
	 * @return true if the user can build there<br>
	 * false if otherwise
	 * @author djoshuac
	 */
	public static boolean canBuildSettlement(VertexLocation vertLoc, boolean isFree) {
		Player user = getUserPlayer();
		
		if (model.hasMunicipality(vertLoc)) {
			System.out.println("Municipality is already there");
			return false;
		}
		
		if (!isWithinBounds(vertLoc)) {
			System.out.println("OUTOFBOUNDS: " + vertLoc);
			return false;
		}
		
		if (getUserPlayer().getUnplacedSettlements() < 1) {
			System.out.println("Not enough unplaced settlements");
			return false;
		}
		
		if (!isFree && !user.getResources().hasEnoughForSettlement()) {
			System.out.println("Not enough resources");
			return false;
		}
		
		if (model.isTooCloseToAnotherMunicipality(vertLoc)) {
			System.out.println("Too close to another municipality: " + vertLoc);
			return false;
		}
		
		if (!model.isAdjacentToRoadOfPlayer(vertLoc, user)) {
			System.out.println("Not adjacent to a road owned by user: " + vertLoc);
			return false;
		}
		
		return true;
	}

	/**
	 * See if the user can build a city
	 * @param vertLoc - the vetex location to build at
	 * @return true if the user can build a settlement at given location<br>
	 * false if otherwise
	 * @author djoshuac
	 */
	public static boolean canBuildCity(VertexLocation vertLoc) {
		Player user = getUserPlayer();
		
		if (!isWithinBounds(vertLoc)) {
			return false;
		}
		
		if (getUserPlayer().getUnplacedCities() < 1) {
			System.out.println("Not enough unplaced cities");
			return false;
		}
		
		if (!user.getResources().hasEnoughForCity()) {
			return false;
		}
		
		if (model.hasSettlement(vertLoc) && model.getSettlement(vertLoc).getOwnerIndex() == user.getIndex()) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * @param playerIndex
	 * @param edgeLoc - the edge location where the road is to be placed
	 * @param isFree - does the road cost resources
	 * @param isDisconnected - is the road allowed to be disconnected from all other roads (initial setup phase)
	 * @pre the edge location must be valid
	 * @return true when a player is allowed to build a road
	 * @author djoshuac
	 */
	public static boolean canBuildRoad(EdgeLocation edgeLoc, boolean isFree, boolean isDisconnected) {
		Player user = getUserPlayer();

		if (!isWithinBounds(edgeLoc)) {
			System.out.println("OUTOFBOUNDS: " + edgeLoc);
			return false;
		}
		
		if (model.hasRoad(edgeLoc)) {
			System.out.println("A road already is there");
			return false;
		}
		
		if (getUserPlayer().getUnplacedRoads() < 1) {
			System.out.println("No more unplaced roads");
			return false;
		}
		
		if (!isFree && !user.getResources().hasEnoughForRoad()) {
			System.out.println("Not enough resources");
			return false;
		}
		
		if (isDisconnected) { // when we are in the initial setup stage - we want to make sure the placement of the road doesn't force an illegal settlement placement
			for (VertexLocation vertex : model.getNearbyVertices(edgeLoc)) {
				if (!model.hasMunicipality(vertex) && !model.isTooCloseToAnotherMunicipality(vertex)) {
					return true;
				}
			}
			System.out.println("Forces illegal settlement placement");
			return false;
		}
		else { // normally - we want to ensure that the road is adjacent to another road
			for (VertexLocation vertex : model.getNearbyVertices(edgeLoc)) {
				if (model.isAdjacentToRoadOfPlayer(vertex, user)) {
					return true;
				}
			}
			System.out.println("Not adjacent to road owned by the player");
			return false;
		}
	}
	
	public static boolean canPlaceRobber(HexLocation hexLoc) {
		if (!isWithinBounds(hexLoc)) {
			return false;
		}
		
		if (hexLoc.equals(model.getTransferModel().getMap().getRobber())) {
			return false;
		}
		
		return true;
	}

	public static boolean canDomesticTrade(TradeOffer offer) {
		
		int currentPlayer = model.getTransferModel().getTurnTracker().getPlayerTurn();
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

	public static boolean canAcceptTrade() {
		if(model.getTransferModel().getTradeOffer() == null) {
			return false;
		}
		else {
			return canDomesticTrade(model.getTransferModel().getTradeOffer());
		}
	}
	
	public static boolean canMaritimeTrade(int playerIndex, ResourceType tradeResource, ResourceType desiredResource) {
		
		Player player = model.getTransferModel().getPlayers().get(playerIndex);
		
		int tradeRatio = getTradeRatio(playerIndex, tradeResource);
		
		return player.getResources().hasResource(tradeResource, tradeRatio) && 
				model.getTransferModel().getBank().hasResource(desiredResource, 1);
	}
	
	public static int getTradeRatio(int playerIndex, ResourceType tradeResource) {
		
		int ratio = 4;
		
		for (Port port : getPorts()) {
			
			int x = port.getLocation().getX();
			int y = port.getLocation().getY();
			
			EdgeDirection direction = port.getDirection();
			
			EdgeLocation relativeEdge = (new EdgeLocation(x, y, direction)).getNormalizedLocation();
			
			for (VertexLocation vertex : model.getNearbyVertices(relativeEdge)) {
				
				if(model.hasSettlement(vertex)) {
					if(model.getSettlement(vertex).getOwnerIndex() == playerIndex) {
						if (ratio == 4 && port.getRatio() == 3) {
							ratio = 3;
						}
						else if (tradeResource == port.getPortResource()){
							return 2;
						}
					}
				}
				
				else if(model.hasCity(vertex)) {
					if(model.getCity(vertex).getOwnerIndex() == playerIndex) {
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
		if(model.getTransferModel().getTurnTracker().getPlayerTurn() != playerIndex) {
			return false;
		}
		else if(rList.getOre() == 0 || rList.getSheep() == 0 || rList.getWheat() == 0) {
			return false;
		}
		else if(model.getTransferModel().getDeck().getTotalCards() > 0) {
			return true;
		}
		else if(model.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		else {
			return false;
		}
		
		
	}
	
	public static boolean canPlaySoldier(int playerIndex, int victimIndex, HexLocation robberMove) {
		
		if(playerIndex != model.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(model.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		else if(model.getTransferModel().getPlayers().get(playerIndex).hasPlayedDevCard() == true) {
			return false;
		}
		else if(model.getTransferModel().getPlayers().get(playerIndex).getOldDevCards().getSoldier() == 0) {
			return false;
		}
		else if(robberMove.equals(findRobber())) {
			return false;
		}
		else if((victimIndex != -1) || (playerIndex == victimIndex)) {
			if(model.getTransferModel().getPlayers().get(victimIndex).getResources().getTotalCards() == 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean canMonument(int playerIndex) {
		
		if(playerIndex != model.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(model.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		
		if(((model.getTransferModel().getPlayers().get(playerIndex).getVictoryPoints() +
				model.getTransferModel().getPlayers().get(playerIndex).getOldDevCards().getMonument()) == 10)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean canRoadBuilding(int playerIndex, EdgeLocation placeOne, EdgeLocation placeTwo) {
	
		if(playerIndex != model.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(model.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		else if(model.getTransferModel().getPlayers().get(playerIndex).hasPlayedDevCard() == true) {
			return false;
		}
		else if(model.getTransferModel().getPlayers().get(playerIndex).getOldDevCards().getRoadBuilding() == 0) {
			return false;
		}
		else if(model.getTransferModel().getPlayers().get(playerIndex).getUnplacedRoads() < 2) {
			return false;
		}
		
		if(canBuildRoad(placeOne, true, false) == true) {
			if((canBuildRoad(placeTwo, true, false) == true) ||
				(model.getAdjacentEdges(placeTwo).contains(placeOne) == true)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean canMonopoly(int playerIndex, ResourceType mine) {
		
		if(playerIndex != model.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(model.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		else if(model.getTransferModel().getPlayers().get(playerIndex).hasPlayedDevCard() == true) {
			return false;
		}
		else if(model.getTransferModel().getPlayers().get(playerIndex).getOldDevCards().getMonopoly() == 0) {
			return false;
		}
		
		return true;
	}
	
	public static boolean canYearOfPlenty(int playerIndex, ResourceType resourceOne, ResourceType resourceTwo) {
		
		if(playerIndex != model.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(model.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		
		if(resourceOne == resourceTwo) {
			if(model.getTransferModel().getBank().hasResource(resourceOne, 2)) {
				return true;
			}
		}
		else if(model.getTransferModel().getBank().hasResource(resourceOne, 1)) {
			if(model.getTransferModel().getBank().hasResource(resourceTwo, 1)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean canWin(int playerIndex) {
		return model.getTransferModel().getTurnTracker().getPlayerTurn() == playerIndex && 
				model.getTransferModel().getPlayers().get(playerIndex).getVictoryPoints() > 9;
	}
	
	/**
	 * Queries the hex location of the robber.
	 * @pre ModelFacade.updateModel() must have been called with a valid model
	 * @post see return
	 * @return the hex location of the robber
	 * @author djoshuac
	 */
	public static HexLocation findRobber() {
		return model.getTransferModel().getMap().getRobber();
	}

	/**
	 * @post sets the user information for the user
	 * @param userInfo - the userInfo to set it to
	 * @pre model must not be null
	 * @author djoshuac
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
	 * @author djoshuac
	 */
	public static void setGameInfo(GameInfo game) {
		model.setGameInfo(game);
	}
	/**
	 * Gets the game info for the game the user has joined or is trying to join
	 * @return the game info for the game the user has joined or is trying to join
	 * @pre model must not be null
	 * @post see return
	 * @author djoshuac
	 */
	public static GameInfo getGameInfo() {
		return model.getGameInfo();
	}
	/**
	 * This function clears the current game info the user is trying to interact with.
	 * @pre model must not be null
	 * @post game info is set to null
	 * @author djoshuac
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
	
	/**
	 * Gets the chat log for the given game
	 * @return the chat log for the given game
	 * @pre model must not be null
	 */
	public static MessageList getChatLog() {
		return model.getTransferModel().getChat();
	}

	/**
	 * @pre Model must be initialized
	 * @return the list of players in the game
	 * @author djoshuac
	 */
	public static List<Player> getPlayers() {
		return model.getTransferModel().getPlayers();
	}
	
	/**
	 * @return the player object of the owner of the longest road<br>
	 * null if no player has the longest road
	 * @author djoshuac
	 */
	public static Player getPlayerWithLongestRoad() {
		return getPlayer(model.getTransferModel().getLargestRoadOwnerIndex());
	}
	
	/**
	 * @return the player object of the owner of the largest army<br>
	 * null if no player has largest army
	 * @author djoshuac
	 */
	public static Player getPlayerWithLargestArmy() {
		return getPlayer(model.getTransferModel().getLargestArmyOwnerIndex());
	}
	
	/**
	 * @return the player whose turn it is
	 * @author djoshuac
	 */
	public static Player getPlayerWhoseTurnItIs() {
		return getPlayer(whoseTurnIsItAnyway());
	}
	
	/**
	 * @return true is the location is within bounds<br>
	 * false if without
	 * @author djoshuac
	 */
	public static boolean isWithinBounds(EdgeLocation edge) {
		return model.getTransferModel().getMap().getEdges().contains(new EdgeValue(edge));
	}
	
	/**
	 * @return true is the location is within bounds<br>
	 * false if without
	 * @author djoshuac
	 */
	public static boolean isWithinBounds(VertexLocation vertex) {
		return model.getTransferModel().getMap().getVertexValues().contains(new VertexValue(vertex));
	}

	/**
	 * @param hexLoc - hex location to check
	 * @return true is hex is within the map<br>
	 * false if hex is without
	 */
	private static boolean isWithinBounds(HexLocation hexLoc) {
		for (Hex hex : model.getTransferModel().getMap().getHexes()) {
			if (hex.getLocation().equals(hexLoc)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the game history for the game
	 * @pre the model must be initialized
	 * @return the game log.
	 * @author djoshuac
	 */
	public static MessageList getGameHistory() {
		return model.getTransferModel().getLog();
	}

	/**
	 * Moves the robber to the given location
	 * @param hexLoc - the location to move the robber to
	 * @pre the model must be initialized
	 * @author djoshuac
	 */
	public static void moveRobber(HexLocation hexLoc) {
		model.getTransferModel().getMap().setRobber(hexLoc);
	}

	/**
	 * @pre the model must be initialized
	 * @return an array of robbable players
	 */
	public static RobPlayerInfo[] getRobbablePlayerInfo() {
		Set<RobPlayerInfo> info = new HashSet<RobPlayerInfo>();
		for (VertexLocation location : findRobber().getVertices()) {
			if (model.hasMunicipality(location)) {			
				Player p = getPlayer(model.getMunicipality(location).getOwnerIndex());
				
				RobPlayerInfo robInfo = new RobPlayerInfo();
				robInfo.setColor(p.getColor());
				robInfo.setID(p.getID());
				robInfo.setName(p.getName());
				robInfo.setNumCards(p.getResources().getTotal());
				robInfo.setPlayerIndex(p.getIndex());
				
				if (p.getIndex() != getUserPlayer().getIndex()) { // don't rob self
					info.add(robInfo);
				}
			}
		}
		
		return info.toArray(new RobPlayerInfo[info.size()]);
	}
}


