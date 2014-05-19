package view;

import java.awt.*;

import javax.swing.*;

import model.*;

/**
 * The Window poping up at the end showing the winner, second and third client in gold/silver/bronze.
 * Furthermore displays extra information about these teams and extra information about the tournament, 
 * such as runtime. 
 * @author Jan Fritze & Manuel Kaiser
 *
 */
@SuppressWarnings("serial")
public class FinishedWindow extends JFrame {
	
	/**
	 * Constructs a window showing "Congratulations!" on top of a list of the first 3 teams in the tournament.
	 * The Winner Panel is colored gold, second silver, third bronze.<br><br>
	 * For each team further information, achieved Goals, Goals Against, Goal Difference and Success Rate 
	 * of shots is shown.<br><br>
	 * At the bottom a small bar shows tournament information of how many Matches were played, how long it took,
	 * how many goals were scored overall and the average latency in the end.
	 * 
	 * @param t
	 */
	public FinishedWindow(final Tournament t){
		super();
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				FinishedWindow.this.setSize(600, 300);
				FinishedWindow.this.setLocationRelativeTo(t.getMasterWindow());
				Container contentPane = FinishedWindow.this.getContentPane();
				contentPane.setLayout(new BorderLayout());
				
				JLabel titleLabel = new JLabel("Congratulations!");
				titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
				titleLabel.setBackground(Color.WHITE);
				titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
				contentPane.add(titleLabel, BorderLayout.NORTH);
				
				JPanel ranks = new JPanel(new GridLayout(3,1));
				contentPane.add(ranks);
				
				JPanel[] winners = new JPanel[3];
				
				for(int i=0; i<3; i++)
				{
					winners[i] = new JPanel(new BorderLayout());
					Team team;
					try {
						team = t.getMasterWindow().getTeamSet().get(i);
					} catch (IndexOutOfBoundsException ioobe) {//in case only two teams are playing
						team = t.getMasterWindow().getTeamSet().get(i-1);
					}
					JLabel name = new JLabel((i+1) + ". " + team.getName());
					name.setFont(new Font("Arial", Font.BOLD, 36));
					winners[i].add(name, BorderLayout.CENTER);
					JLabel info = new JLabel("Goals: " + team.getGoals() + "    GoalsAgainst: " + team.getGoalsAgainst() + 
							"    Difference:" +	(team.getGoals() - team.getGoalsAgainst()) + "    Success Rate: " +
							100 * team.getGoals() / team.getFinishedShots() + "%");
					winners[i].add(info, BorderLayout.SOUTH);
					ranks.add(winners[i]);
				}
				winners[0].setBackground(new Color(255, 215, 0));
				winners[1].setBackground(new Color(239, 232, 239));
				winners[2].setBackground(new Color(205, 127, 50));
				
				JPanel tournamentInfo = new JPanel(new BorderLayout());
				tournamentInfo.add(new JLabel("Tournament Info"), BorderLayout.NORTH);
				JPanel info = new JPanel();
				info.add(new JLabel("Matches played: " + t.getNoOfMatches() + "  "));
				info.add(new JLabel("Duration: " + t.getDuration()/60000 + " min " + (int)(t.getDuration()/1000) % 60 + " s  "));
				info.add(new JLabel("Goals: " + t.getGoals() + "  "));
				info.add(new JLabel("Average Latency: " + t.getAverageClientLatency() + "ms"));
				
				contentPane.add(info, BorderLayout.SOUTH);
				
				FinishedWindow.this.setVisible(true);
			}
		});
	}
}
