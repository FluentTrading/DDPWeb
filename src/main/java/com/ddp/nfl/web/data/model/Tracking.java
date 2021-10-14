package com.ddp.nfl.web.data.model;


public class Tracking {

    public String sportName;
    public String leagueName;
    public String coverageType;
    public String trackingName;
    public String trackingId;
    
    public Tracking() {}

	public Tracking(String sportName, String leagueName, String coverageType, String trackingName, String trackingId) {		
		this.sportName = sportName;
		this.leagueName = leagueName;
		this.coverageType = coverageType;
		this.trackingName = trackingName;
		this.trackingId = trackingId;
	}

	public final String getSportName() {
		return sportName;
	}

	public final String getLeagueName() {
		return leagueName;
	}

	public final String getCoverageType() {
		return coverageType;
	}

	public final String getTrackingName() {
		return trackingName;
	}

	public final String getTrackingId() {
		return trackingId;
	}

	@Override
	public String toString() {
		return "Tracking [sportName=" + sportName + ", leagueName=" + leagueName + ", coverageType=" + coverageType
				+ ", trackingName=" + trackingName + ", trackingId=" + trackingId + "]";
	}
    
    
    
    
}
