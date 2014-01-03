package view;

import viewControl.*;

public class PopupDialogTestClients extends PopupDialog
{
	
	private PopupTestClientsListener pTCL;
	

	public PopupDialogTestClients(ServerWindow serverWindow)
	{
		super();
		
		info.setText("No of TestClients");
		input.setText("100");
		start.setText("OK");
		
		pTCL = new PopupTestClientsListener(this, serverWindow);
		input.addActionListener(pTCL);
		start.addActionListener(pTCL);
		
		setVisible(true);
	}
	
	
	
	
	
}
