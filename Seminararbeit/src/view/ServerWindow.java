package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import control.*;

import viewControl.*;

public class ServerWindow extends JFrame {
	
	
	private WMServer wmServer;
	
	//private JPanel north;
	private JPanel west;
	private JPanel testClientPanel;
	private JButton startButton;
	private JButton showLogButton;
	private JButton addTestClients;
	private JButton plusTestClient;
	
	JLabel noOfRound;
	JLabel noOfContestants;
	JLabel noOfTestClients;
	JLabel noOfPlayedMatches;
	JLabel noOfGoals;
	
	private PopupDialogPort popup;
	
	
	public ServerWindow()
	{
		//set up basic frame
		super();
		setTitle("WM Server");
		setSize(800, 600);
		setLocation(200, 100);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//create topbar with buttons for control
		//north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//c.add(north, BorderLayout.NORTH);
		
		//create right panel with information about the tournament
		west = new JPanel(new GridLayout(0, 1));
		c.add(west, BorderLayout.WEST);
		west.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		//create Buttons for starting game and show log
		startButton = new JButton("Start Game");
		startButton.addActionListener(new StartTournamentListener(this));
		west.add(startButton);
		showLogButton = new JButton("Show Log");
		showLogButton.addActionListener(new ShowLogListener());
		west.add(showLogButton);
		
		//create panel and Buttons for starting TestClients
		testClientPanel = new JPanel(new BorderLayout());
		addTestClients = new JButton("add Test Clients");
		addTestClients.addActionListener(new AddTestClientsListener(this));
		plusTestClient = new JButton("+");
		plusTestClient.addActionListener(new PopupTestClientsListener(this));
		west.add(testClientPanel);
		testClientPanel.add(addTestClients, BorderLayout.CENTER);
		testClientPanel.add(plusTestClient, BorderLayout.EAST);

		
		//create Labels for information
		noOfRound = new JLabel("Round No.: xx");
		west.add(noOfRound);
		noOfContestants = new JLabel("Contestants in Game: xx");
		west.add(noOfContestants);
		noOfTestClients = new JLabel("TestClients in Game: xx");
		west.add(noOfTestClients);
		noOfPlayedMatches = new JLabel("Matches played: xx");
		west.add(noOfPlayedMatches);
		noOfGoals = new JLabel("Goals: xx");
		west.add(noOfGoals);
		
		setVisible(true);
		setEnabled(false);
		
		//create popup dialog to request Port
		popup = new PopupDialogPort(this);
		popup.setSize(200, 100);
		popup.setLocation(this.getLocationOnScreen().x + 100, this.getLocationOnScreen().y + 22); //TODO center popoup independently of ServerWindow size
		popup.setVisible(true);
		
	}
	
	public void updateView()
	{
		
	}
	
	public void updateNoOfRound(int round) 
	{
		this.noOfRound.setText("Round No.: " + round);
	}

	public void updateNoOfContestants(int contestants) 
	{
		this.noOfContestants.setText("Contestants in Game: " + contestants);
	}
	
	public void updateNoOfTestClients(int testClients) 
	{
		this.noOfTestClients.setText("TestClients in Game: " + testClients);
	}

	public void updateNoOfPlayedMatches(int playedMatches) 
	{
		this.noOfPlayedMatches.setText("Matches played: " + playedMatches);
	}

	public void updateNoOfGoals(int goals) 
	{
		this.noOfGoals.setText("Goals: " + goals);
	}
	
	
	//////////////////////// Getter and Setter ////////////////////////
	
	public void setWMServer(WMServer wmServer){
		this.wmServer = wmServer;
	}
	
	public WMServer getWMServer(){
		return this.wmServer;
	}
	
}
