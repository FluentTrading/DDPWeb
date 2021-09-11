package com.ddp.nfl.web.data.model;

import java.util.List;

public class Link {
	
    public List<String> rel;
    public String href;
    public String text;
    public boolean isExternal;
    public boolean isPremium;
    public String language;
    public String shortText;
    
    public Link() {}
    
	public Link(List<String> rel, String href, String text, boolean isExternal, boolean isPremium, String language,
			String shortText) {
		super();
		this.rel = rel;
		this.href = href;
		this.text = text;
		this.isExternal = isExternal;
		this.isPremium = isPremium;
		this.language = language;
		this.shortText = shortText;
	}
	public final List<String> getRel() {
		return rel;
	}
	public final void setRel(List<String> rel) {
		this.rel = rel;
	}
	public final String getHref() {
		return href;
	}
	public final void setHref(String href) {
		this.href = href;
	}
	public final String getText() {
		return text;
	}
	public final void setText(String text) {
		this.text = text;
	}
	public final boolean isExternal() {
		return isExternal;
	}
	public final void setExternal(boolean isExternal) {
		this.isExternal = isExternal;
	}
	public final boolean isPremium() {
		return isPremium;
	}
	public final void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}
	public final String getLanguage() {
		return language;
	}
	public final void setLanguage(String language) {
		this.language = language;
	}
	public final String getShortText() {
		return shortText;
	}
	public final void setShortText(String shortText) {
		this.shortText = shortText;
	}
	@Override
	public String toString() {
		return "Link [rel=" + rel + ", href=" + href + ", text=" + text + ", isExternal=" + isExternal + ", isPremium="
				+ isPremium + ", language=" + language + ", shortText=" + shortText + "]";
	}
    
    

}
