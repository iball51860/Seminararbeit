package view;

import java.awt.*;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
	private JLabel serverInfo;
	private JLabel noOfTestClients;
	private JLabel noPlaying;
	private JLabel noOfPlayedMatches;
	private JLabel noOfGoals;
	private JLabel successRate;
	private JLabel shotsPerMatch;
	
	private PopupDialogPort popup;
	
	private ArrayTeamSet<Team> teamSet;
	private JButton[] teamButtons;
	
	
	public ServerWindow()
	{
		super();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
						//set up basic frame

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
		startButton.addActionListener(new StartTournamentListener(ServerWindow.this));
		startButton.setEnabled(false);
		west.add(startButton);
		
		//create panels and Buttons for starting TestClients
		testClientPanel = new JPanel(new BorderLayout());
		addTestClients = new JButton("add Test Clients");
		addTestClients.addActionListener(new AddTestClientsListener(ServerWindow.this));
		plusTestClient = new JButton("+");
		plusTestClient.addActionListener(new PopupTestClientsListener(ServerWindow.this));
		west.add(testClientPanel);
		testClientPanel.add(addTestClients, BorderLayout.CENTER);
		testClientPanel.add(plusTestClient, BorderLayout.EAST);
				
		//create Labels for information
		currentRound = new JLabel("Round: 0");
		west.add(currentRound);
		noOfClients = new JLabel("Clients at Server: 0");
		west.add(noOfClients);
		try {
			serverInfo = new JLabel("Address (IP : Port): " + InetAddress.getLocalHost().getHostAddress() + " : ");
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		west.add(serverInfo);
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
		resetServer.addActionListener(new ResetServerListener(ServerWindow.this));
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
		updateResult.addActionListener(new UpdateResultListener(ServerWindow.this));
		JScrollPane spResultList = new JScrollPane();
		spResultList.add(resultList);
		spResultList.setViewportView(resultList);
		result = new JPanel(new BorderLayout());
		result.add(spResultList, BorderLayout.CENTER);
		result.add(updateResult, BorderLayout.SOUTH);
		
		log = new JPanel(new BorderLayout());
		logString = new JTextArea();
		logString.setEditable(false);
		JScrollPane spLog = new JScrollPane();
		spLog.add(logString);
		spLog.setViewportView(logString);
		logSettings = new JPanel(new GridLayout(0, 1));
		infoLog = new JLabel("Show:");
		teamBox1 = new JComboBox<String>();
		teamBox1.addItem("no Team");
		teamBox1.addItem("all Teams");
		teamBox2 = new JComboBox<String>();
		teamBox2.addItem("no Team");
		teamBox2.setEnabled(false);
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
		log.add(spLog, BorderLayout.CENTER);
//		log.add(logSettings, BorderLayout.EAST);
//		log.add(updateLog, BorderLayout.SOUTH);
		
		tabPane.add("Matrix", teamView);
		tabPane.add("Result", result);
		tabPane.add("Log", log);
		tabPane.addChangeListener(new TabbedPaneListener(ServerWindow.this));
		
		//create popup dialog to request Port
		popup = new PopupDialogPort(ServerWindow.this);
		popup.setVisible(true);
		
		redirectConsoleOutput();
		
		setVisible(true);
		setEnabled(false);
			}
		});
	}
	
	
	public void redirectConsoleOutput() {
		// Redirect console output to TextArea

		final JTextArea area = logString;
		PrintStream stream = new PrintStream(System.out) {

			@Override
			public void print(String s) {
				area.append(s + "\n");
			}
		};
		System.setOut(stream);
	}
	
	
	/**
	 * initialise the teamView-Panel: Each team gets a Button with a color (green = inGame, red = game over)
	 * @param tSet
	 */
	public void updateTeamView(final Tournament t)
	{
		teamView.removeAll();
		this.tournament = t;
		progress.setMaximum(t.getNoOfShots());
		Thread progressThread = new Thread(){
			public void run(){
				while(t.isRunning()){
					updateShots(t);
					try {
						sleep(30);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		};
		progressThread.start();
		this.teamSet = t.getPlaying().clone();
		final ArrayTeamSet<Team> clone = this.teamSet;
		final int size = (int) Math.ceil(Math.sqrt(this.teamSet.size()));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				//Orders from updateMetaData() for the first setup
				ServerWindow.this.currentRound.setText("Round: "
						+ Analyser.getCurrentRoundName(t));
				ServerWindow.this.noPlaying.setText("Teams playing: "
						+ t.getPlaying().size());
				ServerWindow.this.noOfPlayedMatches.setText("Matches played: "
						+ t.getFinishedMatches() + " / " + t.getNoOfMatches());
				ServerWindow.this.noOfGoals.setText("Goals: " + t.getGoals());
				int rate = (int) ((double) t.getGoals()
						/ (double) t.getFinishedShots() * 100);
				ServerWindow.this.successRate.setText("Success Rate: " + rate
						+ " %");
				ServerWindow.this.shotsPerMatch.setText("Shots per Match: "
						+ t.getNoOfShotsPerMatch());
				
				teamView.setLayout(new GridLayout(size, size, 1, 1));
				teamButtons = new JButton[Team.getCount() + 1];
				Iterator<Team> it = clone.iterator();
				while (it.hasNext()) {
					Team t = it.next();
					if (!t.getName().equals("bottt"))
					{
						teamButtons[t.getID()] = new JButton(t.getName());
						teamButtons[t.getID()].setBackground(Color.GREEN);
						teamButtons[t.getID()].setOpaque(true);
						teamButtons[t.getID()].setBorderPainted(false);
						teamButtons[t.getID()]
								.addActionListener(new ShowTeamListener(
										ServerWindow.this, t));
						teamButtons[t.getID()].setToolTipText(t.getName()
								+ t.getID());
						teamView.add(teamButtons[t.getID()]);
						teamBox1.addItem(t.getName());
						teamBox2.addItem(t.getName());
					}
				}
				//updateResultList();
				//teamView.updateUI();
			}
		});
	}
	
	/**
	 * If Team t is still in Game set Color of it's Button to green 
	 * else set Color to red
	 * @param t
	 */
	public void updateTeamView(final Team t)
	{
		if(!t.getName().equals("bottt"))
		{
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (!t.isInGame()) {
						teamButtons[t.getID()].setBackground(Color.RED);
					} else {
						teamButtons[t.getID()].setBackground(Color.GREEN);
					}
					teamView.updateUI();
				}
			});
		}
	}
	
	public void updateTeamInMatchView(final Team a, final Team b)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (!a.getName().equals("bottt")) {
					teamButtons[a.getID()].setBackground(Color.BLUE);
				}
				if (!b.getName().equals("bottt")) {
					teamButtons[b.getID()].setBackground(Color.BLUE);
				}
				teamView.updateUI();
			}
		});
	}
	
	public void updateClientsAtServer(int clientsAtServer)
	{
		this.noOfClients.setText("Clients at server: " + clientsAtServer);
		if(clientsAtServer >=2)
		{
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ServerWindow.this.startButton.setEnabled(true);
				}
			});
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
	
	
	public void updateMetaData(final Tournament t)
	{
		this.tournament = t;
		/*JLabel currentRound = this.currentRound;
		JLabel noPlaying = this.noPlaying;
		JLabel noOfPlayedMatches = this.noOfPlayedMatches;
		JLabel*/
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ServerWindow.this.currentRound.setText("Round: "
						+ Analyser.getCurrentRoundName(t));
				ServerWindow.this.noPlaying.setText("Teams playing: "
						+ t.getPlaying().size());
				ServerWindow.this.noOfPlayedMatches.setText("Matches played: "
						+ t.getFinishedMatches() + " / " + t.getNoOfMatches());
				ServerWindow.this.noOfGoals.setText("Goals: " + t.getGoals());
				int rate = (int) ((double) t.getGoals()
						/ (double) t.getFinishedShots() * 100);
				ServerWindow.this.successRate.setText("Success Rate: " + rate
						+ " %");
//				progress.setValue(t.getFinishedShots());
//				updateResult.setEnabled(true);
			}
		});
	}
	
	
	public void updateShots(final Tournament t)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ServerWindow.this.noOfGoals.setText("Goals: " + t.getGoals());
				progress.setValue(t.getFinishedShots());
			}
		});
	}
	
	
	public void updateResultList()
	{
		if(teamSet == null)
		{
			return;
		}
		int count = 1;
		Collections.sort(teamSet);
		final StringBuffer sb = new StringBuffer();
		for(Team t : teamSet)
		{
			int rate = (int) ((double)t.getGoals() * 100 / (double)t.getFinishedShots());
			sb.append(count++ + ". | " + t.getName() + " | " + t.getWonMatches() + " victories | " + t.getGoals() + " goals | " +
					"Success Rate: " + rate + " % | " + " Goal Difference:" + (t.getGoals()-t.getGoalsAgainst()) + " | Avg. Reaction:" + 
					t.getAvgReactionTime() + " ms\n");
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				resultList.setText(sb.toString());
			}
		});
	}
	
	
	public void updateNoOfTestClients(final int testClients)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ServerWindow.this.noOfTestClients
						.setText("TestClients at Server: " + testClients);
			}
		});
	}
	
	public void showFinish(){
		new FinishedWindow(this.tournament);
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


	/**
	 * @return the teamSet of the ServerWindow in natural Order
	 */
	public ArrayTeamSet<Team> getTeamSet() {
		Collections.sort(teamSet);
		return teamSet;
	}
	
}
