package server.facades;

import java.util.ArrayList;
import java.util.List;

import server.model.ServerModel;
import shared.exceptions.ServerException;
import shared.transferClasses.AcceptTrade;
import shared.transferClasses.BuildCity;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;
import shared.transferClasses.BuyDevCard;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
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
import client.model.ModelFacade;
import client.model.Player;
import client.model.ResourceList;
import client.model.VertexLocation;

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
	public void buildCity(int gameID, BuildCity build) {
		// TODO Auto-generated method stub
		if(build.getFree() == false) {
			games.get(gameID).payForCity(build.getPlayerIndex());
		}
		
		games.get(gameID).placeCity(build.getSpotOne(), build.getPlayerIndex());
	}

	@Override
	public void buildRoad(int gameID, BuildRoad build) throws ServerException {
		// TODO Auto-generated method stub
		if(canBuildRoad(build.getPlayerIndex(), build.getRoadLocation(), gameID) == false) {
			throw new ServerException("Can't put a road there, buddy");
		}
		
		if(build.getFree() == false) {
			//games.get(gameID).payForRoad(build.getPlayerIndex());
			ServerData.getInstance().getGameModel(gameID).payForRoad(build.getPlayerIndex());
		}
		
		//games.get(gameID).placeRoad(build.getRoadLocation(), build.getPlayerIndex());
		ServerData.getInstance().getGameModel(gameID).placeRoad(build.getRoadLocation(), build.getPlayerIndex());
	}

	@Override
	public void buildSettlement(int gameID, BuildSettlement build) {
		// TODO Auto-generated method stub
		if(build.getFree() == false) {
			games.get(gameID).payForSettlement(build.getPlayerIndex());
		}
		
		games.get(gameID).placeSettlement(build.getSpotOne(), build.getPlayerIndex());
	}

	@Override
	public void buyDevCard(int gameID, BuyDevCard buy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void discardCards(int gameID, DiscardCards discard) {
		// TODO Auto-generated method stub
		
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
	public void robPlayer(int gameID, RobPlayer robbery) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollNumber(int gameID, RollNumber roll) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendChat(int gameID, SendChat chat) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void soldier(int gameID, Soldier soldier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void yearOfPlenty(int gameID, YearOfPlenty plenty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CreateGameResponse createGame(CreateGameRequest createGame) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean canBuildRoad(int playerIndex, EdgeLocation edgeLoc, int gameID) {
		// TODO Auto-generated method stub
		
		ServerModel thisGame = ServerData.getInstance().getGameModel(gameID);
		
		if(playerIndex != thisGame.getTransferModel().getTurnTracker().getCurrentPlayer()) {
			return false;
		}
		
		if(thisGame.getEdges().get(edgeLoc.getNormalizedLocation()).hasRoad() == true) {
			return false;
		}
		
		ResourceList rList = thisGame.getTransferModel().getPlayers().get(playerIndex).getResources();
		
		if(rList.getBrick() == 0 || rList.getWood() == 0) {
			return false;
		}
		
		List<VertexLocation> points = thisGame.getNearbyVertices(edgeLoc);
		
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
				if(thisGame.getEdges().get(nearbyEdge.getNormalizedLocation()).getRoad().getOwner() == playerIndex) {
					return true;
				}
			}
		}
		
		return false;
	}
}
