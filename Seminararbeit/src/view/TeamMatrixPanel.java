/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.ArrayTeamSet;
import model.Team;
import model.Tournament;
import control.listeners.ShowTeamListener;

/**
 * @author Jan
 *
 */
@SuppressWarnings("serial")
public class TeamMatrixPanel extends JPanel {

	private JButton[] teamButtons;
	
	public TeamMatrixPanel(final ServerWindow serverWindow, Tournament t){
		final ArrayTeamSet<Team> clone = t.getPlaying().clone();
		final int size = (int) Math.ceil(Math.sqrt(t.getPlaying().size()));
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {				
					setLayout(new GridLayout(size, size, 1, 1));
					teamButtons = new JButton[Team.getCount() + 1];
					for(Team team : clone){
						if (!team.getName().equals("bottt"))
						{
							teamButtons[team.getID()] = new JButton(team.getName());
							teamButtons[team.getID()].setBackground(Color.GREEN);
							teamButtons[team.getID()].setOpaque(true);
							teamButtons[team.getID()].setBorderPainted(false);
							teamButtons[team.getID()]
									.addActionListener(new ShowTeamListener(serverWindow, team));
							teamButtons[team.getID()].setToolTipText(team.getName() + team.getID());
							add(teamButtons[team.getID()]);
							serverWindow.getLogPanel().registerTeam(team); //TODO NICHT OPTIMAL, DA LOGPANEL aus TEAMMATRIX aufgerufen
						}
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void updateTeamView(final Team t)
	{
		if(!t.getName().equals("bottt"))
		{
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						if (!t.isInGame()) {
							teamButtons[t.getID()].setBackground(Color.RED);
							teamButtons[t.getID()].setForeground(Color.BLACK);
						} else {
							teamButtons[t.getID()].setBackground(Color.GREEN);
							teamButtons[t.getID()].setForeground(Color.BLACK);
						}
						teamButtons[t.getID()].repaint();
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateTeamInMatchView(final Team a, final Team b)
	{
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					if (!a.getName().equals("bottt")) {
						teamButtons[a.getID()].setBackground(Color.BLUE);
						teamButtons[a.getID()].setForeground(Color.WHITE);
						teamButtons[a.getID()].repaint();
					}
					if (!b.getName().equals("bottt")) {
						teamButtons[b.getID()].setBackground(Color.BLUE);
						teamButtons[b.getID()].setForeground(Color.WHITE);
						teamButtons[b.getID()].repaint();
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
