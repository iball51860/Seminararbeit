package model;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;

import control.Analyser;
import control.Logger;
import control.WMServer;

public class Team implements Comparable<Team> 
{
	public static final int WEAKEST = 0;
	public static final int MIDDLE = 1;
	public static final int STRONGEST = 2;
	
	private static int count = 0;
	private int id;
	private String name = "noName";
	
	private int[] strength = new int[3];
	//TODO delete unnecessary variables
	private int goals;
	private int goalsInCurrentRound;
	private int goalsAgainst;
	private int goalsAgainstInCurrentRound;
	private int wonMatches;
	private int finishedShots;
	
	private String lastInput;
	private boolean isInGame;

	private Socket clientSocket;
	private BufferedReader reader;
	private PrintWriter writer;
	private WMServer server;
	private boolean isOnline = true;
	private long avgReactionTime = 0;
	private int registeredReactions = 0;
	
	public Team(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
		try
		{
		this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
		}
		catch(IOException ioe){ioe.printStackTrace();}
		++count;
		setID(count);
		setIsInGame(false);
		strength = Analyser.generateStrength(40, 80, 180);
	}
	
	public Team(Socket clientSocket, WMServer server)
	{
		this(clientSocket);
		this.server = server;
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
			String read = reader.readLine();
			if (read == null)
			{
				read = "x";
			}
//			Logger.log("Reading \"" + read + "\" from " + name + ".", this, Logger.COMMUNICATION);
			return read;
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			return "x";}
	}
	
	public void write(String msg)
	{
//		Logger.log("Sending \"" + msg + "\" to " + name + ".", this, Logger.COMMUNICATION);
		writer.println(msg);
	}
	
	/*public void switchToBot() {
		System.out.println("SwitchToBot");
		int port = clientSocket.getPort();
				
		try {
			clientSocket.close();
			clientSocket = new Socket("localhost", port);
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writer = new PrintWriter(clientSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new TestClient(port);
	}*/
	
	//TODO Javadoc
	@Override
	public String toString(){
		return name + id;
	}
	
	public boolean equals(Object o){
		Team otherTeam = (Team) o;
		try { //check, if other Team is a testclient (own InetAddress)
			if(InetAddress.getLocalHost().getHostAddress().equals(this.getClientSocket().getInetAddress().getHostAddress()) || this.getClientSocket().getInetAddress().getHostAddress().equals("172.20.10.3"))
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
			if(this.getClientSocket().getInetAddress().getHostAddress().equals(otherTeam.getClientSocket().getInetAddress().getHostAddress()))
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
			return  otherTeam.getWonMatches() - wonMatches;
		}
		else 
		{
			return (otherTeam.getGoals() - otherTeam.getGoalsAgainst()) - (goals - goalsAgainst);
		}
	}

	
	/////////////////////////get- and set-methods///////////////////////////////
	
	
	/**
	 * Returns the name of the team. Is usually of type uxxxx, where x represents any letter of the alphabet.
	 * @return name of the team
	 */
	public synchronized String getName() {
		if(name == null){
			try{
				wait();
			}catch(InterruptedException ie){ie.printStackTrace();}
		}
		return name;
	}
	/**
	 * Sets the given String as name for the team. Should be of type uxxxx, where x represents any letter of the alphabet.
	 * @param name - String that represents the name, the team shall receive.
	 */
	public synchronized void setName(String name){
		this.name = name;
		notifyAll();
	}
	
	public boolean isInGame() {
		return isInGame;
	}
	
	public void setIsInGame(boolean inGame){
		this.isInGame = inGame;
	}

	/**
	 * @return the count
	 */
	public static int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public static void setCount(int count) {
		Team.count = count;
	}
	
	public static void incrementCount(int increment) {
		count += increment;
	}

	/**
	 * @return the id
	 */
	public int getID() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return the strength
	 */
	public int[] getStrength() {
		return strength;
	}

	/**
	 * @param strength the strength to set
	 */
	public void setStrength(int[] strength) {
		this.strength = strength;
	}

	public int getIndexOfStrength(int strength)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(Integer i : this.strength)
		{
			list.add(i);
		}
		Collections.sort(list);
		int toSearch = list.get(strength);
		for(int i=0; i<this.strength.length; i++)
		{
			if(toSearch == this.strength[i])
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * @return the goals
	 */
	public int getGoals() {
		return goals;
	}

	/**
	 * @param goals the goals to set
	 */
	public void setGoals(int goals) {
		this.goals = goals;
	}

	/**
	 * @return the goalsInCurrentRound
	 */
	public int getGoalsInCurrentRound() {
		return goalsInCurrentRound;
	}

	/**
	 * @param goalsInCurrentRound the goalsInCurrentRound to set
	 */
	public void setGoalsInCurrentRound(int goalsInCurrentRound) {
		this.goalsInCurrentRound = goalsInCurrentRound;
	}

	/**
	 * @return the goalsAgainst
	 */
	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	/**
	 * @param goalsAgainst the goalsAgainst to set
	 */
	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	/**
	 * @return the goalsAgainstInCurrentRound
	 */
	public int getGoalsAgainstInCurrentRound() {
		return goalsAgainstInCurrentRound;
	}

	/**
	 * @param goalsAgainstInCurrentRound the goalsAgainstInCurrentRound to set
	 */
	public void setGoalsAgainstInCurrentRound(int goalsAgainstInCurrentRound) {
		this.goalsAgainstInCurrentRound = goalsAgainstInCurrentRound;
	}

	/**
	 * @return the wonMatches
	 */
	public int getWonMatches() {
		return wonMatches;
	}

	/**
	 * @param wonMatches the wonMatches to set
	 */
	public void setWonMatches(int wonMatches) {
		this.wonMatches = wonMatches;
	}

	public void incrementWonMatches(int increment) {
		this.wonMatches += increment;
	}
	
	/**
	 * @param finishedShots the finishedShots to set
	 */
	public void setFinishedShots(int finishedShots) {
		this.finishedShots = finishedShots;
	}

	/**
	 * @return the lastInput
	 */
	public String getLastInput() {
		return lastInput;
	}

	/**
	 * @param lastInput the lastInput to set
	 */
	public void setLastInput(String lastInput) {
		this.lastInput = lastInput;
	}

	/**
	 * @return the clientSocket
	 */
	public Socket getClientSocket() {
		return clientSocket;
	}

	/**
	 * @param clientSocket the clientSocket to set
	 */
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	/**
	 * @return the reader
	 */
	public BufferedReader getReader() {
		return reader;
	}

	/**
	 * @param reader the reader to set
	 */
	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	/**
	 * @return the writer
	 */
	public PrintWriter getWriter() {
		return writer;
	}

	/**
	 * @param writer the writer to set
	 */
	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	/**
	 * @param isInGame the isInGame to set
	 */
	public void setInGame(boolean isInGame) {
		this.isInGame = isInGame;
	}

	/**
	 * @return the server
	 */
	public WMServer getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(WMServer server) {
		this.server = server;
	}

	public int getFinishedShots() {
		return finishedShots;
	}

	public void incrementFinishedShots(int increment) {
		this.finishedShots += increment;
	}

	/**
	 * @return the isOnline
	 */
	public boolean isOnline() {
		return isOnline;
	}

	/**
	 * @param isOnline the isOnline to set
	 */
	public void setOnline(boolean isOnline) {
		Logger.log(name + " set offline. Replaced by bot.", this, Logger.SERVER);
		this.isOnline = isOnline;
	}

	/**
	 * @return the avgReactionTime
	 */
	public long getAvgReactionTime() {
		/*if(avgReactionTime > 3000 && registeredReactions <= 10)
		{
			return 0;
		}*/
		return avgReactionTime;
	}
	
	/**
	 * @param l the avgReactionTime to set
	 */
	public void registerReactionTime(long reactionTime) {
		long previous = avgReactionTime * registeredReactions;
		previous += reactionTime;
		++registeredReactions;
		this.avgReactionTime = previous / registeredReactions;
	}
	

}
