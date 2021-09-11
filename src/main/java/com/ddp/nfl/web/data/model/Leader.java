package com.ddp.nfl.web.data.model;

import java.util.List;

public class Leader{
	
    public String displayValue;
    public double value;
    public Athlete athlete;
    public Team team;
    public String name;
    public String displayName;
    public String shortDisplayName;
    public String abbreviation;
    public List<Leader> leaders;
    
    public Leader() {}
    
	public Leader(String displayValue, double value, Athlete athlete, Team team, String name, String displayName,
			String shortDisplayName, String abbreviation, List<Leader> leaders) {
	
		this.displayValue = displayValue;
		this.value = value;
		this.athlete = athlete;
		this.team = team;
		this.name = name;
		this.displayName = displayName;
		this.shortDisplayName = shortDisplayName;
		this.abbreviation = abbreviation;
		this.leaders = leaders;
	}

	public final String getDisplayValue() {
		return displayValue;
	}

	public final double getValue() {
		return value;
	}

	public final Athlete getAthlete() {
		return athlete;
	}

	public final Team getTeam() {
		return team;
	}

	public final String getName() {
		return name;
	}

	public final String getDisplayName() {
		return displayName;
	}

	public final String getShortDisplayName() {
		return shortDisplayName;
	}

	public final String getAbbreviation() {
		return abbreviation;
	}

	public final List<Leader> getLeaders() {
		return leaders;
	}

	@Override
	public String toString() {
		return "Leader [displayValue=" + displayValue + ", value=" + value + ", athlete=" + athlete + ", team=" + team
				+ ", name=" + name + ", displayName=" + displayName + ", shortDisplayName=" + shortDisplayName
				+ ", abbreviation=" + abbreviation + ", leaders=" + leaders + "]";
	}
    
	
    
}