package control.listeners;

import java.awt.event.*;

import javax.swing.SwingUtilities;

import view.*;

/**
 * Listener related to {@link PopupDialog}s that ask for a Value.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class ClosePopupListener implements ActionListener, KeyListener
{
	/**Instance of the Server Window from which the Listener is triggered*/
	ServerWindow serverWindow;
	/**Instance of the dialog asking for the value*/
	TeamPopupDialog popup;
	
	/**
	 * Constructor setting an instance of {@link ServerWindow} and {@link TeamPopupDialog} to hold.
	 * @param serverWindow
	 * @param popup
	 */
	public ClosePopupListener(ServerWindow serverWindow, TeamPopupDialog popup)
	{
		this.serverWindow = serverWindow;
		this.popup = popup;
	}
	
	/**
	 * Calls {@code close()}. Closes Popup and enables ServerWindow.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		close();
	}


	/**
	 * Calls {@link close()}. Closes Popup and enables ServerWindow.
	 */
	public void keyTyped(KeyEvent e) 
	{
		close();
	}

	/**
	 * Closes Popup and enables ServerWindow.
	 */
	public void close()
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				serverWindow.setEnabled(true);
				popup.dispose();
			}
		});
	}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}
}
