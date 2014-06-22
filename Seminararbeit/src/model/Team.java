package model;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;

import control.Analyser;
import control.Logger;
import control.TeamCommunication;
import control.WMServer;
/**
 * Represents a Team that is controled by one client. A Team is uniquely identifyable by its ID.
 * The Class holds the server-sided Data of the Team, not the client sided. It is the clients
 * representation on the server and is used to store it's game data and communication details
 * such as the stream from and to the server or whether the client is still available.
 * @author Jan Fritze & Manuel Kaiser
 * @see TeamCommunication
 */
public class Team implements Comparable<Team> 
{
	/**Provides representational number for weakest strength. If called in {@code getIndexOfStrength(int strength)} getIndexOfStrength will
	 * return the weakest side as Integer (0=left, 1=middle, 2=right).*/
	public static final int WEAKEST = 0;
	/**Provides representational number for middle strength. If called in {@code getIndexOfStrength(int strength)} getIndexOfStrength will
	 * return the second-strongest (weakest) side as Integer (0=left, 1=middle, 2=right).*/
	public static final int MIDDLE = 1;
	/**Provides representational number for strongest strength. If called in {@code getIndexOfStrength(int strength)} getIndexOfStrength will
	 * return the strongest side as Integer (0=left, 1=middle, 2=right).*/
	public static final int STRONGEST = 2;
	
	/**Count to create IDs**/
	private static int count = 0;
	
	/**ID that uniquely identifies each Team*/
	private int id;
	/**Name as sent by the teams client*/
	private String name = "noNme";

	/**Array representing the teams strengths. Indexes:<br>0 = left<br>1 = middle<br>2 = right*/
	private int[] strength = new int[3];
	
	/**Goals the team has scored during the whole game*/
	private int goals;
	/**Goals the team has scored in the current Round*/
	private int goalsInCurrentRound;
	/**The Goals opponent Teams have achieved against this team*/
	private int goalsAgainst;
	/**The Goals the opponent Team has achieved against this team in the current Round*/
	private int goalsAgainstInCurrentRound;
	/**Number of Matches this team won*/
	private int wonMatches;
	/**Number of shots this team has shot in the game*/
	private int finishedShots;
	
	/**The last message received from the client*/
	private String lastInput;
	/**True if team is still in the Tournament, false if not.
	 * Flag gets set to false when Team looses.<br><br>NOTE: isInGame can be true even when 
	 * isOnline is false. That is in case the team was replaced by a bot and is still playing.*/
	private boolean isInGame;

	/**The Socket used to communicate through with this team.*/
	private Socket clientSocket;
	/**The reader used to read messages from this team.*/
	private BufferedReader reader;
	/**The writer used to send messages to this team.*/
	private PrintWriter writer;
	/**An Instance of the WMServer this team has signed on to.*/
	private WMServer server;
	/**True if client is still available (online), false if offline.
	 * If the client is offline its decisions are automatically calculated by the server.*/
	private boolean isOnline = true;
	/**A queue holding the latest measured latencies of this team. This queue is used to calculate 
	 * a flowing average.<br><br>NOTE: The less values, the higher the deviation. However, the more values,
	 * the <strong>less</strong> focus is directed to <strong>recent</strong> reaction times.*/
	private ArrayBlockingQueue<Long> LastReactionTimes = new ArrayBlockingQueue<Long>(50);
	
	/**
	 * Constructs the technical details to enable communication such as the reader and writer. Provides
	 * ID and calculates random strengths for this team.
	 * @param clientSocket
	 * @see Analyser
	 */
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
	
	/**
	 * Constructs the technical details to enable communication such as the reader and writer. Provides
	 * ID and calculates random strengths for this team. Sets the Server the Team is signed on to.
	 * @param clientSocket
	 * @param server
	 */
	public Team(Socket clientSocket, WMServer server)
	{
		this(clientSocket);
		this.server = server;
	}
	
	/**
	 * Sets the round-related variables goalsAgainstInCurrentRound and goalsInCurrentRound to 0. 
	 * Is called at the beginning of each new round.
	 */
	public void resetRoundVariables()
	{
		setGoalsAgainstInCurrentRound(0);
		setGoalsInCurrentRound(0);
	}
	
	/**
	 * Reads a line from this teams Client out of the InputStream.
	 * @return
	 */
	public String read()
	{
		return TeamCommunication.read(reader);
	}
	
	/**
	 * Sends a String to the teams client.
	 * @param message
	 */
	public void write(String message)
	{
		TeamCommunication.write(message, writer);
	}
	
	/**
	 * Flushes the input stream from the client. Use to make sure no old irrelevant messages
	 * are read. 
	 */
	public void flushReader(){
		TeamCommunication.flushReader(reader);
	}
	
