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
 * Shows all Teams in a Matrix where each team has a small field that changes its color depending on the teams current status.
 * The Team-Fields are actually Buttons that Provide further informatino upon clicking on them.<br>
 * Green = Just won a match or just entered the game<br>
 * Blue = Currently Playing<br>
 * Red = Lost and is not Playing anymore.<br>
 * Yeah, just like the blue and red pill you take to get out of or stay in the matrix. Do you prefer the truth, Neo?
 * @author Jan Fritze & Manuel Kaiser
 *
 */
@SuppressWarnings("serial")
public class TeamMatrixPanel extends JPanel {

	/**Array of all Buttons being displayed. <strong>Each Team is held by the array at the position of its ID.</strong>*/
	private JButton[] teamButtons;
	
	/**
	 * Constructs the smallest quadratic Matrix that is able to hold all teams and places a colored button for each team in it. Also adds Tooltips for each team.
	 * @param serverWindow
	 * @param t
	 */
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
	
	/**
	 * Updates the status of all Buttons in the Matrix.
	 * @param t
	 */
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
	
	/**
	 * Updates the Matrix for two teams that just started playing a match.
	 * @param a
	 * @param b
	 */
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
