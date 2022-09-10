package com.ddp.nfl.web.data.model;

public class Format {

	public Regulation regulation;

	public Format(Regulation regulation) {		
		this.regulation = regulation;
	}

	public final Regulation getRegulation() {
		return regulation;
	}

	@Override
	public String toString() {
		return "Format []";
	}
	
	
	
}
