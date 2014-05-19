/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import model.ArrayTeamSet;
import model.Team;

/**
 * Panel holding the ResultList.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
@SuppressWarnings("serial")
public class ResultListPanel extends JPanel {

	/**TextArea showing the List*/
	private JTextArea resultList;
	/**Scrollpane providing a scrolling-option to the Textarea holding the resultlist*/
	private JScrollPane spResultList;
	/**Thread for continuously updating the resultList if it is visible*/
	private Thread updater;
	
	/**
	 * Constructs the panel showing the resultlist. The panel only contains the list (JTextArea).
	 * @param serverWindow
	 */
	public ResultListPanel(final ServerWindow serverWindow){
		super(new BorderLayout());
		resultList = new JTextArea();
		resultList.setEditable(false);
		spResultList = new JScrollPane();
		spResultList.setViewportView(resultList);
		add(spResultList, BorderLayout.CENTER);
		
	}
	
	/**
	 * 
	 * @param serverWindow
	 */
	public void activateResultListUpdater(final ServerWindow serverWindow){ //TODO move to TabbedPaneListener and kill thread everytime resultlist is deselected
		updater = new Thread(){
			public void run(){
				while(serverWindow.getTournament().isRunning()){
					if(serverWindow.getTabPane().getSelectedIndex()==2)
					{
						updateResultList(serverWindow.getTeamSet());
					}
					try {
						sleep(80);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		updater.start();
	}
	
	/**
	 * updates the Result List with recent information and reorders
	 * @param teamSet
	 */
	public void updateResultList(ArrayTeamSet<Team> teamSet)
	{
		if(teamSet == null)
		{
			return;
		}
		int count = 1;
		Collections.sort(teamSet); //TODO use sortingAlgorithm that is more efficient if list is NEARLY in order already
		final StringBuffer sb = new StringBuffer();
		for(Team t : teamSet)
		{
			int rate = (int) ((double)t.getGoals() * 100 / (double)t.getFinishedShots());
			long avg;
			sb.append(count++ + ". " + t.getName() + "\t" + t.getWonMatches() + " Victories\t" + t.getGoals() + " Goals\t" +
					"Success Rate: " + rate + " %\t" + " Goal Difference: " + (t.getGoals()-t.getGoalsAgainst()) + "\tAvg. Reaction: " + 
					(avg = t.getAvgReactionTime()) + " ms\tStandard Deviation: " + t.getStandardDeviation(avg) + "ms\n");
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				resultList.setText(sb.toString());
				resultList.setCaretPosition(0);
			}
		});
	}
	
	/**
	 * 
	 * @return TextArea Resultlist
	 */
	public JTextArea getResultList() {
		return resultList;
	}
}
