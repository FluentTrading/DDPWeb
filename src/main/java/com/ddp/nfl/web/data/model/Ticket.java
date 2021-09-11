package com.ddp.nfl.web.data.model;

import java.util.List;

public class Ticket {

    public String summary;
    public int numberAvailable;
    public List<Link> links;
    
    public Ticket() {}
    
    
	public Ticket(String summary, int numberAvailable, List<Link> links) {
		this.summary = summary;
		this.numberAvailable = numberAvailable;
		this.links = links;
	}
	
	public final String getSummary() {
		return summary;
	}
	
	public final int getNumberAvailable() {
		return numberAvailable;
	}
	
	public final List<Link> getLinks() {
		return links;
	}
	
	@Override
	public String toString() {
		return "Ticket [summary=" + summary + ", numberAvailable=" + numberAvailable + "]";
	}
    
    
}