	/**
	 * Returns a String representation of the Team.
	 * Here the representation is the Teams name with its ID appended.
	 */
	@Override
	public String toString(){
		return name + id;
	}
	
	/**
	 * Compares two Objects and checks, whether they are equal.
	 * Since one Team represent one client and one client may only have one Team, two teams are equal when
	 * they represent the same client or have the same ID. The only exception to the first Rule applies for the TestClients
	 * that are started from the same server. Here the client, which is the same as the server is allowed to register an infinite 
	 * number of teams.<br>Simplified (not actual code):<br>
	 * {@code if(teamA.ID == teamB.ID)}<br>
	 * &nbsp&nbsp&nbsp{@code return true;}<br>
	 * {@code else}<br>
	 * &nbsp&nbsp&nbsp{@code if(teamA.IPAddress.equals(teamB.IPAddress) && teamA.IPAddress.notEquals(ServerIP))}{@code {<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp{@code return true;}<br>&nbsp&nbsp&nbsp&nbsp{@code }}<br>
	 * &nbsp&nbsp&nbsp&nbsp{@code return false;}<br><br>
	 * NOTE: Message signature is equals(Object o). The method typecasts the Object to Team. Only Compare Teams. If you want to compare
	 * other types we recommend to add code checking for class of the object to compare.
	 */
	public boolean equals(Object o){
		if(o == null && this != null)
		{
			return false;
		}
		Team otherTeam = (Team) o;
		try { //check, if other Team is a testclient (own InetAddress)
			if(InetAddress.getLocalHost().getHostAddress().equals(this.getClientSocket().getInetAddress().getHostAddress())/* || this.getClientSocket().getInetAddress().getHostAddress().equals("192.168.2.194")*/)
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
	
	/**
	 * Gives a natural order to Instances of Team. <br>Returns the difference of the Teams won Matches to the other Teams won Matches.
	 * Alternatively, if both teams won the same amount of Matches gives the difference between the teams goal differences.
	 */
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

	/**
	 * Enqueues a measured Latency in the LastReactionTimes List, so it can be used for further calculation.
	 * Might remove older values if max. capacity is reached, according to first in, first out (FIFO).
	 * @param l the avgReactionTime to register
	 */
	public void registerReactionTime(long reactionTime) {
		if(LastReactionTimes.remainingCapacity() == 0)
		{
			LastReactionTimes.poll();
		}
		LastReactionTimes.offer(reactionTime);
	}
	
	/**
	 * Returns the side on which the Team has the weakest, middle or strongest strength.<br>
	 * WEAKEST = 0<br>MIDDLE = 1<br>STRONGEST = 2<br><br>
	 * Side will be returned as Integer<br>0 = Left<br>1 = Middle<br>2 = Right
	 * @param strength
	 * @return
	 */
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
	
	/////////////////////////get-, set and increment-methods///////////////////////////////
	/* FOR REASONS OF SAVING TIME WE WILL NOT PROVIDE DETAILED JAVADOC DOCUMENTATION FOR GETTERS AND SETTER. THANK YOU FOR UNDERSTANDING*/
	

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
	
	/**
	 * Increments the count of Teams.
	 * @param increment
	 */
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

	/**
	 * increments the won matches of this team by the increment
	 * @param increment
	 */
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
	 * Returns the average Reaction time of the teams last x latencies, where x is 
	 * the number of latencies stored in the queue LastReactionTimes.
	 * @return the avgReactionTime
	 */
	public long getAvgReactionTime() {
		if(LastReactionTimes.size() <= 10)
		{
			return 1;
		}
		else
		{
			long reaction = 0;
			for (Long l : LastReactionTimes) {
				reaction += l;
			}
			return reaction / LastReactionTimes.size();
		}
	}
	
	/**
	 * Returns the standard Deviation for an already calculated average of this clients reaction.
	 * The Deviation calculates how reliable the client responds with the same latency.
	 *	<br><br><strong>The given Average must be calculated very recently from LastReactionTimes.</strong>
	 * @param avg
	 * @return
	 */
	public long getStandardDeviation(long avg)
	{
		long deviation = 0;
		for(Long l : LastReactionTimes){
			deviation += Math.pow((l-avg), 2);
		}
		deviation = deviation / LastReactionTimes.size();
		return (long) Math.sqrt(deviation);
	}
	
	/**
	 * Returns the standard Deviation for this clients reaction.
	 * The Deviation calculates how reliable the client responds with the same latency.
	 * @param avg
	 * @return
	 */
	public long getStandardDeviation(){
		return getStandardDeviation(getAvgReactionTime());
	}
	

}
