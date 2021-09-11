package com.ddp.nfl.web.data.model;

public class Week {
	
    public int number;
    
    public Week() {}

	public Week(int number) {		
		this.number = number;
	}

	public final int getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return "Week [number=" + number + "]";
	}
    
    

}
