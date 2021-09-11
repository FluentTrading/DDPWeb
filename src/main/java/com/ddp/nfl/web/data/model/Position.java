package com.ddp.nfl.web.data.model;

public class Position {

    public String abbreviation;

    public Position() {}
    
	public Position(String abbreviation) {
	
		this.abbreviation = abbreviation;
	}

	public final String getAbbreviation() {
		return abbreviation;
	}

	@Override
	public String toString() {
		return "Position [abbreviation=" + abbreviation + "]";
	}
    
    
}
