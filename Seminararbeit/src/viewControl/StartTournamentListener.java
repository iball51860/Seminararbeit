package viewControl;

import java.awt.event.*;

import view.*;


public class StartTournamentListener implements ActionListener 
{
	
	private ServerWindow serverWindow;
	
	
	public StartTournamentListener(ServerWindow serverWindow)
	{
		super();
		this.serverWindow = serverWindow;
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		serverWindow.setEnabled(false);
		
		PopupDialogShots p = new PopupDialogShots(serverWindow);
		p.setVisible(true);
		
	}
}
