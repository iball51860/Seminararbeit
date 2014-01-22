package control;

import model.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameManager extends Thread{	
	
	private Tournament tournament;
	
	public GameManager(Tournament t)
	{
		this.tournament = t;
		this.setName("GameManagerThread");
	}
	
	public void run()
	{
		playGame(tournament);
	}
	
	public static void playGame(Tournament t)
	{
		long start = System.currentTimeMillis();
		for(Team te : t.getPlaying())
		{
			te.setIsInGame(true);
		}
		t.getMasterWindow().updateTeamView(t);
		Logger.log("\nStarting Game.\n" + t.getNoOfRounds() + " Rounds to play.\n" + t.getNoOfMatches() + " " +
				"Matches to Play.\n" + t.getNoOfShotsPerMatch() + " Shots per Match.", Logger.GAME);
		
		int finalExtraShots = t.getNoOfShots() - (t.getNoOfShotsPerMatch() * t.getNoOfMatches());
		for(int i=t.getNoOfRounds(); i>=1 && t.isRunning(); i--) //counts rounds DOWN for better consistency with no of Teams playing
		{
			t.setCurrentRound(i);
			Logger.log("\n" + Analyser.getCurrentRoundName(t).toUpperCase(), Logger.ROUND);
			
			ArrayTeamSet<Team> copy = t.getPlaying().clone();
			Collections.shuffle(copy);
			if(copy.size()%2 != 0)
			{
				Team bot = t.getServer().createBot();
				copy.add(bot);
			}
			ArrayTeamSet<Team> losersOfTheRound = copy.clone();
			int sizeAtStart = copy.size();
			CopyOnWriteArrayList<SubManager> threadList = new CopyOnWriteArrayList<SubManager>();
			
			for(int j=0; j<sizeAtStart && t.isRunning(); j+=2)
			{
				//number of threads can be limited
				while(threadList.size() >= 10)
				{
					for(SubManager thread : threadList)
					{
						if(!thread.isAlive())
						{
							threadList.remove(thread);
						}
					}
				}
				Team a = copy.get(0);
				Team b = copy.get(1);
				copy.remove(0);
				copy.remove(0);
				a.resetRoundVariables();
				b.resetRoundVariables();
				int goalsToPlayInMatch = t.getNoOfShotsPerMatch();
				if(i==1) //add excess shots in final
				{
					goalsToPlayInMatch += finalExtraShots;
				}
				SubManager newSubManager = new SubManager(a, b, t, goalsToPlayInMatch);
				newSubManager.start();
				threadList.add(newSubManager);
			}
			
			while(!threadList.isEmpty())
			{
				for(SubManager thread : threadList)
				{
					if(!thread.isAlive())
					{
						threadList.remove(thread);
					}
				}
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
			losersOfTheRound.retainAll(t.getLost());
			Communication.broadcast(losersOfTheRound, Communication.GAMEOVER);
		}
		Logger.log(t.getPlaying().get(0) + " wins! Congratulations, " + t.getPlaying().get(0) + "!", t.getPlaying().get(0), Logger.GAME);
		t.setDuration(System.currentTimeMillis() - start);
		t.getMasterWindow().showFinish();
	}
	
	public static Team playMatch(Team a, Team b, int shots, Tournament t)
	{
		Logger.log(a.getName() + " vs. " + b.getName(), a, Logger.MATCH);
		Logger.log(b.getName() + " vs. " + a.getName(), b, Logger.MATCH);
		t.getMasterWindow().updateTeamInMatchView(a, b);
		int aGoals = 0;
		int bGoals = 0;
		Communication.sendMsg(a, Communication.NEWMATCH + " " + b.getName() + b.getID());
		Communication.sendMsg(b, Communication.NEWMATCH + " " + a.getName() + a.getID());
		
		for(int i=1; i<=shots && t.isRunning(); i+=2)
		{
			boolean aScores = playShot(a, b);
			boolean bScores = playShot(b, a);
			t.incrementFinishedShots(2);
			
			if(aScores)
			{
				aGoals++;
				t.incrementGoals(1);
			}
			if(bScores)
			{
				bGoals++;
				t.incrementGoals(1);
			}
			if((t.getFinishedShots() % (t.getNoOfShots()/1000)) == 0)	
				//TODO Exception behandeln wenn weniger als 1000 Schüsse gespielt werden
					{
						t.getMasterWindow().updateShots(t);
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
//			Logger.log(shooting.getName() + " scores against " + keeping.getName(), shooting, Logger.SHOT);
			goal = true;
		}
		else
		{
//			Logger.log(keeping.getName() + " keeps a goal from " + shooting.getName(), shooting, Logger.SHOT);
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
		shooting.incrementFinishedShots(1);
		return goal;
	}
}
