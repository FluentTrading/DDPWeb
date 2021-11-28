package com.ddp.nfl.web.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Weather {

    public String displayValue;
    public int highTemperature;
    public int temperature;
    public String conditionId;
       
    @JsonIgnore
    public Link link;
    
    public Weather() {}
    
	public Weather(String displayValue, int highTemperature, String conditionId) {
		this(displayValue, highTemperature, -1, conditionId );		
	}
		
    
	public Weather(String displayValue, int highTemperature, int temperature, String conditionId) {
		this.displayValue = displayValue;
		this.highTemperature = highTemperature;
		this.temperature = temperature;
		this.conditionId = conditionId;
	}
	
	public final String getDisplayValue() {
		return displayValue;
	}
	
	
	public final int getTemperature() {
		return temperature;
	}
	
	
	public final int getHighTemperature() {
		return highTemperature;
	}
	
	public final String getConditionId() {
		return conditionId;
	}
	
	@Override
	public String toString() {
		return "Weather [displayValue=" + displayValue + ", highTemperature=" + highTemperature + ", conditionId="
				+ conditionId + "]";
	}
    
    
}
