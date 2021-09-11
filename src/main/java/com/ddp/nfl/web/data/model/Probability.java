package com.ddp.nfl.web.data.model;

public class Probability{
	
    public double tiePercentage;
    public double homeWinPercentage;
    public double awayWinPercentage;
    public int secondsLeft;
    
    public Probability() {}
    
	public Probability(double tiePercentage, double homeWinPercentage, double awayWinPercentage, int secondsLeft) {		
		this.tiePercentage = tiePercentage;
		this.homeWinPercentage = homeWinPercentage;
		this.awayWinPercentage = awayWinPercentage;
		this.secondsLeft = secondsLeft;
	}
	
	public final double getTiePercentage() {
		return tiePercentage;
	}
	
	public final double getHomeWinPercentage() {
		return homeWinPercentage;
	}
	
	public final double getAwayWinPercentage() {
		return awayWinPercentage;
	}
	
	public final int getSecondsLeft() {
		return secondsLeft;
	}
	
	@Override
	public String toString() {
		return "Probability [tiePercentage=" + tiePercentage + ", homeWinPercentage=" + homeWinPercentage
				+ ", awayWinPercentage=" + awayWinPercentage + ", secondsLeft=" + secondsLeft + "]";
	}
    
    
}