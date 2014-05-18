package control.listeners;

import java.awt.event.*;

import control.Logger;

import view.ServerWindow;
/**
 * Listener triggered by some of the settings in the LogPanel.
 * Updates the TextArea with current Log.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class UpdateLogListener implements ActionListener 
{
	/**ServerWindow holding the LogPanel*/
	ServerWindow serverWindow;
	/**Array of instancenames to get the log for*/
	String[] names;
	/**Booleanarray holding the boolean to print in log at the specific types positions*/
	boolean[] types;
	
	/**
	 * Constructor sets the ServerWindow and initiates the Arrays
	 * @param serverWindow
	 */
	public UpdateLogListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
		names = new String[2];
		types = new boolean[7];
	}
	
	/**
	 * Retrieves the updated Log and posts it to the {@link LogPanel}{@code s} TextArea.
	 */
	public void actionPerformed(ActionEvent e)
	{
		names[0] = (String) serverWindow.getTeamBox1().getSelectedItem();
		names[1] = (String) serverWindow.getTeamBox2().getSelectedItem();
		for(int i = 0; i < types.length; i++)
		{
			types[i] = serverWindow.getLogPanel().getType()[i].isSelected();
		}
		serverWindow.getLogString().setText(Logger.getLog(names, types));
	}
}