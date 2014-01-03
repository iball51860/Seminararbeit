package control;

import model.*;
import java.util.*;

import testClient.TestClient;

public class GameManager {	
	
	public static void playGame(Tournament t)
	{
		System.out.println("Starting Game.");
		System.out.println(t.getNoOfRounds() + " Rounds to play");
		System.out.println(Analyser.calculateNoOfShotsPerMatch(t.getPlaying().size(), t.getNoOfShots()) + " Shots per Match\n");
		
		for(int i=1; i<=t.getNoOfRounds(); i++)
		{
			System.out.println("Round No. " + i + ". " + t.getPlaying().size() + " Teams in Game.");
			//TODO reset Round specific variables of teams in Round
			Communication.broadcast(t.getPlaying(), Communication.NEWROUND);
			ArrayTeamSet<Team> copy = t.getPlaying().clone();
			Collections.shuffle(copy);
			if(copy.size()%2 != 0)
			{
				copy.add(t.getServer().createBot());
			}
			int sizeAtStart = copy.size();
			for(int j=0; j<sizeAtStart; j+=2)
			{
				Team a = copy.get(0);
				Team b = copy.get(1);
				copy.remove(0);
				copy.remove(1);
				Team winner = playMatch(a, b, t.getNoOfShotsPerMatch());
				if(winner.equals(a))
				{
					t.getPlaying().remove(b);
					t.getLost().add(b);
				}
				else
				{
					t.getPlaying().remove(a);
					t.getLost().add(a);
				}
			}
			if(i == 1) //Relegation in erster Runde
			{
				int dif = (int) (Math.pow(2, t.getNoOfRounds()-1) - t.getPlaying().size());
				Collections.sort(t.getLost());
				for(int j=0; j<dif; j++)
				{
					Team rescued = t.getLost().get(0);
					t.getLost().remove(0);
					t.getPlaying().add(rescued);
				}
			}
		}
	}
	
	/**
	 * Simulates the Match given as parameter. Calls the playShot method. Winning Teams get 3 Points, 
	 * loosing Teams get none. In case of a draw, both teams gain 1 Point. Method adds the
	 * Points towards the pointsInCurrentRound- and points variable of the respective Team.
	 * @param m - Match to be played
	 */
	public static Team playMatch(Team a, Team b, int shots)
	{
		int aGoals = 0;
		int bGoals = 0;
		Communication.sendMsg(a, Communication.NEWMATCH + " " + b.getName() + b.getID());
		Communication.sendMsg(b, Communication.NEWMATCH + " " + a.getName() + a.getID());
		
		for(int i=1; i<=shots; i=+2)
		{
			boolean aScores = playShot(a, b);
			boolean bScores = playShot(b, a);
			
			if(aScores)
			{
				aGoals++;
			}
			if(bScores)
			{
				bGoals++;
			}
		}
		
		if(aGoals < bGoals) //Team b wins
		{
			return b;
		}
		if(aGoals == bGoals) //draw: draw lots
		{
			if(Math.random() < 0.5)
			{
				return a;
			}
			else
			{
				return b;
			}
		}
		else //Team a wins
		{
			return a;
		}
	}
	
	/**
	 * Simulates a shot of the first Team shooting against the second Team keeping. 
	 * Returns a boolean, whether a goal was scored by the attacking team.
	 * Chances of success for a shot are determined by whether the striker and keeper 
	 * of the teams choose the same side to shoot/jump and their strengths. Sides to 
	 * choose from are left, middle and right.
	 * If the keeper decides for the same side as the striker, the chance of scoring 
	 * is the strikers strength for that side minus the keepers strengths in percent.
	 * If the keeper is better than the striker, then chances for a goal are zero.
	 * If the keeper decides for a different side, the chance of scoring is the 
	 * strikers strength in percent.
	 * @param shooting - The team attacking
	 * @param keeping - The team defending
	 * @return Variable of type boolean, whether a goal was scored.
	 */
	public static boolean playShot(Team shooting, Team keeping)
	{
		String[] decisionCode = {"l", "m", "r"};
		
		int decisionA = Communication.requestDecision(shooting, Communication.SHOOT);
		int decisionB = Communication.requestDecision(keeping, Communication.KEEP);
	
		//Calculate chances
		double nettoStrength = shooting.getStrength()[decisionA];
		if(decisionA == decisionB)
		{
			nettoStrength =- keeping.getStrength()[decisionA];
		}
		nettoStrength = nettoStrength / 100;
		
		boolean goal = false;
		if(Math.random() < nettoStrength)
		{
			goal = true;
		}
		Communication.sendMsg(shooting, Communication.SHOTRESULT + " " + decisionCode[decisionB] + " " + goal);
		Communication.sendMsg(keeping, Communication.SHOTRESULT + " " + decisionCode[decisionA] + " " + goal);
		
		if(goal)
		{
			shooting.setGoals(shooting.getGoals() + 1);
			shooting.setGoalsInCurrentRound(shooting.getGoalsInCurrentRound() + 1);
			keeping.setGoalsAgainst(keeping.getGoalsAgainst() + 1);
			keeping.setGoalsAgainstInCurrentRound(keeping.getGoalsAgainstInCurrentRound() + 1);
		}
		return goal;
	}
}
