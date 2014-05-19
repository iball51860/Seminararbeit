package view;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.swing.*;

import model.*;
import control.*;
import control.listeners.*;

/**
 * The Base Window. The Serverwindow is the main frame used for the GUI of the WMTournament.
 * It is the first to be initiated and the other instances of logic and GUI, such as the 
 * {@link WMServer} or the Panels are called from it.<br><br>
 * The Window consist of a left column holding all controls and information about server, tournament and Testclients.<br>
 * The Bottom contains a ProgressBar showing the Progress of the Tournament.<br>
 * The Tabbedpane that uses most of the space holds 4 Panels.<br>The first panel shows the server-access information in the beginning
 * and switches to a view of the TeamMatrix that shows information about each teams status later.<br>The second Tab shows all currently
 * running matches with its standing and visualized as Progressbar.<br>The third Tab shows a List of all teams and detailed information about them.
 * This list is sorted and the best team is displayed at the top.<br>The fourth Tab show a log of the major events happening in the Game.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
@SuppressWarnings("serial")
public class ServerWindow extends JFrame {
	
	/**Instance of the Server providing the data and platform*/
	private WMServer wmServer;
	/**Instance of the Tournament providing current data*/
	private Tournament tournament;
	/**Instance of the Teams informations are displayed about*/
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
	
	/**Initial dialog asking for the Serverport*/
	private PopupDialogPort popup;
	
	/**
	 * Constructs a ServerWindow as described in {@link ServerWindow}. It holds the controls on the left, 
	 * tabbedPane on the right and Progressbar at the bottom.
	 */
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
	
	/**
	 * Updates the in-Game-Status of the given Teams. Switches color, etc.
	 * @param a
	 * @param b
	 */
	public void updateTeamInMatchView(final Team a, final Team b)
	{
		teamMatrixPanel.updateTeamInMatchView(a, b);
	}
	
	/**
	 * Updates the Number of Clients at Server displayed in the panel on the left.
	 * @param clientsAtServer
	 */
	public void updateClientsAtServer(int clientsAtServer)
	{
		controlPanel.updateClientsAtServer(clientsAtServer);
	}
	
	/**
	 * Updates the MetaData for the given tournament
	 * @param tournament
	 */
	public void updateMetaData(final Tournament tournament)
	{
		this.tournament = tournament;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				controlPanel.updateMetaData(tournament);
			}
		});
	}
	
	/**
	 * Updates the number of played shots in a label in the left control panel
	 * @param tournament
	 */
	public void updateShots(final Tournament tournament)
	{
		{
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						controlPanel.updateGoals(tournament);
						progress.setValue(Analyser.calculateProgress(tournament));
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Updates the resultlist in the Resultlistpanel.
	 */
	public void updateResultList()
	{
		resultListPanel.updateResultList(teamSet);
	}
	
	/**
	 * Updates the Label showing the number of TestClients at the Server.
	 * @param testClients
	 */
	public void updateNoOfTestClients(final int testClients)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				controlPanel.updateNoOfTestClients(testClients);
			}
		});
	}
	
	/**
	 * Appends a LogLine to the Log in the LogPanel
	 * @param logLine
	 */
	public void appendLogLine(final LogLine logLine)
	{
		logPanel.appendLogLine(logLine, tabPane);
	}
	
	/**
	 * Opens a new Finish-Window displaying the glorious information about the first three teams.
	 */
	public void showFinish()
	{
		new FinishedWindow(this.tournament);
	}
	
	/**
	 * Adds a new Progress bar for the match in the matchPanel
	 * @param teamA
	 * @param teamB
	 */
	public void addMatch(final Team teamA, final Team teamB)
	{
		matchPanel.addMatch(teamA, teamB);
	}
	
	/**
	 * Cleans the MatchPanel of all Progressbar and its threads.
	 */
	public void cleanMatchPanel()
	{
		matchPanel.cleanMatchPanel();
	}
	
	/////////////////////////get-, set and increment-methods///////////////////////////////
	/* FOR REASONS OF SAVING TIME WE WILL NOT PROVIDE DETAILED JAVADOC DOCUMENTATION FOR GETTERS AND SETTER. THANK YOU FOR UNDERSTANDING*/
	
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
