package control;

import model.*;


public class SubManager extends Thread
{
	private Team a;
	private Team b;
	private Tournament t;
	private int goalsToPlayInMatch;
	
	
	public SubManager(Team a, Team b, Tournament t, int goalsToPlayInMatch)
	{
		this.a = a;
		this.b = b;
		this.t = t;
		this.goalsToPlayInMatch = goalsToPlayInMatch;
	}
	
	
	public void run()
	{
		Team winner = GameManager.playMatch(a, b, goalsToPlayInMatch, t);
		winner.incrementWonMatches(1);
		t.incrementFinishedMatches(1);
		Team looser;
		if(winner.equals(a))
		{
			looser = b;
		}
		else
		{
			looser = a;				
		}
		Logger.log(winner.getName() + " wins against " + looser.getName() + "!", winner, Logger.MATCH);
		Logger.log(winner.getName() + " wins against " + looser.getName() + "!", looser, Logger.MATCH);
		if(!looser.getName().equals("bottt"))
		{
			t.getLost().add(looser);
			looser.setIsInGame(false);
		}
		t.getMasterWindow().updateShots(t);
		t.getMasterWindow().updateTeamView(looser);	
		t.getMasterWindow().updateTeamView(winner);
		t.getMasterWindow().updateMetaData(t);
	}
	
}
