package viewControl;

import java.awt.event.*;

import testClient.TestClient;
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
		
		
		TestClient[] dummy = new TestClient[60];
		
		for(int i = 0; i < dummy.length; i++)
		{
			dummy[i] = new TestClient(serverWindow.getWMServer().getPort());
			System.out.println("X" + i);
			dummy[i].start();
			System.out.println("Dummy" + (i+1) + " started.");
		}
		
		
		serverWindow.getWMServer().startGame(shots);
	}

}
