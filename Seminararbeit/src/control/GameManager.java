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
			//TODO decomment  CommunicationManager.broadcast(contestantsInGame, CommunicationManager.NEWROUND)
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
		//CommunicationManager.sendMsg(a, CommunicationManager.NEWMATCH + b.getName() + b.getID());
		//CommunicationManager.sendMsg(b, CommunicationManager.NEWMATCH + a.getName() + a.getID());
		for(int i=1; i<=noOfShotsPerMatch; i++)
		{
			//int decisionA = CommunicationManager.requestDecision(a); //TODO implement keeper oder striker schuss
		}
	}
}
