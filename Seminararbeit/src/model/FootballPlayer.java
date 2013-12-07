package model;

public class FootballPlayer
{
	private int[] strength = new int[3];
	
	public FootballPlayer(int l, int m, int r){
		setStrength(l, m, r);
	}
	
	public int[] getStrength() //TODO overwrite or overload with return int in dependance on l/m/r
	{
		return strength;
	}
	
	public void setStrength(int l, int m, int r){
		strength[0] = l;
		strength[1] = m;
		strength[2] = r;
	}
}
