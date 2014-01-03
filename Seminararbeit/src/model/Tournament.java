package model;

import java.util.*;

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
	
	public Tournament(ArrayTeamSet<Team> contestants, int noOfShots, ServerWindow masterWindow){
		this.playing = contestants;
		this.lost = new ArrayTeamSet();
		this.noOfShots = noOfShots;
		this.noOfShotsPerMatch = Analyser.calculateNoOfShotsPerMatch(contestants.size(), noOfShots);
		this.noOfRounds = Analyser.calculateNoOfRounds(contestants.size());
		this.masterWindow = masterWindow;
		this.server = masterWindow.getWMServer();
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
	
	
}
