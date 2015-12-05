package server.facades;

import java.util.ArrayList;
import java.util.List;

import server.model.ServerModel;
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
import client.model.ResourceList;
import client.model.Status;
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
	public void acceptTrade(int gameID, AcceptTrade accept) {
		// TODO Auto-generated method stub
		
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
		if(canBuyDevCard(buy.getPlayerIndex(), gameID)) {
			throw new ServerException("Can't buy a dev card, homeslice");
		}
		
		ServerData.getInstance().getGameModel(gameID).buyDevCard(buy.getPlayerIndex());
	}

	@Override
	public void discardCards(int gameID, DiscardCards discard) throws ServerException {
		// TODO Auto-generated method stub
		if(canDiscard(discard.getPlayerIndex(), discard.getDiscardedCards(), gameID) == true) {
			throw new ServerException("Don't have to discard cards, my friend");
		}
		
		ServerData.getInstance().getGameModel(gameID).discardCards(discard.getPlayerIndex(), discard.getDiscardedCards());
	}

	@Override
	public void finishTurn(int gameID, FinishTurn end) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void maritimeTrade(int gameID, MaritimeTrade trade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void monopoly(int gameID, Monopoly monoploy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void monument(int gameID, Monument monument) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void offerTrade(int gameID, OfferTrade offer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void roadBuilding(int gameID, RoadBuilding roadBuild) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void robPlayer(int gameID, RobPlayer robbery) throws ServerException {
		// TODO Auto-generated method stub
		if(canRob(robbery.getPlayerIndex(), robbery.getVictimIndex(), robbery.getLocation(), gameID) == false) {
			throw new ServerException("Can't steal from anyone, charlie");
		}
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
		if(canPlaySoldier(soldier.getPlayerIndex(), soldier.getVictimIndex(), soldier.getLocation(), gameID)) {
			throw new ServerException("You can't play this right now, amigo");
		}
	}

	@Override
	public void yearOfPlenty(int gameID, YearOfPlenty plenty) {
		// TODO Auto-generated method stub
		
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
		
		return false;
	}
	
	public boolean canYearOfPlenty(int playerIndex, ResourceType resourceOne, ResourceType resourceTwo, int gameID) {
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getPlayerTurn()) {
			return false;
		}
		else if(thisGame.getTransferModel().getTurnTracker().getStatus() != Status.Playing) {
			return false;
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

	
}
