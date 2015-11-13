package client.base;

import javax.swing.JComponent;

/**
 * Base class for JComponent-based views
 */
@SuppressWarnings("serial")
public class ComponentView extends JComponent implements IView
{
	
	private IController controller;
	
	public IController getController()
	{
		return controller;
	}
	
	public void setController(IController controller)
	{
		this.controller = controller;
	}
	
}

