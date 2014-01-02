package model;

import java.util.*;

import control.Analyser;

public class Tournament {
	
	private ArrayTeamSet<Team> playing;
	private ArrayTeamSet<Team> lost;
	private int noOfShots;
	private int noOfRounds;
	
	public Tournament(ArrayTeamSet<Team> contestants, int noOfShots){
		this.playing = contestants;
		this.lost = new ArrayTeamSet();
		this.noOfShots = noOfShots;
		this.noOfRounds = Analyser.calculateNoOfRounds(contestants.size());
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
	
	
}
