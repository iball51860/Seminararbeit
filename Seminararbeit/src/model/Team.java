package model;

import java.io.*;
import java.net.*;

import control.Analyser;

public class Team implements Comparable<Team> 
{
	
	private static int count = 0;
	private int id; //TODO overthink
	private String name;
	
	private int[] strength = new int[3];
	
	private int points;
	private int goals;
	private int goalsInCurrentRound;
	private int goalsAgainst;
	private int goalsAgainstInCurrentRound;
	private int wonMatches;
	
	private String lastInput;
	private boolean isInGame;

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
		setIsInGame(false);
		strength = Analyser.generateStrength(40, 80, 180);
	}
	
	public Team(Socket clientSocket, int id){
		this(clientSocket);
		setID(id);
	}
	
	public void resetRoundVariables()
	{
		setGoalsAgainstInCurrentRound(0);
		setGoalsInCurrentRound(0);
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
	

	public boolean equals(Team otherTeam){
		try { //check, if other Team is a testclient (own InetAddress)
			if(this.getSocket().getInetAddress().equals(InetAddress.getLocalHost()))
			{
				if(this.getID() == otherTeam.getID())
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			//check, if the teams have the same IP
			if(this.getSocket().getInetAddress().equals(otherTeam.getSocket().getInetAddress()))
			{
				return true;
			}
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		
		return false;
	}
	
	public int compareTo(Team otherTeam) 
	{
		if(wonMatches != otherTeam.getWonMatches())
		{
			return wonMatches - otherTeam.getWonMatches();
		}
		else 
		{
			return (goals - goalsAgainst) - (otherTeam.getGoals() - otherTeam.getGoalsAgainst());
		}
	}

	
	/////////////////////////get- and set-methods///////////////////////////////
	

	public static int getCount()
	{
	return count;
	}
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
	
	
	public void setIsInGame(boolean isInGame){
		this.isInGame = isInGame;
	}
	
	public boolean isInGame() {
		return isInGame;
	}
	
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return points;
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
	
	private int getWonMatches() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int[] getStrength() //TODO overwrite or overload with return int in dependance on l/m/r
	{
		return strength;
	}
	
	
}
