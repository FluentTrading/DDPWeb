package com.ddp.nfl.web.data.model;

import java.util.Arrays;

public class Logo {
	
	private String href;
	private String width;
	private String height;
	private String alt;
	private String[] rel;
	private String lastUpdated;
	
	
	public Logo() {}
	
	public Logo(String lastUpdated, String width, String alt, String[] rel, String href, String height) {
		this.lastUpdated = lastUpdated;
		this.width = width;
		this.alt = alt;
		this.rel = rel;
		this.href = href;
		this.height = height;
	}

	public final String getLastUpdated() {
		return lastUpdated;
	}

	public final String getWidth() {
		return width;
	}

	public final String getAlt() {
		return alt;
	}

	public final String[] getRel() {
		return rel;
	}

	public final String getHref() {
		return href;
	}

	public final String getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "Logos [lastUpdated=" + lastUpdated + ", width=" + width + ", alt=" + alt + ", rel="
				+ Arrays.toString(rel) + ", href=" + href + ", height=" + height + "]";
	}

	

	

}
