package view;

import javax.swing.*;

import viewControl.*;

import java.awt.*;

public class PopupDialog extends JDialog
{
	
	private Container c;
	private JButton start;
	private JTextField portInput;
	private JLabel info;
	private StartServerListener startServer;
	
	public PopupDialog(ServerWindow serverWindow)
	{
		setUndecorated(true);
		
		c = getContentPane();
		c.setLayout(new GridLayout(3, 1));
		
		info = new JLabel("Port eingeben");
		info.setHorizontalAlignment(SwingConstants.CENTER); //TODO Set something if Enter is hit.
		portInput = new JTextField("4444");
		start = new JButton("Start Server");
		
		startServer = new StartServerListener(this, serverWindow);
		portInput.addActionListener(startServer);
		start.addActionListener(startServer);
		
		
		c.add(info);
		c.add(portInput);
		c.add(start);
		
		
		setVisible(true);
		
	}
	
	
	public JTextField getPortInput(){
		return portInput;
	}
	
}
