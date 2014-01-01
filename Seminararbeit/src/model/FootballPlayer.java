package model;

import control.*;

public class FootballPlayer //TODO single strength triple, move into team
{
	private int[] strength = new int[3];
	
	public FootballPlayer(){
		strength = Analyser.generateStrength();
	}
	
	public int[] getStrength() //TODO overwrite or overload with return int in dependance on l/m/r
	{
		return strength;
	}
	
	
	public String toString() 
	{
		return "" + strength[0] + " " + strength[1] + " " + strength[2];
	}
	
}
