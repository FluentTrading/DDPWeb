package com.ddp.nfl.web.data.model;

public class Probability{
	
    public double tiePercentage;
    public double homeWinPercentage;
    public double awayWinPercentage;
    public int secondsLeft;
    
    public Probability() {}
    
    private static final String TWO_DIGIT_FORMAT = "%.2f";
    
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
	
	public String toLiteString() {
		return "HomeWin: " + String.format(TWO_DIGIT_FORMAT, homeWinPercentage) + "%" + ", AwayWin: " + String.format(TWO_DIGIT_FORMAT, awayWinPercentage) + "%";
	}
    
	
	@Override
	public String toString() {
		return "Probability [tiePercentage=" + tiePercentage + ", homeWinPercentage=" + homeWinPercentage
				+ ", awayWinPercentage=" + awayWinPercentage + ", secondsLeft=" + secondsLeft + "]";
	}
    
    
}