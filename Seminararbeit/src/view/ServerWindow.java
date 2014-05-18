package view;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import model.*;
import control.*;
import control.listeners.*;

@SuppressWarnings("serial")
public class ServerWindow extends JFrame {
	
	
	private WMServer wmServer;
	private Tournament tournament;
	
	//JPanel west;
	private ControlPanel controlPanel;
	
	//JProgressBar bottom
	private JProgressBar progress;
	 
	
	//TabbedPane
	private JTabbedPane tabPane;
	
	//TabbedPane Start
	private JPanel startPanel;
	private JLabel showIp;
	private JLabel showPort;
	
	//TabbedPane Matches
	private JPanel matchPanel;
	private ArrayList<MatchPanelUpdater> threadList;
	
	
	//TabbedPane Team-Matrix
	private JPanel teamView;
	
	//TabbedPane Result-List
	private JPanel result;
	private JTextArea resultList;
	private JScrollPane spResultList;
	private JButton updateResult;
	
	//TabbedPane Log
	private LogPanel logPanel;
	
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
		
		//create left panel with information about the tournament
		controlPanel = new ControlPanel(ServerWindow.this);
		c.add(controlPanel, BorderLayout.WEST);
		
		
		//create ProgressBar
		progress = new JProgressBar();
		c.add(progress, BorderLayout.SOUTH);
		
		
		//create JTabbedPane 
		tabPane = new JTabbedPane(JTabbedPane.TOP);
		c.add(tabPane, BorderLayout.CENTER);
		
		//create Panel for "Start"
		startPanel = new JPanel(new GridLayout(2, 1));
		try {
			showIp = new JLabel("IP-Address: " + InetAddress.getLocalHost().getHostAddress());
			showIp.setFont(new Font("Arial", Font.BOLD, 50));
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		showPort = new JLabel("Port: XXXX");
		showPort.setFont(new Font("Arial", Font.BOLD, 50));
		startPanel.add(showIp);
		startPanel.add(showPort);
		
		//create Panel for the final
		matchPanel = new JPanel(new GridLayout(0, 2));
		threadList = new ArrayList<MatchPanelUpdater>();
		
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
		logPanel = new LogPanel(ServerWindow.this);
	
		//build JTabbedPane
		tabPane.add("Start", startPanel);
		tabPane.addTab("Matches", matchPanel);
		tabPane.add("Result", result);
		tabPane.add("Log", logPanel);
		tabPane.addChangeListener(new TabbedPaneListener(ServerWindow.this));
			
		//create popup dialog to request Port
		popup = new PopupDialogPort(ServerWindow.this);
		popup.setVisible(true);
		
		setVisible(true);
		setEnabled(false);
			}
		});
	}
	
	
	/**
	 * initialise the teamView-Panel: Each team gets a Button with a color (green = inGame, red = game over)
	 * @param tSet
	 */
	public void updateTeamView(final Tournament t)
	{
		this.tournament = t;
		int finalExtraShots = t.getNoOfShots() - (t.getNoOfShotsPerMatch() * t.getNoOfMatches());
		progress.setMaximum(Analyser.calculateNoOfRounds(t) * Analyser.calculateNoOfShotsPerMatch(t) + finalExtraShots);
		this.teamSet = t.getPlaying().clone();
		final ArrayTeamSet<Team> clone = this.teamSet;
		final int size = (int) Math.ceil(Math.sqrt(this.teamSet.size()));
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					
					teamView.removeAll();
					controlPanel.updateMetaData(t);
					
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
							logPanel.registerTeam(t);
						}
					}
					updateResultList();
					updateResult.setEnabled(true);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
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
		controlPanel.updateClientsAtServer(clientsAtServer);
	}
	
	
	
	public void updateMetaData(final Tournament t)
	{
		this.tournament = t;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				controlPanel.updateMetaData(t);
			}
		});
	}
	
	
	public void updateShots(final Tournament t)
	{
		{
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						controlPanel.updateShots(t);
						progress.setValue(Analyser.calculateProgress(t));
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
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
				controlPanel.updateNoOfTestClients(testClients);
			}
		});
	}
	
	public void appendLogLine(final LogLine ll)
	{
		logPanel.appendLogLine(ll, tabPane);
	}
	
	public void showFinish()
	{
		new FinishedWindow(this.tournament);
	}
	
	public void addMatch(final Team a, final Team b)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				JLabel text = new JLabel(a.getName() + " " + a.getGoalsInCurrentRound() + " : " 
						+ b.getGoalsInCurrentRound() + " " + b.getName());
				JProgressBar bar = new JProgressBar();
				bar.setMinimum(-50);
				bar.setMaximum(50);
				matchPanel.add(text);
				matchPanel.add(bar);
				MatchPanelUpdater newThread = new MatchPanelUpdater(a, b, text, bar);
				newThread.start();
				matchPanel.updateUI();
				threadList.add(newThread);
			}
		});
	}
	
	public void cleanMatchPanel()
	{
		matchPanel.removeAll();
		for(MatchPanelUpdater t : threadList)
		{
			t.setFlag(false);
		}
	}
	
	//////////////////////// Getter and Setter ////////////////////////
	
	public void setWMServer(WMServer wmServer){
		this.wmServer = wmServer;
	}
	
	public WMServer getWMServer(){
		return this.wmServer;
	}
	
	public JButton getStartButton(){
		return controlPanel.getStartButton();
	}
	
	public Tournament getTournament(){
		return this.tournament;
	}

	public LogPanel getLogPanel(){
		return logPanel;
	}
	
	public JComboBox<String> getTeamBox1() {
		return logPanel.getTeamBox1();
	}

	public JComboBox<String> getTeamBox2() {
		return logPanel.getTeamBox2();
	}
	
	public JTextArea getLogString(){
		return logPanel.getLogConsole();
	}
	
	public JButton getSaveLog() {
		return logPanel.getSaveLog();
	}
	
	public JTextArea getLogConsole() {
		return logPanel.getLogConsole();
	}

	/**
	 * @return the teamSet of the ServerWindow in natural Order
	 */
	public ArrayTeamSet<Team> getTeamSet() {
		Collections.sort(teamSet);
		return teamSet;
	}


	public JLabel getServerPort() {
		return controlPanel.getServerPort();
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


	public JButton getAddTestClients() {
		return controlPanel.getAddTestClients();
	}


	public JButton getPlusTestClient() {
		return controlPanel.getPlusTestClient();
	}	
}
