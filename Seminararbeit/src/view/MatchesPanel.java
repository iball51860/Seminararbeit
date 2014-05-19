/**
 * 
 */
package view;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import model.Team;

import control.listeners.MatchPanelUpdater;

/**
 * Displays each Match in form of a Progress Bar that moves to one of the Clients sides, depending on the
 * current standing.<br>
 * All Matches are Listed as List in a two-Column GridLayout
 * @author Jan Fritze & Manuel Kaiser
 *
 */
@SuppressWarnings("serial")
public class MatchesPanel extends JPanel {

	/**List of threads that automatically keep updating the Progress Bars, that represent the Matches.*/
	private ArrayList<MatchPanelUpdater> threadList;
	
	/**
	 * Constructs an empty Panel ready for adding matches.
	 */
	public MatchesPanel(){
		//create Panel for the final
		super(new GridLayout(0, 2)); //TODO move the labels above the progessBar and establis single-column Gridlayout
		threadList = new ArrayList<MatchPanelUpdater>();
	}
	
	/**
	 * Adds a new Match to the Panel. Creates Label an Progress Bar and Starts a Thread that keeps updating that Progress Bar.
	 * @param a - left Team
	 * @param b - right Team
	 */
	public void addMatch(final Team a, final Team b)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				JLabel text = new JLabel(a.getName() + " " + a.getGoalsInCurrentRound() + " : " 
						+ b.getGoalsInCurrentRound() + " " + b.getName());
				JProgressBar bar = new JProgressBar();
				bar.setMinimum(-50);
				bar.setMaximum(50);
				add(text);
				add(bar);
				MatchPanelUpdater newThread = new MatchPanelUpdater(a, b, text, bar);
				newThread.start();
				updateUI();
				threadList.add(newThread);
			}
		});
	}
	
	/**
	 * Empties Panel from all Matches. Sets the Running flag of the Updater threads to false, so they terminate.
	 */
	public void cleanMatchPanel()
	{
		removeAll();
		for(MatchPanelUpdater t : threadList)
		{
			t.setRunning(false);
		}
	}
}
