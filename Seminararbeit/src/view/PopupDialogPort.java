package view;

import javax.swing.*;

import control.listeners.*;



@SuppressWarnings("serial")
public class PopupDialogPort extends PopupDialog
{
	
	private StartServerListener startServer;
	
	
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
