package client.join;

import java.util.List;

import client.data.PlayerInfo;
import client.model.ModelFacade;

/**
 * An IPlayerWaitingState where there are enough players in the game to start playing
 * @author djoshuac
 */
public class NotEnoughPlayersState implements IPlayerWaitingState {

	/**
	 * The controller for this NotEnoghPlayers state
	 */
	private PlayerWaitingController controller;
	
	/**
	 * Constructs a NotEnoughPlayersState with the given controller
	 * @param controller - the controller for this NotEnoughPlayersState state
	 */
	public NotEnoughPlayersState(PlayerWaitingController controller) {
		ModelFacade.alertThatPlayerIsWaitingForPlayersToJoin();
		
		this.controller = controller;
		numberOfShownJoinedPlayers = 0;
		
		String[] aiTypes = {"none"}; //ServerProxy.listAITypes(); // not needed for credit
		controller.getView().setAIChoices(aiTypes);
		
		controller.getView().showModal();
	}
	
	/**
	 * The number of players currently visible to the view
	 */
	private int numberOfShownJoinedPlayers;
	
	/**
	 * Refreshes the game list in the join game veiw
	 */
	private void refreshPlayerInfo(List<PlayerInfo> info) {
		numberOfShownJoinedPlayers = info.size();
		controller.getView().setPlayers(info);
		
		controller.getView().closeModal(); // view doesn't seem to update unless closed and reopened
		controller.getView().showModal();
	}
	
	/**
	 * Checks for number of players in game and updates the view to show them.
	 * @return This state if there are still not enough players.<br>
	 * An EnoughPlayersState If the game has enough players
	 */
	@Override
	public void update() {
		List<PlayerInfo> joinedPlayerInfo = ModelFacade.getJoinedPlayersInfo();
		
		if (joinedPlayerInfo.size() == 4) {
			while(controller.getView().isModalShowing()) {
				controller.getView().closeModal();
			}
			controller.setState(new EnoughPlayersState());
			ModelFacade.alertThatAllPlayersHaveJoined();
		}
		else {
			if (joinedPlayerInfo.size() != numberOfShownJoinedPlayers) {
				refreshPlayerInfo(joinedPlayerInfo);
			}
		}
	}
}
