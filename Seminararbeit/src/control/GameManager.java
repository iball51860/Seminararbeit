package control;

import model.*;
import java.util.*;

import testClient.TestClient;

public class GameManager extends Thread{	
	
	private Tournament tournament;
	
	public GameManager(Tournament t)
	{
		this.tournament = t;
	}
	
	public void run()
	{
		playGame(tournament);
	}
	
	public static void playGame(Tournament t)
	{
		for(Team te : t.getPlaying())
		{
			te.setIsInGame(true);
		}
		t.getMasterWindow().updateTeamView(t.getPlaying());
		System.out.println("Starting Game.");
		System.out.println(t.getNoOfRounds() + " Rounds to play");
		System.out.println(Analyser.calculateNoOfShotsPerMatch(t.getPlaying().size(), t.getNoOfShots()) + " Shots per Match\n");
		
		int finalExtraShots = t.getNoOfShots() - (t.getNoOfShotsPerMatch() * t.getNoOfMatches());
		for(int i=t.getNoOfRounds(); i>=1; i--) //counts rounds down for better consistency with no of Teams playing
		{
			t.setCurrentRound(i);
			System.out.println("Round No. " + i + ". " + t.getPlaying().size() + " Teams in Game.");
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
				copy.remove(0);
				a.resetRoundVariables();
				b.resetRoundVariables();
				int goalsToPlayInMatch = t.getNoOfShotsPerMatch();
				if(i==1)
				{
					goalsToPlayInMatch += finalExtraShots;
				}
				Team winner = playMatch(a, b, goalsToPlayInMatch);
				t.incrementFinishedMatches(1);
				t.incrementGoals(a.getGoalsInCurrentRound() + b.getGoalsInCurrentRound());
				t.getMasterWindow().updateMetaData(t);
				Team looser;
				if(winner.equals(a))
				{
					looser = b;
				}
				else
				{
					looser = a;				
				}
				t.getPlaying().remove(looser);
				t.getLost().add(looser);
				looser.setIsInGame(false);
				t.getMasterWindow().updateTeamView(looser);
				
			}
			if(i == t.getNoOfRounds()) //Relegation in erster Runde
			{
				int dif = (int) (Math.pow(2, t.getNoOfRounds()-1) - t.getPlaying().size());
				Collections.sort(t.getLost());
				for(int j=0; j<dif; j++)
				{
					Team rescued = t.getLost().get(0);
					t.getLost().remove(0);
					t.getPlaying().add(rescued);
					rescued.setIsInGame(true);
					t.getMasterWindow().updateTeamView(rescued);
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
		
		for(int i=1; i<=shots; i+=2)
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
			nettoStrength -= keeping.getStrength()[decisionA];
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
