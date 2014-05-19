package testClient;

import java.io.*;
import java.net.*;

import control.*;

/**
 * Threads that acts as a client from the same computer as the server.
 * Can be used to create opponents when testing clients.
 * TestClients are not supposed to be very effective, they just implement basic strategies.
 * However, the random strategy is simple yet might be effective if not objected by the opponent.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class TestClient extends Thread{
	
	/**Counts the number of initiated TestClients*/
	private static int count;
	/**Socket for ServerCommunication*/
	private Socket socket;
	/**Reader for incoming messages from Server*/
	private BufferedReader fromServer;
	/**Writer for sending messages to the Server*/
	private PrintWriter toServer;
	/**Life is like a box of chocolates. As long as this flag is true, the client is running.*/
	private boolean runForrestRun = true;
	/**Name of this client, "dummy" by default*/
	private String name;
	
	/**Legen...<br>Represents the Latency to simulate more realistic conditions.<br>...dary! Legendary!*/
	private static int waitForIt = 0;
	
	/**Constructs a TestClient connecting to the given port with name "dummy".*/
	public TestClient(int port)
	{
		this(port, "dummy");
	}
	
	/**
	 * Constructs a TestClient connecting to the given port with the given name.
	 * @param port
	 * @param name
	 */
	public TestClient(int port, String name)
	{
		try 
		{
			count++;
			socket = new Socket(InetAddress.getLocalHost(), port);
			this.name = name;
			this.setName("TestClientThread-" + count); //set Name of Thread, NOT of the Team
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Communication with Server<br>
	 * 1. Reader + Writer get initialized<br>
	 * 2. Reader reads String from Server<br>
	 * 3. Switch case generates some answers by using the method below
	 */
	public void run()
	{
		try 
		{
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			toServer = new PrintWriter(socket.getOutputStream(), true);
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		}
		
		while(runForrestRun)
		{
			String message = "default";
			String frontSubString = "default";
			try
			{
				if((message = fromServer.readLine()) != null)
				{
					frontSubString = message.substring(0, 3);
				}
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			switch(frontSubString)
			{
				case Communication.NAME:
					sendToServer(name);
					break;
				case Communication.STRENGTH:
					break;
				case Communication.NEWGAME:
					break;
				case Communication.NEWROUND:
					break;
				case Communication.NEWMATCH:
					break;
				case Communication.SHOOT:
					sendToServer(generateDecision());
					break;
				case Communication.KEEP:
					sendToServer(generateDecision());
					break;
				case Communication.SHOTRESULT:
					break;
				case Communication.MATCHRESULT:
					break;
				case Communication.GAMEOVER:
					runForrestRun = false;
					break;
				default:
					System.out.println(message);
					runForrestRun = false;
			}
		}
	}
	
	
	/**
	 * Method to send Strings to the Server
	 * @param msg
	 */
	private void sendToServer(String msg)
	{
		try {
			sleep(waitForIt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		toServer.println(msg);
	}
	
	/**
	 * Generates a random decision with equal chances of each side.
	 * @return
	 */
	private String generateDecision()
	{
		double random = Math.random();
		if(random < 1.0/3.0) 
		{
			return "l";
		}
		if(random > 2.0/3.0)
		{
			return "m";
		}
		return "r";
	}
	
	/**
	 * 
	 * @return socket for communication
	 */
	public Socket getSocket()
	{
		return this.socket;
	}
	
	/**
	 * 
	 * @param running
	 */
	public void setRunForrestRun(boolean running){
		this.runForrestRun = running;
	}
	
	
	/**
	 * 
	 * @param wait - latency
	 */
	public static void setWaitForIt (int wait){
		waitForIt = wait;
	}
	
	
}
