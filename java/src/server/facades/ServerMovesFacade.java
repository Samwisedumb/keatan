package server.facades;

import java.util.ArrayList;
import java.util.List;

import server.model.ServerModel;
import shared.definitions.EdgeDirection;
import shared.definitions.ResourceType;
import shared.exceptions.ServerException;
import shared.transferClasses.AcceptTrade;
import shared.transferClasses.BuildCity;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;
import shared.transferClasses.BuyDevCard;
import shared.transferClasses.DiscardCards;
import shared.transferClasses.FinishTurn;
import shared.transferClasses.Game;
import shared.transferClasses.JoinGameRequest;
import shared.transferClasses.MaritimeTrade;
import shared.transferClasses.Monopoly;
import shared.transferClasses.Monument;
import shared.transferClasses.OfferTrade;
import shared.transferClasses.RoadBuilding;
import shared.transferClasses.RobPlayer;
import shared.transferClasses.RollNumber;
import shared.transferClasses.SendChat;
import shared.transferClasses.Soldier;
import shared.transferClasses.YearOfPlenty;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.Player;
import client.model.Port;
import client.model.ResourceList;
import client.model.Status;
import client.model.TradeOffer;
import client.model.VertexLocation;
import client.model.VertexValue;

/**
 * Server Facade that handles all "moves" commands for all games
 */
public class ServerMovesFacade implements IMovesFacade {
	
	private static ServerMovesFacade instance = null;
	
	private List<ServerModel> games;
	private List<Game> gameTags;
	
	/**
	 * returns a singleton of ServerMovesFacade
	 */
	public static ServerMovesFacade getInstance() {
		if(instance == null) {
			instance = new ServerMovesFacade();
		}
		
		return instance;
	}
	
	public ServerMovesFacade() {
		games = new ArrayList<ServerModel>();
		gameTags = new ArrayList<Game>();
	}
	
	public List<Game> getGameTags() {
		return gameTags;
	}
	
	
	public boolean addPlayerToGame(JoinGameRequest request, String playerName, int playerID) {
		
		int gameSize = games.get(request.getGameID()).getTransferModel().getPlayers().size();
		
		if(gameSize == 4) {
			return false;
		}
		else {
			Player newPlayer = new Player(playerName, gameSize + 1, request.getColor(), playerID);
			games.get(request.getGameID()).getTransferModel().getPlayers().add(newPlayer);
		}
		
		return true;
	}
	
	@Override
	public void acceptTrade(int gameID, AcceptTrade accept) throws ServerException {
		// TODO Auto-generated method stub
		if(canAcceptTrade(gameID) == false) {
			throw new ServerException("You can't trade, mon ami");
		}
		ServerData.getInstance().getGameModel(gameID).acceptTrade(accept.getPlayerIndex(), accept.getWillAccept());
	}
	
	@Override
	public void buildCity(int gameID, BuildCity build) throws ServerException {
		// TODO Auto-generated method stub
		if(canBuildCity(build.getPlayerIndex(), build.getSpotOne(), gameID, build.getFree()) == false) {
			throw new ServerException("Can't put a city there, mate");
		}
		
		if(build.getFree() == false) {
			ServerData.getInstance().getGameModel(gameID).payForCity(build.getPlayerIndex());
		}
		
		ServerData.getInstance().getGameModel(gameID).placeCity(build.getSpotOne(), build.getPlayerIndex());
	}

	@Override
	public void buildRoad(int gameID, BuildRoad build) throws ServerException {
		// TODO Auto-generated method stub
		if(canBuildRoad(build.getPlayerIndex(), build.getRoadLocation(), gameID, build.getFree()) == false) {
			throw new ServerException("Can't put a road there, buddy");
		}
		
		if(build.getFree() == false) {
			ServerData.getInstance().getGameModel(gameID).payForRoad(build.getPlayerIndex());
		}
		
		ServerData.getInstance().getGameModel(gameID).placeRoad(build.getRoadLocation(), build.getPlayerIndex());
	}

	@Override
	public void buildSettlement(int gameID, BuildSettlement build) throws ServerException {
		// TODO Auto-generated method stub
		if(canBuildSettlement(build.getPlayerIndex(), build.getSpotOne(), gameID, build.getFree()) == false) {
			throw new ServerException("Can't put a settlement there, pal");
		}
		
		if(build.getFree() == false) {
			ServerData.getInstance().getGameModel(gameID).payForSettlement(build.getPlayerIndex());
		}
		
		ServerData.getInstance().getGameModel(gameID).placeSettlement(build.getSpotOne(), build.getPlayerIndex());
	}

