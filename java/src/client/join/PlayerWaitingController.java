package client.join;

import client.base.Controller;
import client.model.ModelFacade;
import client.server.ServerPoller;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {
	/**
	 * The state of the PlayerWaitingController
	 */
	IPlayerWaitingState state;
	
	public PlayerWaitingController(IPlayerWaitingView view) {
		super(view);
		
		ModelFacade.addObserver(this);
		
		state = new EnoughPlayersState();
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}
	
	@Override
	public void start() {
		state = new NotEnoughPlayersState(this);
	}

	@Override
	public void addAI() {
		System.out.println("Adding AI is not required for credit");
//		try {
//			//ServerProxy.addAI(new AddAIRequest(getView().getSelectedAI()));
//		}
//		catch (ServerException e) {
//			// To the Ta's of the past. Why is there no message view?  ('n')
//			getView().closeModal();
//		}
	}
	
	/**
	 * Sets the state of the controller to the given state
	 * @param state - the state to put the controller in
	 */
	protected void setState(IPlayerWaitingState state) {
		this.state = state;
	}
	
	@Override
	public void update() {
		state.update();
	}
}

