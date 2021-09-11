package com.ddp.nfl.web.data.model;

public class Linescore {
	
	public double value;

	public Linescore() {
		this( 0.0);
	}
	 
	public Linescore(double value) {
		this.value = value;
	}

	public final double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Linescore [value=" + value + "]";
	}
	 
	 

}