	@Override
	public void buyDevCard(int gameID, BuyDevCard buy) throws ServerException {
		// TODO Auto-generated method stub
		if(canBuyDevCard(buy.getPlayerIndex(), gameID) == false) {
			throw new ServerException("Can't buy a dev card, homeslice");
		}
		
		ServerData.getInstance().getGameModel(gameID).buyDevCard(buy.getPlayerIndex());
	}

	@Override
	public void discardCards(int gameID, DiscardCards discard) throws ServerException {
		// TODO Auto-generated method stub
		if(canDiscard(discard.getPlayerIndex(), discard.getDiscardedCards(), gameID) == false) {
			throw new ServerException("Don't have to discard cards, my friend");
		}
		
		ServerData.getInstance().getGameModel(gameID).discardCards(discard.getPlayerIndex(), discard.getDiscardedCards());
	}

	@Override
	public void finishTurn(int gameID, FinishTurn end) throws ServerException {
		// TODO Auto-generated method stub
		if(canEndTurn(end.getPlayerIndex(), gameID) == false) {
			throw new ServerException("It's not over yet, slick!");
		}
		ServerData.getInstance().getGameModel(gameID).endTurn(end.getPlayerIndex());
	}

	@Override
	public void maritimeTrade(int gameID, MaritimeTrade trade) throws ServerException {
		// TODO Auto-generated method stub
		if(canMaritimeTrade(trade.getPlayerIndex(), trade.getInputResource(), trade.getOutputResource(), trade.getRatio(), gameID)) {
			throw new ServerException("You can't trade, mon ami");
		}
		ServerData.getInstance().getGameModel(gameID).maritimeTrade(trade.getPlayerIndex(), trade.getRatio(),
				trade.getInputResource(), trade.getOutputResource());
	}

	@Override
	public void monopoly(int gameID, Monopoly monopoly) throws ServerException {
		// TODO Auto-generated method stub
		if(canMonopoly(monopoly.getPlayerIndex(), monopoly.getResource(), gameID) == false) {
			throw new ServerException("You can't play this right now, amigo");
		}
		ServerData.getInstance().getGameModel(gameID).monopoly(monopoly.getPlayerIndex(), monopoly.getResource());
	}

	@Override
	public void monument(int gameID, Monument monument) throws ServerException {
		// TODO Auto-generated method stub
		if(canMonument(monument.getPlayerIndex(), gameID) == false) {
			throw new ServerException("You haven't won yet, boy");
		}
		ServerData.getInstance().getGameModel(gameID).monument(monument.getPlayerIndex());
	}

	@Override
	public void offerTrade(int gameID, OfferTrade offer) throws ServerException {
		// TODO Auto-generated method stub
		if(canDomesticTrade(new TradeOffer(offer.getPlayerIndex(), offer.getReciever(), offer.getOffer()), gameID) == false) {
			throw new ServerException("You can't trade, mon ami");
		}
		ServerData.getInstance().getGameModel(gameID).offerTrade(offer.getPlayerIndex(), offer.getReciever(), offer.getOffer());
	}

	@Override
	public void roadBuilding(int gameID, RoadBuilding roadBuild) throws ServerException {
		// TODO Auto-generated method stub
		if(canRoadBuilding(roadBuild.getPlayerIndex(), roadBuild.getSpotOne(), roadBuild.getSpotTwo(), gameID) == false) {
			throw new ServerException("You can't play this right now, amigo");
		}
		ServerData.getInstance().getGameModel(gameID).roadBuilding(roadBuild.getPlayerIndex(), roadBuild.getSpotOne(), roadBuild.getSpotTwo());
	}

	@Override
	public void robPlayer(int gameID, RobPlayer robbery) throws ServerException {
		// TODO Auto-generated method stub
		if(canRob(robbery.getPlayerIndex(), robbery.getVictimIndex(), robbery.getLocation(), gameID) == false) {
			throw new ServerException("Can't steal from anyone, charlie");
		}
		ServerData.getInstance().getGameModel(gameID).robPlayer(robbery.getPlayerIndex(), robbery.getVictimIndex(), robbery.getLocation());
	}

