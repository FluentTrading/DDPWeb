package com.ddp.nfl.web.analytics.core;

import static com.ddp.nfl.web.util.DDPUtil.*;

import com.ddp.nfl.web.analytics.drives.*;
import com.ddp.nfl.web.analytics.home.*;
import com.ddp.nfl.web.analytics.summary.*;


public final class GameCenter{

    private final String gameId;
    private final int nextupdate;
    private final TeamInfo home;
    private final TeamInfo away;
    private final DriveManager drives;
    private final SummaryManager scrsummary;
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
    
    public GameCenter( String gameId, int nextupdate, TeamInfo home, TeamInfo away, DriveManager drives,
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

    public final SummaryManager getScrsummary( ) {
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
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 2048 );
        builder.append( "GameCenter[" );
        builder.append( PRINT_NEWLINE );
        builder.append( "GameId=" ).append( gameId );
        builder.append( ", Weather=" ).append( weather );
        builder.append( ", Media=" ).append( media );
        builder.append( ", Yl=" ).append( yl );
        builder.append( ", Quarter=" ).append( qtr );
        builder.append( ", Down=" ).append( down );
        builder.append( ", Togo=" ).append( togo );
        builder.append( ", Redzone=" ).append( redzone );
        builder.append( ", Clock=" ).append( clock );
        builder.append( ", Posteam=" ).append( posteam );
        builder.append( ", Stadium=" ).append( stadium );
        builder.append( ", Note=" ).append( note );
        builder.append( "]" );
        
        builder.append( PRINT_NEWLINE );
        builder.append( "Home:" );
        builder.append( PRINT_NEWLINE );
        builder.append( home );
        builder.append( PRINT_NEWLINE );
        builder.append( "Away:" );
        builder.append( PRINT_NEWLINE );
        builder.append( away );
        builder.append( PRINT_NEWLINE );
        builder.append( "Drives:" );
        builder.append( PRINT_NEWLINE );
        builder.append( drives );
        builder.append( PRINT_NEWLINE );
        builder.append( "ScoreSummary:" );
        builder.append( PRINT_NEWLINE );
        builder.append( scrsummary );
        
        
        return builder.toString( );
    }
    
   
}