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
	private boolean waitingForClients = true;
	
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
				t.setIP(t.getSocket().getInetAddress());
				//TODO sockets sollten nur akzeptiert werden wenn die IP noch nicht bekannt ist
				Tournament.contestants.add(t);
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}
	
	
	public void setWaitingForClients(boolean waitingForClients){
		this.waitingForClients = waitingForClients;
	}
	
	public void setPort(int p){
		port = p;
	}
	
	
}
