package control.listeners;

import java.awt.event.*;
import javax.swing.SwingUtilities;

import view.*;

/**
 * Listener-Class for the Add Test Clients Button.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class AddTestClientsListener implements ActionListener
{
	/**Instance of the ServerWindow from which the Listener was triggered.*/
	private ServerWindow serverWindow;
	
	
	public AddTestClientsListener(ServerWindow serverWindow)
	{
		super();
		this.serverWindow = serverWindow;
	}
	
	/**
	 * Opens a Dialog Window asking for the Number of Testclients to start.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				serverWindow.setEnabled(false);
				PopupDialogTestClients p = new PopupDialogTestClients(
						serverWindow);
				p.setSize(200, 100);
				p.setVisible(true);
			}
		});
		
		
	}
	
	
	
}
