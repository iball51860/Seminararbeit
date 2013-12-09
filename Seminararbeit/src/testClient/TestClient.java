package testClient;

import java.io.*;
import java.net.*;

import control.*;


public class TestClient extends Thread{
	
	private Socket socket;
	private BufferedReader fromServer;
	private BufferedWriter toServer;
	private boolean runForrestRun = true;
	
	
	public TestClient()
	{
		socket = new Socket(InetAddress.getLocalHost(), WMServer.getPort());
	}
	
	/**
	 * Communication with Server
	 * 1. Reader + Writer gets initialized
	 * 2. Reader reads String from Server
	 * 3. Switch case generates some answers with using the method below
	 */
	public void run()
	{
		try 
		{
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			toServer = new BufferedWriter(new PrintWriter(socket.getOutputStream()));
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		}
		
		while(runForrestRun)
		{
			String message = "default";
			String frontSubString = "default";
			String backSubString = "default";
			try
			{
				if((message = fromServer.readLine()) != null)
				{
					frontSubString = message.substring(0, 3);
					//backSubString = message.substring(3);
				}
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			switch(frontSubString)
			{
				case Communication.NAME:
					sendToServer("dummy");
					break;
				case Communication.STRENGTH:
					break;
				case Communication.NEWGAME:
					break;
				case Communication.NEWROUND:
					break;
				case Communication.NEWMATCH:
					break;
				case Communication.SHOT:
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
			}
		}
	}
	
	
	/**
	 * Method to send Strings to the Server
	 * @param msg
	 */
	private void sendToServer(String msg)
	{
		try
		{
			toServer.write(msg);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	private String generateDecision()
	{
		return "m";
	}
	
	
	
}
