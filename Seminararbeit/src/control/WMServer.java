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
	
	private static int port;
	private boolean isRunning = true;
	
	private ServerSocket serverSocket;
	
	private TreeSet<Team> clientsAtServer;
	private ArrayList<InetAddress> ips;
	
	
	
	public WMServer (int p)
	{
		port = p;
		clientsAtServer = new TreeSet<Team>();
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
		
		ips = new ArrayList<InetAddress>();
		
		while(isRunning)
		{
			try 
			{
				Socket s = serverSocket.accept();
				if(!(ips.contains(s.getInetAddress()))) //TODO add backdoor for dummys (own IP)
				{
					ips.add(s.getInetAddress());
					clientsAtServer.add(new Team(s));
				}
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}
	
	
	public void startGame(int noOfRounds)
	{
		TreeSet<Team> copy = (TreeSet<Team>) clientsAtServer.clone();
		(new GameManager(copy, noOfRounds)).startGame();
	}
	
	
	
}
