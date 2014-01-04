package viewControl;

import java.awt.event.*;

import view.*;

public class CloseTeamPopupListener implements ActionListener, KeyListener
{
	
	ServerWindow serverWindow;
	TeamPopupDialog popup;
	
	
	public CloseTeamPopupListener(ServerWindow serverWindow, TeamPopupDialog popup)
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


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
