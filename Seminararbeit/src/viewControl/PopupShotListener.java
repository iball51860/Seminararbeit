package viewControl;

import java.awt.event.*;

import control.Analyser;

import view.*;

public class PopupShotListener implements ActionListener
{
	
	private PopupDialogShots popup;
	private ServerWindow serverWindow;
	
	
	public PopupShotListener(PopupDialogShots popup, ServerWindow serverWindow)
	{
		this.popup = popup;
		this.serverWindow = serverWindow;
	}
	
	
	
	public void actionPerformed(ActionEvent e) 
	{
		try
		{
			Integer shots = Integer.valueOf(popup.getInput().getText());
			int minShots = 2 * Analyser.calculateNoOfMatches(serverWindow.getWMServer().getClientsAtServer().size());
			if(shots < minShots)
			{
				popup.getInput().setText("" + minShots);
				return;
			}
			serverWindow.getWMServer().startGame(shots);
		}
		catch(NumberFormatException nfe)
		{
			popup.getInput().setText("100000");
		}
		
		
		popup.dispose();
		serverWindow.setEnabled(true);
		
		
	}
	
}
