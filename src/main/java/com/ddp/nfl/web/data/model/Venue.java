package com.ddp.nfl.web.data.model;

public class Venue {

    public String id;
    public String fullName;
    public Address address;
    public int capacity;
    public boolean indoor;
    
    public Venue() {}
    
    
	public Venue(String id, String fullName, Address address, int capacity, boolean indoor) {
		this.id = id;
		this.fullName = fullName;
		this.address = address;
		this.capacity = capacity;
		this.indoor = indoor;
	}
	
	public final String getId() {
		return id;
	}
	
	
	public final String getFullName() {
		return fullName;
	}
	
	
	public final Address getAddress() {
		return address;
	}
	
	
	public final int getCapacity() {
		return capacity;
	}
	
	
	public final boolean isIndoor() {
		return indoor;
	}
	
	
	@Override
	public String toString() {
		return "Venue [id=" + id + ", fullName=" + fullName + ", address=" + address + ", capacity=" + capacity
				+ ", indoor=" + indoor + "]";
	}
    
    
}
