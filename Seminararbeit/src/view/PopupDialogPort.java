package view;

import javax.swing.*;

import control.listeners.*;

/**
 * PopupDialog asking for the Portnumber.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
@SuppressWarnings("serial")
public class PopupDialogPort extends PopupDialog
{
	/**Listener added to the TextField and the Button.<br><br>See {@link StartServerListener} for more information.*/
	private StartServerListener startServer;
	
	/**
	 * Constructs {@link PopupDialog} with Label asking for port and Button "Start Server".
	 * @param serverWindow
	 */
	public PopupDialogPort(final ServerWindow serverWindow)
	{
		super(serverWindow);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				info.setText("Port eingeben");
				input.setText("4444");
				start.setText("Start Server");
				startServer = new StartServerListener(PopupDialogPort.this,
						serverWindow);
				input.addActionListener(startServer);
				start.addActionListener(startServer);
				setVisible(true);
			}
		});
	}
	
}
