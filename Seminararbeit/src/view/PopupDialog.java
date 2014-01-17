package view;

import javax.swing.*;

import java.awt.*;

@SuppressWarnings("serial")
public class PopupDialog extends JDialog
{
	
	Container c;
	JButton start;
	JTextField input;
	JLabel info;
	
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
	
	
	public JTextField getInput(){
		return input;
	}
	
	public JLabel getInfoLabel(){
		return this.info;
	}

}
