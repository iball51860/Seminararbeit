package view;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.swing.*;

import model.*;
import control.*;
import control.listeners.*;

@SuppressWarnings("serial")
public class ServerWindow extends JFrame {
	
	
	private WMServer wmServer;
	private Tournament tournament;
	private ArrayTeamSet<Team> teamSet;	
	
	/**Panel on the left where Gamedata is shown and Settings are made*/
	private ControlPanel controlPanel;
	
	/**Progressbar at the bottom, showing the Progress of the game*/
	private JProgressBar progress;
	 	
	/**Tabbedpane holding the Panels for different Game Visualizations*/
	private JTabbedPane tabPane;
	
	/**Panel showing big IP-Address and Port Number at the Beginning*/
	private StartPanel startPanel;
	
	/**Panel showing the current standings of all Matches*/
	private MatchesPanel matchPanel;
	
	/**Panel showing the Status of all Teams*/
	private TeamMatrixPanel teamMatrixPanel;
	
	/**Panel showing a Resultlist*/
	private ResultListPanel resultListPanel;
	
	/**Panel showing the Log*/
	private LogPanel logPanel;
	
	private PopupDialogPort popup;
	
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
		startPanel = new StartPanel();
		
		//create Panel for the final
		matchPanel = new MatchesPanel();
		
		//create Panel for "Result-List"
		resultListPanel = new ResultListPanel(ServerWindow.this);
		
		//create Pane for "Log"
		logPanel = new LogPanel(ServerWindow.this);
	
		//build JTabbedPane
		tabPane.add("Start", startPanel);
		tabPane.addTab("Matches", matchPanel);
		tabPane.add("Result", resultListPanel);
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
	 * Initialize the teamMatrixPanel-Panel: Each team gets a Button with a color (green = inGame, red = game over)
	 * @param tSet
	 */
	public void registerTournament(final Tournament t)
	{
		this.tournament = t;
		controlPanel.updateMetaData(t);
		int finalExtraShots = t.getNoOfShots() - (t.getNoOfShotsPerMatch() * t.getNoOfMatches());
		progress.setMaximum(Analyser.calculateNoOfRounds(t) * Analyser.calculateNoOfShotsPerMatch(t) + finalExtraShots);
		this.teamSet = t.getPlaying().clone();
		teamMatrixPanel = new TeamMatrixPanel(ServerWindow.this, t);

		tabPane.setComponentAt(0, teamMatrixPanel);
		tabPane.setTitleAt(0, "Matrix");
		resultListPanel.activateResultListUpdater(ServerWindow.this);
	}
	
	/**
	 * If Team t is still in Game set Color of it's Button to green 
	 * else set Color to red
	 * @param t
	 */
	public void updateTeamView(final Team t)
	{
		teamMatrixPanel.updateTeamView(t);
	}
	
	public void updateTeamInMatchView(final Team a, final Team b)
	{
		teamMatrixPanel.updateTeamInMatchView(a, b);
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
		resultListPanel.updateResultList(teamSet);
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
		matchPanel.addMatch(a, b);
	}
	
	public void cleanMatchPanel()
	{
		matchPanel.cleanMatchPanel();
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
		return resultListPanel.getResultList();
	}
	
	public JLabel getShowPort() {
		return startPanel.getShowPort();
	}
	
	public JTabbedPane getTabPane() {
		return tabPane;
	}
	
	public JPanel getTeamView() {
		return teamMatrixPanel;
	}


	public JButton getAddTestClients() {
		return controlPanel.getAddTestClients();
	}


	public JButton getPlusTestClient() {
		return controlPanel.getPlusTestClient();
	}	
}
