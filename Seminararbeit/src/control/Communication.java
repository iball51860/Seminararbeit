package control;

import java.io.IOException;
import java.util.*;

import model.*;


/**
 * The communication class offers methods to send and receive messages to an from the clients.
 * 
 * @author Jan Fritze, Manuel Kaiser
 *
 */
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
	
	public static final long MILLISTOTIMEOUT = 5000;
	public static final long ALLOWEDAVGREACTION = 400;
	
	
	/**
	 * This method sends a message to each team.
	 * 
	 * @param The collection "teams" is needed to send a message to all of the teams.
	 * @param The string is the message with which will be send to all teams.
	 */
	public static void broadcast(Collection<Team> teams, String msg)
	{
		Logger.log("Broadcasting: \"" + msg + "\" to " + teams.size() + " Teams.", Logger.COMMUNICATION);
		for(Team t : teams)
		{
			sendMsg(t, msg);
		}
	}
	
	/**
	 * This method sends a message to one selected team.
	 * 
	 * @param team
	 * @param message
	 */
	public static void sendMsg(Team team, String msg)
	{
		team.write(msg);
	}
	
	/**
	 * Sends the decision String to the specified Team(Client), gets an answer and 
	 * reverts it to an integer that represents the decision. (L,l=0; M,m=1; R,r=2).
	 * If the answer doesn't fit the decision scheme, the method returns -1 to signal
	 * a wrong answer.
	 * @param team from which decision is invoked
	 * @param msg that calls for decision, must be Communication.SHOOT or Communication.KEEP
	 * @return int with value of {0, 1, 2} meaning, in this order left, middle, right or -1 for wrong feedback
	 */
	public static int requestDecision(Team team, String msg)
	{
		if(!team.isOnline())
		{
			return team.getIndexOfStrength(Team.WEAKEST);
		}
		team.setLastInput(null);
		sendMsg(team, msg);
		long start = System.currentTimeMillis();
		boolean maxTimeout = false;
		boolean avgTimeout = false;
		while((maxTimeout = (System.currentTimeMillis() - start < MILLISTOTIMEOUT)) && team.getLastInput() == null)
		{
			team.setLastInput(team.read().substring(0, 1));
		}
		team.registerReactionTime(System.currentTimeMillis() - start);
		if((avgTimeout = (team.getAvgReactionTime() > ALLOWEDAVGREACTION)))
		{
			team.setOnline(false);
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
				if(maxTimeout || avgTimeout)
				{
					Logger.log(team.getName() + ": Maximum timeout: " + maxTimeout + "\tAverage timeout: " + 
							avgTimeout, team, Logger.SERVER);
				}
				else
				{
					Logger.log(team.getName() + ": no valid decision. Sent 'l', 'm' or 'r' after receiving '" +
							SHOOT + "' or '" + KEEP + "'.", team, Logger.COMMUNICATION);
				}
				//Logger.log(team.getName() + ": Timeout", team, Logger.SERVER);
				team.setOnline(false);
				return requestDecision(team, msg);
		}		
	}
	
	/**
	 * Request the name of one specific team. If the reaction time is more than 5000 milliseconds the 
	 * server removes the team from the server instance of the list with all contestants on the server.
	 * 
	 * @param team
	 */
	public static void requestName(Team team)
	{
		team.setLastInput(null);
		sendMsg(team, NAME);
		String s = null;
		long start = System.currentTimeMillis();
		while((System.currentTimeMillis() - start < 5000) && team.getLastInput() == null) //TODO switch to return if received name
		{
			s = team.read();
			if(s.length() < 5)
			{
				for(int i = 5-s.length(); i>0; i--)
				{
					s = s + "x";
				}
			}
			if(s.length() > 5)
			{
				s = s.substring(0, 5);
			}
			team.setLastInput(s);
			team.setName(s);
		}
		team.registerReactionTime(System.currentTimeMillis() - start);
		if(team.getLastInput() == null)
		{
			team.getServer().getClientsAtServer().remove(team);
			team.setOnline(false);
			Logger.log(team.getName() + ": Timeout.", team, Logger.SERVER);
			try {
				team.getClientSocket().close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends the calculated Strengths in a certain format (e.g. STR 586062) to the specific team.
	 * 
	 * @param team which receives strengths
	 */
	public static void sendStrengths(Team team)
	{
		String s = STRENGTH + " " + team.getStrength()[0] + team.getStrength()[1] + team.getStrength()[2];
		sendMsg(team, s);
	}
	
	
}
