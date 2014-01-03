package viewControl;

import java.awt.event.*;

import control.WMServer;

import view.*;

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
		try
		{
			port = Integer.valueOf(popup.getInput().getText());
		}
		catch(NumberFormatException nfe)
		{
			popup.getInput().setText("4444");
			return;
		}
		popup.dispose();
		serverWindow.setEnabled(true);
		serverWindow.setWMServer(new WMServer(port));
		serverWindow.getWMServer().start();
	}

}
