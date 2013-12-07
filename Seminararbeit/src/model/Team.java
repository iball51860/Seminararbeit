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
