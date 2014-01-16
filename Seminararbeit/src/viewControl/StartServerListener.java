package viewControl;

import java.awt.event.*;

import control.WMServer;

import view.*;

import java.io.*;
import java.net.*;

public class StartServerListener implements ActionListener {
	
	
	private PopupDialogPort popup;
	private ServerWindow serverWindow;
	
	
	public StartServerListener(PopupDialogPort popup, ServerWindow serverWindow)
	{
		super();
		this.popup = popup;
		this.serverWindow = serverWindow;
	}

	
	public void actionPerformed(ActionEvent e) 
	{
		int port = 4444;
		boolean portIsFree = false;
		try
		{
			port = Integer.valueOf(popup.getInput().getText());
			
			Socket socket = new Socket("localhost", port); //check if server is already running on port
			socket.close();
		}
		catch(NumberFormatException nfe)
		{
			popup.getInput().setText("4444");
			return;
		}
		catch(ConnectException ce){
			portIsFree = true;
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		if(!portIsFree)
		{
			popup.getInput().setText("4444");
			return;
		}
		popup.dispose();
		serverWindow.setEnabled(true);
		serverWindow.setWMServer(new WMServer(port, serverWindow));
		serverWindow.getWMServer().start();
	}

}
