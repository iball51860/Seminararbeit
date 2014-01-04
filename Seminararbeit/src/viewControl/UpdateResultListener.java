package viewControl;

import java.awt.event.*;

import view.*;

public class UpdateResultListener implements ActionListener
{
	
	ServerWindow serverWindow;
	
	
	public UpdateResultListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		serverWindow.updateResultList();
	}
	
}
