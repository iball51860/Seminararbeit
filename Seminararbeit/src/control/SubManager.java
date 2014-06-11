package control;

import model.*;

/**
 * Manages one Match, so matches can be run at the same time.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class SubManager extends Thread
{
	private Team a;
	private Team b;
	private Tournament t;
	private int goalsToPlayInMatch;
	
	/**
	 * Constructs a match with two teams and the given number of shots, the tournament the match is played for.
	 * @param a
	 * @param b
	 * @param t
	 * @param goalsToPlayInMatch
	 */
	public SubManager(Team a, Team b, Tournament t, int goalsToPlayInMatch)
	{
		this.a = a;
		this.b = b;
		this.t = t;
		this.goalsToPlayInMatch = goalsToPlayInMatch;
	}
	
	/**
	 * Runs a Match as Thread. updates information in the serverWindow too.
	 */
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