	@Override
	public void rollNumber(int gameID, RollNumber roll) throws ServerException {
		// TODO Auto-generated method stub
		if(canRoll(roll.getPlayerIndex(), gameID) == false) {
			throw new ServerException("Can't roll dice now, brother");
		}
		
		ServerData.getInstance().getGameModel(gameID).getResources(roll.getNumber(), roll.getPlayerIndex());
	}

	@Override
	public void sendChat(int gameID, SendChat chat) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void soldier(int gameID, Soldier soldier) throws ServerException {
		// TODO Auto-generated method stub
		if(canPlaySoldier(soldier.getPlayerIndex(), soldier.getVictimIndex(), soldier.getLocation(), gameID) == false) {
			throw new ServerException("You can't play this right now, amigo");
		}
		ServerData.getInstance().getGameModel(gameID).soldier(soldier.getPlayerIndex(),  soldier.getVictimIndex(), soldier.getLocation());
	}

	@Override
	public void yearOfPlenty(int gameID, YearOfPlenty plenty) throws ServerException {
		// TODO Auto-generated method stub
		if(canYearOfPlenty(plenty.getPlayerIndex(), plenty.getResourceOne(), plenty.getResourceTwo(), gameID) == false) {
			throw new ServerException("You can't play this right now, amigo");
		}
		ServerData.getInstance().getGameModel(gameID).yearOfPlenty(plenty.getPlayerIndex(), plenty.getResourceOne(), plenty.getResourceTwo());
	}

