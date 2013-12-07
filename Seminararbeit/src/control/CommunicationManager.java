package control;

import java.util.*;

import model.*;

public class CommunicationManager 
{
	//TODO implement methods for single Team communication (e.g. requestDecision(Team t)), maybe add String-decoder
	//TODO implement validation of communication (kicking of clients)
	
	
	
	
	/*
	 public void getInput()
	 {
		long start = System.currentTimeMillis();
		boolean b = true;
		Team help;
		Iterator<Team> it = Tournament.getContestants().iterator();
		while(b)
		{
			while(it.hasNext())
			{
				help = it.next();
				String msg = help.read();
				if(msg != null)
				{
					help.setLastInput(msg);
				}
			}
			it = Tournament.getContestants().iterator();
			b = false;
			while(it.hasNext())
			{
				help = (Team)it.next();
				if(help.getLastInput() == null)
				{
					b = true;
				}
			}
			if((System.currentTimeMillis() - start) > 5000)
			{
				while(it.hasNext())
				{
					help = (Team)it.next();
					if(help.getLastInput() == null)
					{
						//TODO Client muss mit einem Hilfsclient ersetzt werden
						help.setInMatch(false);
					}
				}
				b = false;
			}
		}
	}
	 */
	
	
}
