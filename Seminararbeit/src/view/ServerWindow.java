package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import viewControl.*;

public class ServerWindow extends JFrame {
	
	public ServerWindow()
	{
		//set up basic frame
		super();
		setTitle("WM Server");
		setSize(400, 300);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//create topbar with buttons for control
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		c.add(north, BorderLayout.NORTH);
		
		JButton startButton = new JButton("Start Game");
		startButton.addActionListener(new StartTournamentListener());
		north.add(startButton);
		JButton showLogButton = new JButton("Show Log");
		showLogButton.addActionListener(new ShowLogListener());
		north.add(showLogButton);

		//create right panel with information about the tournament
		JPanel east = new JPanel(new GridLayout(0, 1));
		c.add(east, BorderLayout.EAST);
		
		east.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		JLabel noOfRound = new JLabel("Round No. xx");
		east.add(noOfRound);
		JLabel noOfContestants = new JLabel("Contestants in Game: xx");
		east.add(noOfContestants);
		JLabel noOfGroups = new JLabel("Groups playing: xx");
		east.add(noOfGroups);
		JLabel noOfPlayedMatches = new JLabel("Matches played: xxxxx");
		east.add(noOfPlayedMatches);
		JLabel noOfGoals = new JLabel("Goals: xxxxxx");
		east.add(noOfGoals);
		
		
		setVisible(true);
	}
}
