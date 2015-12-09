package client.roll;

import java.util.Random;

import shared.exceptions.ServerException;
import shared.transferClasses.RollNumber;
import client.base.Controller;
import client.base.MasterController;
import client.model.ModelFacade;
import client.model.Player;
import client.model.Status;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

	private IRollResultView resultView;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		
		setResultView(resultView);
		
		ModelFacade.addObserver(this);
		
		rand = new Random();
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {
		int dice = rand.nextInt(6) + 1;
		dice += rand.nextInt(6) + 1;
		
		getResultView().setRollValue(dice);
		try {
			MasterController.getSingleton().rollDice(new RollNumber(ModelFacade.getUserPlayer().getIndex(), dice));
		}
		catch (ServerException e) {
			System.out.println("Failed to roll dice: " + e.getReason());
		}
		
		getResultView().showModal();
	}
	
	private Random rand;
	
	@Override
	public void update() {
		Status state = ModelFacade.whatStateMightItBe();
		Player user = ModelFacade.getUserPlayer();		
		
		if (user.getIndex() == ModelFacade.whoseTurnIsItAnyway() && state == Status.Rolling) {
			rollDice();
		}
	}

}

