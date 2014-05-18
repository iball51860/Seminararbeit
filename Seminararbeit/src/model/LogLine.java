package model;

import control.Logger;

/**
 * Represents a logline. A logline can be associated with an instance of a team, object and a type.
 * 
 * @author Jan Fritze & Manuel Kaiser
 * @see Logger
 */
public class LogLine implements Comparable<LogLine> {
	
	/**Message Body transfered*/
	private String message;
	/**Type of the message, can be Logger.<br>DEFAULT = 0<br>SERVER = 1<br>
	 * COMMUNICATION = 2<br>GAME = 3<br>ROUND = 4<br>MATCH = 5<br>SHOT = 6; */
	private int type = Logger.DEFAULT;
	/**TimeStamp of creation*/
	private long creationDate;
	
	/**If associated to an instance of an Object, object Name*/
	private String instanceName;
	/**If associated with a {@link Team}, ID of team*/
	private int instanceID;
	
	/**
	 * Constructs a logline with just a body and creation TimeStamp.
	 * @param message
	 */
	public LogLine(String message)
	{
		this.creationDate = System.currentTimeMillis();
		this.message = message;
	}
	
	/**
	 * Constructs a logline with body, creation TimeStamp and associated type.
	 * @param message
	 * @param type
	 */
	public LogLine(String message, int type)
	{
		this(message);
		if(type >= 0 && type <= Logger.SHOT)
		{
			this.type = type;
		}
		else
		{
			System.out.println("Fehler");
		}
	}
	
	/**
	 * Constructs a LogLine with body, creation TimeStamp and associated Object.
	 * @param message
	 * @param o
	 */
	public LogLine(String message, Object o)
	{
		this(message);
		this.instanceName = o.toString();
	}
	
	/**
	 * Constructs a LogLine with body, creation TimeStamp, associated Object and associated Type
	 * @param message
	 * @param o
	 * @param type
	 */
	public LogLine(String message, Object o, int type)
	{
		this(message, type);
		this.instanceName = o.toString();
	}
	
	/**
	 * Constructs a LogLine with body, creation TimeStamp and associated {@link Team}
	 * @param message
	 * @param team
	 */
	public LogLine(String message, Team team)
	{
		this(message);
		this.instanceName = team.getName();
		this.instanceID = team.getID();
	}
	
	/**
	 * Constructs a LogLine with body, creation TimeStamp, associated {@link Team} and Type.
	 * @param message
	 * @param team
	 * @param type
	 */
	public LogLine(String message, Team team, int type)
	{
		this(message, type);
		this.instanceName = team.getName();
		this.instanceID = team.getID();
	}
	
	/**
	 * Compares the creationDate of two LogLines.
	 */
	public int compareTo(LogLine ll)
	{
		return (int) (this.creationDate - ll.creationDate);
	}
	
	public String toString()
	{
		return this.message;
	}
	
	/////////////////////////getters and setters///////////////////////////////

	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the creationDate
	 */
	public long getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the instanceName
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * @param instanceName the instanceName to set
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	/**
	 * @return the instanceID
	 */
	public int getInstanceID() {
		return instanceID;
	}

	/**
	 * @param instanceID the instanceID to set
	 */
	public void setInstanceID(int instanceID) {
		this.instanceID = instanceID;
	}
}
