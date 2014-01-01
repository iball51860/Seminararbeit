package control;

import java.util.TreeSet;

import model.Group;
import model.Team;



public class Analyser {

	public static int calculateNoOfRounds(int noOfTeams){
		return (int) Math.ceil((Math.log(noOfTeams) / Math.log(2)));
	}

	public static int calculateGroupSize(int size, int shots) { //TODO move to createGroups
		return 2;
	}

	public static int calculateMatchesPerGroup(int size, int shots) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static TreeSet<Group> createGroups(TreeSet<Team> contestantsInGame, int groupSize) {
		
		int noOfGroups = (int) Math.ceil(contestantsInGame.size()/groupSize);
		int noOfBots = (noOfGroups * groupSize) - contestantsInGame.size();
		
		
		TreeSet<Group> groups = new TreeSet<Group>();
		
		for(int i = 1; i <= groupSize; i++)
		{
			
		}
		return null;
	}

	public static int calculateNoOfShotsPerMatch(int size, int shots) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Generates a random triple of strengths in an array of integers. All three values 
	 * are element of the interval [40,80]. The sum of the three values equals exactly 180.
	 * @return Triple of Strengths
	 */
	public static int[] generateStrength()
	{
		int lowerBound = 40;
		int upperBound = 80;
		int strengthSum = 180;
		int[] strength = new int[3];
		int testSum  = 0;
		
		for(int i = 0; i < strength.length; i++) //generate random values between Bounds
		{
			int thisSide;
			thisSide = lowerBound + (int) Math.random() * (upperBound - lowerBound + 1);
			strength[i] = thisSide;
			testSum =+ thisSide;
		}
		
		int dif = strengthSum - testSum; //calculate difference to correct so strengthSum is met
		int changer = dif / Math.abs(dif);
		while(dif != 0){ //add the difference on the strengths randomly and assure Bounds
			int correctedSide = (int) Math.random() * 3;
			if((strength[correctedSide] + changer) >= lowerBound && (strength[correctedSide] + changer) <= upperBound)
			{
				strength[correctedSide] =+ changer;
				dif =- changer;
			}
		}
		
		return strength;
	}
}
