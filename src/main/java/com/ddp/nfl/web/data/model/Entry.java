package com.ddp.nfl.web.data.model;

public class Entry {
	
    public String label;
    public String alternateLabel;
    public String detail;
    public String value;
    public String startDate;
    public String endDate;
    
    public Entry() {}
    
	public Entry(String label, String alternateLabel, String detail, String value, String startDate, String endDate) {
		super();
		this.label = label;
		this.alternateLabel = alternateLabel;
		this.detail = detail;
		this.value = value;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public final String getLabel() {
		return label;
	}
	public final void setLabel(String label) {
		this.label = label;
	}
	public final String getAlternateLabel() {
		return alternateLabel;
	}
	public final void setAlternateLabel(String alternateLabel) {
		this.alternateLabel = alternateLabel;
	}
	public final String getDetail() {
		return detail;
	}
	public final void setDetail(String detail) {
		this.detail = detail;
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
	@Override
	public String toString() {
		return "Entry [label=" + label + ", alternateLabel=" + alternateLabel + ", detail=" + detail + ", value="
				+ value + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
    
    

}
