package view;

import javax.swing.SwingUtilities;

import control.listeners.*;

/**
 * Popup asking for the number of shot to be played in a tournament.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
@SuppressWarnings("serial")
public class PopupDialogShots extends PopupDialog
{
	/**Listener added to Button and Textfield that retrieves the number of Shots*/
	private PopupShotListener pIL;
	
	/**
	 * Constructs a Popup asking for the shots to be played. See also {@link PopupDialog}.
	 * @param serverWindow
	 */
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
