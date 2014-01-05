package viewControl;

import java.awt.event.*;

import testClient.*;
import view.*;

public class ResetServerListener implements ActionListener
{
	
	ServerWindow serverWindow;
	
	
	public ResetServerListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		serverWindow.getWMServer().shutDown();
		if(serverWindow.getTournament() != null)
		{
			serverWindow.getTournament().setRunning(false);
		}
		for(TestClient tc : PopupTestClientsListener.getTestClients())
		{
			tc.setRunForrestRun(false);
		}
		PopupTestClientsListener.setCount(0);
		serverWindow.dispose();
		serverWindow = new ServerWindow();
	}

}
