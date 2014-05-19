package model;

import view.ServerWindow;

import control.*;

/**
 * Represents a Tournament with all the necessary metadata, e.g. which teams are playing.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class Tournament {
	
	/**Teams that are still playing in this round. See also {@link Team}.*/
	private ArrayTeamSet<Team> playing;
	/**Teams that have lost until now (independent of current Round). Teams might reenter the tournament in case of relegation.  See also {@link Team}.*/
	private ArrayTeamSet<Team> lost;
	/**Number of Shot to be played in this tournament*/
	private int noOfShots;
	/**No of Shots to be played in a normal match. Only Exception: final*/
	private int noOfShotsPerMatch;
	/**No of Rounds necessary to determine a winner. Usually less than 10.*/
	private int noOfRounds;
	/**Instance of the {@link ServerWindow} with the visualisation to this Tournament*/
	private ServerWindow serverWindow;
	/**Instance of the {@link WMServer} providing the platform for this Tournament*/
	private WMServer server;
	/**Round the Tournament is currently in represented as Integer. 1 = Final, 2 = Seminfinal, etc. Number usually represents number of matches Played. Exception: Relegation*/
	private int currentRound;
	/**Number of Matches to be played throughout the tournament*/
	private int noOfMatches;
	/**Number of Matches finished so far in the tournament*/
	private int finishedMatches;
	/**Number of Matches finished before current round*/
	private int finishedMatchesInFinishedRounds;
	/**Number of shots finished so far in the tournament*/
	private int finishedShots;
	/**Number of goals scored in this match so far*/
	private int goals;
	
	/**Flag showing whether the tournament has finished or not.*/
	private boolean isRunning;
	/**The duration, how long the tournament needed to conduct. Is just set after the tournament has finished.*/
	private long duration = 0;

	/**
	 * Constructs a new Tournament from the given data: Teams, Shots to play and the serverWindow where it is visualized.
	 * Calculates basic data such as the Number of necessary matchs, rounds and Shots per Match.
	 * @param contestants
	 * @param noOfShots
	 * @param serverWindow
	 */
	public Tournament(ArrayTeamSet<Team> contestants, int noOfShots, ServerWindow serverWindow){
		this.playing = contestants;
		this.lost = new ArrayTeamSet<Team>();
		this.noOfShots = noOfShots;
		this.noOfShotsPerMatch = Analyser.calculateNoOfShotsPerMatch(contestants.size(), noOfShots);
		this.noOfRounds = Analyser.calculateNoOfRounds(contestants.size());
		this.serverWindow = serverWindow;
		this.server = serverWindow.getWMServer();
		this.currentRound = 0;
		this.noOfMatches = Analyser.calculateNoOfMatches(playing.size());
		this.finishedMatches = 0;
		this.finishedShots = 0;
		this.isRunning = true;
	}

	/////////////////////////get-, set and increment-methods///////////////////////////////
	/* FOR REASONS OF SAVING TIME WE WILL NOT PROVIDE DETAILED JAVADOC DOCUMENTATION FOR GETTERS AND SETTERS. THANK YOU FOR UNDERSTANDING*/
	
	/**
	 * @return the playing
	 */
	public synchronized ArrayTeamSet<Team> getPlaying() {
		return playing;
	}

	/**
	 * @param playing the playing to set
	 */
	public synchronized void setPlaying(ArrayTeamSet<Team> playing) {
		this.playing = playing;
	}

	/**
	 * @return the lost
	 */
	public synchronized ArrayTeamSet<Team> getLost() {
		return lost;
	}

	/**
	 * @param lost the lost to set
	 */
	public synchronized void setLost(ArrayTeamSet<Team> lost) {
		this.lost = lost;
	}

	/**
	 * @return the noOfShots
	 */
	public synchronized int getNoOfShots() {
		return noOfShots;
	}

	/**
	 * @param noOfShots the noOfShots to set
	 */
	public synchronized void setNoOfShots(int noOfShots) {
		this.noOfShots = noOfShots;
	}

	/**
	 * @return the noOfShotsPerMatch
	 */
	public synchronized int getNoOfShotsPerMatch() {
		return noOfShotsPerMatch;
	}

	/**
	 * @param noOfShotsPerMatch the noOfShotsPerMatch to set
	 */
	public synchronized void setNoOfShotsPerMatch(int noOfShotsPerMatch) {
		this.noOfShotsPerMatch = noOfShotsPerMatch;
	}

	/**
	 * @return the noOfRounds
	 */
	public synchronized int getNoOfRounds() {
		return noOfRounds;
	}

	/**
	 * @param noOfRounds the noOfRounds to set
	 */
	public synchronized void setNoOfRounds(int noOfRounds) {
		this.noOfRounds = noOfRounds;
	}

	/**
	 * @return the serverWindow
	 */
	public synchronized ServerWindow getMasterWindow() {
		return serverWindow;
	}

	/**
	 * @param serverWindow the serverWindow to set
	 */
	public synchronized void setMasterWindow(ServerWindow masterWindow) {
		this.serverWindow = masterWindow;
	}

	/**
	 * @return the server
	 */
	public synchronized WMServer getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public synchronized void setServer(WMServer server) {
		this.server = server;
	}

	/**
	 * @return the currentRound
	 */
	public synchronized int getCurrentRound() {
		return currentRound;
	}

	/**
	 * @param currentRound the currentRound to set
	 */
	public synchronized void setCurrentRound(int currentRound) {
		finishedMatchesInFinishedRounds = finishedMatches;
		this.currentRound = currentRound;
	}
	
	/**
	 * Returns the Rounds played in the tournament so far, including the current Round.
	 * @return the currentRound2
	 */
	public synchronized int getNoOfRoundsPlayed() {
		return noOfRounds - currentRound;// + 1;
	}

	/**
	 * @return the noOfMatches
	 */
	public synchronized int getNoOfMatches() {
		return noOfMatches;
	}

	/**
	 * @param noOfMatches the noOfMatches to set
	 */
	public synchronized void setNoOfMatches(int noOfMatches) {
		this.noOfMatches = noOfMatches;
	}

	/**
	 * @return the finishedMatches
	 */
	public synchronized int getFinishedMatches() {
		return finishedMatches;
	}

	/**
	 * @param finishedMatches the finishedMatches to set
	 */
	public synchronized void setFinishedMatches(int finishedMatches) {
		this.finishedMatches = finishedMatches;
	}
	
	public synchronized void incrementFinishedMatches(int increment) {
		this.finishedMatches += increment;
	}
	
	/**
	 * @return the finishedShots
	 */
	public synchronized int getFinishedShots() {
		return finishedShots;
	}

	/**
	 * @param finishedShots the finishedShots to set
	 */
	public synchronized void setFinishedShots(int finishedShots) {
		this.finishedShots = finishedShots;
	}
	
	public synchronized void incrementFinishedShots(int increment) {
		this.finishedShots += increment;
	}
	
	/**
	 * @return the goals
	 */
	public synchronized int getGoals() {
		return goals;
	}

	/**
	 * @param goals the goals to set
	 */
	public synchronized void setGoals(int goals) {
		this.goals = goals;
	}
	
	public synchronized void incrementGoals(int increment) {
		this.goals += increment;
	}

	/**
	 * @return the isRunning
	 */
	public synchronized boolean isRunning() {
		return isRunning;
	}

	/**
	 * @param isRunning the isRunning to set
	 */
	public synchronized void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * @return the duration
	 */
	public synchronized long getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public synchronized void setDuration(long duration) {
		this.duration = duration;
	}
	
	/**
	 * Returns the average of all Clients average Latency.
	 * @return overallAverageLatency
	 */
	public synchronized long getAverageClientLatency() {
		long avgReaction  = 0;
		
		for(Team team : getMasterWindow().getTeamSet())
		{
			avgReaction += team.getAvgReactionTime();
		}
		avgReaction = avgReaction / getMasterWindow().getTeamSet().size();
		return avgReaction;
	}

	public int getFinishedMatchesInFinishedRounds() {
		return finishedMatchesInFinishedRounds;
	}

	public void setFinishedMatchesInFinishedRounds(
			int finishedMatchesInFinishedRounds) {
		this.finishedMatchesInFinishedRounds = finishedMatchesInFinishedRounds;
	}
}
