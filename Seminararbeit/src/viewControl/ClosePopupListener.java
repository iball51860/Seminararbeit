package viewControl;

import java.awt.event.*;

import javax.swing.SwingUtilities;

import view.*;

public class ClosePopupListener implements ActionListener, KeyListener
{
	
	ServerWindow serverWindow;
	TeamPopupDialog popup;
	
	
	public ClosePopupListener(ServerWindow serverWindow, TeamPopupDialog popup)
	{
		this.serverWindow = serverWindow;
		this.popup = popup;
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		close();
	}



	public void keyTyped(KeyEvent e) 
	{
		close();
	}

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
