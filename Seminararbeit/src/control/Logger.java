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
	
	
	/**
	 * logs a simple message without furhter specifications.
	 * 
	 * @param message - Message that will be saved in a logline.
	 */
	public static void log(String message)
	{
		LogLine ll = new LogLine(message);
		logbook.add(ll);
	}
	
	/**
	 * Logs a message of a certain type of information.
	 * @param message
	 * @param type
	 */
	public static void log(String message, int type)
	{
		LogLine ll = new LogLine(message, type);
		logbook.add(ll);
	}
	
	/**
	 * Logs a message related to a certain Object and a certain information type
	 * @param message
	 * @param o
	 * @param type
	 */
	public static void log(String message, Object o, int type)
	{
		LogLine ll = new LogLine(message, o, type);
		logbook.add(ll);
	}
	
	/**
	 * Logs a message related to a certain team.
	 * @param message
	 * @param team
	 */
	public static void log(String message, Team team)
	{
		logbook.add(new LogLine(message, team));
	}
	
	/**
	 * Logs a message related to a certain team and information-type
	 * @param message
	 * @param team
	 * @param type
	 */
	public static void log(String message, Team team, int type)
	{
		LogLine ll = new LogLine(message, team, type);
		logbook.add(ll);
	}
	
	/**
	 * Returns the whole log up to this point, where each logged message is seperated by a newline character
	 * @return
	 */
	public static String getLog()
	{
		Collections.sort(logbook);
		String log = "";
		for(LogLine ll : logbook){
			log += ll + "\n";
		}
		return log;
	}
	
	/**
	 * Returns the log with messages of the specified types.
	 * @param types
	 * @return
	 */
	public static String getLog(boolean[] types)
	{
		//Checks whether any of the types are specified as false
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

	/**
	 * Returns the Log with messages related to the specified Instances of Objects
	 * @param instanceNames
	 * @return
	 */
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
	
	/**
	 * Returns Logs with messages of the specified types and related to the specified instances of Objects
	 * 
	 * @param instanceNames
	 * @param types
	 * @return
	 * @throws NullPointerException
	 */
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
	
	/**
	 * Resets the log and deletes all previous log-messages.
	 */
	public static void clear()
	{
		logbook = new ArrayList<LogLine>();
	}
}
