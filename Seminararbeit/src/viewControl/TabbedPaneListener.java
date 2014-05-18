package viewControl;

import javax.swing.event.*;

import control.Logger;

import view.ServerWindow;

public class TabbedPaneListener implements ChangeListener
{
	ServerWindow serverWindow;
	private String[] names;
	private boolean[] types;
	
	
	public TabbedPaneListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
		
		names = new String[2];
		types = new boolean[7];
	}
	

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
