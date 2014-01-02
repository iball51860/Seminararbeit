package view;

import viewControl.*;

public class PopupDialogShots extends PopupDialog
{
	
	private ServerWindow serverWindow;
	private PopupShotListener pIL;
	
	
	public PopupDialogShots(ServerWindow serverWindow) 
	{
		super();
		
		info.setText("No of Shots");
		input.setText("1000");
		start.setText("OK");
		
		pIL = new PopupShotListener(this, serverWindow);
		input.addActionListener(pIL);
		start.addActionListener(pIL);
		
		setVisible(true);
	}
	
	
	

}
