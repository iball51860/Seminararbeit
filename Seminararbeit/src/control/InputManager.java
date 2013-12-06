package control;

import java.util.*;

import model.*;

public class InputManager 
{
	
	private TreeSet<Team> teams;
	
	
	public InputManager(TreeSet<Team> teams)
	{
		this.teams = teams;
	}
	
	
	public void start()
	{
		Object[] list = teams.toArray();
		while(true)
		{
			for(int i = 0; i < list.length; i++)
			{
				String s = ((Team)list[i]).read();
				if(s != null)
				{
					((Team)list[i]).setLastInput(s);
				}
				
			}
		}
	}
	
}
