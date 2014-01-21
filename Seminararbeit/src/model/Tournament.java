package model;

import view.ServerWindow;

import control.*;

public class Tournament {
	
	private ArrayTeamSet<Team> playing;
	private ArrayTeamSet<Team> lost;
	private int noOfShots;
	private int noOfShotsPerMatch;
	private int noOfRounds;
	private ServerWindow masterWindow;
	private WMServer server;
	private int currentRound;
	private int noOfMatches;
	private int finishedMatches;
	private int finishedShots;
	private int goals;
	
	private boolean isRunning;
	private long duration = 0;

	public Tournament(ArrayTeamSet<Team> contestants, int noOfShots, ServerWindow masterWindow){
		this.playing = contestants;
		this.lost = new ArrayTeamSet<Team>();
		this.noOfShots = noOfShots;
		this.noOfShotsPerMatch = Analyser.calculateNoOfShotsPerMatch(contestants.size(), noOfShots);
		this.noOfRounds = Analyser.calculateNoOfRounds(contestants.size());
		this.masterWindow = masterWindow;
		this.server = masterWindow.getWMServer();
		this.currentRound = 0;
		this.noOfMatches = Analyser.calculateNoOfMatches(playing.size());
		this.finishedMatches = 0;
		this.isRunning = true;
	}

	/**
	 * @return the playing
	 */
	public ArrayTeamSet<Team> getPlaying() {
		return playing;
	}

	/**
	 * @param playing the playing to set
	 */
	public void setPlaying(ArrayTeamSet<Team> playing) {
		this.playing = playing;
	}

	/**
	 * @return the lost
	 */
	public ArrayTeamSet<Team> getLost() {
		return lost;
	}

	/**
	 * @param lost the lost to set
	 */
	public void setLost(ArrayTeamSet<Team> lost) {
		this.lost = lost;
	}

	/**
	 * @return the noOfShots
	 */
	public int getNoOfShots() {
		return noOfShots;
	}

	/**
	 * @param noOfShots the noOfShots to set
	 */
	public void setNoOfShots(int noOfShots) {
		this.noOfShots = noOfShots;
	}

	/**
	 * @return the noOfShotsPerMatch
	 */
	public int getNoOfShotsPerMatch() {
		return noOfShotsPerMatch;
	}

	/**
	 * @param noOfShotsPerMatch the noOfShotsPerMatch to set
	 */
	public void setNoOfShotsPerMatch(int noOfShotsPerMatch) {
		this.noOfShotsPerMatch = noOfShotsPerMatch;
	}

	/**
	 * @return the noOfRounds
	 */
	public int getNoOfRounds() {
		return noOfRounds;
	}

	/**
	 * @param noOfRounds the noOfRounds to set
	 */
	public void setNoOfRounds(int noOfRounds) {
		this.noOfRounds = noOfRounds;
	}

	/**
	 * @return the masterWindow
	 */
	public ServerWindow getMasterWindow() {
		return masterWindow;
	}

	/**
	 * @param masterWindow the masterWindow to set
	 */
	public void setMasterWindow(ServerWindow masterWindow) {
		this.masterWindow = masterWindow;
	}

	/**
	 * @return the server
	 */
	public WMServer getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(WMServer server) {
		this.server = server;
	}

	/**
	 * @return the currentRound
	 */
	public int getCurrentRound() {
		return currentRound;
	}

	/**
	 * @param currentRound the currentRound to set
	 */
	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}

	/**
	 * @return the noOfMatches
	 */
	public int getNoOfMatches() {
		return noOfMatches;
	}

	/**
	 * @param noOfMatches the noOfMatches to set
	 */
	public void setNoOfMatches(int noOfMatches) {
		this.noOfMatches = noOfMatches;
	}

	/**
	 * @return the finishedMatches
	 */
	public int getFinishedMatches() {
		return finishedMatches;
	}

	/**
	 * @param finishedMatches the finishedMatches to set
	 */
	public void setFinishedMatches(int finishedMatches) {
		this.finishedMatches = finishedMatches;
	}
	
	public void incrementFinishedMatches(int increment) {
		this.finishedMatches += increment;
	}
	
	/**
	 * @return the finishedShots
	 */
	public int getFinishedShots() {
		return finishedShots;
	}

	/**
	 * @param finishedShots the finishedShots to set
	 */
	public void setFinishedShots(int finishedShots) {
		this.finishedShots = finishedShots;
	}
	
	public void incrementFinishedShots(int increment) {
		this.finishedShots += increment;
	}
	
	/**
	 * @return the goals
	 */
	public int getGoals() {
		return goals;
	}

	/**
	 * @param goals the goals to set
	 */
	public void setGoals(int goals) {
		this.goals = goals;
	}
	
	public void incrementGoals(int increment) {
		this.goals += increment;
	}

	/**
	 * @return the isRunning
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * @param isRunning the isRunning to set
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}
}
