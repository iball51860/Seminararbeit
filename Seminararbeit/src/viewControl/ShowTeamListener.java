package viewControl;

import java.awt.event.*;

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
		serverWindow.setEnabled(false);
		
		TeamPopupDialog p = new TeamPopupDialog(serverWindow, t);
		p.setSize(200, 150);
		p.setLocation(serverWindow.getLocationOnScreen().x + 300, serverWindow.getLocationOnScreen().y + 150); //TODO center popoup independently of ServerWindow size
		p.setVisible(true);
		
	}
	
	
	
}
