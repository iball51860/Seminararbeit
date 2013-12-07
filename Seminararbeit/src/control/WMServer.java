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
	
	private static int port;
	private static boolean waitingForClients = true;
	
	private ServerSocket serverSocket;
	
	
	
	public WMServer (int p)
	{
		port = p;
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
			try 
			{
				Team t = new Team(serverSocket.accept());
				//TODO sockets sollten nur akzeptiert werden wenn die IP noch nicht bekannt ist
				Tournament.getContestants().add(t);
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}
	
	
	public static void setWaitingForClients(boolean waitingForClients){
		waitingForClients = waitingForClients;
	}
	
	public void setPort(int p){
		port = p;
	}
	
	
}
