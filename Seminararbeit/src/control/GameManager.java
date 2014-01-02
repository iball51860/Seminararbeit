package control;

import model.*;
import java.util.*;

public class GameManager {
	
	private TreeSet<Team> contestants;
	private TreeSet<Team> contestantsInGame;
	private int shots;
	private int noOfRounds;
	private int groupSize;
	private int matchesPerGroup;
	private int noOfShotsPerMatch;
	

	public GameManager(TreeSet<Team> contestants, int shots)
	{
		this.contestants = contestants;
		this.contestantsInGame = contestants;
		this.shots = shots;
		this.noOfRounds = Analyser.calculateNoOfRounds(contestants.size());
		this.groupSize = Analyser.calculateGroupSize(contestants.size(), shots);
		this.matchesPerGroup = Analyser.calculateMatchesPerGroup(contestants.size(), shots);
		this.noOfShotsPerMatch =Analyser.calculateNoOfShotsPerMatch(contestants.size(), shots);
	}
	
	
	public void playGame()
	{
		System.out.println("Starting Game.");
		System.out.println(groupSize + " Teams per Group.");
		System.out.println(noOfRounds + " Rounds to play");
		System.out.println(noOfShotsPerMatch + " Shots per Match\n");
		
		for(int i=1; i<=noOfRounds; i++)
		{
			System.out.println("Round No. " + i + ". " + contestantsInGame.size() + " Teams in Game.");
			//TODO reset Round specific variables of teams in Round
			Communication.broadcast(contestantsInGame, Communication.NEWROUND);
			TreeSet<Group> groups = Analyser.createGroups(contestantsInGame, groupSize);
			for(Group g : groups)
			{
				for(Match m : g.getMatches()) //play all matches of the Round
				{
					playMatch(m);
				}
				
				contestantsInGame = new TreeSet<Team>();  //create new, empty list to be filled with first half contestants
				
			}
		}
	}
	
	/**
	 * Simulates the Match given as parameter. Calls the playShot method. Winning Teams get 3 Points, 
	 * loosing Teams get none. In case of a draw, both teams gain 1 Point. Method adds the
	 * Points towards the pointsInCurrentRound- and points variable of the respective Team.
	 * @param m - Match to be played
	 */
	public void playMatch(Match m)
	{
		Team a = m.getTeams()[0];
		Team b = m.getTeams()[1];
		int aGoals = 0;
		int bGoals = 0;
		Communication.sendMsg(a, Communication.NEWMATCH + " " + b.getName() + b.getID());
		Communication.sendMsg(b, Communication.NEWMATCH + " " + a.getName() + a.getID());
		
		for(int i=1; i<=noOfShotsPerMatch; i=+2)
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
			b.setPointsInCurrentRound(b.getPointsInCurrentRound() + 3);
			b.setPoints(b.getPoints() + 3);
		}
		if(aGoals == bGoals) //draw
		{
			a.setPointsInCurrentRound(a.getPointsInCurrentRound() + 1);
			a.setPoints(a.getPoints() + 1);
			b.setPointsInCurrentRound(b.getPointsInCurrentRound() + 1);
			b.setPoints(b.getPoints() + 1);
		}
		else //Team a wins
		{
			a.setPointsInCurrentRound(a.getPointsInCurrentRound() + 3);
			a.setPoints(a.getPoints() + 3);
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
	public boolean playShot(Team shooting, Team keeping)
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
