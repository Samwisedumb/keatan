package client.map.states;

import shared.definitions.PieceType;
import client.base.IView;
import client.base.MasterController;
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
		if (MasterController.getSingleton().hasGameBegun()) {
			controller.initFromModel();
			System.out.println("Map inited");
			
			PlayerInfo user = ModelFacade.getUserPlayerInfo();
			Status gameStatus = ModelFacade.whatStateMightItBe();
			
			if (ModelFacade.whoseTurnIsItAnyway() != user.getIndex()) {
				System.out.println("It is not my turn to do stuff");
				controller.setState(new MapControllerNotTurnState(controller));
			}
			else {
				switch (gameStatus) {
				case Rolling:
					System.out.println("It's my turn to roll");
					controller.setState(new MapControllerRollingDiceState(controller));
					break;
				case Discarding:
					System.err.println("Discarding state is not made yet");
					break;
				case FirstRound:
					System.out.println("I'm in first round (initial place state)");
					controller.setState(new MapControllerInitializeState(controller));
					break;
				case Playing:
					System.out.println("I'm in Build State");
					controller.setState(new MapControllerBuildTradeState(controller));
					break;
				case Robbing:
					System.out.println("I'm in Theivery State");
					controller.setState(new MapControllerThieveryState(controller));
					break;
				case SecondRound:
					System.out.println("I'm in second round (initial place state)");
					controller.setState(new MapControllerInitializeState(controller));
					break;
				default:
					System.err.println("Unrecognized game status: " + gameStatus.name());
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
