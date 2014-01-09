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
	private JButton addTestClients;
	private JButton plusTestClient;
	private JProgressBar progress;
	
	//TabbedPane for the Team-Matrix, the Result-List and the Log
	JTabbedPane tabPane;
	private JPanel teamView;
	private JPanel result;
	private JTextArea resultList;
	private JButton updateResult;
	private JPanel log;
	private JPanel logSettings;
	private JTextArea logString;
	private JLabel infoLog;
	private JComboBox<String> teamBox1;
	private JComboBox<String> teamBox2;
	private JCheckBox[] type;
	private JButton updateLog;
	
	//Label for Meta Data
	private JLabel currentRound;
	private JLabel noOfClients;
	private JLabel noOfTestClients;
	private JLabel noPlaying;
	private JLabel noOfPlayedMatches;
	private JLabel noOfGoals;
	private JLabel successRate;
	private JLabel shotsPerMatch;
	
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
		currentRound = new JLabel("Round: 0");
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
		successRate = new JLabel("Success Rate: 0 %");
		west.add(successRate);
		shotsPerMatch = new JLabel("Shots per Match: wois i no et");
		west.add(shotsPerMatch);
		
		//create reset-Server-Button
		resetServer = new JButton("Reset Server");
		resetServer.addActionListener(new ResetServerListener(this));
		west.add(resetServer);
		
		
		//create ProgressBar
		progress = new JProgressBar();
		c.add(progress, BorderLayout.SOUTH);
		//progress.setStringPainted(true);
		
		//create JTabedPane for "Team-Matrix", "Result-List" and "Log"
		tabPane = new JTabbedPane(JTabbedPane.TOP);
		c.add(tabPane, BorderLayout.CENTER);
		teamView = new JPanel();
		
		resultList = new JTextArea();
		resultList.setEditable(false);
		updateResult = new JButton("Update Result");
		updateResult.setEnabled(false);
		updateResult.addActionListener(new UpdateResultListener(this));
		ScrollPane spResultList = new ScrollPane();
		result = new JPanel(new BorderLayout());
		result.add(spResultList, BorderLayout.CENTER);
		result.add(updateResult, BorderLayout.SOUTH);
		spResultList.add(resultList);
		
		log = new JPanel(new BorderLayout());
		logString = new JTextArea();
		logString.setEditable(false);
		ScrollPane spLog = new ScrollPane();
		spLog.add(logString);
		logSettings = new JPanel(new GridLayout(0, 1));
		infoLog = new JLabel("Show:");
		RefreshLogListener rLL = new RefreshLogListener(this);
		teamBox1 = new JComboBox<String>();
		teamBox1.addItem("no Team");
		teamBox1.addItem("all Teams");
		teamBox1.addActionListener(rLL);
		teamBox2 = new JComboBox<String>();
		teamBox2.addItem("no Team");
		teamBox2.setEnabled(false);
		teamBox2.addActionListener(rLL);
		logSettings.add(infoLog);
		logSettings.add(teamBox1);
		logSettings.add(teamBox2);
		
		type = new JCheckBox[7];
		type[0] = new JCheckBox("default");
		type[1] = new JCheckBox("Server");
		type[2] = new JCheckBox("Communication");
		type[3] = new JCheckBox("Game");
		type[4] = new JCheckBox("Round");
		type[5] = new JCheckBox("Match");
		type[6] = new JCheckBox("Shot");
		for(JCheckBox jCB : type)
		{
			logSettings.add(jCB);
		}
		updateLog = new JButton("Update Log");
		updateLog.addActionListener(rLL);
		log.add(spLog, BorderLayout.CENTER);
		log.add(logSettings, BorderLayout.EAST);
		log.add(updateLog, BorderLayout.SOUTH);
		
		tabPane.add("Matrix", teamView);
		tabPane.add("Result", result);
		tabPane.add("Log", log);
		tabPane.addChangeListener(new TabbedPaneListener(this));
		
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
			if(!t.getName().equals("bottt"));
			{
				teamButtons[t.getID()] = new JButton(t.getName());
				teamButtons[t.getID()].setBackground(Color.GREEN);
				teamButtons[t.getID()].setOpaque(true);
				teamButtons[t.getID()].setBorderPainted(false);
				teamButtons[t.getID()].addActionListener(new ShowTeamListener(this, t));
				teamButtons[t.getID()].setToolTipText(t.getName() + t.getID());
				teamView.add(teamButtons[t.getID()]);
				teamBox1.addItem(t.getName());
				teamBox2.addItem(t.getName());
			}
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
		if(!t.getName().equals("bottt"))
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
	}
	
	public void updateTeamInMatchView(Team a, Team b)
	{
		if(!a.getName().equals("bottt"))
		{
			teamButtons[a.getID()].setBackground(Color.BLUE);
		}
		if(!b.getName().equals("bottt"))
		{
			teamButtons[b.getID()].setBackground(Color.BLUE);
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
				teamButtons[t.getID()] = new JButton(t.getName() + t.getID());
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
		this.currentRound.setText("Round: " + Analyser.getCurrentRoundName(t));
		this.noPlaying.setText("Teams playing: " + t.getPlaying().size());
		this.noOfPlayedMatches.setText("Matches played: " + t.getFinishedMatches() + " / " + t.getNoOfMatches());
		this.noOfGoals.setText("Goals: " + t.getGoals());
		int rate = (int)((double)t.getGoals() / (double)t.getFinishedShots() * 100);
		this.successRate.setText("Success Rate: " + rate + " %");
		this.shotsPerMatch.setText("Shots per Match: " + t.getNoOfShotsPerMatch());
		progress.setMaximum(t.getNoOfShots());
		progress.setValue(t.getFinishedShots());
		updateResult.setEnabled(true);
	}
	
	
	public void updateResultList()
	{
		if(teamSet == null)
		{
			return;
		}
		int count = 1;
		Collections.sort(teamSet);
		StringBuffer sb = new StringBuffer();
		for(Team t : teamSet)
		{
			int rate = (int) ((double)t.getGoals() * 100 / (double)t.getFinishedShots());
			sb.append(count++ + ". | " + t.getName() + " | " + t.getWonMatches() + " victories | " + t.getGoals() + " goals | " +
					"Success Rate: " + rate + " % | " + (t.getGoals()-t.getGoalsAgainst()) + " Goal Difference\n");
		}
		resultList.setText(sb.toString());
	}
	
	
	public void refreshLog()
	{
		String[] teams = new String[2];
		teams[0] = (String) teamBox1.getSelectedItem();
		if(teamBox2.isEnabled())
		{
			teams[1] = (String) teamBox2.getSelectedItem();
		}
		boolean[] types = new boolean[7];
		for(int i = 0; i < type.length; i++)
		{
			types[i] = type[i].isSelected();
		}
		String s = Logger.getLog(teams, types);
		logString.setText(s);
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

	public JComboBox<String> getTeamBox1() {
		return teamBox1;
	}

	public JComboBox<String> getTeamBox2() {
		return teamBox2;
	}
	
}
