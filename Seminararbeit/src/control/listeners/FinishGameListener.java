package control.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.GameManager;

/**
 * Listener that is triggered by the "Finish Game after current Round"-Button
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class FinishGameListener implements ActionListener
{
	/**
	 * Calls {@code interruptGame()} in {@link GameManager}, which sets the current round to 1 (Final), so
	 * the game finishes afterwards.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		GameManager.interruptGame();
	}
	
	
}
