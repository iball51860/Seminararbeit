package control.listeners;

import java.awt.event.*;
import javax.swing.SwingUtilities;

import model.*;
import view.*;

/**
 * Listener Triggered by a click on a Team in the {@link TeamMatrixPanel}.
 * Opens a small frame with information about the team.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class ShowTeamListener implements ActionListener
{
	/**ServerWindow from which the Listener is triggered*/
	private ServerWindow serverWindow;
	/**Team the Button belongs to. Action will open frame with info about this team*/
	private Team team;
	
	/**
	 * Listener setting the serverWindow and Team
	 * @param serverWindow
	 * @param t
	 */
	public ShowTeamListener(ServerWindow serverWindow, Team t)
	{
		super();
		this.serverWindow = serverWindow;
		this.team = t;
	}
	
	/**
	 * Disables the ServerWindow and opens a new frame.
	 */
	public void actionPerformed(ActionEvent e)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				serverWindow.setEnabled(false);
				new TeamPopup(serverWindow, team);
			}
		});
		
	}
	
	
	
}
