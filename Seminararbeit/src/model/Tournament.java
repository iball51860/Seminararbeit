package model;

import java.util.*;

public class Tournament {
	
	private static ArrayTeamSet<Team> contestants = new ArrayTeamSet<Team>(); //TODO delete static
	
	//TODO add data from Analyser
	
	public static ArrayTeamSet<Team> getContestants()
	{
		return contestants;
	}
}
