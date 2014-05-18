package view;

import javax.swing.SwingUtilities;

import control.listeners.*;


@SuppressWarnings("serial")
public class PopupDialogShots extends PopupDialog
{
	
	private PopupShotListener pIL;
	
	
	public PopupDialogShots(final ServerWindow serverWindow) 
	{
		super(serverWindow);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				info.setText("No of Shots");
				input.setText("20000");
				start.setText("OK");
				pIL = new PopupShotListener(PopupDialogShots.this, serverWindow);
				input.addActionListener(pIL);
				start.addActionListener(pIL);
				setVisible(true);
			}
		});
	}
	
	
	

}
