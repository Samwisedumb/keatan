package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.model.EdgeLocation;
import client.model.VertexLocation;
import server.facades.ServerData;
import server.facades.ServerMovesFacade;
import shared.definitions.CatanColor;
import shared.definitions.EdgeDirection;
import shared.definitions.VertexDirection;
import shared.exceptions.ServerException;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;

public class ServerMovesFacadeTestCaseWheeWillyWilly {

	@Test
	public void test() {
		ServerData.getInstance().addPlayer(0, CatanColor.GREEN, "Wibbles", 27);
		
		BuildRoad testBuildRoad = new BuildRoad(0, new EdgeLocation(1, -1, EdgeDirection.SouthEast), true);
		BuildSettlement testBuild = new BuildSettlement(0, new VertexLocation(1, -1, VertexDirection.SouthEast), true);
		

				
		try {
			ServerMovesFacade.getInstance().buildRoad(0, testBuildRoad);
			ServerMovesFacade.getInstance().buildSettlement(0, testBuild);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			System.out.println("PBBBBTH!");
		}
		
	}

}
