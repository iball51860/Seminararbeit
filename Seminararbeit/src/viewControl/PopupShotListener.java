package viewControl;

import java.awt.event.*;

import view.*;

public class PopupShotListener implements ActionListener
{
	
	private PopupDialogShots popup;
	private ServerWindow serverWindow;
	
	
	public PopupShotListener(PopupDialogShots popup, ServerWindow serverWindow)
	{
		this.popup = popup;
		this.serverWindow = serverWindow;
	}
	
	
	
	public void actionPerformed(ActionEvent e) 
	{
		//TODO add Empfehlung
		try
		{
			serverWindow.getWMServer().startGame(Integer.valueOf(popup.getInput().getText()));
		}
		catch(NumberFormatException nfe)
		{
			popup.getInput().setText("100000");
		}
		
		
		popup.dispose();
		serverWindow.setEnabled(true);
		
		
	}
	
}
