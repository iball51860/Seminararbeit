/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import model.ArrayTeamSet;
import model.Team;

import control.listeners.UpdateResultListener;

/**
 * @author Jan
 *
 */
public class ResultListPanel extends JPanel {

	private JTextArea resultList;
	private JScrollPane spResultList;
	private JButton updateResult;
	
	public ResultListPanel(ServerWindow serverWindow){
		super(new BorderLayout());
		resultList = new JTextArea();
		resultList.setEditable(false);
		updateResult = new JButton("Update Result");
		updateResult.setEnabled(false);
		updateResult.addActionListener(new UpdateResultListener(serverWindow));
		spResultList = new JScrollPane();
		spResultList.setViewportView(resultList);
		add(spResultList, BorderLayout.CENTER);
		add(updateResult, BorderLayout.SOUTH);
	}
	
	public void updateResultList(ArrayTeamSet<Team> teamSet)
	{
		if(teamSet == null)
		{
			return;
		}
		int count = 1;
		Collections.sort(teamSet);
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
	
	public JButton getUpdateResult() {
		return updateResult;
	}
	
	public JTextArea getResultList() {
		return resultList;
	}
}
