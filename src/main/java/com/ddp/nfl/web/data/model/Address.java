package com.ddp.nfl.web.data.model;

public class Address {

    public String city;
    public String state;
    
    public Address() {}
    
	public Address(String city, String state) {
		this.city = city;
		this.state = state;
	}
	
	public final String getCity() {
		return city;
	}
	
	public final String getState() {
		return state;
	}
	
	@Override
	public String toString() {
		return "Address [city=" + city + ", state=" + state + "]";
	}
    
    
}
