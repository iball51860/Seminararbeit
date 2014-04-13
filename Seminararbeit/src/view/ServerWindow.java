package view;

import java.awt.*;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
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
	
	//JPanel west;
	private JPanel west;
	private JPanel testClientPanel;
	private JButton startButton;
	private JButton finishButton;
	private JButton resetServer;
	private JButton addTestClients;
	private JButton plusTestClient;
	private JProgressBar progress;
	 
	
	//TabbedPane
	private JTabbedPane tabPane;
	
	//TabbelPane Start
	private JPanel startPanel;
	private JLabel showIp;
	private JLabel showPort;
	
	//TabbedPane Team-Matrix
	private JPanel teamView;
	
	//TabbedPane Result-List
	private JPanel result;
	private JTextArea resultList;
	private JScrollPane spResultList;
	private JButton updateResult;
	
	//TabbedPane Log
	private JPanel log;
	private JPanel logSettings;
	private JTextArea logConsole;
	private JLabel infoLog;
	private JComboBox<String> teamBox1;
	private JComboBox<String> teamBox2;
	public JCheckBox[] type;
	private JButton saveLog;
	
	//Label for Meta Data
	private JLabel currentRound;
	private JLabel noOfClients;
	private JLabel serverIP;
	private JLabel serverPort;
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
		setSize(1000, 600);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//create right panel with information about the tournament
		west = new JPanel(new GridLayout(0, 1));
		c.add(west, BorderLayout.WEST);
		west.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		//create Buttons for starting game
		startButton = new JButton("Start Game");
		startButton.addActionListener(new StartTournamentListener(ServerWindow.this));
		startButton.setEnabled(false);
		west.add(startButton);
		
		//create panels and Buttons for starting TestClients
		testClientPanel = new JPanel(new BorderLayout());
		addTestClients = new JButton("Add Test Clients");
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
			serverIP = new JLabel("IP-Address: " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		west.add(serverIP);
		serverPort = new JLabel("Port: XXXX");
		west.add(serverPort);
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
		shotsPerMatch = new JLabel("Shots per Match: ");
		west.add(shotsPerMatch);
		
		//create finishButton for interrupting Game after the current round
		finishButton = new JButton("Interrupt Game");
		finishButton.addActionListener(new FinishGameListener());
		west.add(finishButton);
		
		//create reset-Server-Button
		resetServer = new JButton("Reset Server");
		resetServer.addActionListener(new ResetServerListener(ServerWindow.this));
		west.add(resetServer);
		
		
		//create ProgressBar
		progress = new JProgressBar();
		c.add(progress, BorderLayout.SOUTH);
		//progress.setStringPainted(true);
		
		
		//create JTabbedPane 
		tabPane = new JTabbedPane(JTabbedPane.TOP);
		c.add(tabPane, BorderLayout.CENTER);
		
		//create Panel for "Start"
		startPanel = new JPanel(new GridLayout(2, 1));
		try {
			showIp = new JLabel("IP-Address: " + InetAddress.getLocalHost().getHostAddress());
			showIp.setFont(new Font("Arial", Font.BOLD, 55));
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		showPort = new JLabel("Port: XXXX");
		showPort.setFont(new Font("Arial", Font.BOLD, 55));
		startPanel.add(showIp);
		startPanel.add(showPort);
		
		//create Panel for "Team-Matrix"
		teamView = new JPanel();
		
		//create Panel for "Result-List"
		resultList = new JTextArea();
		resultList.setEditable(false);
		updateResult = new JButton("Update Result");
		updateResult.setEnabled(false);
		updateResult.addActionListener(new UpdateResultListener(ServerWindow.this));
		spResultList = new JScrollPane();
		spResultList.setViewportView(resultList);
		result = new JPanel(new BorderLayout());
		result.add(spResultList, BorderLayout.CENTER);
		result.add(updateResult, BorderLayout.SOUTH);
		
		//create Pane for "Log"
		log = new JPanel(new BorderLayout());
		logConsole = new JTextArea();
		logConsole.setEditable(false);
		JScrollPane spLog = new JScrollPane();
		spLog.setViewportView(logConsole);
		logSettings = new JPanel(new GridLayout(0, 1));
		infoLog = new JLabel("Show:");
		UpdateLogListener updateLogListener = new UpdateLogListener(ServerWindow.this);
		teamBox1 = new JComboBox<String>();
		teamBox1.addItem("No Teams");
		teamBox1.addItem("All Teams");
		teamBox1.addActionListener(updateLogListener);
		teamBox1.addActionListener(new TeamBoxListener(ServerWindow.this));
		teamBox2 = new JComboBox<String>();
		teamBox2.addItem("No Team");
		teamBox2.setEnabled(false);
		teamBox2.addActionListener(updateLogListener);
		logSettings.add(infoLog);
		logSettings.add(teamBox1);
		logSettings.add(teamBox2);
		type = new JCheckBox[7];
		type[0] = new JCheckBox("Default");
		type[1] = new JCheckBox("Server");
		type[2] = new JCheckBox("Communication");
		type[3] = new JCheckBox("Game");
		type[4] = new JCheckBox("Round");
		type[5] = new JCheckBox("Match");
		type[6] = new JCheckBox("Shot");
		for(JCheckBox jCB : type)
		{
			jCB.setSelected(true);
			jCB.addActionListener(updateLogListener);
			logSettings.add(jCB);
		}
		logSettings.remove(type[0]);
		logSettings.remove(type[6]);
		saveLog = new JButton("Save Log");
		saveLog.addActionListener(new SaveLogListener(ServerWindow.this));
		saveLog.setVisible(true);
		log.add(spLog, BorderLayout.CENTER);
		log.add(logSettings, BorderLayout.EAST);
		log.add(saveLog, BorderLayout.SOUTH);
		
		
//		tabPane.add("Matrix", teamView);
		tabPane.add("Start", startPanel);
		tabPane.add("Result", result);
		tabPane.add("Log", log);
		tabPane.addChangeListener(new TabbedPaneListener(ServerWindow.this));
		
		
		//create popup dialog to request Port
		popup = new PopupDialogPort(ServerWindow.this);
		popup.setVisible(true);
		
		setVisible(true);
		setEnabled(false);
			}
		});
		Logger.setTarget(this);
	}
	
	
