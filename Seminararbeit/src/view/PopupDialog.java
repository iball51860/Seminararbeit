package view;

import javax.swing.*;

import java.awt.*;

/**
 * Generic Dialog to retrieve single Datatype information from the user. 
 * Known inheriting classes: {@link PopupDialogPort}, {@link PopupDialogShots}, {@link PopupDialogTestClients}
 * @author Jan Fritze & Manuel Kaiser<br>
 */
@SuppressWarnings("serial")
public class PopupDialog extends JDialog
{
	/**Base Container of the Popup*/
	Container c;
	/**Button to confirm input*/
	JButton start;
	/**TextField to type information*/
	JTextField input;
	/**Label asking for the information*/
	JLabel info;
	
	/**
	 * Constructs a simple Popup at the top in the middle of the ServerWindow asking for Simple Information, such as an Integer or a String.
	 * @param serverWindow
	 */
	public PopupDialog(ServerWindow serverWindow)
	{
		this();
		final int x = (int) (serverWindow.getLocation().getX() + (serverWindow.getWidth()/2) - (getWidth()/2));
		final int y = (int) (serverWindow.getLocation().getY() + 22);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PopupDialog.this.setLocation(x, y);
			}
		});
	}

	/**
	 * Creates a simple Popup asking for Simple Information such as an Integer or a String.
	 */
	public PopupDialog()
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dispose();
				setUndecorated(true);
				pack();
				setAlwaysOnTop(true);
				setSize(200, 100);
				c = getContentPane();
				c.setLayout(new GridLayout(3, 1));
				info = new JLabel();
				info.setHorizontalAlignment(SwingConstants.CENTER);
				input = new JTextField();
				start = new JButton();
				c.add(info);
				c.add(input);
				c.add(start);
			}
		});	
	}
	
	/**
	 * 
	 * @return TextField with Input
	 */
	public JTextField getInput(){
		return input;
	}
	
	/**
	 * 
	 * @return Label
	 */
	public JLabel getInfoLabel(){
		return this.info;
	}

}
