package com.ddp.nfl.web.data.model;

public class Start {

    public int yardLine;
    public String text;
    public Team team;
    
	public Start(){}
	
	public Start(int yardLine, Team team, String text) {	
		this.yardLine = yardLine;
		this.team = team;
		this.text = text;
	}
	
	public final int getYardLine() {
		return yardLine;
	}
	
	public final Team getTeam() {
		return team;
	}
	
	public final String getText() {
		return text;
	}

	@Override
	public String toString() {
		return "Start [yardLine=" + yardLine + ", text=" + text + ", team=" + team + "]";
	}
	
	
    
    
}
