package control;

import java.util.*;

import model.*;

public class Communication 
{	
	public static final String NAME 		= "NME";
	public static final String STRENGTH 	= "STR";
	public static final String NEWGAME 		= "NWG";
	public static final String NEWROUND 	= "NWR";
	public static final String NEWMATCH 	= "NWM";
	public static final String SHOOT		= "SHO";
	public static final String KEEP			= "KEP";
	public static final String SHOTRESULT	= "SHR";
	public static final String MATCHRESULT	= "MRS";
	public static final String GAMEOVER		= "GOV";
	
	
	
	public static void broadcast(Set<Team> teams, String msg)
	{
		for(Team t : teams)
		{
			sendMsg(t, msg);
		}
	}
	
	
	public static void sendMsg(Team team, String msg)
	{
		team.write(msg);
	}
	
	/**
	 * Sents the decision String to the specified Team(Client), gets an answer and 
	 * reverts it to an integer that represents the decision. (L,l=0; M,m=1; R,r=2).
	 * If the answer doesn't fit the decision scheme, the method returns -1 to signal
	 * a wrong answer.
	 * @param team from which decision is invoked
	 * @param msg that calls for decision, musst be Communication.SHOOT or Communication.KEEP
	 * @return int with value of {0, 1, 2} meaning, in this order left, middle, right or -1 for wrong feedback
	 */
	public static int requestDecision(Team team, String msg)
	{
		team.setLastInput(null);
		sendMsg(team, msg);
		long start = System.currentTimeMillis();
		while((System.currentTimeMillis() - start < 5000) && team.getLastInput() == null)
		{
			team.setLastInput(team.read().substring(0, 1));
		}
		String s = team.getLastInput().toLowerCase();
		switch (s)
		{
			case "l":
				return 0;
			case "m":
				return 1;
			case "r":
				return 2;
			default:
				return -1; //TODO throwable und ne Exception werfen und diese behandeln ist evt. eleganter...
				//TODO ersetze Client durch Dummy
		}
		//return "DummyDecision";
		
	}
	
	
	public static void requestName(Team team)
	{
		team.setLastInput(null);
		sendMsg(team, NAME);
		String s = null;
		long start = System.currentTimeMillis();
		while((System.currentTimeMillis() - start < 5000) && team.getLastInput() == null)
		{
			s = team.read().substring(0, 5);
			team.setLastInput(s);
			team.setName(s);
		}
		if(team.getLastInput() == null)
		{
			//TODO ersetze Client durch Dummy
		}
	}
	
	public static void sendStrengths(Team team)
	{
		String s = STRENGTH + " K " + team.getKeeper().toString() + " S " + team.getKeeper().toString();
		sendMsg(team, s);
	}
	
	
}
