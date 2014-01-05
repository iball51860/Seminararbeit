package control;

import model.Tournament;

/**
 * The Analyser class is a service class for a tournament. It contains only static methods,
 * that analyse tournament data and return the invoked value for that tournament. The methods 
 * calculate for a tournament construct as described in {@link Tournament}.
 * @author Jan Frederic Fritze, Manuel Kaiser
 * 
 */

public class Analyser {

	/**
	 * Calculates the number of Rounds necessary to determine a winner out of a given number of
	 * Teams in a K.O.-System Tournament. 
	 * 
	 * Note that this method returns the overall number of Rounds and neither the round the tournament
	 * is currently in, nor the number of rounds left to play.
	 * @param noOfTeams: Number of Teams participating in the Tournament 
	 * @return The number (as integer) of rounds necessary to determine a winner as
	 */
	public static int calculateNoOfRounds(int noOfTeams){
		return (int) Math.ceil((Math.log(noOfTeams) / Math.log(2)));
	}
	
	/**
	 * Calculates the number of Rounds necessary to determine a winner out of a given number of
	 * Teams in a K.O.-System Tournament. 
	 * 
	 * Note that this method returns the overall number of Rounds and neither the round the tournament 
	 * is currently in, nor the number of rounds left to play.
	 * @param tournament: A Tournament for which the round-calculation is necessary 
	 * @return The number (as integer) of rounds necessary to determine a winner
	 */
	public static int calculateNoOfRounds(Tournament tournament){
		return calculateNoOfRounds(tournament.getPlaying().size() + tournament.getLost().size());
	}
	
	/**
	 * Calculates how many matches will be simulated during the conduction of a tournament, based on the
	 * number of teams participating in the tournament. The calculation is based on a tournament with 
	 * K.O.-System to determine its winner. 
	 * 
	 * Note that this method returns the overall number of matches and not the number of matches left to play.
	 * @param noOfTeams: Number of Teams participating in the Tournament 
	 * @return The number (as integer) of matches necessary to determine a winner
	 */
	public static int calculateNoOfMatches(int noOfTeams){
		int noOfRounds = calculateNoOfRounds(noOfTeams);
		int noOfMatches = 0;
		for(int i=0; i<noOfRounds-1; i++)
		{
			noOfMatches += Math.pow(2, i);
		}
		noOfMatches += Math.ceil(noOfTeams/2.0);
		return noOfMatches;
	}
	
	/**
	 * Calculates how many matches will be simulated during the conduction of a tournament, based on the
	 * number of teams participating in the tournament. The calculation is based on a tournament with 
	 * K.O.-System to determine its winner. 
	 * 
	 * Note that this method returns the overall number of matches and not the number of matches left to play.
	 * @param tournament: A Tournament for which the match-calculation is necessary 
	 * @return The number (as integer) of matches necessary to determine a winner
	 */
	public static int calculateNoOfMatches(Tournament tournament){
		return calculateNoOfMatches(tournament.getPlaying().size() + tournament.getLost().size());
	}

	/**
	 * Calculates how many shots are simulated in a single match, based on the number of teams and the
	 * overall amount of shots to be played in the tournament.
	 * 
	 * Note that this method will possibly return a number, that, if applied on each match, will not
	 * result in the given overall number of shots. Instead it will result in the <b>next number lower than
	 * the given overall number of shots</b>, where the shots can be evenly distributed to the matches.
	 * 
	 * If accurate simulation of the overall amount of shots is necessary, it is recommended to calculate 
	 * the difference between the given Number of Teams and the overall shot amount returned. The difference 
	 * can be calculated with <code>calculateNoOfMatches(noOfTeams) * calculateNoOfShotsPerMatch(noOfTeams, shots)</code> 
	 * and add them as necessary, e.g. in the final match.
	 * @param noOfTeams - Number of Teams playing in the tournament
	 * @param shots - order of magnitude of how many shots shall be simulated throughout the tournament
	 * @return Number of shots that have to be simulated in each match in order to reach the given overall shot amount
	 */
	public static int calculateNoOfShotsPerMatch(int noOfTeams, int shots) {
		int noOfMatches = calculateNoOfMatches(noOfTeams);
		int noOfShots = (int) shots/noOfMatches;
		if(noOfShots%2 != 0)
		{
			--noOfShots;
		}
		
		return noOfShots;
	}
	
