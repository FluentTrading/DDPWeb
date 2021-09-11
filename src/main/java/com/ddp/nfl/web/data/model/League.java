package com.ddp.nfl.web.data.model;

import java.util.List;

public class League {
	
    public String id;
    public String uid;
    public String name;
    public String abbreviation;
    public String slug;
    public Season season;
    public String calendarType;
    public boolean calendarIsWhitelist;
    public String calendarStartDate;
    public String calendarEndDate;
    public List<Calendar> calendar;
    
    
	public League() {}		
	

	public League(String id, String uid, String name, String abbreviation, String slug, Season season,
			String calendarType, boolean calendarIsWhitelist, String calendarStartDate, String calendarEndDate,
			List<Calendar> calendar) {
	
		this.id = id;
		this.uid = uid;
		this.name = name;
		this.abbreviation = abbreviation;
		this.slug = slug;
		this.season = season;
		this.calendarType = calendarType;
		this.calendarIsWhitelist = calendarIsWhitelist;
		this.calendarStartDate = calendarStartDate;
		this.calendarEndDate = calendarEndDate;
		this.calendar = calendar;
	}
	
	public final String getId() {
		return id;
	}
	public final void setId(String id) {
		this.id = id;
	}
	public final String getUid() {
		return uid;
	}
	public final void setUid(String uid) {
		this.uid = uid;
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final String getAbbreviation() {
		return abbreviation;
	}
	public final void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public final String getSlug() {
		return slug;
	}
	public final void setSlug(String slug) {
		this.slug = slug;
	}
	public final Season getSeason() {
		return season;
	}
	public final void setSeason(Season season) {
		this.season = season;
	}
	public final String getCalendarType() {
		return calendarType;
	}
	public final void setCalendarType(String calendarType) {
		this.calendarType = calendarType;
	}
	public final boolean isCalendarIsWhitelist() {
		return calendarIsWhitelist;
	}
	public final void setCalendarIsWhitelist(boolean calendarIsWhitelist) {
		this.calendarIsWhitelist = calendarIsWhitelist;
	}
	public final String getCalendarStartDate() {
		return calendarStartDate;
	}
	public final void setCalendarStartDate(String calendarStartDate) {
		this.calendarStartDate = calendarStartDate;
	}
	public final String getCalendarEndDate() {
		return calendarEndDate;
	}
	public final void setCalendarEndDate(String calendarEndDate) {
		this.calendarEndDate = calendarEndDate;
	}
	public final List<Calendar> getCalendar() {
		return calendar;
	}
	public final void setCalendar(List<Calendar> calendar) {
		this.calendar = calendar;
	}
	@Override
	public String toString() {
		return "League [id=" + id + ", uid=" + uid + ", name=" + name + ", abbreviation=" + abbreviation + ", slug="
				+ slug + ", season=" + season + ", calendarType=" + calendarType + ", calendarIsWhitelist="
				+ calendarIsWhitelist + ", calendarStartDate=" + calendarStartDate + ", calendarEndDate="
				+ calendarEndDate + ", calendar=" + calendar + "]";
	}
    
    

}
