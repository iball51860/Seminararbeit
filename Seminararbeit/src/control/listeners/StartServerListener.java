package control.listeners;

import java.awt.event.*;

import control.WMServer;

import view.*;

import java.io.*;
import java.net.*;

import javax.swing.SwingUtilities;

/**
 * Listener connected to the initial Dialog asking for the Port to use.
 * Stars a server.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class StartServerListener implements ActionListener {
	
	/**Dialog asking for the port*/
	private PopupDialogPort popup;
	/**ServerWindow representing the new Server*/
	private ServerWindow serverWindow;
	
	/**
	 * Constructor setting the ServerWindow and the Popupdialog
	 * @param popup
	 * @param serverWindow
	 */
	public StartServerListener(PopupDialogPort popup, ServerWindow serverWindow)
	{
		super();
		this.popup = popup;
		this.serverWindow = serverWindow;
	}

	/**
	 * retrieves the Portnumber and starts a server at that port.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		int port = 4444;
		boolean portIsFree = false;
		try
		{
			port = Integer.valueOf(popup.getInput().getText());
			
			if(port < 1024)
			{
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						popup.getInfoLabel().setText(
								"Port reserviert. Neuer Port:");
						popup.getInput().setText("4444");
						
					}
				});
				return;
			}
			
			Socket socket = new Socket("localhost", port); //check if server is already running on port
			socket.close();
		}
		catch(NumberFormatException nfe)
		{
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					popup.getInput().setText("4444");
				}
			});
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
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					popup.getInfoLabel().setText("Port belegt. Neuer Port:");
				}
			});
			return;
		}
		final int port2 = port;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				popup.dispose();
				serverWindow.setEnabled(true);
				serverWindow.getServerPort().setText("Port: " + port2);
				serverWindow.getShowPort().setText("Port: " + port2);
			}
		});
		serverWindow.setWMServer(new WMServer(port, serverWindow));
		serverWindow.getWMServer().start();
	}

}
