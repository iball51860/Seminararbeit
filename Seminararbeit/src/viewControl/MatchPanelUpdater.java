package viewControl;

import javax.swing.*;

import model.*;


public class MatchPanelUpdater extends Thread
{
	private Team a;
	private Team b;
	
	private JLabel text;
	private JProgressBar bar;
	
	private boolean flag = true;
	
	public MatchPanelUpdater(Team a, Team b, JLabel text, JProgressBar bar)
	{
		this.a = a;
		this.b = b;
		this.text = text;
		this.bar = bar;
	}
	
	
	public void run()
	{
		while(flag)
		{
			try
			{
				sleep(100);
			}
			catch(InterruptedException ie){}
			
			text.setText(a.getName() + " " + a.getGoalsInCurrentRound() + " : " + b.getGoalsInCurrentRound() + " " + b.getName());
			bar.setValue(a.getGoalsInCurrentRound() - a.getGoalsAgainstInCurrentRound());
		}
	}


	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
