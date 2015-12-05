package client.map;

import shared.definitions.HexType;
import shared.definitions.PieceType;
import client.base.Controller;
import client.data.RobPlayerInfo;
import client.map.states.MapControllerState;
import client.map.states.MapControllerWaitingToStartState;
import client.model.City;
import client.model.EdgeLocation;
import client.model.Hex;
import client.model.HexLocation;
import client.model.ModelFacade;
import client.model.Port;
import client.model.Road;
import client.model.Settlement;
import client.model.VertexLocation;

/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
	
	private IRobView robView;
	private IMapView mapView;
	
	private MapControllerState state;

	public MapController(IMapView view, IRobView robView) {
		super(view);
		this.robView = robView;
		this.mapView = view;		
		
		state = new MapControllerWaitingToStartState(this);
		
		ModelFacade.addObserver(this);
		
		mapIsDrawn = false;
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

	private boolean mapIsDrawn;
	
	/**
	 * Initializes the map from the model
	 */
	@Override
	public void initFromModel() {
		if (!mapIsDrawn) {
			for (Hex hex : ModelFacade.getHexes()) {
				getView().addHex(hex.getGuiLocation(), hex.getType());
				
				if (hex.getType() != HexType.DESERT) {
					getView().addNumber(hex.getGuiLocation(), hex.getChitNumber());
				}
			}
			drawWater();
		}
		
		getView().placeRobber(ModelFacade.findRobber().convertToGui());
		getView().placeRobber(new HexLocation(0,0));
		
		for (Road r : ModelFacade.getRoads()) {
			EdgeLocation location = r.getLocation().convertToGui();
			getView().placeRoad(location, ModelFacade.getPlayer(r.getOwnerIndex()).getColor());
		}
		
		for (City c : ModelFacade.getCities()) {
			VertexLocation location = c.getLocation().convertToGui();
			getView().placeSettlement(location, ModelFacade.getPlayer(c.getOwnerIndex()).getColor());
		}
			
		for (Settlement s : ModelFacade.getSettlements()) {
			VertexLocation location = s.getLocation().convertToGui();
			getView().placeSettlement(location, ModelFacade.getPlayer(s.getOwnerIndex()).getColor());
		}
		
		for (Port p : ModelFacade.getPorts()) {
			EdgeLocation location = new EdgeLocation(p.getLocation().getX(), p.getLocation().getY(), p.getDirection()).convertToGui();
			getView().addPort(location, p.getResource());
		}
		
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
		return state.canPlaceRoad(edgeLoc.convertFromGui());
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return state.canPlaceSettlement(vertLoc.convertFromGui());
	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc) {
		return state.canPlaceCity(vertLoc.convertFromGui());
	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		return state.canPlaceRobber(hexLoc.convertFromGui());
	}

	@Override
	public final void placeRoad(EdgeLocation edgeLoc) {
		state.placeRoad(edgeLoc.convertFromGui());
	}

	@Override
	public void placeSettlement(VertexLocation vertLoc) {
		state.placeSettlement(vertLoc.convertFromGui());
	}

	@Override
	public void placeCity(VertexLocation vertLoc) {
		state.placeCity(vertLoc.convertFromGui());
	}

	@Override
	public void placeRobber(HexLocation hexLoc) {
		state.placeRobber(hexLoc.convertFromGui());
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
	
	@Override
	public IMapView getMapView() {
		return mapView;
	}

	@Override
	public MapControllerState getState() {
		return state;
	}
}



