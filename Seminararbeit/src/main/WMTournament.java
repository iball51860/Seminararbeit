package main;

import control.*;

public class WMTournament {
	
	public static final int PORT = 4444;

	/**
	 * Main-Class of the WM-Game for ProKSy. Creates a Window of the type ServerWindow 
	 * and starts a server on which the Game will be played.
	 * @param args
	 *///TODO complete Javadoc
	
	//TODO create final variables for further use
	
	public static void main(String[] args) {
		// TODO Start Window + Server
		
		WMServer server = new WMServer(PORT);
		server.start();
		

	}

}
