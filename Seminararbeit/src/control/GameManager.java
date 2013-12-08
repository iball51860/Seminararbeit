package control;

import model.*;
import java.util.*;

public class GameManager {
	
	private TreeSet<Team> contestants;
	private TreeSet<Team> contestantsInGame;
	private int shots;
	
	//TODO add game-logic
	public GameManager(TreeSet<Team> contestants, int shots)
	{
		this.contestants = contestants;
		this.contestantsInGame = contestants;
		this.shots = shots;
	}
	
	
	public void startGame()
	{
		int noOfRounds = Analyser.recommendNoOfRounds(contestants.size(), shots);
		int groupSize = Analyser.recommendGroupSize(contestants.size(), shots);
		int matchesPerGroup = Analyser.recommendMatchesPerGroup(contestants.size(), shots);
		int noOfShotsPerMatch =Analyser.recommendNoOfShotsPerMatch(contestants.size(), shots);
		
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
	
	public static void playMatch(Match m)
	{
		
	}
}
