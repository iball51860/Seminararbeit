/**
 * 
 */
package control;

import java.util.ArrayList;
import java.util.Collections;

//import view.ServerWindow;

import model.LogLine;
import model.Team;

/**
 * The Logger class manages the information that will be logged in separate log lines.
 * Each logline has a time stamp and an attribute (e.g. server, communication, etc.) to order all loglines.
 * All method are static for a better access.
 * 
 * @author Jan Fritze, Manuel Kaiser
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
	
//	private static ServerWindow target;
//	private static boolean targetEnabled = false;
	
	
	/**
	 * 
	 * 
	 * @param message - Message that will be saved in a logline.
	 */
	public static void log(String message)
	{
		LogLine ll = new LogLine(message);
		logbook.add(ll);
//		if(targetEnabled)
//		{
//			target.appendLogLine(ll);
//		}
	}
	
	public static void log(String message, int type)
	{
		LogLine ll = new LogLine(message, type);
		logbook.add(ll);
//		if(targetEnabled)
//		{
//			target.appendLogLine(ll);
//		}
	}
	
	public static void log(String message, Object o, int type)
	{
		LogLine ll = new LogLine(message, o, type);
		logbook.add(ll);
//		if(targetEnabled)
//		{
//			target.appendLogLine(ll);
//		}
	}
	
	public static void log(String message, Team team)
	{
		logbook.add(new LogLine(message, team));
	}
	
	public static void log(String message, Team team, int type)
	{
		LogLine ll = new LogLine(message, team, type);
		logbook.add(ll);
//		if(targetEnabled)
//		{
//			target.appendLogLine(ll);
//		}
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
				if((ll.getInstanceName() + ll.getInstanceID()).equalsIgnoreCase(name));
				{
					log += ll + "\n";
					System.out.println(ll.getInstanceName());
				}
			}
		}
		return log;
	}
	
	public static String getLog(String[] instanceNames, boolean[] types) throws NullPointerException
	{
		for(String name : instanceNames){
			if(name.equalsIgnoreCase("all Teams"))
			{
				return getLog(types);
			}
		}
		
		String log = "";
		for(LogLine ll : logbook)
		{
			boolean validInstance = false;
			for(String name : instanceNames)
			{
				validInstance = (ll.getInstanceName() == null || (ll.getInstanceName() + ll.getInstanceID()).equalsIgnoreCase(name) || validInstance);
			}
			
			int type = ll.getType();
			switch(type)
			{
			case SERVER:
				if(types[SERVER] && validInstance)
				{
						log += ll + "\n";
				}
				break;
			case COMMUNICATION:
				if(types[COMMUNICATION] && validInstance)
				{
					log += ll + "\n";
				}
				break;
			case GAME:
				if(types[GAME] && validInstance)
				{
					log += ll + "\n";
				}
				break;
			case ROUND:
				if(types[ROUND] && validInstance)
				{
					log += ll + "\n";
				}
				break;
			case MATCH:
				if(types[MATCH] && validInstance)
				{
					log += ll + "\n";
				}
				break;
			case SHOT:
				if(types[SHOT] && validInstance)
				{
					log += ll + "\n";
				}
				break;
			default:
				if(types[DEFAULT] && validInstance)
				{
					log += ll + "\n";
				}
			}
		}
		
		return log;
	}
	
	public static void clear()
	{
		logbook = new ArrayList<LogLine>();
	}
	
//	public static void setTarget(ServerWindow target) {
//		Logger.target = target;
//		if (target != null) {
//			Logger.targetEnabled = true;
//		}
//		else
//		{
//			Logger.targetEnabled = false;
//		}
//	}
}
