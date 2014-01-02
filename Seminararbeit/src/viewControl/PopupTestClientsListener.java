package viewControl;

import java.awt.event.*;

import testClient.*;
import view.*;

public class PopupTestClientsListener implements ActionListener
{
	
	private PopupDialogTestClients popup;
	private ServerWindow serverWindow;
	
	
	public PopupTestClientsListener(PopupDialogTestClients popup, ServerWindow serverWindow)
	{
		this.popup = popup;
		this.serverWindow = serverWindow;
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		int noOfTestClients = 0;
		try
		{
			noOfTestClients = Integer.valueOf(popup.getInput().getText());
		}
		catch(NumberFormatException nfe)
		{
			popup.getInput().setText("100");
		}
		
		TestClient[] dummy = new TestClient[noOfTestClients];
		
		for(int i = 0; i < dummy.length; i++)
		{
			dummy[i] = new TestClient(serverWindow.getWMServer().getPort());
			System.out.println("X" + i);
			dummy[i].start();
			System.out.println("Dummy" + (i+1) + " started.");
		}
		
		
		popup.dispose();
		serverWindow.setEnabled(true);
	}
	
	
	
}
