package control;

import java.io.IOException;
import java.util.*;

import model.*;

//TODO create extra method parseDecision(String) that parses the characters / String of a decision to an integer -> save redundant code

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
		//create artificial decision for offline teams
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
	 * Requests the necessary decisions for one shot. The method is programmed to do so parallely for the shooting and
	 * the keeping team, to reduce time-cost.
	 * @param shooter
	 * @param keeper
	 * @return int-Array with the shooting teams decision at index 0 and the keeping teams decision at index 1
	 */
	public static int[] requestDecisions(Team shooter, Team keeper){
		int[] decisions = new int[2];
		//if one (or both) is offline, generate automated decision and request only from the other team
		if(!shooter.isOnline())
		{
			decisions[0] = shooter.getIndexOfStrength(Team.WEAKEST);
			decisions[1] = requestDecision(keeper, KEEP);
			return decisions;
		}
		if(!keeper.isOnline())
		{
			decisions[1] = keeper.getIndexOfStrength(Team.WEAKEST);
			decisions[0] = requestDecision(shooter, SHOOT);
			return decisions;
		}
		//null the last input
		shooter.setLastInput(null);
		keeper.setLastInput(null);
		//notify the clients
		sendMsg(shooter, SHOOT);
		sendMsg(keeper, KEEP);
		
		//prepare variables and get starting time for request
		long start = System.currentTimeMillis();
		long shooterReaction;
		long keeperReaction;
		boolean maxTimeout = false;
		boolean shooterAnswered = false;
		boolean keeperAnswered = false;
		
		//wait for answers from both at the same time, flag any answeredTeam once they answered and register reaction time individually
		//!teamAnswered is true if the team has not answered yet
		while(!maxTimeout && !(shooterAnswered && keeperAnswered)){
			if(!shooterAnswered){
				shooter.setLastInput(shooter.read().substring(0, 1));
			}
			if(!keeperAnswered){
				keeper.setLastInput(keeper.read().substring(0, 1));
			}
			if(!shooterAnswered && shooter.getLastInput() != null){
				shooterAnswered = true;
				shooterReaction = System.currentTimeMillis() - start;
				shooter.registerReactionTime(shooterReaction);
				if(shooter.getAvgReactionTime() > ALLOWEDAVGREACTION)
				{
					shooter.setOnline(false);
					Logger.log(shooter.getName() + ": Timeout by Average Reaction! Average must be less than "
							+ ALLOWEDAVGREACTION + "ms.", shooter, Logger.SERVER);
				}
			}
			if(!keeperAnswered && keeper.getLastInput() != null){
				keeperAnswered = true;
				keeperReaction = System.currentTimeMillis() - start;
				keeper.registerReactionTime(keeperReaction);
				if(keeper.getAvgReactionTime() > ALLOWEDAVGREACTION)
				{
					keeper.setOnline(false);
					Logger.log(keeper.getName() + ": Timeout by Average Reaction! Average must be less than "
							+ ALLOWEDAVGREACTION + "ms.", keeper, Logger.SERVER);
				}
			}		
			maxTimeout = System.currentTimeMillis() - start > MILLISTOTIMEOUT;
		}

		//if the while-loop was interrupted because of timeout, find the timed-out client and set him offline
		//then, create an automated answer for that client and, if the other client isn't offline too, use his decision
		if(maxTimeout){
			if(!shooterAnswered && !keeperAnswered){
				shooter.setOnline(false);
				Logger.log(shooter.getName() + ": Timeout! Reaction must be less than " 
				+ MILLISTOTIMEOUT + "ms.", shooter, Logger.SERVER);
				keeper.setOnline(false);
				Logger.log(shooter.getName() + ": Timeout! Reaction must be less than " 
				+ MILLISTOTIMEOUT + "ms.", keeper, Logger.SERVER);
				return requestDecisions(shooter, keeper);
			}
			if(!shooterAnswered){
				shooter.setOnline(false);
				Logger.log(shooter.getName() + ": Timeout! Reaction must be less than " 
				+ MILLISTOTIMEOUT + "ms.", shooter, Logger.SERVER);
				decisions[0] = shooter.getIndexOfStrength(Team.WEAKEST);
				switch (keeper.getLastInput().toLowerCase())
				{
					case "l":
						decisions[1] = 0;
					case "m":
						decisions[1] = 1;
					case "r":
						decisions[1] = 2;
					default:
						Logger.log(keeper.getName() + ": no valid decision. Sent 'l', 'm' or 'r' after receiving '" +
									SHOOT + "' or '" + KEEP + "'.", keeper, Logger.COMMUNICATION);
						keeper.setOnline(false);
						decisions[1] = requestDecision(keeper, KEEP);
				}
			}
			if(!keeperAnswered){
				keeper.setOnline(false);
				Logger.log(keeper.getName() + ": Timeout! Reaction must be less than " 
				+ MILLISTOTIMEOUT + "ms.", keeper, Logger.SERVER);
				decisions[1] = keeper.getIndexOfStrength(Team.WEAKEST);
				switch (shooter.getLastInput().toLowerCase())
				{
					case "l":
						decisions[0] = 0;
					case "m":
						decisions[0] = 1;
					case "r":
						decisions[0] = 2;
					default:
						Logger.log(shooter.getName() + ": no valid decision. Sent 'l', 'm' or 'r' after receiving '" +
									SHOOT + "' or '" + KEEP + "'.", shooter, Logger.COMMUNICATION);
						keeper.setOnline(false);
						decisions[0] = requestDecision(shooter, SHOOT);
				}
			}
			return decisions;
		}
		
		//Parse the decisions to int-values
		switch (shooter.getLastInput().toLowerCase())
		{
			case "l":
				decisions[0] = 0;
			case "m":
				decisions[0] = 1;
			case "r":
				decisions[0] = 2;
			default:
				Logger.log(shooter.getName() + ": no valid decision. Sent 'l', 'm' or 'r' after receiving '" +
							SHOOT + "' or '" + KEEP + "'.", shooter, Logger.COMMUNICATION);
				keeper.setOnline(false);
				decisions[0] = requestDecision(shooter, SHOOT);
		}
		
		switch (keeper.getLastInput().toLowerCase())
		{
			case "l":
				decisions[1] = 0;
			case "m":
				decisions[1] = 1;
			case "r":
				decisions[1] = 2;
			default:
				Logger.log(keeper.getName() + ": no valid decision. Sent 'l', 'm' or 'r' after receiving '" +
							SHOOT + "' or '" + KEEP + "'.", keeper, Logger.COMMUNICATION);
				keeper.setOnline(false);
				decisions[1] = requestDecision(keeper, KEEP);
		}
		
		return decisions;
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
