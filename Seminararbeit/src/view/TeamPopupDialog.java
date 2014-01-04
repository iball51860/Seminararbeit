package view;

import java.awt.*;
import javax.swing.*;

import viewControl.*;
import model.*;

public class TeamPopupDialog extends JDialog
{
	
	private Container c;
	
	private JLabel name;
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
		c.setLayout(new GridLayout(5, 1));
		
		name = new JLabel(t.getName());
		strength = new JLabel("Left: " + t.getStrength()[0] + " | Middle: " + t.getStrength()[1] + " | Right: " + t.getStrength()[2]);
		goals = new JLabel("Goals: " + t.getGoals());
		points = new JLabel("Points: " + t.getPoints());
		//info.setHorizontalAlignment(SwingConstants.CENTER);
		close = new JButton("close");
		close.addActionListener(new CloseTeamPopupListener(serverWindow, this));
		close.addKeyListener(new CloseTeamPopupListener(serverWindow, this));
		
		c.add(name);
		c.add(strength);
		c.add(goals);
		c.add(points);
		c.add(close);
		
	}
	
	
}
