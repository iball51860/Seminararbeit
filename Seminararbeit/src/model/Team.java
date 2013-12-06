package model;

import java.io.*;
import java.net.*;

public class Team implements Comparable<Team> {
	
	private int id;
	private String name;
	
	private FootballPlayer keeper;
	private FootballPlayer striker;
	
	private int points;

	private Socket clientSocket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public int getID(){
		return id;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	//TODO Javadoc
	public String toString(){
		return name + id;
	}
	
	public int compareTo(Team otherTeam) //TODO: Compare Team by points
	{
		return 0;
	}
}
