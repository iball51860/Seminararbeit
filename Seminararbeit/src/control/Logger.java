/**
 * 
 */
package control;

import java.util.ArrayList;
import java.util.Collections;

import model.LogLine;
import model.Team;

/**
 * @author Jan
 *
 */
public class Logger {

	public static final int DEFAULT = 0;
	public static final int SERVER = 1;
	public static final int COMMUNICATION = 2;
	public static final int GAME = 3;
	public static final int ROUND = 4;
	public static final int MATCH = 5;
	public static final int SHOT = 6; //always needs to be last and greatest integer or change method LogLine(String, int) in Class LogLine

	private static ArrayList<LogLine> logbook = new ArrayList<LogLine>();
	
	public static void log(String message)
	{
		logbook.add(new LogLine(message));
	}
	
	public static void log(String message, int type)
	{
		logbook.add(new LogLine(message, type));
	}
	
	public static void log(String message, Object o)
	{
		logbook.add(new LogLine(message, o));
	}
	
	public static void log(String message, Object o, int type)
	{
		logbook.add(new LogLine(message, o, type));
	}
	
	public static void log(String message, Team team)
	{
		logbook.add(new LogLine(message, team));
	}
	
	public static void log(String message, Team team, int type)
	{
		logbook.add(new LogLine(message, team, type));
	}
	
	public static String getLog()
	{
		Collections.sort(logbook);
		String log = "";
		for(LogLine ll : logbook){
			log += ll + "\n";
		}
		return log;
	}
	
	public static String getLog(boolean[] types)
	{
		boolean oneFalse = true;
		for(boolean b : types){
			if(!b)
			{
				oneFalse = b;
			}
		}
		if(oneFalse)
		{
			return getLog();
		}
		
		String log = "";
		for(LogLine ll : logbook)
		{
			int type = ll.getType();
			switch(type)
			{
			case SERVER:
				if(types[SERVER])
				{
					log += ll + "\n";
				}
				break;
			case COMMUNICATION:
				if(types[COMMUNICATION])
				{
					log += ll + "\n";
				}
				break;
			case GAME:
				if(types[GAME])
				{
					log += ll + "\n";
				}
				break;
			case ROUND:
				if(types[ROUND])
				{
					log += ll + "\n";
				}
				break;
			case MATCH:
				if(types[MATCH])
				{
					log += ll + "\n";
				}
				break;
			case SHOT:
				if(types[SHOT])
				{
					log += ll + "\n";
				}
				break;
			default:
				if(types[DEFAULT])
				{
					log += ll + "\n";
				}
			}
		}
		return log;
	}

	public static String getLog(String[] instanceNames)
	{
		for(String name : instanceNames){
			if(name.equalsIgnoreCase("all Teams"))
			{
				return getLog();
			}
		}
		
		String log = "";
		for(LogLine ll : logbook){
			for(String name : instanceNames)
			{
				if(ll.getInstanceName().equalsIgnoreCase(name));
				{
					log += ll + "\n";
				}
			}
		}
		return log;
	}
	
	public static String getLog(String[] instanceNames, boolean[] types)
	{
		for(String name : instanceNames){
			if(name != null && name.equalsIgnoreCase("all Teams"))
			{
				return getLog(types);
			}
		}
		
		String log = "";
		for(LogLine ll : logbook)
		{
			int type = ll.getType();
			switch(type)
			{
			case SERVER:
				if(types[SERVER])
				{
					log += ll + "\n";
				}
				break;
			case COMMUNICATION:
				if(types[COMMUNICATION])
				{
					for(String name : instanceNames)
					{
						if(ll.getInstanceName().equalsIgnoreCase(name));
						{
							log += ll + "\n";
						}
					}
				}
				break;
			case GAME:
				if(types[GAME])
				{
					for(String name : instanceNames)
					{
						if(ll.getInstanceName().equalsIgnoreCase(name));
						{
							log += ll + "\n";
						}
					}
				}
				break;
			case ROUND:
				if(types[ROUND])
				{
					for(String name : instanceNames)
					{
						if(ll.getInstanceName().equalsIgnoreCase(name));
						{
							log += ll + "\n";
						}
					}
				}
				break;
			case MATCH:
				if(types[MATCH])
				{
					for(String name : instanceNames)
					{
						if(ll.getInstanceName().equalsIgnoreCase(name));
						{
							log += ll + "\n";
						}
					}
				}
				break;
			case SHOT:
				if(types[SHOT])
				{
					for(String name : instanceNames)
					{
						if(ll.getInstanceName().equalsIgnoreCase(name));
						{
							log += ll + "\n";
						}
					}
				}
				break;
			default:
				if(types[DEFAULT])
				{
					for(String name : instanceNames)
					{
						if(ll.getInstanceName().equalsIgnoreCase(name));
						{
							log += ll + "\n";
						}
					}
				}
			}
		}
		
		return log;
	}
	
	public static void clear()
	{
		logbook = new ArrayList<LogLine>();
	}
}
