package com.ddp.nfl.web.data.model;

public class Drive {
	    
    public String description;
    public Start start;
    public End end;
    public TimeElapsed timeElapsed;
    public String result;

    public Drive() {}
    
    public Drive(String description, Start start, End end, TimeElapsed timeElapsed, String result) {
		this.description = description;
		this.start = start;
		this.end = end;
		this.timeElapsed = timeElapsed;
		this.result= result;
	}
	
    public final String getDescription() {
		return description;
	}
	
    public final Start getStart() {
		return start;
	}
	
    public final End getEnd() {
		return end;
	}
    
    public final TimeElapsed getTimeElapsed() {
		return timeElapsed;
	}
    
    public final String getResult() {
 		return result;
 	}

	@Override
	public String toString() {
		return "Drive [description=" + description + ", start=" + start + ", end=" + end + ", timeElapsed="
				+ timeElapsed + ", result=" + result + "]";
	}
   
    

}
