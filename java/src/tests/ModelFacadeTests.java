package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import shared.definitions.CatanColor;
import shared.definitions.EdgeDirection;
import shared.definitions.ResourceType;
import shared.definitions.VertexDirection;
import shared.exceptions.ServerException;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.JoinGameRequest;
import shared.transferClasses.UserCredentials;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.ModelFacade;
import client.model.Player;
import client.model.ResourceList;
import client.model.Road;
import client.model.TradeOffer;
import client.model.TransferModel;
import client.model.VertexLocation;
import client.model.VertexObject;
import client.server.ClientServer;
import client.server.ServerProxy;

public class ModelFacadeTests {

	@Before
	public void setup() {
		UserCredentials fu = new UserCredentials("Fu_", "Wind_");
		UserCredentials rin = new UserCredentials("Rin", "Forest");
		UserCredentials ka = new UserCredentials("Ka_", "Fire_");
		UserCredentials zan = new UserCredentials("Zan", "Mountain");
		
		ServerProxy.initialize(new ClientServer("localhost", "8081"));
		
		try {
			ServerProxy.register(fu);
			ServerProxy.register(rin);
			ServerProxy.register(ka);
			ServerProxy.register(zan);
		}	catch (ServerException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
		}
		
		try {

			
			ServerProxy.login(fu);
			CreateGameRequest brickRequest = new CreateGameRequest(false, false, false, "Kagemusha");
			CreateGameResponse brickResponse = ServerProxy.createGame(brickRequest);

			ServerProxy.joinGame(new JoinGameRequest(brickResponse.getId(), CatanColor.BLUE));
			
			ServerProxy.login(rin);
			ServerProxy.joinGame(new JoinGameRequest(brickResponse.getId(), CatanColor.GREEN));
			
			ServerProxy.login(ka);
			ServerProxy.joinGame(new JoinGameRequest(brickResponse.getId(), CatanColor.RED));
			
			ServerProxy.login(zan);
			ServerProxy.joinGame(new JoinGameRequest(brickResponse.getId(), CatanColor.PURPLE));

			ModelFacade.forceUpdateModel(ServerProxy.getModel(-1));
			
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCanProduceResource() {
		TransferModel tModel = null;
		try {
			tModel = ServerProxy.getModel(-1);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Give player 0 a city to the east, player 1 a city to the NW, and player 2 a settlement to the SW
		VertexObject city1 = new VertexObject(0, new VertexLocation(0,0, VertexDirection.East));
		tModel.getMap().addCity(city1);
		VertexObject settlement = new VertexObject(1, new VertexLocation(0,0, VertexDirection.NorthWest));
		tModel.getMap().addSettlement(settlement);
		VertexObject city2 = new VertexObject(1, new VertexLocation(0,0, VertexDirection.East));
		tModel.getMap().addCity(city2);
		
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canProduceResource(0, new HexLocation(0,0)),true);
		assertEquals(ModelFacade.canProduceResource(1, new HexLocation(0,0)),true);
		assertEquals(ModelFacade.canProduceResource(2, new HexLocation(0,0)),false);
	}

	@Test
	public void testCanReceiveResource() {
		TransferModel tModel = null;
		try {
			tModel = ServerProxy.getModel(-1);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tModel.getBank().setBrick(5);
		
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canReceiveResource(5,ResourceType.BRICK),true);
		assertEquals(ModelFacade.canReceiveResource(6,ResourceType.BRICK),false);
	}

	@Test
	public void testCanBuildSettlement() {
		TransferModel tModel = null;
		try {
			tModel = ServerProxy.getModel(-1);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player player = tModel.getPlayers().get(0);
		//Set resources high enough, and build in empty spot
		player.setResources(new ResourceList(1,1,1,1,1));
		tModel.getMap().addRoad(new Road(0, new EdgeLocation(-2,0,EdgeDirection.NorthEast)));
		tModel.getMap().addRoad(new Road(0, new EdgeLocation(-2,0,EdgeDirection.NorthWest)));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildSettlement(0, new VertexLocation(-2,0,VertexDirection.East)),true);
		
		//Test border cases for resources
		player.setResources(new ResourceList(1,1,1,0,1));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildSettlement(0, new VertexLocation(-2,0,VertexDirection.East)),false);
		player.setResources(new ResourceList(1,1,0,1,1));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildSettlement(0, new VertexLocation(-2,0,VertexDirection.East)),false);
		player.setResources(new ResourceList(1,1,1,1,0));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildSettlement(0, new VertexLocation(-2,0,VertexDirection.East)),false);
		player.setResources(new ResourceList(0,1,1,1,1));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildSettlement(0, new VertexLocation(-2,0,VertexDirection.East)),false);
		
		//Build in spot with previous settlement
		tModel.getMap().addCity(new VertexObject(0,new VertexLocation(-2,0,VertexDirection.East)));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildSettlement(0, new VertexLocation(-2,0,VertexDirection.East)),false);
		
		//Build too close to other settlement
		assertEquals(ModelFacade.canBuildSettlement(0, new VertexLocation(-2,0,VertexDirection.NorthEast)),false);
		
		//Build two spots away from other settlement
		player.setResources(new ResourceList(1,1,1,1,1));
		ModelFacade.updateModel(tModel);
		assertEquals(ModelFacade.canBuildSettlement(0, new VertexLocation(-2,0,VertexDirection.NorthWest)),true);
		
		//Build with not enough resources.
		player.setResources(new ResourceList(0,0,0,0,0));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildSettlement(0, new VertexLocation(-2,0,VertexDirection.West)),false);
		
	}

	@Test
	public void testCanBuildCity() {
		TransferModel tModel = null;
		try {
			tModel = ServerProxy.getModel(-1);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player player = tModel.getPlayers().get(0);
		//Set resources high enough, and build in empty spot
		player.setResources(new ResourceList(5,5,5,5,5));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildCity(0, new VertexLocation(0,0,VertexDirection.East)),false);
		
		//Build in spot with previous settlement
		tModel.getMap().addSettlement(new VertexObject(0,new VertexLocation(0,0,VertexDirection.East)));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildCity(0, new VertexLocation(0,0,VertexDirection.East)),true);
		
		//Test resource border cases
		player.setResources(new ResourceList(2,2,2,2,2));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildCity(0, new VertexLocation(0,0,VertexDirection.East)),false);
		player.getResources().setOre(3);
		player.getResources().setWheat(1);
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildCity(0, new VertexLocation(0,0,VertexDirection.East)),false);
				
		//Build on city
		tModel.getMap().addCity(new VertexObject(0,new VertexLocation(0,0,VertexDirection.West)));
		assertEquals(ModelFacade.canBuildCity(0, new VertexLocation(0,0,VertexDirection.West)),false);
		
		//Build with not enough resources.
		player.setResources(new ResourceList(0,0,0,0,0));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildCity(0, new VertexLocation(0,0,VertexDirection.East)),false);
		
	}

	@Test
	public void testCanBuildRoad() {
		TransferModel tModel = null;
		try {
			tModel = ServerProxy.getModel(-1);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player player = tModel.getPlayers().get(0);
		//Set resources high enough, and add test roads
		player.setResources(new ResourceList(1,1,1,1,1));
		tModel.getMap().addRoad(new Road(0, new EdgeLocation(-1,-1,EdgeDirection.North)));
		tModel.getMap().addRoad(new Road(1, new EdgeLocation(-1,-1,EdgeDirection.NorthWest)));
		ModelFacade.forceUpdateModel(tModel);
		
		assertEquals(ModelFacade.canBuildRoad(0, new EdgeLocation(-1,-1,EdgeDirection.NorthEast)),true);
		assertEquals(ModelFacade.canBuildRoad(0, new EdgeLocation(-1,-1,EdgeDirection.SouthWest)),false);
		assertEquals(ModelFacade.canBuildRoad(0, new EdgeLocation(-1,-1,EdgeDirection.North)),false);
		assertEquals(ModelFacade.canBuildRoad(0, new EdgeLocation(-1,-1,EdgeDirection.NorthWest)),false);
		
		//Set resources low
		player.setResources(new ResourceList(0,0,0,0,0));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuildRoad(0, new EdgeLocation(-1,-1,EdgeDirection.NorthEast)),false);
	}

	@Test
	public void testCanDomesticTrade() {
		TransferModel tModel = null;
		try {
			tModel = ServerProxy.getModel(-1);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player player = tModel.getPlayers().get(0);
		
		TradeOffer trade = new TradeOffer(0,0,new ResourceList(-1,1,0,0,0));
		player.setResources(new ResourceList(1,1,1,1,1));
		tModel.getPlayers().get(1).setResources(new ResourceList(1,1,1,1,1));
		tModel.getPlayers().get(2).setResources(new ResourceList(1,1,1,1,1));
		tModel.setVersion(tModel.getVersion()+1);
		ModelFacade.forceUpdateModel(tModel);
		//Can't trade with yourself
		//assertEquals(ModelFacade.canDomesticTrade(trade),false);
		//Good trade
		trade = new TradeOffer(0,1,new ResourceList(-1,1,0,0,0));
		tModel.setVersion(tModel.getVersion()+1);
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canDomesticTrade(trade),true);
		//Can't trade more than you have
		trade = new TradeOffer(0,1,new ResourceList(-2,1,0,0,0));
		tModel.setVersion(tModel.getVersion()+1);
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canDomesticTrade(trade),false);
		//Can't trade more than you have
		trade = new TradeOffer(0,1,new ResourceList(-1,2,0,0,0));
		tModel.setVersion(tModel.getVersion()+1);
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canDomesticTrade(trade),false);
		//Can't trade if it's not your turn
		trade = new TradeOffer(2,1,new ResourceList(-1,1,0,0,0));
		tModel.setVersion(tModel.getVersion()+1);
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canDomesticTrade(trade),false);
	}

	@Test
	public void testCanMaritimeTrade() {
		TransferModel tModel = null;
		try {
			tModel = ServerProxy.getModel(-1);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player player = tModel.getPlayers().get(0);
		//No port trading
		tModel.setBank(new ResourceList(1,1,1,1,1));
		player.setResources(new ResourceList(4,4,4,4,4));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canMaritimeTrade(0, ResourceType.BRICK, ResourceType.WOOD),true);
		player.setResources(new ResourceList(3,3,3,3,3));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canMaritimeTrade(0, ResourceType.BRICK, ResourceType.WOOD),false);
		tModel.setBank(new ResourceList(0,0,0,0,0));
		player.setResources(new ResourceList(4,4,4,4,4));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canMaritimeTrade(0, ResourceType.BRICK, ResourceType.WOOD),false);
		
		//tModel.getMap().addPort(new Port(ResourceType.BRICK, new HexLocation(0,2), EdgeDirection.North,0));
		//General ports aren't currently working
		HexLocation portLoc = tModel.getMap().getPorts().get(0).getLocation();
		tModel.getMap().addSettlement(new VertexObject(0,new VertexLocation(portLoc.getX(),portLoc.getY(),VertexDirection.NorthEast)));
		//Special port trading
		tModel.setBank(new ResourceList(1,1,1,1,1));
		player.setResources(new ResourceList(2,2,2,2,2));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canMaritimeTrade(0, ResourceType.BRICK, ResourceType.WOOD),true);
		player.setResources(new ResourceList(1,1,1,1,1));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canMaritimeTrade(0, ResourceType.BRICK, ResourceType.WOOD),false);
	}

	@Test
	public void testCanBuyDevelopmentCard() {
		TransferModel tModel = null;
		try {
			tModel = ServerProxy.getModel(-1);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player player = tModel.getPlayers().get(0);
		player.setResources(new ResourceList(1,1,1,1,1));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuyDevelopmentCard(0),true);
		tModel.getPlayers().get(0).getResources().setWheat(0);
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuyDevelopmentCard(0),false);
		player.setResources(new ResourceList(1,1,1,1,1));
		tModel.getDeck().setMonopoly(0);
		tModel.getDeck().setMonument(0);
		tModel.getDeck().setRoadBuilding(0);
		tModel.getDeck().setSoldier(0);
		tModel.getDeck().setYearOfPlenty(0);
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canBuyDevelopmentCard(0),false);
	}

	@Test
	public void testCanLoseCardsFromDieRoll() {
		TransferModel tModel = null;
		try {
			tModel = ServerProxy.getModel(-1);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player player = tModel.getPlayers().get(0);
		player.setResources(new ResourceList(2,2,2,1,1));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canLoseCardsFromDieRoll(0),true);
		player.setResources(new ResourceList(2,2,1,1,1));
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canLoseCardsFromDieRoll(0),false);
	}

	@Test
	public void testCanWin() {
		TransferModel tModel = null;
		try {
			tModel = ServerProxy.getModel(-1);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player player = tModel.getPlayers().get(0);
		player.setVictoryPoints(10);
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canWin(0),true);
		player.setVictoryPoints(9);
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canWin(0),false);
		tModel.getTurnTracker().endPlayerTurn();
		player.setVictoryPoints(10);
		ModelFacade.forceUpdateModel(tModel);
		assertEquals(ModelFacade.canWin(0),false);
	}
}
