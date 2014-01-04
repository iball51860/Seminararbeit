package view;

import viewControl.*;

public class PopupDialogShots extends PopupDialog
{
	
	private PopupShotListener pIL;
	
	
	public PopupDialogShots(ServerWindow serverWindow) 
	{
		super();
		
		info.setText("No of Shots");
		input.setText("100000");
		start.setText("OK");
		
		pIL = new PopupShotListener(this, serverWindow);
		input.addActionListener(pIL);
		start.addActionListener(pIL);
		
		setVisible(true);
	}
	
	
	

}
