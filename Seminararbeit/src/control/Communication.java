package control;

import java.util.*;

import model.*;

public class Communication 
{
	//TODO implement methods for single Team communication (e.g. requestDecision(Team t)), maybe add String-decoder
	//TODO implement validation of communication (kicking of clients)
	
	static final String NAME 		= "NME";
	static final String STRENGTH 	= "STR";
	static final String NEWGAME 	= "NWG";
	static final String NEWROUND 	= "NWR";
	static final String NEWMATCH 	= "NWM";
	static final String SHOT		= "SHO";
	static final String KEEP		= "KEP";
	static final String OPPONENT	= "OPP";
	static final String RESULT		= "MRS";
	static final String GAMEOVER	= "GOV";
	
	
	
	public static void broadcast(Set<Team> teams, String msg)
	{
		Iterator<Team> it = teams.iterator();
		while(it.hasNext())
		{
			sendMsg(it.next(), msg);
		}
	}
	
	
	public static void sendMsg(Team team, String msg)
	{
		team.write(msg);
	}
	
	
	public static int requestDecision(Team team, String msg)
	{
		sendMsg(team, msg);
		long start = System.currentTimeMillis();
		while((System.currentTimeMillis() - start < 5000) && team.getLastInput() == null)
		{
			team.setLastInput(team.read().substring(0, 1));
		}
		switch (team.getLastInput())
		{
			case "l":
				return 0;
			case "L":
				return 0;
			case "m":
				return 1;
			case "M":
				return 1;
			case "r":
				return 2;
			case "R":
				return 2;
			default:
				return -1;
				//TODO ersetze Client durch Dummy
		}
		//team.setLastInput(null);
		//return "DummyDecision";
		
	}
	
	
	public static void requestName(Team team)
	{
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
		team.setLastInput(null);
	}
	
	public static void sendStrengths(Team team)
	{
		String s = STRENGTH + " K " + team.getKeeper().toString() + " S " + team.getKeeper().toString();
		
		sendMsg(team, s);
	}
	
	
}
