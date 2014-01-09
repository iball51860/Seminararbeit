package viewControl;

import javax.swing.event.*;

import view.ServerWindow;

public class TabbedPaneListener implements ChangeListener
{
	ServerWindow serverWindow;
	
	
	public TabbedPaneListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
	}
	

	public void stateChanged(ChangeEvent e) 
	{
		serverWindow.updateResultList();
		serverWindow.refreshLog();
	}

}
