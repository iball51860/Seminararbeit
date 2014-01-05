package view;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import model.*;
import control.*;
import viewControl.*;

public class ServerWindow extends JFrame {
	
	
	private WMServer wmServer;
	private Tournament tournament;
	
	//private JPanel north;
	private JPanel west;
	private JPanel testClientPanel;
	private JButton startButton;
	private JButton resetServer;
	private JButton showResult;
	private JButton showLogButton;
	private JButton addTestClients;
	private JButton plusTestClient;
	private JProgressBar progress;
	
	JTabbedPane tabPane;
	private JPanel teamView;
	private JTextArea resultList;
	
	private JLabel currentRound;
	private JLabel noOfClients;
	private JLabel noOfTestClients;
	private JLabel noPlaying;
	private JLabel noOfPlayedMatches;
	private JLabel noOfGoals;
	
	private PopupDialogPort popup;
	
	ArrayTeamSet<Team> teamSet;
	JButton[] teamButtons;
	
	
	public ServerWindow()
	{
		//set up basic frame
		super();
		setTitle("WM Server");
		setSize(800, 600);
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
		
		//create Buttons for starting game, show Result list and show log
		startButton = new JButton("Start Game");
		startButton.addActionListener(new StartTournamentListener(this));
		startButton.setEnabled(false);
		west.add(startButton);
		showResult = new JButton("Update Result");
		showResult.addActionListener(new UpdateResultListener(this));
		west.add(showResult);
		showLogButton = new JButton("Show Log");
		showLogButton.addActionListener(new ShowLogListener());
		west.add(showLogButton);
		
		//create panels and Buttons for starting TestClients
		testClientPanel = new JPanel(new BorderLayout());
		addTestClients = new JButton("add Test Clients");
		addTestClients.addActionListener(new AddTestClientsListener(this));
		plusTestClient = new JButton("+");
		plusTestClient.addActionListener(new PopupTestClientsListener(this));
		west.add(testClientPanel);
		testClientPanel.add(addTestClients, BorderLayout.CENTER);
		testClientPanel.add(plusTestClient, BorderLayout.EAST);
				
		//create Labels for information
		currentRound = new JLabel("Round No: 0");
		west.add(currentRound);
		noOfClients = new JLabel("Clients at Server: 0");
		west.add(noOfClients);
		noOfTestClients = new JLabel("TestClients at Server: 0");
		west.add(noOfTestClients);
		noPlaying = new JLabel("Teams playing: 0");
		west.add(noPlaying);
		noOfPlayedMatches = new JLabel("Matches played: 0");
		west.add(noOfPlayedMatches);
		noOfGoals = new JLabel("Goals: 0");
		west.add(noOfGoals);
		
		//create reset-Server-Button
		resetServer = new JButton("Reset Server");
		resetServer.addActionListener(new ResetServerListener(this));
		west.add(resetServer);
		
		
		//create ProgressBar
		progress = new JProgressBar();
		c.add(progress, BorderLayout.SOUTH);
		//progress.setStringPainted(true);
		
		//create JTabedPane for "Team-Matrix" and "Result-List"
		tabPane = new JTabbedPane(JTabbedPane.TOP);
		c.add(tabPane, BorderLayout.CENTER);
		teamView = new JPanel();
		resultList = new JTextArea();
		ScrollPane sp = new ScrollPane();
		resultList.setEditable(false);
		sp.add(resultList);
		tabPane.addTab("Matrix", teamView);
		tabPane.addTab("Result", sp);
		
		setVisible(true);
		setEnabled(false);
		
		//create popup dialog to request Port
		popup = new PopupDialogPort(this);
		popup.setVisible(true);
		
	}
	
