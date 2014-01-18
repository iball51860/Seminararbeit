package view;

import javax.swing.SwingUtilities;

import viewControl.*;

public class PopupDialogTestClients extends PopupDialog
{
	
	private PopupTestClientsListener pTCL;
	

	public PopupDialogTestClients(final ServerWindow serverWindow)
	{
		super(serverWindow);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				info.setText("No of TestClients");
				input.setText("100");
				start.setText("OK");
				pTCL = new PopupTestClientsListener(
						PopupDialogTestClients.this, serverWindow);
				input.addActionListener(pTCL);
				start.addActionListener(pTCL);
				setVisible(true);
			}
		});
	}
	
	
	
	
	
}
