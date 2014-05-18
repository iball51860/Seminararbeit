package control.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.GameManager;

public class FinishGameListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		GameManager.interruptGame();
	}
	
	
}
