package model;

import java.util.*;

public class Tournament {
	
	private static TreeSet<Team> contestants = new TreeSet<Team>();
	

	
	public static TreeSet<Team> getContestants()
	{
		return contestants;
	}
}
