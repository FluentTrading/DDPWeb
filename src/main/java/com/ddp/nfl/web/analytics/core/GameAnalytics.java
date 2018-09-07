package com.ddp.nfl.web.analytics.core;

import com.ddp.nfl.web.analytics.drives.*;
import com.ddp.nfl.web.analytics.home.*;
import com.ddp.nfl.web.analytics.summary.*;


public final class GameAnalytics{

    private final String gameId;
    private final int nextupdate;
    private final TeamInfo home;
    private final TeamInfo away;
    private final SummaryManager scrsummary;
    private final DriveManager drives;
    private final String weather;
    private final String media;
    private final String yl;
    private final String qtr;
    private final String note;
    private final int down;
    private final int togo;
    private final boolean redzone;
    private final String clock;
    private final String posteam;
    private final String stadium;
    
    public GameAnalytics( String gameId, int nextupdate, TeamInfo home, TeamInfo away, DriveManager drives,
            SummaryManager scrsummary, String weather, String media, String yl, String qtr, String note, int down,
            int togo, boolean redzone, String clock, String posteam, String stadium ){
    
        this.gameId     = gameId;
        this.nextupdate = nextupdate;                 
        this.home       = home;
        this.away       = away;
        this.drives     = drives;
        this.scrsummary = scrsummary;
        this.weather    = weather;
        this.media      = media;
        this.yl         = yl;
        this.qtr        = qtr;
        this.note       = note;
        this.down       = down;
        this.togo       = togo;
        this.redzone    = redzone;
        this.clock      = clock;
        this.posteam    = posteam;
        this.stadium    = stadium;
    
    }
    
    public final String getGameId( ) {
        return gameId;
    }
    
    
    public final int getNextUpdate( ) {
        return nextupdate;
    }

    public final TeamInfo getHome( ) {
        return home;
    }

    public final TeamInfo getAway( ) {
        return away;
    }

    public final DriveManager getDrives( ) {
        return drives;
    }
        
    public final SummaryManager getSummaryManager( ) {
        return scrsummary;
    }

    public final String getWeather( ) {
        return weather;
    }

    public final String getMedia( ) {
        return media;
    }

    public final String getYl( ) {
        return yl;
    }

    public final String getQtr( ) {
        return qtr;
    }

    public final String getNote( ) {
        return note;
    }

    public final int getDown( ) {
        return down;
    }

    public final int getTogo( ) {
        return togo;
    }

    public final boolean isRedzone( ) {
        return redzone;
    }

    public final String getClock( ) {
        return clock;
    }

    public final String getPosteam( ) {
        return posteam;
    }

    public final String getStadium( ) {
        return stadium;
    }

    @Override
    public String toString( ) {
        StringBuilder builder = new StringBuilder( );
        builder.append( "GameAnalytics [gameId=" ).append( gameId ).append( ", nextupdate=" ).append( nextupdate ).append( ", home=" ).append( home ).append( ", away=" ).append( away ).append( ", scrsummary=" ).append( scrsummary ).append( ", weather=" ).append( weather ).append( ", media=" ).append( media ).append( ", yl=" ).append( yl ).append( ", qtr=" ).append( qtr ).append( ", note=" ).append( note ).append( ", down=" ).append( down ).append( ", togo=" ).append( togo ).append( ", redzone=" ).append( redzone ).append( ", clock=" ).append( clock ).append( ", posteam=" ).append( posteam ).append( ", stadium=" ).append( stadium ).append( "]" );
        return builder.toString( );
    }
  
   
}