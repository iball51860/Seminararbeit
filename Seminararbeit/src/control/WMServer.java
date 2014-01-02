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
public class WMServer extends Thread
{
	
	private int port;
	public int getPort() {
		return this.port;
	}

	private boolean isRunning = true;
	
	private ServerSocket serverSocket;
	
	private ArrayTeamSet<Team> clientsAtServer;
	
	
	
	public WMServer (int p)
	{
		port = p;
		clientsAtServer = new ArrayTeamSet<Team>();
		System.out.println("WMServer erzeugt.");
	}
	
	/**
	 * Server gets initialized and accepts Clients
	 */
	public void run() 
	{
		try 
		{
			serverSocket = new ServerSocket(port);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		while(isRunning)
		{
			System.out.println("WMServer läuft.\n");
			try 
			{
				Socket s = serverSocket.accept();
				Team newTeam = new Team(s);
				Communication.requestName(newTeam);
				//Communication.sendStrengths(newTeam);	//TODO implement FootballPlayers for Teams so strengths can be generated
				clientsAtServer.add(newTeam);
				System.out.println("Client " + newTeam.getName() + newTeam.getID() + " (IP: " + newTeam.getSocket().getInetAddress() + ") at Server. " + clientsAtServer.size() + " Teams registered.");
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}
	
	
	public void startGame(int shots)
	{
		Tournament t = new Tournament((ArrayTeamSet<Team>) clientsAtServer.clone(), shots); //TODO catch CastException
		GameManager.playGame(t);
	}
	
	
	
}
