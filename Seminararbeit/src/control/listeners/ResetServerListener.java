package control.listeners;

import java.awt.event.*;

import javax.swing.SwingUtilities;

import testClient.*;
import view.*;

/**
 * Listener triggered by the Reset Server Button.
 * Resets the server.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class ResetServerListener implements ActionListener
{
	/**ServerWindow from which the action is triggered*/
	ServerWindow serverWindow;
	
	/**
	 * Constructor setting the ServerWindow.
	 * @param serverWindow
	 */
	public ResetServerListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
	}
	
	/**
	 * Shuts the currently running Server down. Initiates a new ServerWindow and therefore a new Server.
	 */
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
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				serverWindow.dispose();
				serverWindow = new ServerWindow();
			}
		});
	}

}
