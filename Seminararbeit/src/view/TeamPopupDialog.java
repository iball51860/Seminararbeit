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
	private JLabel success;
	private JButton close;
	
	
	public TeamPopupDialog(ServerWindow serverWindow, Team t)
	{
		setUndecorated(true);
		setSize(200, 180);
		setLocation(serverWindow.getLocationOnScreen().x + 300, serverWindow.getLocationOnScreen().y + 150); //TODO set Location in relation to invoking Button
		
		c = getContentPane();
		c.setLayout(new GridLayout(0, 1));
		
		name = new JLabel(t.getName());
		ip = new JLabel(t.getClientSocket().getInetAddress().toString());
		strength = new JLabel("Left: " + t.getStrength()[0] + " | Middle: " + t.getStrength()[1] + " | Right: " + t.getStrength()[2]);
		goals = new JLabel("Goals: " + t.getGoals());
		victories = new JLabel("Victories: " + t.getWonMatches());
		int rate = (int) ((double)t.getGoals() * 100 / (double)t.getFinishedShots());
		success = new JLabel("Success Rate: " + rate + " %");
		close = new JButton("close");
		close.addActionListener(new ClosePopupListener(serverWindow, this));
		close.addKeyListener(new ClosePopupListener(serverWindow, this));
		
		c.add(name);
		c.add(ip);
		c.add(strength);
		c.add(goals);
		c.add(victories);
		c.add(success);
		c.add(close);
		
	}
	
	
}
