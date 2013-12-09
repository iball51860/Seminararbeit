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
	private int goalsAgainst;
	
	private String lastInput;
	private boolean inMatch;

	private Socket clientSocket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public Team(Socket clientSocket) //TODO add possibility to ad name and id
	{								//TODO generate keeper/striker
		this.clientSocket = clientSocket;
		try
		{
		this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
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
	
	
	public int getID(){
		return id;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	
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
		try
		{
			writer.write(msg);
		}
		catch(IOException ioe){ioe.printStackTrace();}
	}
	
	
	//TODO Javadoc
	@Override
	public String toString(){
		return name + id;
	}
	
	
	public int compareTo(Team otherTeam) //TODO: Compare Team by points
	{
		return 0;
	}
}
