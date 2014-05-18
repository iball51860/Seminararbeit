package control.listeners;

import javax.swing.*;

import model.*;

/**
 * Thread operating the Progressbars that represent single Matches. One Thread operates 
 * one progressbar.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class MatchPanelUpdater extends Thread
{
	/**Team shown on the left side*/
	private Team teamA;
	/**Team shown on the right side*/
	private Team teamB;
	
	/**Label showing the current standing*/
	private JLabel text;
	/**Progress bar giving teamA visualisation of the current standing*/
	private JProgressBar bar;
	
	/**Flag whether the thread lives*/
	private boolean running = true;
	
	/**Constructor setting all variables*/
	public MatchPanelUpdater(Team a, Team b, JLabel text, JProgressBar bar)
	{
		this.teamA = a;
		this.teamB = b;
		this.text = text;
		this.bar = bar;
	}
	
	
	public void run()
	{
		while(running)
		{
			try
			{
				sleep(100);
			}
			catch(InterruptedException ie){}
			
			text.setText(teamA.getName() + " " + teamA.getGoalsInCurrentRound() + " : " + teamB.getGoalsInCurrentRound() + " " + teamB.getName());
			bar.setValue(teamA.getGoalsInCurrentRound() - teamA.getGoalsAgainstInCurrentRound());
		}
	}

	/**
	 * Sets the running flag. Once set to false, the thread terminates.
	 * @param flag
	 */
	public void setFlag(boolean flag) {
		this.running = flag;
	}
	
}
