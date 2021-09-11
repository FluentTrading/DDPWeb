package com.ddp.nfl.web.data.model;

import java.util.List;

public class Calendar {
	
    public String label;
    public String value;
    public String startDate;
    public String endDate;
    public List<Entry> entries;
    
    public Calendar() {}
    
	public Calendar(String label, String value, String startDate, String endDate, List<Entry> entries) {
		this.label = label;
		this.value = value;
		this.startDate = startDate;
		this.endDate = endDate;
		this.entries = entries;
	}
	public final String getLabel() {
		return label;
	}
	public final void setLabel(String label) {
		this.label = label;
	}
	public final String getValue() {
		return value;
	}
	public final void setValue(String value) {
		this.value = value;
	}
	public final String getStartDate() {
		return startDate;
	}
	public final void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public final String getEndDate() {
		return endDate;
	}
	public final void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public final List<Entry> getEntries() {
		return entries;
	}
	public final void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
	@Override
	public String toString() {
		return "Calendar [label=" + label + ", value=" + value + ", startDate=" + startDate + ", endDate=" + endDate
				+ "]";
	}
    
    

}
