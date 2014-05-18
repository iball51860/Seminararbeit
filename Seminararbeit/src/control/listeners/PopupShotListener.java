package control.listeners;

import java.awt.event.*;

import javax.swing.SwingUtilities;

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
			final int minShots = 2 * Analyser.calculateNoOfMatches(serverWindow.getWMServer().getClientsAtServer().size());
			if(shots < minShots)
			{
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						popup.getInput().setText("" + minShots);
					}
				});
				return;
			}
			serverWindow.getWMServer().startGame(shots);
		}
		catch(NumberFormatException nfe)
		{
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					popup.getInput().setText("20000");
				}
			});
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				popup.dispose();
				serverWindow.setEnabled(true);
				serverWindow.getStartButton().setEnabled(false);
				serverWindow.getAddTestClients().setEnabled(false);
				serverWindow.getPlusTestClient().setEnabled(false);
			}
		});	
	}
	
}
