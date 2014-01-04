package view;

import java.awt.*;
import javax.swing.*;

import viewControl.*;
import model.*;

public class TeamPopupDialog extends JDialog
{
	
	private Container c;
	
	private JLabel name;
	private JLabel ip;
	private JLabel strength;
	private JLabel goals;
	private JLabel points;
	private JButton close;
	
	private ServerWindow serverWindow;
	
	
	public TeamPopupDialog(ServerWindow serverWindow, Team t)
	{
		setUndecorated(true);
		this.serverWindow = serverWindow;
		
		c = getContentPane();
		c.setLayout(new GridLayout(6, 1));
		
		name = new JLabel(t.getName());
		ip = new JLabel(t.getSocket().getInetAddress().toString());
		strength = new JLabel("Left: " + t.getStrength()[0] + " | Middle: " + t.getStrength()[1] + " | Right: " + t.getStrength()[2]);
		goals = new JLabel("Goals: " + t.getGoals());
		points = new JLabel("Points: " + t.getPoints());
		//info.setHorizontalAlignment(SwingConstants.CENTER);
		close = new JButton("close");
		close.addActionListener(new ClosePopupListener(serverWindow, this));
		close.addKeyListener(new ClosePopupListener(serverWindow, this));
		
		c.add(name);
		c.add(ip);
		c.add(strength);
		c.add(goals);
		c.add(points);
		c.add(close);
		
	}
	
	
}
