package view;

import javax.swing.*;

import viewControl.*;

import java.awt.*;

public class PopupDialogPort extends PopupDialog
{
	
	private StartServerListener startServer;
	
	
	public PopupDialogPort(ServerWindow serverWindow)
	{
		super(serverWindow);
		
		info.setText("Port eingeben");
		input.setText("4444");
		start.setText("Start Server");
		
		startServer = new StartServerListener(this, serverWindow);
		input.addActionListener(startServer);
		start.addActionListener(startServer);
		
		setVisible(true);
	}
	
}
