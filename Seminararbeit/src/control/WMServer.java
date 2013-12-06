package control;


import java.net.*;
import java.io.*;
import java.util.*;

import model.*;


/**
 * 
 * Server Class 
 *
 */
public class WMServer 
{
	
	private int port;
	private boolean waitingForClients = true;
	
	private ServerSocket serverSocket;
	private TreeSet<Team> teams;
	
	
	
	public WMServer (int port)
	{
		this.port = port;
		TreeSet<Team> teams = new TreeSet<Team>();
	}
	
	/**
	 * Server gets initialized and accepts Clients
	 */
	public void start() 
	{
		try 
		{
			serverSocket = new ServerSocket(port);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		while(waitingForClients) 
		{
			Team t = new Team(serverSocket.accept());
			teams.add(t);
		}
	}
	
	
	public void setWaitingForClients(boolean waitingForClients){
		this.waitingForClients = waitingForClients;
	}	
	
	
}
