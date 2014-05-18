package control.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.ServerWindow;

public class TeamBoxListener implements ActionListener
{
	ServerWindow serverWindow;
	
	
	public TeamBoxListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(serverWindow.getTeamBox1().getSelectedIndex() > 1)
		{
			serverWindow.getTeamBox2().setEnabled(true);
		}
		else
		{
			serverWindow.getTeamBox2().setSelectedIndex(0);
			serverWindow.getTeamBox2().setEnabled(false);
		}
	}

}
