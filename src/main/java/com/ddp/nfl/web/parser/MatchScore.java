package com.ddp.nfl.web.parser;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.schedule.*;


public final class MatchScore{
    
    private final boolean isPlaying;
    private final boolean isFinished;
    
    private final NFLTeam home;
    private final int homeScore;
    
    private final NFLTeam away;
    private final int awayScore;
    
    private final String quarter;
    private final NFLSchedule schedule;
    
    public MatchScore( boolean isPlaying, boolean isFinished, NFLTeam home, int homeScore, 
                         NFLTeam visit, int visitScore, String quarter, NFLSchedule schedule ){
        
        this.isPlaying  = isPlaying;
        this.isFinished = isFinished;
        this.home       = home;
        this.homeScore  = homeScore;
        this.away      = visit;
        this.awayScore = visitScore;
        this.quarter    = quarter;
        this.schedule   = schedule;
    
    }
    

    public final boolean isPlaying( ) {
        return isPlaying;
    }

    
    public final boolean isFinished( ){
        return isFinished;
    }

    
    public final NFLTeam getHomeTeam( ){
        return home;
    }

    
    public final int getHomeScore( ){
        return homeScore;
    }

    
    public final NFLTeam getAwayTeam( ){
        return away;
    }

    
    public final int getAwayScore( ){
        return awayScore;
    }

    
    public final String getQuarter( ){
        return quarter;
    }

    
    public final NFLSchedule getSchedule( ){
        return schedule;
    }
    

    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( 128 );
        
        builder.append( "NFLMatchInfo [isPlaying=" ).append( isPlaying ).append( ", isFinished=" ).append( isFinished );
        builder.append( ", home=" ).append( home ).append( ", homeScore=" ).append( homeScore );
        builder.append( ", visit=" ).append( away ).append( ", visitScore=" ).append( awayScore );
        builder.append( ", quarter=" ).append( quarter ).append( ", schedule=" ).append( schedule ).append( "]" );
        
        return builder.toString( );
    }
    
        


}
