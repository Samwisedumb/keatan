package client.points;

import java.util.Observable;

import client.base.Controller;
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
		
		initFromModel();
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

	private void initFromModel() {
		/*
		getPointsView().setPoints(ModelFacade.getThisPlayer().getVictoryPoints());
		
		if(ModelFacade.getWinner() == ModelFacade.getPlayerInfo().getIndex()) {
			getFinishedView().setWinner(ModelFacade.getThisPlayer().getName(), true);
		}
		else if(ModelFacade.getWinner() != -1) {
			getFinishedView().setWinner(ModelFacade.getAPlayer(ModelFacade.getWinner()).getName(), false);
		}
		*/
		getPointsView().setPoints(5);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		initFromModel();
	}
	
}

