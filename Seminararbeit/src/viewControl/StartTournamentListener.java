package viewControl;

import java.awt.event.*;
import view.ServerWindow;

import control.*;

public class StartTournamentListener implements ActionListener {
	
	
	private ServerWindow serverWindow;
	
	
	public StartTournamentListener(ServerWindow serverWindow)
	{
		super();
		this.serverWindow = serverWindow;
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		int shots = 5;	//TODO popup das abfragt wie viele Schüsse gespielt werden soll
		serverWindow.getWMServer().startGame(shots);
	}

}
