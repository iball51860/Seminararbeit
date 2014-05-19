/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import model.Tournament;

import control.Analyser;
import control.listeners.AddTestClientsListener;
import control.listeners.ChangeReactionTimeListener;
import control.listeners.FinishGameListener;
import control.listeners.PopupTestClientsListener;
import control.listeners.ResetServerListener;
import control.listeners.StartTournamentListener;


/**
 * GUI for the settings and game metadata on the left side.<br>
 * Contains Components for adding clients, starting a game, setting the testclients 
 * latency, to stop a current game at the next possibility or to reset the server.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	
	/**Holds the Components related to create TestClients. Does not hold the reaction time slider and labels.*/
	private JPanel testClientPanel;
	/**Button to start the game*/
	private JButton startButton;
	/**Button to end the game at next possibility*/
	private JButton finishButton;
	/**Button to reset the Server*/
	private JButton resetServer;
	/**Button to add TestClients, included in testClientPanel*/
	private JButton addTestClients;
	/**Button to add a single TestClient, included in testClientPanel*/
	private JButton plusTestClient;
	/**Label showing the currently set Reaction Time.*/
	private JLabel reactionTimeLabel;
	/**Slider to set the reaction Time to simulate*/
	private JSlider reactionTime;
	
	//Labels for Meta Data
	/**Label showing the current Round, e.g. "Seminfinal"*/
	private JLabel currentRound;
	/**Label showing the number of signed in clients*/
	private JLabel noOfClients;
	/**Label showing the servers IP-Address. Do not Confuse with the IP-Address-Label in the StartPanel*/
	private JLabel serverIP;
	/**Label showing the used Port*/
	private JLabel serverPort;
	/**Label showing the number of Test Clients signed in.*/
	private JLabel noOfTestClients;
	/**Label showing the number of Clients still playing.*/
	private JLabel noPlaying;
	/**Label showing the number of played Matches in this tournament*/
	private JLabel noOfPlayedMatches;
	/**Label showing the number of Goals scored so far in the tournament*/
	private JLabel noOfGoals;
	/**Label showing the Rate of successful shots*/
	private JLabel successRate;
	/**Label showing the Shots simulated in each Match except final (final has excess shots)*/
	private JLabel shotsPerMatch;
	
	/**
	 * Constructs a panel showing controls and basic information about the server & tournament.
	 * @param serverWindow
	 */
	public ControlPanel(ServerWindow serverWindow){

		super(new GridLayout(0, 1));
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		//create Buttons for starting game
		startButton = new JButton("Start Game");
		startButton.addActionListener(new StartTournamentListener(serverWindow));
		startButton.setEnabled(false);
		this.add(startButton);
				
		//create panel and Buttons for starting TestClients
		testClientPanel = new JPanel(new BorderLayout());
		addTestClients = new JButton("Add Test Clients");
		addTestClients.addActionListener(new AddTestClientsListener(serverWindow));
		plusTestClient = new JButton("+");
		plusTestClient.addActionListener(new PopupTestClientsListener(serverWindow));
		reactionTimeLabel = new JLabel("Reaction Time (ms): " + 0);
		reactionTime = new JSlider();
		reactionTime.setName("Reaction Time");
		reactionTime.setMaximum(500);
		reactionTime.setValue(0);
		reactionTime.setMajorTickSpacing(100);
		reactionTime.setPaintTicks(true);
		reactionTime.setPaintLabels(true);
		reactionTime.addChangeListener(new ChangeReactionTimeListener(reactionTime, reactionTimeLabel));
		testClientPanel.add(addTestClients, BorderLayout.CENTER);
		testClientPanel.add(plusTestClient, BorderLayout.EAST);	
		this.add(testClientPanel);
		this.add(reactionTimeLabel);
		this.add(reactionTime);
		
		//create Labels for information
		currentRound = new JLabel("Round: 0");
		this.add(currentRound);
		noOfClients = new JLabel("Clients at Server: 0");
		this.add(noOfClients);
		try {
			serverIP = new JLabel("IP-Address: " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		this.add(serverIP);
		serverPort = new JLabel("Port: XXXX");
		this.add(serverPort);
		noOfTestClients = new JLabel("TestClients at Server: 0");
		this.add(noOfTestClients);
		noPlaying = new JLabel("Teams playing: 0");
		this.add(noPlaying);
		noOfPlayedMatches = new JLabel("Matches played: 0");
		this.add(noOfPlayedMatches);
		noOfGoals = new JLabel("Goals: 0");
		this.add(noOfGoals);
		successRate = new JLabel("Success Rate: 0 %");
		this.add(successRate);
		shotsPerMatch = new JLabel("Shots per Match: ");
		this.add(shotsPerMatch);
			
		//create finishButton for interrupting Game after the current round
		finishButton = new JButton("Stop Game after current Round");
		finishButton.addActionListener(new FinishGameListener());
		this.add(finishButton);
			
		//create reset-Server-Button
		resetServer = new JButton("Reset Server");
		resetServer.addActionListener(new ResetServerListener(serverWindow));
		this.add(resetServer);		
	}
	
	/**
	 * Updates the Labels with more current information about the tournament. Updates
	 * <li>CurrentRound</li><li>Number of playing teams</li><li>Matches played so far</li><li>Number of Goals scored overall</li>
	 * <li>Success Rate of Shots</li><li>Number of Shots per Match</li>
	 * @param t
	 */
	public void updateMetaData(Tournament t){
		currentRound.setText("Round: " + Analyser.getCurrentRoundName(t));
		noPlaying.setText("Teams playing: " + t.getPlaying().size());
		noOfPlayedMatches.setText("Matches played: " + t.getFinishedMatches() + " / " + t.getNoOfMatches());
		noOfGoals.setText("Goals: " + t.getGoals());
		int rate = (int) ((double) t.getGoals() / (double) t.getFinishedShots() * 100);
		successRate.setText("Success Rate: " + rate + " %");
		shotsPerMatch.setText("Shots per Match: "+ t.getNoOfShotsPerMatch());
	}
	
	/**
	 * Updates the number of clients signed on. If number was <=1 before, also enables Start button.
	 * @param clientsAtServer - Number of Clients to write into label.
	 */
	public void updateClientsAtServer(int clientsAtServer)
	{
		this.noOfClients.setText("Clients at server: " + clientsAtServer);
		if(clientsAtServer >=2)
		{
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					startButton.setEnabled(true);
				}
			});
		}
	}
	
	/**
	 * Updates the Label with Goals achieved overall.
	 * @param tournament
	 */
	public void updateGoals(final Tournament tournament)
	{
		noOfGoals.setText("Goals: " + tournament.getGoals());
	}
	
	/**
	 * Updates the Label showing the number of activated TestClients.
	 * @param testClients
	 */
	public void updateNoOfTestClients(int testClients){
		noOfTestClients.setText("TestClients at Server: " + testClients);
	}
	
	/////////////////////////get-, set and increment-methods///////////////////////////////
	/* FOR REASONS OF SAVING TIME WE WILL NOT PROVIDE DETAILED JAVADOC DOCUMENTATION FOR GETTERS AND SETTER. THANK YOU FOR UNDERSTANDING*/
	
	/**
	 * 
	 * @return StartButton
	 */
	public JButton getStartButton(){
		return this.startButton;
	}
	
	/**
	 * 
	 * @return JLabel showing Port
	 */
	public JLabel getServerPort(){
		return this.serverPort;
	}
	
	/**
	 * 
	 * @return JButton to add TestClients
	 */
	public JButton getAddTestClients() {
		return addTestClients;
	}
	
	/**
	 * 
	 * @return JButton adding one TestClient
	 */
	public JButton getPlusTestClient() {
		return plusTestClient;
	}
}
