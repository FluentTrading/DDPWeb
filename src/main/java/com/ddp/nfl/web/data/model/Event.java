package com.ddp.nfl.web.data.model;

import java.util.List;

public class Event {
	
    public String id;
    public String uid;
    public String date;
    public String name;
    public String shortName;
    public Season season;
    public List<Competition> competitions;
    public List<Link> links;
    public Weather weather;
    public Status status;
    
    public Event() {}
    
	public Event(String id, String uid, String date, String name, String shortName, Season season,
			List<Competition> competitions, List<Link> links, Weather weather, Status status) {
		
		this.id = id;
		this.uid = uid;
		this.date = date;
		this.name = name;
		this.shortName = shortName;
		this.season = season;
		this.competitions = competitions;
		this.links = links;
		this.weather = weather;
		this.status = status;
	}
	
	public final String getId() {
		return id;
	}
	
	public final String getUid() {
		return uid;
	}
	
	public final String getDate() {
		return date;
	}
	
	public final String getName() {
		return name;
	}
	
	public final String getShortName() {
		return shortName;
	}
	
	public final Season getSeason() {
		return season;
	}
	
	public final Competition getFirstCompetition() {
		return competitions.get(0);
	}
	
	public final List<Competition> getCompetitions() {
		return competitions;
	}
	
	public final List<Link> getLinks() {
		return links;
	}
	
	public final Weather getWeather() {
		return weather;
	}
	
	public final Status getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return "Event [id=" + id + ", uid=" + uid + ", date=" + date + ", name=" + name + ", shortName=" + shortName
				+ ", season=" + season + ", competitions=" + competitions + ", links=" + links + ", weather=" + weather
				+ ", status=" + status + "]";
	}
    
    
    

}
