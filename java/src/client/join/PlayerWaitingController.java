package client.join;

import java.util.List;

import shared.exceptions.ServerException;
import shared.json.Converter;
import client.base.Controller;
import client.data.PlayerInfo;
import client.model.ModelFacade;
import client.model.TransferModel;
import client.server.ServerPoller;
import client.server.ServerProxy;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {

	public PlayerWaitingController(IPlayerWaitingView view) {
		super(view);
		
		ModelFacade.addObserver(this);
		numberOfShownJoinedPlayers = 0;
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}
	
	
	/**
	 * Refreshes the game list in the join game veiw
	 */
	private void refreshPlayerInfo() {
		List<PlayerInfo> info = ModelFacade.getJoinedPlayersInfo();

		numberOfShownJoinedPlayers = info.size();
		getView().setPlayers(info);
		
		getView().closeModal();
		getView().showModal();
	}
	
	@Override
	public void start() {
		try {
			String[] aiTypes = {"Joe"}; //ServerProxy.listAITypes(); // not needed for credit
			getView().setAIChoices(aiTypes);
			getView().showModal();
	
			TransferModel model = ServerProxy.getModel(-1);
			ModelFacade.updateModel(model);
				
			System.out.println("ServerPoller started");
			ServerPoller.start();
		}
		catch (ServerException e) {
			e.printStackTrace();
		}
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
	 * The number of players shown to the view
	 */
	private int numberOfShownJoinedPlayers;
	
	@Override
	public void update() {
		List<PlayerInfo> joinedPlayerInfo = ModelFacade.getJoinedPlayersInfo();
		
		if (ModelFacade.getJoinedPlayersInfo().size() == 4) {
			numberOfShownJoinedPlayers = 0;
			getView().closeModal();
		}
		else if (joinedPlayerInfo.size() == numberOfShownJoinedPlayers) {
			// do nothing
		}
		else {
			refreshPlayerInfo();
		}
	}
}

