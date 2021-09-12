package com.ddp.nfl.web.data.model;

import java.util.List;

public class LastPlay{
	
    public String id;
    public Type type;
    public String text;
    public int scoreValue;
    public Team team;
    public Probability probability;
    public Drive drive;
    public Start start;
    public End end;
    public int statYardage;
    public List<Athlete> athletesInvolved;
    
    public LastPlay() {}
    
	public LastPlay(String id, Type type, List<Athlete> athletesInvolved, String text, int scoreValue, Team team, Probability probability, Drive drive,
			Start start, End end, int statYardage) {
	
		this.id = id;
		this.type = type;
		this.athletesInvolved = athletesInvolved;
		this.text = text;
		this.scoreValue = scoreValue;
		this.team = team;
		this.probability = probability;
		this.drive = drive;
		this.start = start;
		this.end = end;
		this.statYardage = statYardage;
	}

	public final String getId() {
		return id;
	}
	

	public final List<Athlete> getAthletesInvolved() {
		return athletesInvolved;
	}
	

	public final Type getType() {
		return type;
	}

	public final String getText() {
		return text;
	}

	public final int getScoreValue() {
		return scoreValue;
	}

	public final Team getTeam() {
		return team;
	}

	public final Probability getProbability() {
		return probability;
	}

	public final Drive getDrive() {
		return drive;
	}

	public final Start getStart() {
		return start;
	}

	public final End getEnd() {
		return end;
	}

	public final int getStatYardage() {
		return statYardage;
	}
	
		

	@Override
	public String toString() {
		return "LastPlay [id=" + id + ", type=" + type + ", text=" + text + ", scoreValue=" + scoreValue + ", team="
				+ team + ", probability=" + probability + ", drive=" + drive + ", start=" + start + ", end=" + end
				+ ", statYardage=" + statYardage + ", athletesInvolved=" + athletesInvolved + "]";
	}

	
    
}