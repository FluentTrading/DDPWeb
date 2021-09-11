package com.ddp.nfl.web.data.model;

import java.util.List;

public class Athlete{
	
    public String id;
    public String fullName;
    public String displayName;
    public String shortName;
    public List<Link> links;
    public String headshot;
    public String jersey;
    public Position position;
    public Team team;
    public boolean active;
    
    public Athlete() {}
    
	public Athlete(String id, String fullName, String displayName, String shortName, List<Link> links, String headshot,
			String jersey, Position position, Team team, boolean active) {
	
		this.id = id;
		this.fullName = fullName;
		this.displayName = displayName;
		this.shortName = shortName;
		this.links = links;
		this.headshot = headshot;
		this.jersey = jersey;
		this.position = position;
		this.team = team;
		this.active = active;
	}

	public final String getId() {
		return id;
	}

	public final String getFullName() {
		return fullName;
	}

	public final String getDisplayName() {
		return displayName;
	}

	public final String getShortName() {
		return shortName;
	}

	public final List<Link> getLinks() {
		return links;
	}

	public final String getHeadshot() {
		return headshot;
	}

	public final String getJersey() {
		return jersey;
	}

	public final Position getPosition() {
		return position;
	}

	public final Team getTeam() {
		return team;
	}

	public final boolean isActive() {
		return active;
	}

	@Override
	public String toString() {
		return "Athlete [id=" + id + ", fullName=" + fullName + ", displayName=" + displayName + ", shortName="
				+ shortName + ", links=" + links + ", headshot=" + headshot + ", jersey=" + jersey + ", position="
				+ position + ", team=" + team + ", active=" + active + "]";
	}
    
	
    
}