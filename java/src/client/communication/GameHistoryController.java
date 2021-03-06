package client.communication;

import java.util.ArrayList;
import java.util.List;

import client.base.Controller;
import client.base.MasterController;
import client.model.MessageLine;
import client.model.ModelFacade;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {

	public GameHistoryController(IGameHistoryView view) {
		super(view);
		
		ModelFacade.addObserver(this);
	}
	
	@Override
	public IGameHistoryView getView() {
		return (IGameHistoryView)super.getView();
	}

	@Override
	public void update() {
		if (MasterController.getSingleton().hasGameBegun()) {
			List<LogEntry> gameLog = new ArrayList<LogEntry>();
			
			gameLog.add(new LogEntry(ModelFacade.getUserPlayer().getColor(), "Game has Begun"));
			
			for (MessageLine line : ModelFacade.getGameHistory().getLines()) {
				gameLog.add(new LogEntry(ModelFacade.getPlayer(line.getSource()).getColor(), line.getMessage()));
			}
			
			getView().setEntries(gameLog);
		}
		else {
			List<LogEntry> gameLog = new ArrayList<LogEntry>();
			gameLog.add(new LogEntry(ModelFacade.getUserPlayer().getColor(), "Waiting for players..."));
			
			getView().setEntries(gameLog);
		}
	}
	
}

