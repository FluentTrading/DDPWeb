package com.ddp.nfl.web.data.model;

public class Market {
    
	public String id;
    public String type;
    
    public Market() {}
    
    
	public Market(String id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	public final String getId() {
		return id;
	}
	public final void setId(String id) {
		this.id = id;
	}
	public final String getType() {
		return type;
	}
	public final void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Market [id=" + id + ", type=" + type + "]";
	}
    
    
}
