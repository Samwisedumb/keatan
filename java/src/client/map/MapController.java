package client.map;

import shared.definitions.HexType;
import shared.definitions.PieceType;
import client.base.Controller;
import client.data.RobPlayerInfo;
import client.map.states.MapControllerState;
import client.map.states.MapControllerWaitingToStartState;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.ModelFacade;
import client.model.VertexLocation;

/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
	
	private IRobView robView;
	
	private MapControllerState state;

	public MapController(IMapView view, IRobView robView) {
		super(view);
		this.robView = robView;
		
		state = new MapControllerWaitingToStartState(this);
		
		ModelFacade.addObserver(this);
	}

	@Override
	public void setState(MapControllerState newState) {
		state = newState;
	}

	@Override
	public IMapView getView() {
		return (IMapView) super.getView();
	}

	@Override
	public IRobView getRobView() {
		return robView;
	}

	/**
	 * Initializes the map from the model
	 */
	@Override
	public void initFromModel() {
		getView().addHex(new HexLocation(0,0), HexType.WOOD);
		getView().addHex(new HexLocation(1,1), HexType.DESERT);

		drawWater();
		
		getView().paintMap();
	}

	/**
	 * Adds all the water hexes to the map
	 */
	private void drawWater() {
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
	
	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return state.canPlaceRoad(edgeLoc);
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return state.canPlaceSettlement(vertLoc);
	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc) {
		return state.canPlaceCity(vertLoc);
	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		return state.canPlaceRobber(hexLoc);
	}

	@Override
	public void placeRoad(EdgeLocation edgeLoc) {
		state.placeRoad(edgeLoc);
	}

	@Override
	public void placeSettlement(VertexLocation vertLoc) {
		state.placeSettlement(vertLoc);
	}

	@Override
	public void placeCity(VertexLocation vertLoc) {
		state.placeCity(vertLoc);
	}

	@Override
	public void placeRobber(HexLocation hexLoc) {
		state.placeRobber(hexLoc);
	}

	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		state.startMove(pieceType, isFree, allowDisconnected);
	}

	@Override
	public void cancelMove() {
		state.cancelMove();
	}

	@Override
	public void playSoldierCard() {
		state.playSoldierCard();
	}

	@Override
	public void playRoadBuildingCard() {
		state.playRoadBuildingCard();
	}

	@Override
	public void robPlayer(RobPlayerInfo victim) {
		state.robPlayer(victim);
	}
	
	@Override
	public void update() {
		state.update();
	}
}



