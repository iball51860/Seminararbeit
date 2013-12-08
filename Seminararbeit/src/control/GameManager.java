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
	
	//TODO add game-logic
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
				for(Match m : g.getMatches())
				{
					playMatch(m);
				}
			}
		}
	}
	
	public void playMatch(Match m)
	{
		Team a = m.getTeams()[0];
		Team b = m.getTeams()[1];
		Communication.sendMsg(a, Communication.NEWMATCH + " " + b.getName() + b.getID());
		Communication.sendMsg(b, Communication.NEWMATCH + " " + a.getName() + a.getID());
		
		String[] decisionCode = {"l", "m", "r"};
		
		for(int i=1; i<=noOfShotsPerMatch; i=+2)
		{
			{ //Team a shoots on Team b
				int decisionA = Communication.requestDecision(a, Communication.SHOOT);
				int decisionB = Communication.requestDecision(b, Communication.KEEP);
			
				int nettoStrength = a.getStriker().getStrength()[decisionA];
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
				Communication.sendMsg(a, Communication.SHOTRESULT + " " + decisionCode[decisionB] + " " + goal);
				Communication.sendMsg(b, Communication.SHOTRESULT + " " + decisionCode[decisionA] + " " + goal);
			}
			{ //Team b shoots on Team a
				int decisionA = Communication.requestDecision(a, Communication.KEEP);
				int decisionB = Communication.requestDecision(b, Communication.SHOOT);
			
				int nettoStrength = b.getStriker().getStrength()[decisionB];
				if(decisionA == decisionB)
				{
					nettoStrength =- decisionA;
				}
			
				int random = (int) (Math.random() * 100);
				boolean goal = false;
				if(random < nettoStrength)
				{
					goal = true;
				}
				Communication.sendMsg(a, Communication.SHOTRESULT + " " + decisionCode[decisionB] + " " + goal);
				Communication.sendMsg(b, Communication.SHOTRESULT + " " + decisionCode[decisionA] + " " + goal);
			}
		}
	}
}
