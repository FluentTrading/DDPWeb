package com.ddp.nfl.web.data.model;

import java.util.List;

public class EspnData {
	
    public List<League> leagues;
    public Season season;
    public Week week;
    public List<Event> events;
    
    public EspnData(){}
    
    
	public EspnData( List<League> leagues, Season season, Week week, List<Event> events ){
		this.leagues = leagues;
		this.season = season;
		this.week 	= week;
		this.events = events;
	}
	
	
	public final List<League> getLeagues() {
		return leagues;
	}
	
	public final League getFirstLeague() {
		return leagues.get(0);
	}
	
	
	public final Season getSeason() {
		return season;
	}
	
	public final Week getWeek() {
		return week;
	}
	
	public final List<Event> getEvents() {
		return events;
	}
	
	@Override
	public String toString() {
		return "Root [season=" + season + ", week=" + week + "]";
	}
    

}
