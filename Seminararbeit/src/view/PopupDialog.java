package view;

import javax.swing.*;

import viewControl.*;

import java.awt.*;

public class PopupDialog extends JDialog
{
	
	Container c;
	JButton start;
	JTextField input;
	JLabel info;
	
	public PopupDialog(ServerWindow serverWindow)
	{
		this();
		int x = (int) (serverWindow.getLocation().getX() + (serverWindow.getWidth()/2) - (getWidth()/2));
		int y = (int) (serverWindow.getLocation().getY() + 22);
		this.setLocation(x, y);
	}
	
	public PopupDialog()
	{
		setUndecorated(true);
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
	
	
	public JTextField getInput(){
		return input;
	}
	
	public JLabel getInfoLabel(){
		return this.info;
	}

}
