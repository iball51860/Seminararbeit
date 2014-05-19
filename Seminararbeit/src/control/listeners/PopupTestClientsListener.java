package control.listeners;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import testClient.*;
import view.*;

/**
 * Listener triggered by the {@link PopupDialogTestClients} or the + Button adding a single new client.
 * Creates artificial {@link TestClient}{@code s}.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class PopupTestClientsListener implements ActionListener
{
	/**Popup retreiving number of Clients to be initiated*/
	private PopupDialogTestClients popup;
	/**ServerWindow from which the Popup is opened*/
	private ServerWindow serverWindow;
	/**Number of TestClients to be initiated*/
	private int noOfTestClients = 0;
	
	/**List of new testClients*/
	private static ArrayList<TestClient> testClients = new ArrayList<TestClient>();
	/**Counts the number of added {@link TestClient}{@code s}*/
	private static int countTestClients = 0;
	
	/**Constructor setting a Popup and a ServerWindow. Use if you want to initiate more than
	 * one {@link TestClient} at a time.
	 * @param popup
	 * @param serverWindow
	 */
	public PopupTestClientsListener(PopupDialogTestClients popup, ServerWindow serverWindow)
	{
		this.popup = popup;
		this.serverWindow = serverWindow;
	}
	
	/**
	 * Constructor setting the ServerWindow. Number of TestClients is fixed to 1 TestClient per ActionEvent.
	 * @param serverWindow
	 */
	public PopupTestClientsListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
		noOfTestClients = 1;
	}
	
	/**
	 * Creates amount of TestClients as specified in noOfTestClients. For more information
	 * take a look at the constructors.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if(noOfTestClients == 1)
		{
			TestClient newTC = new TestClient(serverWindow.getWMServer().getPort());
			newTC.start();
			serverWindow.updateNoOfTestClients(++countTestClients);
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
				serverWindow.updateNoOfTestClients(++countTestClients);
				testClients.add(dummy[i]);
			}
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					serverWindow.setEnabled(true);
				}
			});
		}
	}
	
	/**
	 * sets the Count of the Testclients to the specified Integer
	 * @param c
	 */
	public static void setCount(int c)
	{
		countTestClients = c;
	}

	/**
	 * returns the list of initiated TestClients
	 * @return
	 */
	public static ArrayList<TestClient> getTestClients() {
		return testClients;
	}
	
	
	
}
