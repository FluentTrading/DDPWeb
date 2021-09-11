package com.ddp.nfl.web.data.model;

public class Record {

    public String name;
    public String abbreviation;
    public String type;
    public String summary;
    
    public Record() {}
    
    
	public Record(String name, String abbreviation, String type, String summary) {
		this.name = name;
		this.abbreviation = abbreviation;
		this.type = type;
		this.summary = summary;
	}
	
	public final String getName() {
		return name;
	}
	
	
	public final String getAbbreviation() {
		return abbreviation;
	}
	
	public final String getType() {
		return type;
	}
	
	public final String getSummary() {
		return summary;
	}
	
	@Override
	public String toString() {
		return "Record [name=" + name + ", abbreviation=" + abbreviation + ", type=" + type + ", summary=" + summary
				+ "]";
	}
    
    
}
