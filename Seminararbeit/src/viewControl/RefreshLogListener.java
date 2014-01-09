package viewControl;

import java.awt.event.*;

import view.ServerWindow;

public class RefreshLogListener implements ActionListener
{
	
	ServerWindow serverWindow;
	
	
	public RefreshLogListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		serverWindow.refreshLog();
		if(serverWindow.getTeamBox1().getSelectedIndex() > 1)
		{
			serverWindow.getTeamBox2().setEnabled(true);
		}
		else
		{
			serverWindow.getTeamBox2().setEnabled(false);
		}
	}

}
