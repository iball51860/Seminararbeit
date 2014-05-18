/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
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
 * @author Jan
 *
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	
	private JPanel testClientPanel;
	private JButton startButton;
	private JButton finishButton;
	private JButton resetServer;
	private JButton addTestClients;
	private JButton plusTestClient;
	private JLabel reactionTimeLabel;
	private JSlider reactionTime;
	private JProgressBar progress;
	
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
	
	public void updateMetaData(Tournament t){
		currentRound.setText("Round: " + Analyser.getCurrentRoundName(t));
		noPlaying.setText("Teams playing: " + t.getPlaying().size());
		noOfPlayedMatches.setText("Matches played: " + t.getFinishedMatches() + " / " + t.getNoOfMatches());
		noOfGoals.setText("Goals: " + t.getGoals());
		int rate = (int) ((double) t.getGoals() / (double) t.getFinishedShots() * 100);
		successRate.setText("Success Rate: " + rate + " %");
		shotsPerMatch.setText("Shots per Match: "+ t.getNoOfShotsPerMatch());
	}
	
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
	
	public void updateShots(final Tournament t)
	{
		noOfGoals.setText("Goals: " + t.getGoals());
	}
	
	public void updateNoOfTestClients(int testClients){
		noOfTestClients.setText("TestClients at Server: " + testClients);
	}
	
	public JButton getStartButton(){
		return this.startButton;
	}
	
	public JLabel getServerPort(){
		return this.serverPort;
	}
	
	public JButton getAddTestClients() {
		return addTestClients;
	}
	
	public JButton getPlusTestClient() {
		return plusTestClient;
	}
}
