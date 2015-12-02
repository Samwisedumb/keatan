package client.map.states;

import shared.definitions.PieceType;
import client.base.IView;
import client.data.PlayerInfo;
import client.data.RobPlayerInfo;
import client.map.IMapController;
import client.map.IRobView;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.ModelFacade;
import client.model.Status;
import client.model.VertexLocation;

/**
 * An abstract class for a MapControllerState. This object defines methods that all states share
 * and defaults all others to do nothing
 * @author djoshuac
 *
 */
public abstract class MapControllerState implements IMapController {
	private IMapController controller;
	
	public MapControllerState(IMapController controller) {
		this.controller = controller;
	}
	
	abstract public boolean canPlaceRoad(EdgeLocation edgeLoc);
	
	abstract public boolean canPlaceSettlement(VertexLocation vertLoc);
	
	abstract public boolean canPlaceCity(VertexLocation vertLoc);
	
	abstract public boolean canPlaceRobber(HexLocation hexLoc);
	
	/**
	 * Does nothing unless overridden
	 */
	public void placeRoad(EdgeLocation edgeLoc) {
		
	}
	
	/**
	 * Does nothing unless overridden
	 */
	public void placeSettlement(VertexLocation vertLoc) {
		
	}
	
	/**
	 * Does nothing unless overridden
	 */
	public void placeCity(VertexLocation vertLoc) {
		
	}
	
	/**
	 * Does nothing unless overridden
	 */
	public void placeRobber(HexLocation hexLoc) {
		
	}
	
	/**
	 * Does nothing unless overridden
	 */
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		
	}
	
	/**
	 * Does nothing unless overridden
	 */
	public void cancelMove() {
		
	}

	/**
	 * Does nothing unless overridden
	 */
	public void playSoldierCard() {
		
	}
	
	/**
	 * Does nothing unless overridden
	 */
	public void playRoadBuildingCard() {
		
	}
	
	/**
	 * Does nothing unless overridden
	 */
	public void robPlayer(RobPlayerInfo victim) {
		
	}
	
	/**
	 * Queries the ModelFacade for the current state and changes the controller's state accordingly
	 */
	public final void update() {
		if (ModelFacade.isGameReadyToStart()) {
			controller.initFromModel();
			System.out.println("Map inited");
			
			PlayerInfo user = ModelFacade.getUserPlayerInfo();
			Status gameStatus = ModelFacade.whatStateMightItBe();
			
			if (ModelFacade.whoseTurnIsItAnyway() != user.getIndex()) {
				if (gameStatus == Status.FirstRound || gameStatus == Status.SecondRound) {
					System.out.println("I'm in double wait");
					controller.setState(new MapControllerDoubleWaitState(controller));
				}
				else {
					controller.setState(new MapControllerNotTurnState(controller));
				}
			}
			else {
				switch (gameStatus) {
				case Rolling:
					controller.setState(new MapControllerRollingDiceState(controller));
					break;
				case Discarding:
					System.err.println("Discarding state is not made yet");
					break;
				case FirstRound:
					System.out.println("I'm in first round");
					controller.setState(new MapControllerInitializeState(controller));
					break;
				case Playing:
					controller.setState(new MapControllerBuildTradeState(controller));
					break;
				case Robbing:
					controller.setState(new MapControllerThieveryState(controller));
					break;
				case SecondRound:
					controller.setState(new MapControllerInitializeState(controller));
					break;
				default:
					controller.setState(new MapControllerNotTurnState(controller));
					break;
				}
			}
		}
		else {
			controller.setState(new MapControllerWaitingToStartState(controller));
		}
	}
	
	@Override
	public final IView getView() {
		return controller.getView();
	}

	@Override
	public final void initFromModel() {
		controller.initFromModel();
	}

	@Override
	public final void setState(MapControllerState state) {
		controller.setState(state);
	}
	
	@Override
	public final IRobView getRobView() {
		return controller.getRobView();
	}
}
