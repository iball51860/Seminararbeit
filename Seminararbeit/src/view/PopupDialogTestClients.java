package view;

import javax.swing.SwingUtilities;

import control.listeners.*;

/**
 * Dialog asking for the number of TestClients to initiate.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
@SuppressWarnings("serial")
public class PopupDialogTestClients extends PopupDialog
{
	/**Listener listenen for entry confirmation*/
	private PopupTestClientsListener pTCL;
	
	/**
	 * Constructs a Popup asking for the number of Clients to initiate. Assigns listener
	 * @param serverWindow
	 */
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
