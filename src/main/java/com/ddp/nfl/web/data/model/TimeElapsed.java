package com.ddp.nfl.web.data.model;

public class TimeElapsed {

	public String displayValue;

	public TimeElapsed() {}
	
	public TimeElapsed(String displayValue) {
		this.displayValue = displayValue;
	}

	public final String getDisplayValue() {
		return displayValue;
	}

	@Override
	public String toString() {
		return "TimeElapsed [displayValue=" + displayValue + "]";
	}
	
	
}
