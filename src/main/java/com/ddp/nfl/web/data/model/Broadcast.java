package com.ddp.nfl.web.data.model;

import java.util.List;

public class Broadcast {
	
    public String market;
    public List<String> names;
    
    public Broadcast() {}
    
	public Broadcast(String market, List<String> names) {
		this.market = market;
		this.names = names;
	}
	
	public final String getMarket() {
		return market;
	}
	
	public final List<String> getNames() {
		return names;
	}
	
	@Override
	public String toString() {
		return "Broadcast [market=" + market + "]";
	}
    
    

}
