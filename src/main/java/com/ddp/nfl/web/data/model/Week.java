package com.ddp.nfl.web.data.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Week {
	
    public int number;
    @JsonIgnore
    public List<TeamsOnBye> teamsOnBye;
    
    public Week() {}

	public Week(int number, List<TeamsOnBye> teamsOnBye) {		
		this.number = number;
		this.teamsOnBye = teamsOnBye;
	}

	public final int getNumber() {
		return number;
	}

	public final void setNumber(int number) {
		this.number = number;
	}

	public final List<TeamsOnBye> getTeamsOnBye() {
		return teamsOnBye;
	}

	public final void setTeamsOnBye(List<TeamsOnBye> teamsOnBye) {
		this.teamsOnBye = teamsOnBye;
	}

	@Override
	public String toString() {
		return "Week [number=" + number + ", teamsOnBye=" + teamsOnBye + "]";
	}

	    
    

}
