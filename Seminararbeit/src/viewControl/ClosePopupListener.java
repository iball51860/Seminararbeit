package viewControl;

import java.awt.event.*;

import view.*;

public class ClosePopupListener implements ActionListener, KeyListener
{
	
	ServerWindow serverWindow;
	TeamPopupDialog popup;
	
	
	public ClosePopupListener(ServerWindow serverWindow, TeamPopupDialog popup)
	{
		this.serverWindow = serverWindow;
		this.popup = popup;
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		serverWindow.setEnabled(true);
		popup.dispose();
	}



	public void keyTyped(KeyEvent e) 
	{
		serverWindow.setEnabled(true);
		popup.dispose();
	}


	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}
	
	
	
	
	
}
