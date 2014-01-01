package viewControl;

import java.awt.event.*;

import control.WMServer;

import view.*;

public class StartServerListener implements ActionListener {
	
	
	private PopupDialog popup;
	private ServerWindow serverWindow;
	
	
	public StartServerListener(PopupDialog popup, ServerWindow serverWindow)
	{
		super();
		this.popup = popup;
		this.serverWindow = serverWindow;
	}

	
	public void actionPerformed(ActionEvent e) 
	{
		popup.dispose();
		serverWindow.setEnabled(true);
		int port = Integer.valueOf(popup.getPortInput().getText());
		serverWindow.setWMServer(new WMServer(port));
		serverWindow.getWMServer().start();
	}

}
