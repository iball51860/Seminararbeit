package model;

import java.util.*;

public class Tournament {
	
	private static TreeSet<Team> contestants = new TreeSet<Team>(); //TODO delete static
	
	//TODO add data from Analyser
	
	public static TreeSet<Team> getContestants()
	{
		return contestants;
	}
}
