package viewControl;

import java.awt.event.*;
import javax.swing.SwingUtilities;

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
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				serverWindow.setEnabled(false);
				PopupDialogTestClients p = new PopupDialogTestClients(
						serverWindow);
				p.setSize(200, 100);
				p.setVisible(true);
			}
		});
		
		
	}
	
	
	
}
