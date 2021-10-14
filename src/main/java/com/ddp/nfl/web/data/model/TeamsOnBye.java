package com.ddp.nfl.web.data.model;

import java.util.List;

public class TeamsOnBye{
	
    public String id;
    public String uid;
    public String location;
    public String name;
    public String abbreviation;
    public String displayName;
    public String shortDisplayName;
    public boolean isActive;
    public List<Link> links;
    public String logo;
    
	public TeamsOnBye() {}
	
	public TeamsOnBye(String id, String uid, String location, String name, String abbreviation, String displayName,
			String shortDisplayName, boolean isActive, List<Link> links, String logo) {
		this.id = id;
		this.uid = uid;
		this.location = location;
		this.name = name;
		this.abbreviation = abbreviation;
		this.displayName = displayName;
		this.shortDisplayName = shortDisplayName;
		this.isActive = isActive;
		this.links = links;
		this.logo = logo;
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getUid() {
		return uid;
	}

	public final void setUid(String uid) {
		this.uid = uid;
	}

	public final String getLocation() {
		return location;
	}

	public final void setLocation(String location) {
		this.location = location;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getAbbreviation() {
		return abbreviation;
	}

	public final void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public final String getDisplayName() {
		return displayName;
	}

	public final void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public final String getShortDisplayName() {
		return shortDisplayName;
	}

	public final void setShortDisplayName(String shortDisplayName) {
		this.shortDisplayName = shortDisplayName;
	}

	public final boolean isActive() {
		return isActive;
	}

	public final void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public final List<Link> getLinks() {
		return links;
	}

	public final void setLinks(List<Link> links) {
		this.links = links;
	}

	public final String getLogo() {
		return logo;
	}

	public final void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "TeamsOnBye [id=" + id + ", uid=" + uid + ", location=" + location + ", name=" + name + ", abbreviation="
				+ abbreviation + ", displayName=" + displayName + ", shortDisplayName=" + shortDisplayName
				+ ", isActive=" + isActive + ", links=" + links + ", logo=" + logo + "]";
	}
    
	
	
    

}