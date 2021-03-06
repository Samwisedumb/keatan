package client.points;

import client.base.Controller;
import client.base.MasterController;
import client.model.ModelFacade;
import client.model.Player;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {
	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		super(view);
		
		setFinishedView(finishedView);
		
		ModelFacade.addObserver(this);
		
		getPointsView().setPoints(0);
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	@Override
	public void update() {
		if (MasterController.getSingleton().hasGameBegun()) {
			getPointsView().setPoints(ModelFacade.getUserPlayer().getVictoryPoints());
			
			int winnerIndex = ModelFacade.getWinner();
			if (winnerIndex == ModelFacade.whoseTurnIsItAnyway()) {
				Player winner = ModelFacade.getPlayerByIndex(winnerIndex);
				Player user = ModelFacade.getUserPlayer();
				if (winnerIndex == user.getIndex()) {
					getFinishedView().setWinner(user.getName(), true);
				}
				else {
					getFinishedView().setWinner(winner.getName(), false);
				}
			}
		}
		else {
			getPointsView().setPoints(0);
		}
	}
}

