package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.model.EdgeLocation;
import client.model.Status;
import client.model.VertexLocation;
import server.facades.ServerData;
import server.facades.ServerMovesFacade;
import shared.definitions.CatanColor;
import shared.definitions.EdgeDirection;
import shared.definitions.ResourceType;
import shared.definitions.VertexDirection;
import shared.exceptions.ServerException;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;
import shared.transferClasses.FinishTurn;
import shared.transferClasses.MaritimeTrade;
import shared.transferClasses.RollNumber;

public class ServerMovesFacadeTestCaseWheeWillyWilly {

	@Test
	public void test() {
		ServerData.getInstance().addPlayer(0, CatanColor.GREEN, "Wibbles", 27);
		
		ServerData.getInstance().addPlayer(0, CatanColor.BLUE, "Dibbles", 15);
		
		ServerData.getInstance().addPlayer(0, CatanColor.RED, "Fibbles", 74);
		
		ServerData.getInstance().addPlayer(0, CatanColor.PUCE, "Wasbo", 3);
		
		
		
		testFirstRound();
		
		ServerData.getInstance().getGameModel(0).getTransferModel().getTurnTracker().setStatus(Status.Rolling);
		
		RollNumber testRoll = new RollNumber(0, 7);
		
		
		try {
			ServerMovesFacade.getInstance().rollNumber(0, testRoll);
			
		} catch (ServerException e) {
			System.out.println("PBBBBTH!");
		}
		
		ServerData.getInstance().getGameModel(0).getTransferModel().getTurnTracker().setStatus(Status.Playing);
		
		ServerData.getInstance().getGameModel(0).getPlayer(0).addResource(ResourceType.SHEEP, 4);
		
		MaritimeTrade trade = new MaritimeTrade(0, 4, ResourceType.SHEEP, ResourceType.WOOD);
		
		try {
			ServerMovesFacade.getInstance().maritimeTrade(0, trade);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("PBBBTH!");
		}
		
		FinishTurn testEnd = new FinishTurn(0);
		
		try {
			ServerMovesFacade.getInstance().finishTurn(0, testEnd);
		} catch (ServerException e) {
			System.out.println("PBBBBTH!");
		}
	}
	
	public void testFirstRound() {
	
		BuildRoad testBuildRoad = new BuildRoad(0, new EdgeLocation(1, -1, EdgeDirection.SouthEast), true);
		BuildSettlement testBuild = new BuildSettlement(0, new VertexLocation(1, -1, VertexDirection.SouthEast), true);
		
				
		try {
			ServerMovesFacade.getInstance().buildRoad(0, testBuildRoad);
			ServerMovesFacade.getInstance().buildSettlement(0, testBuild);
		} catch (ServerException e) {
			System.out.println("PBBBBTH!");
		}
		
		
		
	}

}
