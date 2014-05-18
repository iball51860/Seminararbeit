package control.listeners;

import java.awt.event.*;

import javax.swing.SwingUtilities;

import control.Analyser;

import view.*;

/**
 * Listener triggered by hitting Return or the OK-Button in the {@link PopupDialogShots}.
 * Starts a tournament.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class PopupShotListener implements ActionListener
{
	/**Popupwindow where the number of shots is entered*/
	private PopupDialogShots popup;
	/**ServerWindow from which the Popup is opened*/
	private ServerWindow serverWindow;
	
	/**Constructor setting the popupwindow and the ServerWindow*/
	public PopupShotListener(PopupDialogShots popup, ServerWindow serverWindow)
	{
		this.popup = popup;
		this.serverWindow = serverWindow;
	}
	
	/**
	 * Retrieves the number of shots to be played and initiates a new tournament with the registered Clients
	 * and the shots to be played.
	 */
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
