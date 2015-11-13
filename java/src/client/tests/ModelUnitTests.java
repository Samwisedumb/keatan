//package client.tests;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import javax.annotation.Resource;
//
//import model.HexLocation;
//import model.Model;
//import model.ModelFacade;
//import model.ResourceList;
//import model.TradeOffer;
//import model.VertexLocation;
//
//import org.junit.Test;
//
//import shared.definitions.VertexDirection;
//
//public class ModelUnitTests {
//	
//	@Test
//	public void testGetAdjacentVertices() {
//		ArrayList<String> names = new ArrayList<String>();
//		names.add("Michael");
//		names.add("Stephen");
//		names.add("Josh");
//		names.add("Will");
//		Model model = new Model(false, false, false, false, names);
//		
//		int twos = 0;
//		
//		for (VertexValue vertex : model.getMap().getVertices().values()) {
//			VertexLocation location = vertex.getLocation();
//			List<VertexValue> adjacents = model.getMap().getAdjacentVertices(location);
//			
//			if (adjacents.size() == 2) {
//				twos++;
//			}
//			else {
//				assertEquals(adjacents.size() == 3, true);
//			}
//		}
//		assertEquals(twos, 18);
//	}
//	
//	@Test
//	public void testCanProduceResource() {
//		ArrayList<String> names = new ArrayList<String>();
//		names.add("Michael");
//		names.add("Stephen");
//		names.add("Josh");
//		names.add("Will");
//		Model model = new Model(false, false, false, false, names);
//		ModelFacade facade = ModelFacade.createInstance(false, false, false, false, names);
//		facade.updateModel(model);
//		
//		Set<HexLocation> hexLocations = model.getMap().getHexes().keySet();
//		ArrayList<VertexDirection> vertexDirections = new ArrayList<VertexDirection>();
//		vertexDirections.add(VertexDirection.East);
//		vertexDirections.add(VertexDirection.NorthEast);
//		vertexDirections.add(VertexDirection.NorthWest);
//		vertexDirections.add(VertexDirection.West);
//		vertexDirections.add(VertexDirection.SouthWest);
//		vertexDirections.add(VertexDirection.SouthEast);
//
//		HexLocation twoTwo = new HexLocation(2, 2);
//		HexLocation twoOne = new HexLocation(2, 1);
//		HexLocation oneTwo = new HexLocation(1, 2);
//		HexLocation oneOne = new HexLocation(1, 1);
//		HexLocation negTwoNegTwo = new HexLocation(-2, -2);
//		HexLocation nonAdjacentHexLocation;
//		for (HexLocation hexLocation : hexLocations) {
//			if (hexLocation.equals(twoTwo) || hexLocation.equals(twoOne) || hexLocation.equals(oneTwo) || hexLocation.equals(oneOne)) {
//				nonAdjacentHexLocation = negTwoNegTwo;
//			}
//			else {
//				nonAdjacentHexLocation = twoTwo;
//			}
//			
//			for (VertexDirection vertexDirection : vertexDirections) {
//				model.getMap().setRobber(nonAdjacentHexLocation);
//				model.getPlayers().get(0).setResources(new ResourceList(0,0,0,0,0));
//				VertexValue vertex = model.getMap().getVertices().get(new VertexLocation(hexLocation,vertexDirection).getNormalizedLocation());
//
//				model.getMap().setRobber(nonAdjacentHexLocation);
//				assertEquals(facade.canProduceResource(hexLocation),false); // no municipality
//				vertex.setSettlement(new Settlement(0));
//				assertEquals(facade.canProduceResource(hexLocation),true); // should work
//				model.getMap().setRobber(hexLocation);
//				assertEquals(facade.canProduceResource(hexLocation),true); // robber should stop production
//				assertEquals(facade.canProduceResource(nonAdjacentHexLocation),false); // no municipality
//				vertex.setSettlement(null);
//				assertEquals(facade.canProduceResource(hexLocation),false); // no municipality and robber
//				
//
//				model.getMap().setRobber(nonAdjacentHexLocation);
//				assertEquals(facade.canProduceResource(hexLocation),false); // no municipality
//				vertex.setCity(new City(0));
//				assertEquals(facade.canProduceResource(hexLocation),true); // should work
//				model.getMap().setRobber(hexLocation);
//				assertEquals(facade.canProduceResource(hexLocation),true); // robber should stop production
//				assertEquals(facade.canProduceResource(nonAdjacentHexLocation),false); // no municipality
//				vertex.setCity(null);
//				assertEquals(facade.canProduceResource(hexLocation),false); // no municipality and robber
//			}
//		}
//	}
//	
//	@Test
//	public void testCanReceiveResource() {
//		ArrayList<String> names = new ArrayList<String>();
//		names.add("Michael");
//		names.add("Stephen");
//		names.add("Josh");
//		names.add("Will");
//		Model model = new Model(false, false, false, false, names);
//		ModelFacade facade = ModelFacade.createInstance(false, false, false, false, names);
//		facade.updateModel(model);
//		
//		model.setBank(new ResourceList(19,19,19,19,19));
//		assertEquals(facade.canReceiveResource(5, Resource.BRICK),true); // bank has plenty to give
//		
//		model.setBank(new ResourceList(0,0,0,0,0));
//		assertEquals(facade.canReceiveResource(5, Resource.BRICK),false);// bank can give nothing
//	}
//	
//	@Test
//	public void testCanDomesticTrade() {
//		ArrayList<String> names = new ArrayList<String>();
//		names.add("Michael");
//		names.add("Stephen");
//		names.add("Josh");
//		names.add("Will");
//		Model model = new Model(false, false, false, false, names);
//		ModelFacade facade = ModelFacade.createInstance(false, false, false, false, names);
//		facade.updateModel(model);
//		
//		model.getTurnTracker().setPlayerTurn(0);
//		model.getPlayers().get(0).setResources(new ResourceList(5,5,5,5,5));
//		model.getPlayers().get(1).setResources(new ResourceList(5,5,5,5,5));
//		model.getPlayers().get(2).setResources(new ResourceList(5,5,5,5,5));
//		model.getPlayers().get(3).setResources(new ResourceList(0,0,0,0,0));
//		
//		ResourceList oneOfAll = new ResourceList(1,1,1,1,1);
//		ResourceList nothing = new ResourceList(0,0,0,0,0);
//		ResourceList twoSheep = new ResourceList(0,0,2,0,0);
//		ResourceList threeOre = new ResourceList(0,3,0,0,0);
//
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,2,oneOfAll,oneOfAll)),true); // should work
//		assertEquals(facade.canDomesticTrade(new TradeOffer(1,2,oneOfAll,oneOfAll)),false); // not player 1's turn
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,0,oneOfAll,oneOfAll)),true); // cannot trade with self
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,3,oneOfAll,oneOfAll)),false); // player 3 has doesn't have enough cards
//		model.getPlayers().get(0).setResources(new ResourceList(0,0,0,0,0));
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,2,oneOfAll,oneOfAll)),false); // player 1 doesn't have enough cards
//		
//		model.getPlayers().get(0).setResources(new ResourceList(0,3,0,0,0));
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,2,threeOre,twoSheep)),true); // should work
//		model.getPlayers().get(2).setResources(new ResourceList(0,0,2,0,0));
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,2,threeOre,twoSheep)),true); // should work
//		model.getPlayers().get(0).setResources(new ResourceList(5,0,5,5,5));
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,2,threeOre,twoSheep)),false); // not enough ore
//		model.getPlayers().get(2).setResources(new ResourceList(5,5,0,5,5));
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,2,threeOre,twoSheep)),false); // not enough ore, not enough sheep
//		model.getPlayers().get(0).setResources(new ResourceList(5,5,5,5,5));
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,2,threeOre,twoSheep)),false); // not enough sheep
//
//		model.getPlayers().get(0).setResources(new ResourceList(5,5,5,5,5));
//		model.getPlayers().get(1).setResources(new ResourceList(5,5,5,5,5));
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,1,nothing,nothing)),true); // should work
//
//		model.getPlayers().get(0).setResources(new ResourceList(0,0,0,0,0));
//		model.getPlayers().get(1).setResources(new ResourceList(0,0,0,0,0));
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,1,nothing,nothing)),true); // should work
//
//		model.getPlayers().get(1).setResources(new ResourceList(0,0,0,0,0));
//		model.getPlayers().get(0).setResources(new ResourceList(5,5,5,5,5));
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,1,nothing,nothing)),true); // should work
//		
//		model.getPlayers().get(0).setResources(new ResourceList(0,0,0,0,0));
//		model.getPlayers().get(1).setResources(new ResourceList(5,5,5,5,5));
//		assertEquals(facade.canDomesticTrade(new TradeOffer(0,1,nothing,nothing)),true); // should work
//	}
//	
//	@Test
//	public void testCanMaritimeTrade() {
//		ArrayList<String> names = new ArrayList<String>();
//		names.add("Michael");
//		names.add("Stephen");
//		names.add("Josh");
//		names.add("Will");
//		Model model = new Model(false, false, false, false, names);
//		ModelFacade facade = ModelFacade.createInstance(false, false, false, false, names);
//		facade.updateModel(model);
//		
//		assertEquals("Stephen has made ports have vertices", "");
//		
//	}
//	
//	@Test
//	public void testCanBuildRoad() {
//		ArrayList<String> names = new ArrayList<String>();
//		names.add("Michael");
//		names.add("Stephen");
//		names.add("Josh");
//		names.add("Will");
//		Model model = new Model(false, false, false, false, names);
//		ModelFacade facade = ModelFacade.createInstance(false, false, false, false, names);
//		facade.updateModel(model);
//		
//		assertEquals("Not build yet", "");
//	}
//	
//	@Test
//	public void testCanBuildSettlement() {
//		ArrayList<String> names = new ArrayList<String>();
//		names.add("Michael");
//		names.add("Stephen");
//		names.add("Josh");
//		names.add("Will");
//		Model model = new Model(false, false, false, false, names);
//		ModelFacade facade = ModelFacade.createInstance(false, false, false, false, names);
//		facade.updateModel(model);
//		
//		Set<HexLocation> hexLocations = model.getMap().getHexes().keySet();
//		ArrayList<VertexDirection> vertexDirections = new ArrayList<VertexDirection>();
//		vertexDirections.add(VertexDirection.East);
//		vertexDirections.add(VertexDirection.NorthEast);
//		vertexDirections.add(VertexDirection.NorthWest);
//		vertexDirections.add(VertexDirection.West);
//		vertexDirections.add(VertexDirection.SouthWest);
//		vertexDirections.add(VertexDirection.SouthEast);
//		
//		for (HexLocation hexLocation : hexLocations) { // for every hex in map			
//			for (int i = 0; i < vertexDirections.size(); i++) { // for every place you can put a settlement on hex
//				VertexDirection direction = vertexDirections.get(i);
//				
//				List<VertexDirection> adjDirections = new ArrayList<VertexDirection>();
//				
//				if (i == 5) {
//					adjDirections.add(vertexDirections.get(0));
//					adjDirections.add(vertexDirections.get(4));
//				}
//				else if (i == 0) {
//					adjDirections.add(vertexDirections.get(1));
//					adjDirections.add(vertexDirections.get(5));
//				}
//				else {
//					adjDirections.add(vertexDirections.get(i + 1));
//					adjDirections.add(vertexDirections.get(i - 1));
//				}
//
//				VertexValue vertex = model.getMap().getVertices().get(new VertexLocation(hexLocation, direction).getNormalizedLocation());
//				
//				model.getPlayers().get(0).setResources(new ResourceList(5,5,5,5,5));
//				
//				
//				for (VertexDirection nonAdjDirection : vertexDirections) { // for every non adjacent direction - including the direction in question
//					if (nonAdjDirection != direction
//							&& nonAdjDirection != adjDirections.get(0)
//							&& nonAdjDirection != adjDirections.get(1)) {
//						
//					}
//				}
//				
//				model.getPlayers().get(0).setResources(new ResourceList(0,0,0,0,0));
//			}
//		}
//		
//		VertexValue vertex = model.getMap().getVertices().get(new VertexLocation(new HexLocation(0,0),VertexDirection.East).getNormalizedLocation());
//		
//		model.getPlayers().get(0).setUnplacedSettlements(1);
//		model.getPlayers().get(1).setUnplacedSettlements(1);
//		model.getPlayers().get(0).setResources(new ResourceList(0,0,0,0,0));
//		assertEquals(ModelFacade.getInstance().canBuildSettlement(0, vertex.getLocation()), false); // not enough resources
//		vertex.setSettlement(new Settlement(0));
//		assertEquals(ModelFacade.getInstance().canBuildSettlement(0, vertex.getLocation()), false); // not enough resources, already settlement
//		model.getPlayers().get(0).setResources(new ResourceList(5,5,5,5,5));
//		model.getPlayers().get(1).setResources(new ResourceList(5,5,5,5,5));
//		assertEquals(ModelFacade.getInstance().canBuildSettlement(0, vertex.getLocation()), false); // already settlement
//		assertEquals(ModelFacade.getInstance().canBuildSettlement(1, vertex.getLocation()), false); // already settlement - other player
//		vertex.setSettlement(null);
//		assertEquals(ModelFacade.getInstance().canBuildSettlement(0, vertex.getLocation()), true); // should be able to - EXCEPT WE DON'T have a road nearby so... but we could be in the set up phase
//		model.getPlayers().get(0).setResources(new ResourceList(1,0,1,1,1));
//		assertEquals(ModelFacade.getInstance().canBuildSettlement(0, vertex.getLocation()), true); // should be able to - barely enough
//		model.getPlayers().get(0).setResources(new ResourceList(0,0,1,1,1));
//		assertEquals(ModelFacade.getInstance().canBuildSettlement(0, vertex.getLocation()), false); // not enough brick
//		model.getPlayers().get(0).setResources(new ResourceList(1,0,0,1,1));
//		assertEquals(ModelFacade.getInstance().canBuildSettlement(0, vertex.getLocation()), false); // not enough sheep
//		model.getPlayers().get(0).setResources(new ResourceList(1,0,1,0,1));
//		assertEquals(ModelFacade.getInstance().canBuildSettlement(0, vertex.getLocation()), false); // not enough wheat
//		model.getPlayers().get(0).setResources(new ResourceList(1,0,1,1,0));
//		assertEquals(ModelFacade.getInstance().canBuildSettlement(0, vertex.getLocation()), false); // not enough wood
//
//		model.getPlayers().get(0).setResources(new ResourceList(1,0,1,1,1));
//		model.getPlayers().get(0).setUnplacedSettlements(0);
//		assertEquals(ModelFacade.getInstance().canBuildSettlement(0, vertex.getLocation()), false); // should be able to
//	}
//	
//	@Test
//	public void testCanBuildCity() {
//		ArrayList<String> names = new ArrayList<String>();
//		names.add("Michael");
//		names.add("Stephen");
//		names.add("Josh");
//		names.add("Will");
//		
//		Model model = new Model(false, false, false, false, names);
//		ModelFacade facade = ModelFacade.createInstance(false, false, false, false, names);
//		facade.updateModel(model);
//
//		Set<HexLocation> hexLocations = model.getMap().getHexes().keySet();
//		ArrayList<VertexDirection> vertexDirections = new ArrayList<VertexDirection>();
//		vertexDirections.add(VertexDirection.East);
//		vertexDirections.add(VertexDirection.NorthEast);
//		vertexDirections.add(VertexDirection.NorthWest);
//		vertexDirections.add(VertexDirection.West);
//		vertexDirections.add(VertexDirection.SouthWest);
//		vertexDirections.add(VertexDirection.SouthEast);
//
//		HexLocation twoTwo = new HexLocation(2, 2);
//		HexLocation twoOne = new HexLocation(2, 1);
//		HexLocation oneTwo = new HexLocation(1, 2);
//		HexLocation oneOne = new HexLocation(1, 1);
//		HexLocation negTwoNegTwo = new HexLocation(-2, -2);
//		HexLocation nonAdjacentHexLocation;
//		for (HexLocation hexLocation : hexLocations) {
//			if (hexLocation.equals(twoTwo) || hexLocation.equals(twoOne) || hexLocation.equals(oneTwo) || hexLocation.equals(oneOne)) {
//				nonAdjacentHexLocation = negTwoNegTwo;
//			}
//			else {
//				nonAdjacentHexLocation = twoTwo;
//			}
//			
//			for (VertexDirection vertexDirection : vertexDirections) {
//				VertexValue failTest = model.getMap().getVertices().get(new VertexLocation(hexLocation, vertexDirection).getNormalizedLocation());
//				VertexValue goodTest = model.getMap().getVertices().get(new VertexLocation(nonAdjacentHexLocation, vertexDirection).getNormalizedLocation());
//				goodTest.setSettlement(new Settlement(0));
//				
//				model.getPlayers().get(0).setUnplacedCities(1);
//				model.getPlayers().get(0).setResources(new ResourceList(5,5,5,5,5));
//				assertEquals(facade.canBuildCity(0, goodTest.getLocation()), true); // has everything
//				assertEquals(facade.canBuildCity(0, failTest.getLocation()),false); // no settlement
//				
//				model.getPlayers().get(0).setResources(new ResourceList(0,3,0,2,0));
//				assertEquals(facade.canBuildCity(0, goodTest.getLocation()), true); // just barely enough
//				model.getPlayers().get(0).setResources(new ResourceList(0,3,0,1,0));
//				assertEquals(facade.canBuildCity(0, goodTest.getLocation()), false); // not enough wheat
//				model.getPlayers().get(0).setResources(new ResourceList(0,2,0,2,0));
//				assertEquals(facade.canBuildCity(0, goodTest.getLocation()), false); // not enough ore
//				
//				model.getPlayers().get(0).setUnplacedCities(0);
//				assertEquals(facade.canBuildCity(0, goodTest.getLocation()),false); // no unplaced cities
//				assertEquals(facade.canBuildCity(0, failTest.getLocation()),false); // no settlement, no unplaced cities
//		
//				model.getPlayers().get(0).setUnplacedCities(1);
//				model.getPlayers().get(0).setResources(new ResourceList(0,0,0,0,0));
//				assertEquals(facade.canBuildCity(0, goodTest.getLocation()),false); // no resources
//				assertEquals(facade.canBuildCity(0, failTest.getLocation()),false); // no settlement, no resources
//				
//				goodTest.setSettlement(null);
//			}
//		}
//	}
//	
//	@Test
//	public void testCanBuyDevelopmentCard() {
//		ArrayList<String> names = new ArrayList<String>();
//		names.add("Michael");
//		names.add("Stephen");
//		names.add("Josh");
//		names.add("Will");
//		Model model = new Model(false, false, false, false, names);
//		ModelFacade facade = ModelFacade.createInstance(false, false, false, false, names);
//		facade.updateModel(model);
//		
//		assertEquals(facade.canBuyDevelopmentCard(0),false); // does not have enough resources
//
//		model.getTurnTracker().setPlayerTurn(1);
//		assertEquals(facade.canBuyDevelopmentCard(0),false); // is not his turn
//		
//		model.getPlayers().get(1).setResources(new ResourceList(5,5,5,5,5));
//		assertEquals(facade.canBuyDevelopmentCard(1), true); //should be able to
//		
//		model.getPlayers().get(1).setResources(new ResourceList(0,1,1,1,0));
//		assertEquals(facade.canBuyDevelopmentCard(1), true); // just enough
//		
//		model.getPlayers().get(1).setResources(new ResourceList(0,1,0,0,0));
//		assertEquals(facade.canBuyDevelopmentCard(1), false); // not enough
//		
//		model.getPlayers().get(1).setResources(new ResourceList(0,0,1,0,0));
//		assertEquals(facade.canBuyDevelopmentCard(1), false); // not enough
//		
//		model.getPlayers().get(1).setResources(new ResourceList(0,0,0,1,0));
//		assertEquals(facade.canBuyDevelopmentCard(1), false); // not enough
//
//		model.getPlayers().get(1).setResources(new ResourceList(0,1,1,0,0));
//		assertEquals(facade.canBuyDevelopmentCard(1), false); // not enough
//		
//		model.getPlayers().get(1).setResources(new ResourceList(0,1,0,1,0));
//		assertEquals(facade.canBuyDevelopmentCard(1), false); // not enough
//		
//		model.getPlayers().get(1).setResources(new ResourceList(0,0,1,1,0));
//		assertEquals(facade.canBuyDevelopmentCard(1), false); // not enough
//	}
//	
//	@Test
//	public void testCanLoseCardsFromDieRoll() {
//		ArrayList<String> names = new ArrayList<String>();
//		names.add("Michael");
//		names.add("Stephen");
//		names.add("Josh");
//		names.add("Will");
//		Model model = new Model(false, false, false, false, names);
//		ModelFacade facade = ModelFacade.createInstance(false, false, false, false, names);
//		facade.updateModel(model);
//		
//		assertEquals(facade.canLoseCardsFromDieRoll(0),false); // does not have cards
//		
//		model.getPlayers().get(0).setResources(new ResourceList(5,5,5,5,5));
//		model.getPlayers().get(1).setResources(new ResourceList(5,5,5,5,5));
//		
//		assertEquals(facade.canLoseCardsFromDieRoll(0),true); // has too many
//		assertEquals(facade.canLoseCardsFromDieRoll(1),true); // has too many
//	}
//	
//	@Test
//	public void testCanLoseCardsFromRobber() {
//		ArrayList<String> names = new ArrayList<String>();
//		names.add("Michael");
//		names.add("Stephen");
//		names.add("Josh");
//		names.add("Will");
//		Model model = new Model(false, false, false, false, names);
//		ModelFacade facade = ModelFacade.createInstance(false, false, false, false, names);
//		facade.updateModel(model);
//		
//		
//		Set<HexLocation> hexLocations = model.getMap().getHexes().keySet();
//		ArrayList<VertexDirection> vertexDirections = new ArrayList<VertexDirection>();
//		vertexDirections.add(VertexDirection.East);
//		vertexDirections.add(VertexDirection.NorthEast);
//		vertexDirections.add(VertexDirection.NorthWest);
//		vertexDirections.add(VertexDirection.West);
//		vertexDirections.add(VertexDirection.SouthWest);
//		vertexDirections.add(VertexDirection.SouthEast);
//
//		HexLocation twoTwo = new HexLocation(2, 2);
//		HexLocation twoOne = new HexLocation(2, 1);
//		HexLocation oneTwo = new HexLocation(1, 2);
//		HexLocation oneOne = new HexLocation(1, 1);
//		HexLocation negTwoNegTwo = new HexLocation(-2, -2);
//		HexLocation nonAdjacentHexLocation;
//		for (HexLocation hexLocation : hexLocations) {
//			if (hexLocation.equals(twoTwo) || hexLocation.equals(twoOne) || hexLocation.equals(oneTwo) || hexLocation.equals(oneOne)) {
//				nonAdjacentHexLocation = negTwoNegTwo;
//			}
//			else {
//				nonAdjacentHexLocation = twoTwo;
//			}
//			
//			for (VertexDirection vertexDirection : vertexDirections) {
//				model.getMap().setRobber(nonAdjacentHexLocation);
//				model.getPlayers().get(0).setResources(new ResourceList(0,0,0,0,0));
//				VertexValue vertex = model.getMap().getVertices().get(new VertexLocation(hexLocation,vertexDirection).getNormalizedLocation());
//	
//				vertex.setSettlement(new Settlement(0));
//	
//				model.getMap().setRobber(hexLocation);
//				assertEquals(facade.canLoseCardsFromRobber(0), false); // player has no resources to steal
//	
//				model.getPlayers().get(1).setResources(new ResourceList(2,2,2,2,2));
//				assertEquals(facade.canLoseCardsFromRobber(1), false); // player has no settlement adjacent to the robber's hex
//				
//				model.getPlayers().get(0).setResources(new ResourceList(2,2,2,2,2));
//				assertEquals(facade.canLoseCardsFromRobber(0), true); // should be able to
//		
//				model.getMap().setRobber(nonAdjacentHexLocation);
//				assertEquals(facade.canLoseCardsFromRobber(0), false); // robber moved to non adjacent hex
//	
//				vertex.setSettlement(null);
//				vertex.setCity(new City(0));
//	
//				model.getMap().setRobber(hexLocation);
//				model.getPlayers().get(0).setResources(new ResourceList(0,0,0,0,0));
//				assertEquals(facade.canLoseCardsFromRobber(0), false); // player has no resources to steal
//	
//				model.getPlayers().get(1).setResources(new ResourceList(2,2,2,2,2));
//				assertEquals(facade.canLoseCardsFromRobber(1), false); // player has no settlement adjacent to the robber's hex
//				
//				model.getPlayers().get(0).setResources(new ResourceList(2,2,2,2,2));
//				assertEquals(facade.canLoseCardsFromRobber(0), true); // should be able to
//		
//				model.getMap().setRobber(nonAdjacentHexLocation);
//				assertEquals(facade.canLoseCardsFromRobber(0), false); // robber no longer on hex
//				vertex.setCity(null);
//			}
//		}
//	}
//	
//	@Test
//	public void testCanWin() {
//		ArrayList<String> names = new ArrayList<String>();
//		names.add("Michael");
//		names.add("Stephen");
//		names.add("Josh");
//		names.add("Will");
//		Model model = new Model(false, false, false, false, names);
//		ModelFacade facade = ModelFacade.createInstance(false, false, false, false, names);
//		facade.updateModel(model);
//		
//		assertEquals(facade.canWin(0),false);
//		
//		model.getPlayers().get(0).setVictoryPoints(10);
//		assertEquals(facade.canWin(0), true);
//		
//		model.getTurnTracker().endPlayerTurn();
//		assertEquals(facade.canWin(0),false);
//	}
//	
//	
//	public static void main(String[] args) {
//
//		String[] testClasses = new String[] {
//				"clientSide.Phase1UnitTests"
//		};
//
//		org.junit.runner.JUnitCore.main(testClasses);
//	}
//
//}
