package com.ddp.nfl.web.data.model;

public class GeoBroadcast {
	
    public Type type;
    public Market market;
    public Media media;
    public String lang;
    public String region;
    
    public GeoBroadcast() {}
    
    
	public GeoBroadcast(Type type, Market market, Media media, String lang, String region) {
		this.type = type;
		this.market = market;
		this.media = media;
		this.lang = lang;
		this.region = region;
	}
	public final Type getType() {
		return type;
	}
	public final void setType(Type type) {
		this.type = type;
	}
	public final Market getMarket() {
		return market;
	}
	public final void setMarket(Market market) {
		this.market = market;
	}
	public final Media getMedia() {
		return media;
	}
	public final void setMedia(Media media) {
		this.media = media;
	}
	public final String getLang() {
		return lang;
	}
	public final void setLang(String lang) {
		this.lang = lang;
	}
	public final String getRegion() {
		return region;
	}
	public final void setRegion(String region) {
		this.region = region;
	}
	@Override
	public String toString() {
		return "GeoBroadcast [type=" + type + ", market=" + market + ", media=" + media + ", lang=" + lang + ", region="
				+ region + "]";
	}
    
    

}
