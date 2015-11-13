package client.communication;

import shared.transferClasses.SendChat;
import client.base.Controller;
import client.exceptions.ServerException;
import client.model.ModelFacade;
import client.server.ServerProxy;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {

	public ChatController(IChatView view) {
		
		super(view);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		try {
			ServerProxy.sendChat(new SendChat(ModelFacade.getPlayerInfo().getIndex(), message));
		}
		catch (ServerException e) {
			//Server error, but no way to tell user
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		getView().setEntries(ModelFacade.getChatLog());
	}

}

