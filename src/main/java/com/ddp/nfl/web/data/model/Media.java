package com.ddp.nfl.web.data.model;

public class Media {
	
    public String shortName;
    
    public Media() {}

	public Media(String shortName) {
		this.shortName = shortName;
	}

	public final String getShortName() {
		return shortName;
	}


	@Override
	public String toString() {
		return "Media [shortName=" + shortName + "]";
	}
    
    

}
