package com.ddp.nfl.web.data.model;

public class End {

    public int yardLine;
    public String text;
    public Team team;
    
	public End(){}
	
	public End(int yardLine, Team team, String text) {	
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
		return "End [yardLine=" + yardLine + ", text=" + text + ", team=" + team + "]";
	}
	
    
}
