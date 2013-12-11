package model;

import java.io.*;
import java.net.*;

public class Team implements Comparable<Team> 
{
	
	private static int count = 0;
	private int id; //TODO overthink
	private String name;
	
	private FootballPlayer keeper;
	private FootballPlayer striker;
	
	private int points;
	private int pointsInCurrentRound;
	private int goals;
	private int goalsInCurrentRound;
	private int goalsAgainst;
	private int goalsAgainstInCurrentRound;
	
	private String lastInput;
	private boolean inMatch;

	private Socket clientSocket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	public Team(Socket clientSocket) //TODO add possibility to ad name and id
	{								//TODO generate keeper/striker
		this.clientSocket = clientSocket;
		try
		{
		this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
		}
		catch(IOException ioe){ioe.printStackTrace();}
		setID(++count);
		setInMatch(true);
		this.keeper = new FootballPlayer();
		this.striker = new FootballPlayer();
	}
	
	public Team(Socket clientSocket, int id){
		this(clientSocket);
		setID(id);
	}
	
	
	public String read()
	{
		try
		{
			return reader.readLine();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			return null;}
	}
	
	public void write(String msg)
	{
		writer.println(msg);
	}
	
	
	//TODO Javadoc
	@Override
	public String toString(){
		return name + id;
	}
	
	/**
	 * Returns the difference of a special value created for each team.
	 * The value puts weight on the actual score of the Team IN ITS CURRENT ROUND.
	 */
	public int compareTo(Team otherTeam) //TODO: add exception for goal-difference interfering with points 
	{
		int ownScore = getPointsInCurrentRound();
		int oppScore = otherTeam.getPointsInCurrentRound();
		
		ownScore = ownScore * 1000000; //increase weight of points
		oppScore = oppScore * 1000000;
		
		ownScore =+ goalsInCurrentRound - goalsAgainstInCurrentRound; //add goal difference
		oppScore =+ otherTeam.getGoalsInCurrentRound() - otherTeam.getGoalsAgainstInCurrentRound();
		
		return ownScore - oppScore; //return difference to the otherTeam
	}

	
	/////////////////////////get- and set-methods///////////////////////////////
	
	/**
	 * Returns the id of the teams instance.
	 * @return id of the team
	 */
	public int getID(){
		return id;
	}
	/**
	 * Sets the given Integer as id for this team.
	 * @param id - id to be set for this team
	 */
	public void setID(int id){
		this.id = id;
	}
	
	/**
	 * Returns the name of the team. Is usually of type uxxxx, where x represents any letter of the alphabet.
	 * @return name of the team
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the given String as name for the team. Should be of type uxxxx, where x represents any letter of the alphabet.
	 * @param name - String that represents the name, the team shall receive.
	 */
	public void setName(String name){
		this.name = name;
	}
	
	
	public Socket getSocket(){
		return clientSocket;
	}
	
	
	public void setLastInput(String lastInput){
		this.lastInput = lastInput;
	}
	
	public String getLastInput(){
		return lastInput;
	}
	
	
	public void setInMatch(boolean inMatch){
		this.inMatch = inMatch;
	}
	
	
	public FootballPlayer getKeeper() {
		return keeper;
	}
	
	public FootballPlayer getStriker() {
		return striker;
	}
	
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getPointsInCurrentRound() {
		
		return pointsInCurrentRound;
	}
	
	public void setPointsInCurrentRound(int pointsInCurrentRound) {
		this.pointsInCurrentRound = pointsInCurrentRound;
	}
	
	
	public void setGoals(int goals) {
		this.goals = goals;
	}
	
	public int getGoals() {
		return goals;
	}

	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}
	
	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	
	public int getGoalsInCurrentRound() {
		return goalsInCurrentRound;
	}

	
	public void setGoalsInCurrentRound(int goalsInCurrentRound) {
		this.goalsInCurrentRound = goalsInCurrentRound;
	}

	
	public int getGoalsAgainstInCurrentRound() {
		return goalsAgainstInCurrentRound;
	}

	
	public void setGoalsAgainstInCurrentRound(int goalsAgainstInCurrentRound) {
		this.goalsAgainstInCurrentRound = goalsAgainstInCurrentRound;
	}
	
	
}
