package main;

import control.*;
import view.*;

public class WMTournament {
	
	public static final int PORT = 4444; //TODO change to user-oriented port

	/**
	 * Main-Class of the WM-Game for ProKSy. Creates a Window of the type ServerWindow 
	 * and starts a server on which the Game will be played.
	 * @param args
	 *///TODO complete Javadoc
	
	//TODO create final variables for further use
	
	public static void main(String[] args) {
		//Start Window + Server
		
		ServerWindow w = new ServerWindow();
		WMServer server = new WMServer(PORT); //TODO switch Serverstart to "Portlistener" in viewControll -> add Portlistener + Frame
		server.start();
		

	}

}
