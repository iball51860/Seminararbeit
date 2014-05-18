package control.listeners;

import java.awt.event.*;
import javax.swing.SwingUtilities;

import model.*;
import view.*;

public class ShowTeamListener implements ActionListener
{
	
	private ServerWindow serverWindow;
	private Team t;
	
	
	public ShowTeamListener(ServerWindow serverWindow, Team t)
	{
		super();
		this.serverWindow = serverWindow;
		this.t = t;
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				serverWindow.setEnabled(false);
				new TeamPopupDialog(serverWindow, t);
			}
		});
		
	}
	
	
	
}