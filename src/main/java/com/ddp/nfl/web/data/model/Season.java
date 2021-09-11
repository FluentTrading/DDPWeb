package com.ddp.nfl.web.data.model;

public class Season {
	
    public int year;
    public String startDate;
    public String endDate;
    public Type type;
    public String slug;
    
    public Season() {}
    
	public Season(int year, String startDate, String endDate, Type type, String slug) {		
		this.year = year;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.slug = slug;
	}
	
	public final int getYear() {
		return year;
	}
	
	
	public final String getStartDate() {
		return startDate;
	}
	
	
	public final String getEndDate() {
		return endDate;
	}
	
	public final Type getType() {
		return type;
	}
	
	public final String getSlug() {
		return slug;
	}
	
	
	@Override
	public String toString() {
		return "Season [year=" + year + ", startDate=" + startDate + ", endDate=" + endDate + ", type=" + type
				+ ", slug=" + slug + "]";
	}
    
    

}
