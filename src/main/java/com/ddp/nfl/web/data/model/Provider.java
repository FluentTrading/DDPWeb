package com.ddp.nfl.web.data.model;

public class Provider {

    public String id;
    public String name;
    public int priority;
    
    public Provider() {}
    
    
	public Provider(String id, String name, int priority) {		
		this.id = id;
		this.name = name;
		this.priority = priority;
	}
	
	public final String getId() {
		return id;
	}
	
	public final String getName() {
		return name;
	}
	
	public final int getPriority() {
		return priority;
	}
	
	@Override
	public String toString() {
		return "Provider [id=" + id + ", name=" + name + ", priority=" + priority + "]";
	}
    
    
	
	
}
