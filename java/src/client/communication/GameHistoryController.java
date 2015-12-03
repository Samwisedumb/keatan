package client.communication;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.CatanColor;
import client.base.Controller;
import client.base.MasterController;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		
		//<temp>
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		
		getView().setEntries(entries);
	
		//</temp>
	}

	@Override
	public void update() {
		if (MasterController.getSingleton().hasGameBegun()) {
			// #TODO make is so master controller keeps track of what goes on
		}
		else {
			getView().setEntries(new ArrayList<LogEntry>());
		}
	}
	
}

