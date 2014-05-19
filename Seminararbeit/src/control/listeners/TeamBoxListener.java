package control.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.ServerWindow;

/**
 * Listener triggered by first Combobox. Related to the Comboboxes under the Log Tab.
 * Enables/Disables second Combobox in dependce of the first Combobox
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class TeamBoxListener implements ActionListener
{
	/**
	 * ServerWindow holding the LogPanel
	 */
	ServerWindow serverWindow;
	
	/**
	 * Constructor setting the ServerWindow.
	 * @param serverWindow
	 */
	public TeamBoxListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
	}
	
	/**
	 * Enabels of Disables the second Combobox in depence of the first combobox selected content.
	 */
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
