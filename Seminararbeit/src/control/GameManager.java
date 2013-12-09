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
		this.noOfRounds = Analyser.recommendNoOfRounds(contestants.size(), shots);
		this.groupSize = Analyser.recommendGroupSize(contestants.size(), shots);
		this.matchesPerGroup = Analyser.recommendMatchesPerGroup(contestants.size(), shots);
		this.noOfShotsPerMatch =Analyser.recommendNoOfShotsPerMatch(contestants.size(), shots);
	}
	
	
	public void startGame()
	{
		for(int i=1; i<=noOfRounds; i++)
		{
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
	 * Simulates a Match given. Calls the playShot method. Winning Teams get 3 Points, 
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
	
	public boolean playShot(Team shooting, Team keeping)
	{
		String[] decisionCode = {"l", "m", "r"};
		
		int decisionA = Communication.requestDecision(shooting, Communication.SHOOT);
		int decisionB = Communication.requestDecision(keeping, Communication.KEEP);
	
		int nettoStrength = shooting.getStriker().getStrength()[decisionA];
		if(decisionA == decisionB)
		{
			nettoStrength =- decisionB;
		}
	
		int random = (int) (Math.random() * 100);
		boolean goal = false;
		if(random < nettoStrength)
		{
			goal = true;
		}
		Communication.sendMsg(shooting, Communication.SHOTRESULT + " " + decisionCode[decisionB] + " " + goal);
		Communication.sendMsg(keeping, Communication.SHOTRESULT + " " + decisionCode[decisionA] + " " + goal);
		
		if(goal)
		{
			shooting.setGoals(shooting.getGoals() + 1);
			keeping.setGoalsAgainst(keeping.getGoalsAgainst() + 1);
		}
		return goal;
	}
}
