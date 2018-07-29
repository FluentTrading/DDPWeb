package com.ddp.nfl.web.analytics.home;

import java.util.*;

public class TeamInfo{

    private final boolean isHome;
    private final String abbr;
    private final int to;
    private final StatsManager stats;
    private final String players;
    private final Map<String, Integer> scoreMap;
    
    private final static int DEFAULT_SCORE  = 0;
    
    
    public TeamInfo( boolean isHome, String abbr, int to, StatsManager stats, Map<String, Integer> scoreMap, String players ){
        this.isHome = isHome;
        this.abbr   = abbr;
        this.to     = to;
        this.stats  = stats;
        this.players= players;
        this.scoreMap = scoreMap;
    }

    
    public final boolean isHome( ) {
        return isHome;
    }
    

    public final String getAbbr( ) {
        return abbr;
    }

    public final int getTo( ) {
        return to;
    }

    public final StatsManager getStats( ) {
        return stats;
    }

    public final String getPlayers( ) {
        return players;
    }
   
    public final int get1QuarterScore( ){
        return getScoreWithDefault( Quarter.FIRST );        
    }
    
    public final int get2QuarterScore( ){
        return getScoreWithDefault( Quarter.SECOND );        
    }
        
    public final int get3QuarterScore( ){
        return getScoreWithDefault( Quarter.THIRD );        
    }
    
    public final int get4QuarterScore( ){
        return getScoreWithDefault( Quarter.FOURTH );        
    }
        
    public final int getOvertimeScore( ){
        return getScoreWithDefault( Quarter.OVERTIME );        
    }
        
    public final int getTotalScore( ){
        return getScoreWithDefault( Quarter.TOTAL );        
    }
        
    protected final int getScoreWithDefault( Quarter qEnum ){
        Integer score = scoreMap.get( qEnum.get() );
        return ( score != null ) ? score.intValue( ) : DEFAULT_SCORE;   
    }
    
    
    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( 1024);
        builder.append( "TeamInfo=");
        if( isHome ) {
            builder.append( "Home=" );
        }else {
            builder.append( "Away=" );
        }
        builder.append( abbr );
        
        builder.append( ", To=" ).append( to );
        builder.append( ", Score:" ). append( scoreMap );
        builder.append( ", Players=" ).append( players );
        builder.append( ", Stats=" ).append( stats );        
        builder.append( "]" );
        
        return builder.toString( );
    }

}