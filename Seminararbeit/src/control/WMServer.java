package control;


import java.net.*;
import java.io.*;
import java.util.*;

import testClient.TestClient;
import view.ServerWindow;

import model.*;


/**
 * 
 * Server Class 
 *
 */
public class WMServer extends Thread
{
	ServerWindow masterWindow;
	private int port;
	private boolean isRunning = true;
	
	private ServerSocket serverSocket;
	
	private ArrayTeamSet<Team> clientsAtServer;
	
	public int getPort() {
		return this.port;
	}

	public ArrayTeamSet<Team> getClientsAtServer(){
		return this.clientsAtServer;
	}
	
	public WMServer (int p, ServerWindow masterWindow)
	{
		this.masterWindow = masterWindow;
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
				registerTeam(s);
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
			catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		}
		System.out.println("WMServer ended.");
	}
	
	public void shutDown() {
		this.isRunning = false;
		try {
			this.serverSocket.close();
			for(Team team : clientsAtServer)
			{
				team.getClientSocket().close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void registerTeam(Socket s)
	{
		Team newTeam = new Team(s, this);
		Communication.requestName(newTeam);
		Communication.sendStrengths(newTeam);
		clientsAtServer.add(newTeam);
		System.out.println("Client " + newTeam.getName() + newTeam.getID() + " (IP: " + newTeam.getClientSocket().getInetAddress().toString().substring(1) + ") at Server. " + clientsAtServer.size() + " Teams registered.");		
		masterWindow.updateClientsAtServer(clientsAtServer.size());
	}
	
	public void startGame(int shots)
	{
		if(clientsAtServer.isEmpty())
		{
			return;
		}
		Tournament t = new Tournament(clientsAtServer.clone(), shots, masterWindow);
		(new GameManager(t)).start();
	}
	
	public Team createBot()
	{
		int registeredClients = clientsAtServer.size();
		TestClient tc = new TestClient(getPort(), "bottt");
		tc.start();
		while(clientsAtServer.size() == registeredClients){
			try{sleep(5);}catch(InterruptedException ie){ie.printStackTrace();}
		}
		for(Team team : clientsAtServer)
		{
			if(team.getName().equals("bottt"))
			{
				return team;
			}
		}
		return null;
	}
}
