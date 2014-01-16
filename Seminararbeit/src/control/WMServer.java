package control;


import java.net.*;
import java.io.*;

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
		
		try {
			Logger.log("WMServer running on Port " + port + " at " + InetAddress.getLocalHost().getHostAddress() + ".", Logger.SERVER);
			System.out.println(Logger.getLog());
			//System.out.println("WMServer running on Port " + port + " at " + InetAddress.getLocalHost().getHostAddress() + ".");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		while(isRunning)
		{
			try 
			{
				Socket s = serverSocket.accept();
				Logger.log("Client with IP " + s.getInetAddress().getHostAddress() + " on Port " + s.getLocalPort(), Logger.SERVER);
				System.out.println(Logger.getLog());
				//System.out.println("New Client with IP " + s.getInetAddress().getHostAddress() + " on Port " + s.getLocalPort() + ".");
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
		Logger.log("WMServer shut down.", Logger.SERVER);
		System.out.println(Logger.getLog());
		System.out.println("WMServer shut down.");
	}
	
	public void shutDown() {
		//Logger.log("Shutting down Server at Port " + port + ".", Logger.SERVER);
		System.out.println("Shutting down Server at Port " + port + ".");
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
		clientsAtServer.add(newTeam);
		Communication.requestName(newTeam);
		if(!clientsAtServer.contains(newTeam))
		{
			return;
		}
		Communication.sendStrengths(newTeam);
		//Logger.log("Client " + newTeam.getName() + newTeam.getID() + " (IP: " + newTeam.getClientSocket().getInetAddress().toString().substring(1) + ") at Server. " + clientsAtServer.size() + " Teams registered.", newTeam, Logger.SERVER);
		System.out.println("Team " + newTeam.getName() + newTeam.getID() + " (IP: " + newTeam.getClientSocket().getInetAddress().toString().substring(1) + ") at Server. " + clientsAtServer.size() + " Teams registered.");
		masterWindow.updateClientsAtServer(clientsAtServer.size());
	}
	
	public void startGame(int shots)
	{
		//Logger.log("Starting new game with " + shots + " shots.", Logger.GAME);
		if(clientsAtServer.isEmpty())
		{	
		//	Logger.log("No teams registered, cannot start game.", Logger.GAME);
			return;
		}
		Tournament t = new Tournament(clientsAtServer.clone(), shots, masterWindow);
		(new GameManager(t)).start();
	}
	
	public Team createBot()
	{
		//Logger.log("Creating Bot.", Logger.SERVER);
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
