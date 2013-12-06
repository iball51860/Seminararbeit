package model;

import java.net.*;

public class Team implements Comparable<Team> {
	
	String id;
	
	FootballPlayer keeper;
	FootballPlayer striker;
	
	int points;

	Socket clientSocket;
	
	public int compareTo(Team otherTeam) //TODO: Compare Team by points
	{
		return 0;
	}
}
