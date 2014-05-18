package control.listeners;

import java.awt.event.*;

import javax.swing.SwingUtilities;

import view.*;

/**
 * Listener related to the StartGame Button.
 * Opens a Dialog asking for the number of shots to simulate for the Tournament.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
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
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				serverWindow.setEnabled(false);
				PopupDialogShots p = new PopupDialogShots(serverWindow);
				p.setVisible(true);
			}
		});
		
	}
}
