/**
 * 
 */
package model;

import control.Logger;

/**
 * @author Jan
 *
 */
public class LogLine implements Comparable<LogLine> {
	
	
	private String message;
	private int type;
	private long creationDate;
	
	private String instanceName;
	private int instanceID;
	
	public LogLine(String message)
	{
		this.creationDate = System.currentTimeMillis();
		this.message = message;
		this.instanceName = "other";
	}
	
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
	
//	public LogLine(String message, Object o)
//	{
//		this(message);
//		this.instanceName = o.toString();
//	}
	
	public LogLine(String message, Object o, int type)
	{
		this(message, type);
		this.instanceName = o.toString();
	}
	
//	public LogLine(String message, Team team)
//	{
//		this(message);
//		this.instanceName = team.getName();
//		this.instanceID = team.getID();
//	}
	
	public LogLine(String message, Team team, int type)
	{
		this(message, type);
		this.instanceName = team.getName();
		this.instanceID = team.getID();
	}
	
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