	public boolean canBuildRoad(int playerIndex, EdgeLocation edgeLoc, int gameID, boolean free) {
		// TODO Auto-generated method stub
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		else if(thisGame.getEdges().get(edgeLoc.getNormalizedLocation()).hasRoad() == true) {
			return false;
		}
		else if(thisGame.getTransferModel().getPlayers().get(playerIndex).getUnplacedRoads() == 0) {
			return false;
		}
		
		ResourceList rList = thisGame.getTransferModel().getPlayers().get(playerIndex).getResources();
		
		if((rList.getBrick() == 0 || rList.getWood() == 0) && (free == false)) {
			return false;
		}
		
		if(thisGame.getEdges().containsKey(edgeLoc.getNormalizedLocation()) == false) {
			return false;
		}
		
		List<VertexLocation> points = thisGame.getNearbyVertices(edgeLoc);
		
		if((thisGame.getTransferModel().getTurnTracker().getStatus() == Status.FirstRound) ||
				(thisGame.getTransferModel().getTurnTracker().getStatus() == Status.SecondRound)) {
			for(VertexLocation point : points) {
				boolean wiggleRoom = false; //To see whether or not a settlement built next to this road could be a problem.
				if(canBuildSettlement(playerIndex, point, gameID, true) == true) {
					wiggleRoom = true; //If a settlement could hypothetically be placed near this road without breaking the rules, place road
				}
				
				return wiggleRoom;
			}
		}
		
		for(VertexLocation point : points) {
			if(thisGame.getVertices().get(point.getNormalizedLocation()).hasMunicipality() == true) {
				if(thisGame.getVertices().get(point.getNormalizedLocation()).ownsMunicipality(playerIndex) == true) {
					return true;
				}
			}
		}
		
		List<EdgeLocation> nearbyEdges = thisGame.getAdjacentEdges(edgeLoc);
		
		for(EdgeLocation nearbyEdge : nearbyEdges) {
			if(thisGame.getEdges().get(nearbyEdge.getNormalizedLocation()).hasRoad() == true) {
				if(thisGame.getEdges().get(nearbyEdge.getNormalizedLocation()).getRoad().getOwnerIndex() == playerIndex) {
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean canBuildSettlement(int playerIndex, VertexLocation vertLoc, int gameID, boolean free) {
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		else if(thisGame.getVertices().get(vertLoc.getNormalizedLocation()).hasMunicipality() == true) {
			return false;
		}
		else if(thisGame.getTransferModel().getPlayers().get(playerIndex).getUnplacedSettlements() == 0) {
			return false;
		}
		
		if(thisGame.getVertices().containsKey(vertLoc.getNormalizedLocation()) == false) {
			return false;
		}
		
		List<VertexLocation> nearbyVertices = thisGame.getAdjacentVertices(vertLoc);
		
		for(VertexLocation point : nearbyVertices) {
			if(thisGame.getVertices().get(point.getNormalizedLocation()).hasMunicipality() == true) {
				return false;
			}
		}
		
		ResourceList rList = thisGame.getTransferModel().getPlayers().get(playerIndex).getResources();
		
		if((rList.getBrick() == 0 || rList.getSheep() == 0 || rList.getWheat() == 0 || rList.getWood() == 0) && (free == false)) {
			return false;		
		}
		
		List<EdgeLocation> nearbyEdges = thisGame.getNearbyEdges(vertLoc);
		
		for(EdgeLocation face : nearbyEdges) {
			if(thisGame.getEdges().get(face.getNormalizedLocation()).hasRoad() == true) {
				if(thisGame.getEdges().get(face.getNormalizedLocation()).getRoad().getOwnerIndex() == playerIndex){
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean canBuildCity(int playerIndex, VertexLocation vertLoc, int gameID, boolean free) {
		// TODO Auto-generated method stub
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		vertLoc = vertLoc.getNormalizedLocation();
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		else if(thisGame.getTransferModel().getPlayers().get(playerIndex).getUnplacedCities() == 0) {
			return false;
		}
		
		ResourceList rList = thisGame.getTransferModel().getPlayers().get(playerIndex).getResources();
		
		if((rList.getOre() < 3 || rList.getWheat() < 2) && (free == false)) {
			return false;
		}
		
		if(thisGame.getVertices().containsKey(vertLoc.getNormalizedLocation()) == false) {
			return false;
		}
		
		VertexValue x = thisGame.getVertices().get(vertLoc.getNormalizedLocation());
		
		if(x.getSettlement() != null) {
			if(x.getSettlement().getOwnerIndex() == playerIndex) {
				return true;
			}
			else {
				return false;
			}
		}

		return false;
	}

	public boolean canRoll(int playerIndex, int gameID) {
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Rolling) {
			return false;
		}
		
		return true;
	}

	public boolean canRob(int playerIndex, int victimIndex, HexLocation robberMove, int gameID) {
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Robbing) {
			return false;
		}
		else if(robberMove.equals(thisGame.getRobber())) {
			return false;
		}
		else if(victimIndex != -1) {
			if(thisGame.getTransferModel().getPlayers().get(victimIndex).getResources().getTotalCards() == 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean canBuyDevCard(int playerIndex, int gameID) {
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		ResourceList rList = thisGame.getTransferModel().getPlayers().get(playerIndex).getResources();
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		else if(rList.getOre() == 0 || rList.getSheep() == 0 || rList.getWheat() == 0) {
			return false;
		}
		else if(thisGame.getTransferModel().getDeck().getTotalCards() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean canPlaySoldier(int playerIndex, int victimIndex, HexLocation robberMove, int gameID) {
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		else if(thisGame.getTransferModel().getPlayers().get(playerIndex).hasPlayedDevCard() == true) {
			return false;
		}
		else if(thisGame.getTransferModel().getPlayers().get(playerIndex).getOldDevCards().getSoldier() == 0) {
			return false;
		}
		else if(robberMove.equals(thisGame.getRobber())) {
			return false;
		}
		else if(victimIndex != -1) {
			if(thisGame.getTransferModel().getPlayers().get(victimIndex).getResources().getTotalCards() == 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean canMonument(int playerIndex, int gameID) {
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		
		if((thisGame.getTransferModel().getPlayers().get(playerIndex).getVictoryPoints() +
				thisGame.getTransferModel().getPlayers().get(playerIndex).getOldDevCards().getMonument() == 10)) {
			return true;
		}
		
		return false;
	}
	
	public boolean canRoadBuilding(int playerIndex, EdgeLocation placeOne, EdgeLocation placeTwo, int gameID) {
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		else if(thisGame.getTransferModel().getPlayers().get(playerIndex).hasPlayedDevCard() == true) {
			return false;
		}
		else if(thisGame.getTransferModel().getPlayers().get(playerIndex).getOldDevCards().getRoadBuilding() == 0) {
			return false;
		}
		else if(thisGame.getTransferModel().getPlayers().get(playerIndex).getUnplacedRoads() < 2) {
			return false;
		}
		
		if(canBuildRoad(playerIndex, placeOne, gameID, true) == true) {
			if((canBuildRoad(playerIndex, placeTwo, gameID, true) == true) ||
				(thisGame.getAdjacentEdges(placeTwo).contains(placeOne) == true)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canMonopoly(int playerIndex, ResourceType mine, int gameID) {
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		else if(thisGame.getTransferModel().getPlayers().get(playerIndex).hasPlayedDevCard() == true) {
			return false;
		}
		else if(thisGame.getTransferModel().getPlayers().get(playerIndex).getOldDevCards().getMonopoly() == 0) {
			return false;
		}
		
		return true;
	}
	
	public boolean canYearOfPlenty(int playerIndex, ResourceType resourceOne, ResourceType resourceTwo, int gameID) {
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		
		if(resourceOne == resourceTwo) {
			if(thisGame.getTransferModel().getBank().hasResource(resourceOne, 2)) {
				return true;
			}
		}
		else if(thisGame.getTransferModel().getBank().hasResource(resourceOne, 1)) {
			if(thisGame.getTransferModel().getBank().hasResource(resourceTwo, 1)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canDiscard(int playerIndex, ResourceList discardList, int gameID) {
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Discarding) {
			return false;
		}
		
		Player thisPlayer = thisGame.getTransferModel().getPlayers().get(playerIndex);
		
		if(thisPlayer.hasDiscarded() == true) {
			return false;
		}
		
		if((thisPlayer.getResources().hasResource(ResourceType.BRICK, discardList.getBrick())) == false ||
		   (thisPlayer.getResources().hasResource(ResourceType.ORE, discardList.getOre()) == false) ||
		   (thisPlayer.getResources().hasResource(ResourceType.SHEEP, discardList.getSheep()) == false) ||
		   (thisPlayer.getResources().hasResource(ResourceType.WHEAT, discardList.getWheat()) == false) ||
		   (thisPlayer.getResources().hasResource(ResourceType.WOOD, discardList.getWood()) == false)) {
			return false;
		}
		
		if(thisPlayer.getResources().getTotalCards() > 7) {
			thisPlayer.setDiscarded(true);
			return false;
		}
		
		return true;
	}

	public boolean canDomesticTrade(TradeOffer offer, int gameID) {
		//TODO
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		int currentPlayer = thisGame.getTransferModel().getTurnTracker().getPlayerTurn();
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
		
		ResourceList sendResources = thisGame.getTransferModel().getPlayers().get(offer.getSender()).getResources();
		if(theOffer.getBrick() > sendResources.getBrick()
				|| theOffer.getOre() > sendResources.getOre()
				|| theOffer.getSheep() > sendResources.getSheep()
				|| theOffer.getWheat() > sendResources.getWheat()
				|| theOffer.getWood() > sendResources.getWood()) {
			return false;
		}
		
		//Check if receiver has enough resources
		
		ResourceList receiveResources = thisGame.getTransferModel().getPlayers().get(offer.getReceiver()).getResources();
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

	public boolean canAcceptTrade(int gameID) {
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(thisGame.getTransferModel().getTradeOffer() == null) {
			return false;
		}
		else {
			return canDomesticTrade(thisGame.getTransferModel().getTradeOffer(), gameID);
		}
	}

	public boolean canMaritimeTrade(int playerIndex, ResourceType tradeResource, ResourceType desiredResource, int supposedRatio, int gameID) {
		//TODO
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		Player player = thisGame.getTransferModel().getPlayers().get(playerIndex);
		
		int tradeRatio = getTradeRatio(playerIndex, tradeResource, thisGame);
	
		if(supposedRatio != tradeRatio) {
			return false;
		}
		
		return player.getResources().hasResource(tradeResource, tradeRatio) &&
				thisGame.getTransferModel().getBank().hasResource(desiredResource, 1);
	}
	
	public int getTradeRatio(int playerIndex, ResourceType tradeResource, ServerModel thisGame) {
		//TODO
		
		int ratio = 4;
		
		for (Port port : thisGame.getTransferModel().getMap().getPorts()) {
			
			int x = port.getLocation().getX();
			int y = port.getLocation().getY();
			
			EdgeDirection direction = port.getDirection();
			
			EdgeLocation relativeEdge = (new EdgeLocation(x, y, direction)).getNormalizedLocation();
			
			for (VertexLocation vertex : thisGame.getNearbyVertices(relativeEdge)) {
				
				if(thisGame.getVertices().get(vertex.getNormalizedLocation()).hasMunicipality() == true) {
					if(thisGame.getVertices().get(vertex.getNormalizedLocation()).ownsMunicipality(playerIndex) == true) {
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

	public boolean canEndTurn(int playerIndex, int gameID) {
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
		}
		
		return true;
	}
}
