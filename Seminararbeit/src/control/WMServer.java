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
	
	private static ArrayList<InetAddress> ips;
	
	
	
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
		
		ips = new ArrayList<InetAddress>();
		
		while(waitingForClients)
		{
			try 
			{
				Socket s = serverSocket.accept();
				if(!(ips.contains(s.getInetAddress())))
				{
					ips.add(s.getInetAddress());
					Tournament.getContestants().add(new Team(s));
				}
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}
	
	
	public static void setWaitingForClients(boolean wFC){
		waitingForClients = wFC;
	}
	
	public void setPort(int p){
		port = p;
	}
	
	
}
