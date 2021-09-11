package com.ddp.nfl.web.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Situation{
	
    @JsonProperty("$ref") 
    public String ref;
    public LastPlay lastPlay;
    public int down;
    public int yardLine;
    public int distance;
    public String downDistanceText;
    public String shortDownDistanceText;
    public String possessionText;
    public boolean isRedZone;
    public int homeTimeouts;
    public int awayTimeouts;
    public String possession;
    
    public Situation() {}
    
	public Situation(String ref, LastPlay lastPlay, int down, int yardLine, int distance, String downDistanceText,
			String shortDownDistanceText, String possessionText, boolean isRedZone, int homeTimeouts, int awayTimeouts,
			String possession) {
	
		this.ref = ref;
		this.lastPlay = lastPlay;
		this.down = down;
		this.yardLine = yardLine;
		this.distance = distance;
		this.downDistanceText = downDistanceText;
		this.shortDownDistanceText = shortDownDistanceText;
		this.possessionText = possessionText;
		this.isRedZone = isRedZone;
		this.homeTimeouts = homeTimeouts;
		this.awayTimeouts = awayTimeouts;
		this.possession = possession;
	}

	public final String getRef() {
		return ref;
	}

	public final LastPlay getLastPlay() {
		return lastPlay;
	}

	public final int getDown() {
		return down;
	}

	public final int getYardLine() {
		return yardLine;
	}

	public final int getDistance() {
		return distance;
	}

	public final String getDownDistanceText() {
		return downDistanceText;
	}

	public final String getShortDownDistanceText() {
		return shortDownDistanceText;
	}

	public final String getPossessionText() {
		return possessionText;
	}

	public final boolean isRedZone() {
		return isRedZone;
	}

	public final int getHomeTimeouts() {
		return homeTimeouts;
	}

	public final int getAwayTimeouts() {
		return awayTimeouts;
	}

	public final String getPossession() {
		return possession;
	}

	@Override
	public String toString() {
		return "Situation [ref=" + ref + ", lastPlay=" + lastPlay + ", down=" + down + ", yardLine=" + yardLine
				+ ", distance=" + distance + ", downDistanceText=" + downDistanceText + ", shortDownDistanceText="
				+ shortDownDistanceText + ", possessionText=" + possessionText + ", isRedZone=" + isRedZone
				+ ", homeTimeouts=" + homeTimeouts + ", awayTimeouts=" + awayTimeouts + ", possession=" + possession
				+ "]";
	}
    
    
    
}