	/**
	 * initialise the teamView-Panel: Each team gets a Button with a color (green = inGame, red = game over)
	 * @param teamSet
	 */
	public void updateTeamView(ArrayTeamSet<Team> teamSet)
	{
		teamView.removeAll();
		this.teamSet = teamSet.clone();
		int size = (int) Math.ceil(Math.sqrt(teamSet.size()));
		teamView.setLayout(new GridLayout(size, size, 1, 1));
		teamButtons = new JButton[Team.getCount() + 1];
		Iterator<Team> it = teamSet.iterator();
		while(it.hasNext())
		{
			Team t = it.next();
			teamButtons[t.getID()] = new JButton(t.getName());
			teamButtons[t.getID()].setBackground(Color.GREEN);
			teamButtons[t.getID()].setOpaque(true);
			teamButtons[t.getID()].setBorderPainted(false);
			teamButtons[t.getID()].addActionListener(new ShowTeamListener(this, t));
			teamButtons[t.getID()].setToolTipText(t.getName());
			teamView.add(teamButtons[t.getID()]);
		}
		updateResultList();
		teamView.updateUI();
	}
	
	/**
	 * If Team t is still in Game set Color of it's Button to green 
	 * else set Color to red
	 * @param t
	 */
	public void updateTeamView(Team t)
	{
		if(!t.isInGame())
		{
			teamButtons[t.getID()].setBackground(Color.RED);
		}
		else
		{
			teamButtons[t.getID()].setBackground(Color.GREEN);
		}
		teamView.updateUI();
	}
	
	public void updateClientsAtServer(int clientsAtServer)
	{
		this.noOfClients.setText("Clients at server: " + clientsAtServer);
		if(clientsAtServer >=2)
		{
			this.startButton.setEnabled(true);
		}
	}
	
	/**
	 * removes all teams who loose from the teamView-Panel
	 * 
	 * @deprecated
	 */
	public void removeLoosingTeams()
	{
		teamView.removeAll();
		Iterator<Team> it = teamSet.iterator();
		teamButtons = new JButton[teamSet.size() + 1];
		while(it.hasNext())
		{
			Team t = it.next();
			if(t.isInGame())
			{
				teamButtons[t.getID()] = new JButton(t.getName());
				teamButtons[t.getID()].setBackground(Color.GREEN);
				teamButtons[t.getID()].setOpaque(true);
				teamButtons[t.getID()].setBorderPainted(false);
				teamButtons[t.getID()].addActionListener(new ShowTeamListener(this, t));
				teamButtons[t.getID()].setToolTipText(t.getName());
				teamView.add(teamButtons[t.getID()]);
			}
		}
	}
	
	
	public void updateMetaData(Tournament t)
	{
		this.tournament = t;
		this.currentRound.setText("Round No: " + t.getCurrentRound());
		this.noPlaying.setText("Teams playing: " + t.getPlaying().size());
		this.noOfPlayedMatches.setText("Matches played: " + t.getFinishedMatches() + " / " + t.getNoOfMatches());
		this.noOfGoals.setText("Goals: " + t.getGoals());
		progress.setMaximum(t.getNoOfShots());
		progress.setValue(t.getFinishedShots());
	}
	
	
	public void updateResultList()
	{
		int count = 1;
		Collections.sort(teamSet);
		StringBuffer sb = new StringBuffer();
		for(Team t : teamSet)
		{
			sb.append(count++ + ". | " + t.getName() + " | " + t.getWonMatches() + " victories | " + t.getGoals() + " goals | " +
					"" + (t.getGoals()-t.getGoalsAgainst()) + " Goal Difference\n");
		}
		resultList.setText(sb.toString());
	}
	
	
	public void updateNoOfTestClients(int testClients)
	{
		this.noOfTestClients.setText("TestClients at Server: " + testClients);
	}
	
	
	
	//////////////////////// Getter and Setter ////////////////////////
	
	public void setWMServer(WMServer wmServer){
		this.wmServer = wmServer;
	}
	
	public WMServer getWMServer(){
		return this.wmServer;
	}
	
	public JButton getStartButton(){
		return startButton;
	}
	
	public Tournament getTournament(){
		return this.tournament;
	}
	
}
