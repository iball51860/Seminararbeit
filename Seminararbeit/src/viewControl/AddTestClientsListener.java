package viewControl;

import java.awt.Point;
import java.awt.event.*;
import javax.swing.JButton;

import view.*;


public class AddTestClientsListener implements ActionListener
{
	
	private ServerWindow serverWindow;
	
	
	public AddTestClientsListener(ServerWindow serverWindow)
	{
		super();
		this.serverWindow = serverWindow;
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		serverWindow.setEnabled(false);
		
		PopupDialogTestClients p = new PopupDialogTestClients(serverWindow);
		p.setSize(200, 100);
		//p.setLocation(serverWindow.getLocationOnScreen().x + 100, serverWindow.getLocationOnScreen().y + 22); //TODO center popoup independently of ServerWindow size
		p.setVisible(true);
		
		
	}
	
	
	
}
