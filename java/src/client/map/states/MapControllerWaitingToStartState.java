package client.map.states;

import client.map.IMapController;
import client.model.EdgeLocation;
import client.model.HexLocation;
import client.model.VertexLocation;

/**
 * This state is for when the user has joined a game and is waiting for it to begin<br>
 * The user should be able to do nothing with the map when in this state
 * @author djoshuac
 */
public class MapControllerWaitingToStartState extends MapControllerState {
	
	public MapControllerWaitingToStartState(IMapController controller) {
		super(controller);
	}

	/**
	 * @return false
	 */
	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return false;
	}
	
	/**
	 * @return false
	 */
	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return false;
	}

	/**
	 * @return false
	 */
	@Override
	public boolean canPlaceCity(VertexLocation vertLoc) {
		return false;
	}

	/**
	 * @return false
	 */
	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		return false;
	}
	
}
