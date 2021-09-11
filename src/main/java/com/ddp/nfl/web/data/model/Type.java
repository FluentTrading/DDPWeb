package com.ddp.nfl.web.data.model;

public class Type {
	
    public String id;
    public int type;
    public String name;
    public String abbreviation;
    public String state;
    public boolean completed;
    public String description;
    public String detail;
    public String shortDetail;
    public String shortName;
    
    public Type() {}

    public Type(String id, int type, String name, String abbreviation, String state, boolean completed,
			String description, String detail, String shortDetail, String shortName) {
		
		this.id = id;
		this.type = type;
		this.name = name;
		this.abbreviation = abbreviation;
		this.state = state;
		this.completed = completed;
		this.description = description;
		this.detail = detail;
		this.shortDetail = shortDetail;
		this.shortName = shortName;
	}
	public final String getId() {
		return id;
	}
	public final void setId(String id) {
		this.id = id;
	}
	public final int getType() {
		return type;
	}
	public final void setType(int type) {
		this.type = type;
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
	public final String getState() {
		return state;
	}
	public final void setState(String state) {
		this.state = state;
	}
	public final boolean isCompleted() {
		return completed;
	}
	public final void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public final String getDescription() {
		return description;
	}
	public final void setDescription(String description) {
		this.description = description;
	}
	public final String getDetail() {
		return detail;
	}
	public final void setDetail(String detail) {
		this.detail = detail;
	}
	public final String getShortDetail() {
		return shortDetail;
	}
	public final void setShortDetail(String shortDetail) {
		this.shortDetail = shortDetail;
	}
	public final String getShortName() {
		return shortName;
	}
	public final void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Override
	public String toString() {
		return "Type [id=" + id + ", type=" + type + ", name=" + name + ", abbreviation=" + abbreviation + ", state="
				+ state + ", completed=" + completed + ", description=" + description + ", detail=" + detail
				+ ", shortDetail=" + shortDetail + ", shortName=" + shortName + "]";
	}
    
    

}
