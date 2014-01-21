package viewControl;

import java.awt.event.*;

import control.Logger;

import view.ServerWindow;

public class UpdateLogListener implements ActionListener 
{
	
	ServerWindow serverWindow;
	String[] names;
	boolean[] types;
	
	
	public UpdateLogListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
		names = new String[2];
		types = new boolean[7];
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		names[0] = (String) serverWindow.getTeamBox1().getSelectedItem();
		names[1] = (String) serverWindow.getTeamBox2().getSelectedItem();
		for(int i = 0; i < types.length; i++)
		{
			types[i] = serverWindow.type[i].isSelected();
		}
		serverWindow.getLogString().setText(Logger.getLog(names, types));
	}

}
