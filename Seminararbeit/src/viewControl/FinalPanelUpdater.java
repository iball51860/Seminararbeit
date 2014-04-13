package viewControl;

import java.util.Collections;

import javax.swing.*;

import control.GameManager;

import model.*;


public class FinalPanelUpdater extends Thread
{
	private ArrayTeamSet<Team> copy;
	
	private Tournament t;
	private JPanel finalPanel;
	private JLabel[] labelTeam;
	private JProgressBar[] progressTeam;
	
	public FinalPanelUpdater(Tournament t, JPanel finalPanel, JLabel[] labelTeam, JProgressBar[] progressTeam)
	{
		this.t = t;
		this.finalPanel = finalPanel;
		this.labelTeam = labelTeam;
		this.progressTeam = progressTeam;
	}
	
	
	public void run()
	{
		while(t.getPlaying().size() > 4)
		{
			try 
			{
				sleep(100);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		copy = t.getPlaying().clone();
		
		while(GameManager.getRoundCout() != 0)
		{
			try 
			{
				sleep(10);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			finalPanel.removeAll();
			Collections.sort(copy);
			for(int i = 0; i < copy.size(); i++)
			{
				labelTeam[i].setText(copy.get(i).getName() + " | Won Matches: " + copy.get(i).getWonMatches() + 
						" | Goal Difference in current Match: " + (copy.get(i).getGoalsInCurrentRound()-copy.get(i).getGoalsAgainstInCurrentRound()));
				progressTeam[i].setValue(copy.get(i).getGoalsInCurrentRound()-copy.get(i).getGoalsAgainstInCurrentRound());
				finalPanel.add(labelTeam[i]);
				finalPanel.add(progressTeam[i]);
			}
			finalPanel.updateUI();
		}
		
		try 
		{
			sleep(200);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		finalPanel.removeAll();
		Collections.sort(copy);
		for(int i = 0; i < copy.size(); i++)
		{
			labelTeam[i].setText(copy.get(i).getName() + " | Won Matches: " + copy.get(i).getWonMatches() + 
					" | Goal Difference in current Match: " + (copy.get(i).getGoalsInCurrentRound()-copy.get(i).getGoalsAgainstInCurrentRound()));
			progressTeam[i].setValue(copy.get(i).getGoalsInCurrentRound()-copy.get(i).getGoalsAgainstInCurrentRound());
			finalPanel.add(labelTeam[i]);
			finalPanel.add(progressTeam[i]);
		}
		finalPanel.updateUI();
	}
	
}
