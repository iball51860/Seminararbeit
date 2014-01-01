package main;

import control.*;
import view.*;

public class WMTournament {
	
	/**
	 * Main-Class of the WM-Game for ProKSy. Creates a Window of the type ServerWindow 
	 * and starts a server on which the Game will be played.
	 * @param args
	 *///TODO complete Javadoc
	
	//TODO create final variables for further use
	
	private static ServerWindow serverWindow;
	
	public static void main(String[] args) {
		//Start Window + Server
		
		serverWindow = new ServerWindow();
		
		

	}
	
	public static ServerWindow getServerWindow(){
		return serverWindow;
	}

}
