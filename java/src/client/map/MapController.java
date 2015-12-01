package client.map;

import com.sun.media.jfxmedia.logging.Logger;

import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.PortType;
import client.base.Controller;
import client.data.PlayerInfo;
import client.data.RobPlayerInfo;
import client.map.states.MapControllerBuildTradeState;
import client.map.states.MapControllerDoublePlaceState;
import client.map.states.MapControllerDoubleWaitState;
import client.map.states.MapControllerNotTurnState;
import client.map.states.MapControllerRollingDiceState;
import client.map.states.MapControllerSetupState;
import client.map.states.MapControllerState;
import client.map.states.MapControllerThieveryState;
import client.model.EdgeLocation;
import client.model.Hex;
import client.model.HexLocation;
import client.model.ModelFacade;
import client.model.Port;
import client.model.Road;
import client.model.Status;
import client.model.VertexLocation;
import client.model.VertexObject;

/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {

	private IRobView robView;

	private MapControllerState state;

	public MapController(IMapView view, IRobView robView) {
		super(view);

		setRobView(robView);

		// This might be wrong
		state = new MapControllerSetupState();

		ModelFacade.addObserver(this);
	}

	public void set_state(MapControllerState newState) {
		state = newState;
	}

	public IMapView getView() {

		return (IMapView) super.getView();
	}

	private IRobView getRobView() {
		return robView;
	}

	private void setRobView(IRobView robView) {
		this.robView = robView;
	}

	protected void initFromModel() {
		getView().addHex(new HexLocation(0,0), HexType.WOOD);
		getView().addHex(new HexLocation(1,1), HexType.DESERT);

		drawWater();
	}

	protected void drawWater() {
		getView().addHex(new HexLocation(0, 3), HexType.WATER);
		getView().addHex(new HexLocation(-1, 3), HexType.WATER);
		getView().addHex(new HexLocation(-2, 3), HexType.WATER);
		getView().addHex(new HexLocation(-3, 3), HexType.WATER);
		getView().addHex(new HexLocation(-3, 2), HexType.WATER);
		getView().addHex(new HexLocation(-3, 1), HexType.WATER);
		getView().addHex(new HexLocation(-3, 0), HexType.WATER);
		getView().addHex(new HexLocation(-2, -1), HexType.WATER);
		getView().addHex(new HexLocation(-1, -2), HexType.WATER);
		getView().addHex(new HexLocation(0, -3), HexType.WATER);
		getView().addHex(new HexLocation(1, -3), HexType.WATER);
		getView().addHex(new HexLocation(2, -3), HexType.WATER);
		getView().addHex(new HexLocation(3, -3), HexType.WATER);
		getView().addHex(new HexLocation(3, -2), HexType.WATER);
		getView().addHex(new HexLocation(3, -1), HexType.WATER);
		getView().addHex(new HexLocation(3, 0), HexType.WATER);
		getView().addHex(new HexLocation(2, 1), HexType.WATER);
		getView().addHex(new HexLocation(1, 2), HexType.WATER);
	}
	
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return state.canPlaceRoad(edgeLoc);
		// return true;
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return state.canPlaceSettlement(vertLoc);
		// return true;
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return state.canPlaceCity(vertLoc);
		// return true;
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		return state.canPlaceRobber(hexLoc);
		// return true;
	}

	public void placeRoad(EdgeLocation edgeLoc) {

		// getView().placeRoad(edgeLoc, CatanColor.ORANGE);
		state.placeRoad(edgeLoc);
	}

	public void placeSettlement(VertexLocation vertLoc) {

		// getView().placeSettlement(vertLoc, CatanColor.ORANGE);
		state.placeSettlement(vertLoc);
	}

	public void placeCity(VertexLocation vertLoc) {

		// getView().placeCity(vertLoc, CatanColor.ORANGE);
		state.placeCity(vertLoc);
	}

	public void placeRobber(HexLocation hexLoc) {

		getView().placeRobber(hexLoc);

		getRobView().showModal();

		state.placeRobber(hexLoc);
	}

	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		getView().startDrop(pieceType, CatanColor.ORANGE, true);

		state.startMove(pieceType, false, false);
	}

	public void cancelMove() {
		state.cancelMove();
	}

	public void playSoldierCard() {
		state.playSoldierCard();
	}

	public void playRoadBuildingCard() {
		state.playRoadBuildingCard();
	}

	public void robPlayer(RobPlayerInfo victim) {
		state.robPlayer(victim);
	}

	boolean joe = false;
	@Override
	public void update() {
		if (ModelFacade.isGameReadyToStart() && !joe) {
			initFromModel();
			System.out.println("Map inited");
			getView().startDrop(PieceType.ROAD, CatanColor.BLUE, true);
			System.out.println("update() -> Dropped");
			joe = true;
//			
//			
//			if (ModelFacade.whoseTurnIsItAnyway() != ModelFacade.getUserPlayerInfo().getIndex()) {
//				if (ModelFacade.whatStateMightItBe() == Status.FirstRound
//						|| ModelFacade.whatStateMightItBe() == Status.SecondRound) {
//					state = new MapControllerDoubleWaitState();
//				}
//				else {
//					state = new MapControllerNotTurnState();
//				}
//			}
//			else {
//				switch (ModelFacade.whatStateMightItBe()) {
//				case Rolling:
//					state = new MapControllerRollingDiceState();
//					break;
//				case Discarding:
//					break;
//				case FirstRound:
//					state = new MapControllerDoublePlaceState();
//					startMove(PieceType.ROAD, true, true);
//					break;
//				case Playing:
//					state = new MapControllerBuildTradeState();
//					break;
//				case Robbing:
//					state = new MapControllerThieveryState();
//					break;
//				case SecondRound:
//					state = new MapControllerDoublePlaceState();
//					break;
//				default:
//					state = new MapControllerNotTurnState();
//					break;
//				}
			//}
		}
	}

}
