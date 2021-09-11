package com.ddp.nfl.web.data.model;

public class Odd {

    public Provider provider;
    public String details;
    public double overUnder;
    
    public Odd() {}
    
	public Odd(Provider provider, String details, double overUnder) {
		this.provider = provider;
		this.details = details;
		this.overUnder = overUnder;
	}
	
	public final Provider getProvider() {
		return provider;
	}
	
	public final String getDetails() {
		return details;
	}
	
	public final double getOverUnder() {
		return overUnder;
	}
	
	public String toLiteString() {
		return details + ", Over/Under " + overUnder;
	}

	@Override
	public String toString() {
		return "Odd [provider=" + provider + ", details=" + details + ", overUnder=" + overUnder + "]";
	}
	
	

    
}
