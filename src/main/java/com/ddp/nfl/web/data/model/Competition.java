package com.ddp.nfl.web.data.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Competition {
	
    public String id;
    public String uid;
    public String date;
    public int attendance;
    public Type type;
    public boolean timeValid;
    public boolean neutralSite;
    public boolean conferenceCompetition;
    public boolean recent;
    public Venue venue;
    public List<Competitor> competitors;
    public List<Object> notes;
    public Situation situation;
    public Status status;
    @JsonIgnore
    public List<Broadcast> broadcasts;
    public List<Leader> leaders;
    public String startDate;
    @JsonIgnore
    public List<GeoBroadcast> geoBroadcasts;
    @JsonIgnore
    public List<Headline> headlines;
    @JsonIgnore
    public List<Ticket> tickets;
    public List<Odd> odds;
    
    public Competition() {}

    
	public Competition(String id, String uid, String date, int attendance, Type type, boolean timeValid,
			boolean neutralSite, boolean conferenceCompetition, boolean recent, Venue venue,
			List<Competitor> competitors, List<Object> notes, Situation situation, Status status,
			List<Broadcast> broadcasts, List<Leader> leaders, String startDate, List<GeoBroadcast> geoBroadcasts,
			List<Ticket> tickets, List<Odd> odds) {		
		this.id = id;
		this.uid = uid;
		this.date = date;
		this.attendance = attendance;
		this.type = type;
		this.timeValid = timeValid;
		this.neutralSite = neutralSite;
		this.conferenceCompetition = conferenceCompetition;
		this.recent = recent;
		this.venue = venue;
		this.competitors = competitors;
		this.notes = notes;
		this.situation = situation;
		this.status = status;
		this.broadcasts = broadcasts;
		this.leaders = leaders;
		this.startDate = startDate;
		this.geoBroadcasts = geoBroadcasts;
		this.tickets = tickets;
		this.odds = odds;
	}



	public final Situation getSituation() {
		return situation;
	}


	public final List<Leader> getLeaders() {
		return leaders;
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
	
	public final int getAttendance() {
		return attendance;
	}
	
	public final Type getType() {
		return type;
	}
	
	public final boolean isTimeValid() {
		return timeValid;
	}
	
	public final boolean isNeutralSite() {
		return neutralSite;
	}
	
	public final boolean isConferenceCompetition() {
		return conferenceCompetition;
	}
	
	public final boolean isRecent() {
		return recent;
	}
	
	public final Venue getVenue() {
		return venue;
	}
	
	public final List<Competitor> getCompetitors() {
		return competitors;
	}
	
	public final List<Object> getNotes() {
		return notes;
	}
	
	public final Status getStatus() {
		return status;
	}
	
	public final List<Broadcast> getBroadcasts() {
		return broadcasts;
	}
	
	public final List<Ticket> getTickets() {
		return tickets;
	}
	
	public final String getStartDate() {
		return startDate;
	}
	
	public final List<GeoBroadcast> getGeoBroadcasts() {
		return geoBroadcasts;
	}
	
	public final List<Odd> getOdds() {
		return odds;
	}
	
	
	public Competitor getHomeCompetitor() {
    	return getDesignatedCompetitor("home");    	    
    }
	
	public Competitor getAwayCompetitor() {
		return getDesignatedCompetitor("away"); 	
    }
	
	
    public Competitor getDesignatedCompetitor( String homeOrAway) {
    	if( competitors.size() != 2 ) {
    		throw new IllegalStateException("There must be 2 competitors! Count: " + competitors.size());
    	}
    	
    	if( competitors.get(0).getHomeAway().equalsIgnoreCase(homeOrAway) ) {
    		return competitors.get(0); 
    	}else {
    		return competitors.get(1);
    	}    	
    }


	@Override
	public String toString() {
		return "Competition [id=" + id + ", uid=" + uid + ", date=" + date + ", attendance=" + attendance + ", type="
				+ type + ", timeValid=" + timeValid + ", neutralSite=" + neutralSite + ", conferenceCompetition="
				+ conferenceCompetition + ", recent=" + recent + ", venue=" + venue + ", competitors=" + competitors
				+ ", notes=" + notes + ", situation=" + situation + ", status=" + status + ", broadcasts=" + broadcasts
				+ ", leaders=" + leaders + ", startDate=" + startDate + ", geoBroadcasts=" + geoBroadcasts
				+ ", tickets=" + tickets + ", odds=" + odds + "]";
	}
	


}
