package client.communication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import client.base.PanelView;

/**
 * Game history view implementation
 */
@SuppressWarnings("serial")
public class GameHistoryView extends PanelView implements IGameHistoryView
{
	
	private LogComponent logPanel;
	private JScrollPane logScroll;
	
	public GameHistoryView()
	{
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		
		logPanel = new LogComponent();
		
		logScroll = new JScrollPane(logPanel);
		
		this.add(logScroll, BorderLayout.CENTER);
	}
	
	@Override
	public IGameHistoryController getController()
	{
		return (IGameHistoryController)super.getController();
	}
	
	@Override
	public void setEntries(final List<LogEntry> entries)
	{
		
		// Can't set entries immediately, because logPanel doesn't
		// have a width or height yet, which messes up the word wrap
		// calculations in LogComponent. Therefore, we call
		// invokeLater.
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				logPanel.setEntries(entries);
				JScrollBar vertical = logScroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
			}
		});
	}
	
}