//	public void redirectConsoleOutput() {
//		// Redirect console output to TextArea
//
//		final JTextArea area = logString;
//		PrintStream stream = new PrintStream(System.out) {
//
//			@Override
//			public void print(String s) {
//				area.append(s + "\n");
//			}
//		};
//		System.setOut(stream);
//	}
	
	
	/**
	 * initialise the teamView-Panel: Each team gets a Button with a color (green = inGame, red = game over)
	 * @param tSet
	 */
	public void updateTeamView(final Tournament t)
	{
		this.tournament = t;
		int noOfShotsLog = (int) (Math.log(t.getNoOfShots()) / Math.log(2) * 1000);
		int noOfShotsPerMatchLog = (int) (Math.log(t.getNoOfShotsPerMatch()) / Math.log(2) * 1000);
		progress.setMaximum(noOfShotsLog - noOfShotsPerMatchLog);
		this.teamSet = t.getPlaying().clone();
		final ArrayTeamSet<Team> clone = this.teamSet;
		final int size = (int) Math.ceil(Math.sqrt(this.teamSet.size()));
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					
					teamView.removeAll();
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
							teamBox1.addItem(t.getName() + t.getID());
							teamBox2.addItem(t.getName() + t.getID());
						}
					}
					updateResultList();
					updateResult.setEnabled(true);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						if (!t.isInGame()) {
							teamButtons[t.getID()].setBackground(Color.RED);
							teamButtons[t.getID()].setForeground(Color.BLACK);
						} else {
							teamButtons[t.getID()].setBackground(Color.GREEN);
							teamButtons[t.getID()].setForeground(Color.BLACK);
						}
						teamButtons[t.getID()].repaint();
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateTeamInMatchView(final Team a, final Team b)
	{
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					if (!a.getName().equals("bottt")) {
						teamButtons[a.getID()].setBackground(Color.BLUE);
						teamButtons[a.getID()].setForeground(Color.WHITE);
						teamButtons[a.getID()].repaint();
					}
					if (!b.getName().equals("bottt")) {
						teamButtons[b.getID()].setBackground(Color.BLUE);
						teamButtons[b.getID()].setForeground(Color.WHITE);
						teamButtons[b.getID()].repaint();
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
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
		//try {
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
				}
			});
//		} catch (InvocationTargetException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	public void updateShots(final Tournament t)
	{
		{
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						ServerWindow.this.noOfGoals.setText("Goals: " + t.getGoals());
						int noOfFinishedShotsLog = (int) (Math.log(t.getFinishedShots()) / Math.log(2) * 1000);
						int noOfShotsPerMatchLog = (int) (Math.log(t.getNoOfShotsPerMatch()) / Math.log(2) * 1000);
						progress.setValue(noOfFinishedShotsLog - noOfShotsPerMatchLog);
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
			long avg;
			sb.append(count++ + ". " + t.getName() + "\t" + t.getWonMatches() + " Victories\t" + t.getGoals() + " Goals\t" +
					"Success Rate: " + rate + " %\t" + " Goal Difference: " + (t.getGoals()-t.getGoalsAgainst()) + "\tAvg. Reaction: " + 
					(avg = t.getAvgReactionTime()) + " ms\tStandard Deviation: " + t.getStandardDeviation(avg) + "ms\n");
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				resultList.setText(sb.toString());
				resultList.setCaretPosition(0);
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
	
	public void appendLogLine(final LogLine ll)
	{
		boolean validInstance = (ll.getInstanceName() == null || getTeamBox1().getSelectedItem().toString().equalsIgnoreCase("All Teams") ||
					getTeamBox1().getSelectedItem().toString().equals(ll.getInstanceName() + ll.getInstanceID()) || 
					getTeamBox2().getSelectedItem().toString().equals(ll.getInstanceName() + ll.getInstanceID()));
		
		//check whether Log is selected, type applies, and message is about a blocked instance a la (LogIsShowing && typeIsSelected && (name==null || allTeams || nameIsInBox1 || nameIsInBox2) )
		if(tabPane.getComponentAt(2).isShowing() && type[ll.getType()].isSelected() && validInstance)
		{
			SwingUtilities.invokeLater(new Runnable(){
				public void run()
				{
					logConsole.append("\n" + ll.getMessage());
				}
			});
		}
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
	
	public JTextArea getLogString(){
		return logConsole;
	}
	
	public JButton getSaveLog() {
		return saveLog;
	}
	
	public JTextArea getLogConsole() {
		return logConsole;
	}
	
//	public JCheckBox[] getType(){		//TODO Methode zum laufen kriegen | Ich hab keine Ahnung warum die nicht will
//		return type;
//	}


	/**
	 * @return the teamSet of the ServerWindow in natural Order
	 */
	public ArrayTeamSet<Team> getTeamSet() {
		Collections.sort(teamSet);
		return teamSet;
	}


	public JLabel getServerPort() {
		return serverPort;
	}


	public JTextArea getResultList() {
		return resultList;
	}
	
	public JLabel getShowPort() {
		return showPort;
	}
	
	public JTabbedPane getTabPane() {
		return tabPane;
	}
	
	public JPanel getTeamView() {
		return teamView;
	}
	
}
