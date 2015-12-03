package client.turntracker;

import shared.definitions.CatanColor;
import client.base.Controller;
import client.data.PlayerInfo;
import client.model.ModelFacade;


/**
 * Implementation for the turn tracker controller.
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {
	private CatanColor shownColor;
	
	public TurnTrackerController(ITurnTrackerView view) {
		super(view);
		
		getView().setLocalPlayerColor(CatanColor.WHITE);
		
		ModelFacade.addObserver(this);
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {

	}

	@Override
	public void update() {
		PlayerInfo user = ModelFacade.getUserPlayerInfo();
		if (user != null && user.getColor() != null && user.getColor() != shownColor) {
			getView().setLocalPlayerColor(user.getColor());
		}
	}
}