	/**
	 * Calculates how many shots are simulated in a single match, based on a given tournament.
	 * 
	 * Note that this method will possibly return a number, that, if applied on each match, will not
	 * result in the given overall number of shots in that tournament. Instead it will result in the 
	 * <b>next number lower than the given overall number of shots</b>, where the shots can be evenly 
	 * distributed to the matches.
	 * 
	 * If accurate simulation of the overall amount of shots is necessary, it is recommended to calculate 
	 * the difference between the given Number of Teams (<code> tournament.getPlaying().size() + 
	 * tournament.getLost().size()</code>) and the overall shot amount returned. The difference 
	 * can be calculated with <code>calculateNoOfMatches(tournament) * calculateNoOfShotsPerMatch(tournament)</code> 
	 * and add them as necessary, e.g. in the final match.
	 * @param noOfTeams - Number of Teams playing in the tournament
	 * @param shots - order of magnitude of how many shots shall be simulated throughout the tournament
	 * @return Number of shots that have to be simulated in each match in order to reach the given overall shot amount
	 */
	public static int calculateNoOfShotsPerMatch(Tournament tournament){
		return calculateNoOfShotsPerMatch(tournament.getPlaying().size() + tournament.getLost().size(), tournament.getNoOfShots());
	}
	
	/**
	 * Generates a random triple of strengths in an array of integers. All three values 
	 * are element of the interval [lowerBound, upperBound]. The sum of the three values equals 
	 * exactly strengthSum.
	 * 
	 * The returned triple can be used as a triple of strengths for a {@link Team}.
	 * @return Triple of Strengths in an int-array
	 */
	public static int[] generateStrength(int lowerBound, int upperBound, int strengthSum)
	{
		int[] strength = new int[3];
		int testSum  = 0;
		
		for(int i = 0; i < strength.length; i++) //generate random values between Bounds
		{
			int thisSide;
			thisSide = lowerBound + (int) (Math.random() * (upperBound - lowerBound + 1));
			strength[i] = thisSide;
			testSum += thisSide;
		}
		int dif = strengthSum - testSum; //calculate difference to correct so strengthSum is met
		int changer = 0;
		if(dif != 0)
		{
		changer = dif / Math.abs(dif);
		}
		while(dif != 0){ //add the difference on the strengths randomly and assure Bounds
			int correctedSide = (int) (Math.random() * 3);
			if((strength[correctedSide] + changer) >= lowerBound && (strength[correctedSide] + changer) <= upperBound)
			{
				strength[correctedSide] += changer;
				dif -= changer;
			}
		}
		
		return strength;
	}
	
	/**
	 * Returns the name of the round the given tournament is in, as in common language. 
	 * (Final, Seminfinal, Quarterfinal, Last 16, etc.)
	 * Returns Relegation if the tournament is in Relegation.
	 * @param tournament: The tournament for which the name shall be calculated
	 * @return Round name of current round of tournament
	 */
	public static String getCurrentRoundName(Tournament tournament)
	{
		int noOfRounds = tournament.getNoOfRounds();
		int currentRound = tournament.getCurrentRound();
		
		if(noOfRounds == currentRound)
		{
			return "Relegation";
		}
		
		switch(currentRound)
		{
			case 1:
				return "Final";
			case 2:
				return "Semifinal";
			case 3:
				return "Quarterfinal";
			default:
				return "Last " + Math.pow(2, currentRound);
		}
	}
}
