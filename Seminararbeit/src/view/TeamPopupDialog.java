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
	private JLabel victories;
	private JButton close;
	
	private ServerWindow serverWindow;
	
	
	public TeamPopupDialog(ServerWindow serverWindow, Team t)
	{
		setUndecorated(true);
		this.serverWindow = serverWindow;
		setSize(200, 180);
		setLocation(serverWindow.getLocationOnScreen().x + 300, serverWindow.getLocationOnScreen().y + 150); //TODO set Location in relation to invoking Button
		
		c = getContentPane();
		c.setLayout(new GridLayout(6, 1));
		
		name = new JLabel(t.getName());
		ip = new JLabel(t.getClientSocket().getInetAddress().toString());
		strength = new JLabel("Left: " + t.getStrength()[0] + " | Middle: " + t.getStrength()[1] + " | Right: " + t.getStrength()[2]);
		goals = new JLabel("Goals: " + t.getGoals());
		victories = new JLabel("Victories: " + t.getWonMatches());
		//info.setHorizontalAlignment(SwingConstants.CENTER);
		close = new JButton("close");
		close.addActionListener(new ClosePopupListener(serverWindow, this));
		close.addKeyListener(new ClosePopupListener(serverWindow, this));
		
		c.add(name);
		c.add(ip);
		c.add(strength);
		c.add(goals);
		c.add(victories);
		c.add(close);
		
	}
	
	
}
