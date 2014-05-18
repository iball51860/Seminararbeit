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
 * @author Jan
 *
 */
@SuppressWarnings("serial")
public class MatchesPanel extends JPanel {

	private ArrayList<MatchPanelUpdater> threadList;
	
	public MatchesPanel(){
		//create Panel for the final
		super(new GridLayout(0, 2));
		threadList = new ArrayList<MatchPanelUpdater>();
	}
	
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
	
	public void cleanMatchPanel()
	{
		removeAll();
		for(MatchPanelUpdater t : threadList)
		{
			t.setFlag(false);
		}
	}
}
