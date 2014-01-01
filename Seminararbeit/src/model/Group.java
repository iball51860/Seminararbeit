package model;

import java.util.*;

public class Group {
	
	private TreeSet<Team> groupTeams;
	private TreeSet<Match> matches;
	
	//TODO Constructor
	public Group(){
		groupTeams = new TreeSet<Team>();
		matches = new TreeSet<Match>();
	}
	
	public void addTeam(Team t){
		groupTeams.add(t);
	}
	
	public void addMatch(Match m){
		matches.add(m);
	}
	
	public void setGroupTeams(TreeSet<Team> groupTeams) {
		this.groupTeams = groupTeams;
	}
	
	public TreeSet<Team> getGroupTeams() {
		return groupTeams;
	}
	
	public void setMatches(TreeSet<Match> matches) {
		this.matches = matches;
	}

	public TreeSet<Match> getMatches() {
		return matches;
	}
}
