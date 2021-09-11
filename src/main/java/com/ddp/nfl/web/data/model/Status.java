package com.ddp.nfl.web.data.model;

public class Status {

    public double clock;
    public String displayClock;
    public int period;
    public Type type;
    
    public Status() {}
	
    public Status(double clock, String displayClock, int period, Type type) {	
		this.clock = clock;
		this.displayClock = displayClock;
		this.period = period;
		this.type = type;
	}
    
	public final double getClock() {
		return clock;
	}
	
	public final String getDisplayClock() {
		return displayClock;
	}
	
	public final int getPeriod() {
		return period;
	}
	
	public final Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "Status [clock=" + clock + ", displayClock=" + displayClock + ", period=" + period + ", type=" + type
				+ "]";
	}
    
    
}
