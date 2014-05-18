package control.listeners;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import testClient.*;
import view.*;

public class PopupTestClientsListener implements ActionListener
{
	
	private PopupDialogTestClients popup;
	private ServerWindow serverWindow;
	private int noOfTestClients = 0;
	
	private static ArrayList<TestClient> testClients = new ArrayList<TestClient>();
	private static int count = 0;
	
	
	public PopupTestClientsListener(PopupDialogTestClients popup, ServerWindow serverWindow)
	{
		this.popup = popup;
		this.serverWindow = serverWindow;
	}
	
	public PopupTestClientsListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
		noOfTestClients = 1;
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		if(noOfTestClients == 1)
		{
			TestClient newTC = new TestClient(serverWindow.getWMServer().getPort());
			newTC.start();
			serverWindow.updateNoOfTestClients(++count);
			testClients.add(newTC);
		}
		else
		{
			try
			{
				noOfTestClients = Integer.valueOf(popup.getInput().getText());
			}
			catch(NumberFormatException nfe)
			{
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						popup.getInput().setText("100");
					}
				});
			}
			
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					popup.dispose();
				}
			});
			
			
			TestClient[] dummy = new TestClient[noOfTestClients];
			
			for(int i = 0; i < dummy.length; i++)
			{
				dummy[i] = new TestClient(serverWindow.getWMServer().getPort());
				dummy[i].start();
				serverWindow.updateNoOfTestClients(++count);
				testClients.add(dummy[i]);
			}
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					serverWindow.setEnabled(true);
				}
			});
		}
	}
	
	
	public static void setCount(int c)
	{
		count = c;
	}

	public static ArrayList<TestClient> getTestClients() {
		return testClients;
	}
	
	
	
}
