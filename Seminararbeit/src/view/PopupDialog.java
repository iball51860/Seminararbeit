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
	
	
	public PopupDialog()
	{
		setUndecorated(true);
		
		c = getContentPane();
		c.setLayout(new GridLayout(3, 1));
		
		info = new JLabel();
		info.setHorizontalAlignment(SwingConstants.CENTER); //TODO Set something if Enter is hit
		input = new JTextField();
		start = new JButton();
		
		c.add(info);
		c.add(input);
		c.add(start);
		
	}
	
	
	public JTextField getInput(){
		return input;
	}
	

}
