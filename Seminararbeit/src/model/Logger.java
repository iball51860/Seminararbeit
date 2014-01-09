/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

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
		return "getLog() not implemented yet.";
	}
	
	public static String getLog(int[] types)
	{
		return "getLog(int[]) not implemented yet.";
	}

	public static String getLog(String[] instanceNames)
	{
		return "getLog(String[]) not implemented yet.";
	}
	
	public static String getLog(String[] instanceNames, int[] types)
	{
		return "getLog(String[], int[]) not implemented yet.";
	}
	
	public static void delete()
	{
		logbook = new ArrayList<LogLine>();
	}
}
