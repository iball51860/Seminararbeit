package control.listeners;

import javax.swing.event.*;

import control.Logger;

import view.ServerWindow;

/**
 * Listener listening to switching Tabs in the tabed Pane holding the games visualisation.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class TabbedPaneListener implements ChangeListener
{
	/**ServerWindow holding the TabbedPane*/
	ServerWindow serverWindow;
	/**Names of Teams to get the Log for when switching*/
	private String[] names;
	/**Log Messagetypes to get the Log for when switching*/
	private boolean[] types;
	
	/**
	 * Constructor setting the serverWindow.
	 * @param serverWindow
	 */
	public TabbedPaneListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
		
		names = new String[2];
		types = new boolean[7];
	}
	
	/**
	 * invoked, when switching Tab. Updates Resultlist and Log.
	 */
	public void stateChanged(ChangeEvent e) 
	{
		serverWindow.updateResultList();
		
		names[0] = (String) serverWindow.getTeamBox1().getSelectedItem();
		names[1] = (String) serverWindow.getTeamBox2().getSelectedItem();
		for(int i = 0; i < types.length; i++)
		{
			types[i] = serverWindow.getLogPanel().getType()[i].isSelected();
		}
		serverWindow.getLogString().setText(Logger.getLog(names, types));
	}

}
