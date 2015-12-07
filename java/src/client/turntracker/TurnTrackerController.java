package client.turntracker;

import java.util.List;

import shared.definitions.CatanColor;
import client.base.Controller;
import client.base.MasterController;
import client.data.PlayerInfo;
import client.model.ModelFacade;
import client.model.Player;


/**
 * Implementation for the turn tracker controller.
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {
	private CatanColor shownColor;
	
	public TurnTrackerController(ITurnTrackerView view) {
		super(view);
		
		getView().setLocalPlayerColor(CatanColor.WHITE);
		
		ModelFacade.addObserver(this);
		
		playersAreDrawn = false;
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {

	}
	
	boolean playersAreDrawn;
	
	@Override
	public void update() {
		PlayerInfo user = ModelFacade.getUserPlayerInfo();
		if (user != null && user.getColor() != null && user.getColor() != shownColor) {
			getView().setLocalPlayerColor(user.getColor());
		}
		
		if (MasterController.getSingleton().hasGameBegun()) {
			List<Player> players = ModelFacade.getPlayers();
			
			if (!playersAreDrawn) {
				for (Player p : players) {
					getView().initializePlayer(p.getIndex(), p.getName(), p.getColor());
				}
				playersAreDrawn = false;
			}

			Player thierTurn = ModelFacade.getPlayerWhoseTurnItIs();
			Player largestArmy = ModelFacade.getPlayerWithLargestArmy();
			Player longestRoad = ModelFacade.getPlayerWithLongestRoad();
			
			for (Player p : players) {
				boolean isThierTurn = thierTurn != null && thierTurn.getID() == p.getID();
				boolean hasLargestArmy = largestArmy != null && largestArmy.getID() == p.getID();
				boolean hasLongestRoad = longestRoad != null && longestRoad.getID() == p.getID();
				
				getView().updatePlayer(p.getIndex(), p.getVictoryPoints(), isThierTurn, hasLargestArmy, hasLongestRoad);
			}
		}
	}
}

