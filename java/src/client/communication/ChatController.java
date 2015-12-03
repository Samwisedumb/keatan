package client.communication;

import java.util.ArrayList;

import shared.exceptions.ServerException;
import shared.transferClasses.SendChat;
import client.base.Controller;
import client.base.MasterController;
import client.model.ModelFacade;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {

	public ChatController(IChatView view) {
		super(view);
		
		ModelFacade.addObserver(this);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		try {
			MasterController.getSingleton().sendChat(new SendChat(ModelFacade.getUserPlayerInfo().getIndex(), message));
		}
		catch (ServerException e) {
			//Server error, but no way to tell user
		}
	}

	@Override
	public void update() {
		if (MasterController.getSingleton().hasGameBegun()) {
			getView().setEntries(ModelFacade.getChatLog());
		}
		else {
			getView().setEntries(new ArrayList<LogEntry>());
		}
	}
}

