package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import control.*;

import viewControl.*;

public class ServerWindow extends JFrame {
	
	
	private WMServer wmServer;
	
	
	public ServerWindow()
	{
		//set up basic frame
		super();
		setTitle("WM Server");
		setSize(400, 300);
		setLocation(400, 200);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//create topbar with buttons for control
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		c.add(north, BorderLayout.NORTH);
		
		JButton startButton = new JButton("Start Game");
		startButton.addActionListener(new StartTournamentListener(this));
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
		setEnabled(false);
		
		//create popup dialog
		PopupDialog p = new PopupDialog(this);
		p.setSize(200, 100);
		p.setLocation(this.getLocationOnScreen().x + 100, this.getLocationOnScreen().y + 22); //TODO center popoup independently of ServerWindow size
		p.setVisible(true);
		
	}
	
	
	public void setWMServer(WMServer wmServer){
		this.wmServer = wmServer;
	}
	
	public WMServer getWMServer(){
		return this.wmServer;
	}
	
}
