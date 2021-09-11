package com.ddp.nfl.web.data.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Competitor {
	
    public String id;
    public String uid;
    public String type;
    public int order;
    public String homeAway;
    public boolean winner;
    public Team team;
    public String score;
    public List<Linescore> linescores;
    public List<Object> statistics;
    public List<Record> records;
    
    @JsonIgnore
    public List<Leader> leaders;
    
    public Competitor() {}
    
    
	public Competitor(String id, String uid, String type, int order, String homeAway, boolean winner, Team team, String score,
			List<Object> statistics, List<Linescore> linescores, List<Record> records, List<Leader> leaders) {
		
		this.id = id;
		this.uid = uid;
		this.type = type;
		this.order = order;
		this.homeAway = homeAway;
		this.winner = winner;				
		this.team = team;
		this.score = score;
		this.statistics = statistics;
		this.linescores = linescores;
		this.records = records;
		this.leaders = leaders;
	}
	
	public final String getId() {
		return id;
	}
	
	
	public final String getUid() {
		return uid;
	}
	
	public final String getType() {
		return type;
	}
	
	public final int getOrder() {
		return order;
	}
	
	public final String getHomeAway() {
		return homeAway;
	}
	
	public final boolean isWinner() {
		return winner;
	}
		
	
	public final Team getTeam() {
		return team;
	}
	
	public final String getScore() {
		return score;
	}
	
	public final List<Object> getStatistics() {
		return statistics;
	}
	
	public final List<Linescore> getLineScores() {
		return linescores;
	}
	
	public final List<Record> getRecords() {
		return records;
	}
	
	public final List<Leader> getLeaders() {
		return leaders;
	}


	@Override
	public String toString() {
		return "Competitor [id=" + id + ", uid=" + uid + ", type=" + type + ", order=" + order + ", homeAway="
				+ homeAway + ", winner=" + winner + ", team=" + team + ", score=" + score + ", linescores=" + linescores
				+ ", statistics=" + statistics + ", records=" + records + ", leaders=" + leaders + "]";
	}
	
    

}
