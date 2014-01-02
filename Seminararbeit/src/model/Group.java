package model;

import java.util.*;

public class Group {
	
	private ArrayTeamSet<Team> groupTeams;
	private HashSet<Match> matches;
	
	//TODO Constructor
	public Group(){
		groupTeams = new ArrayTeamSet<Team>();
		matches = new HashSet<Match>();
	}
	
	public void addTeam(Team t){
		groupTeams.add(t);
	}
	
	public void addMatch(Match m){
		matches.add(m);
	}
	
	public void setGroupTeams(ArrayTeamSet<Team> groupTeams) {
		this.groupTeams = groupTeams;
	}
	
	public ArrayTeamSet<Team> getGroupTeams() {
		return groupTeams;
	}
	
	public void setMatches(HashSet<Match> matches) {
		this.matches = matches;
	}

	public HashSet<Match> getMatches() {
		return matches;
	}
}
