package com.ddp.nfl.web.analytics.home;

import java.util.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public class TeamInfo{

    private final boolean isHome;
    private final String teamAbbr;
    private final int to;
    private final String players;
    private final Map<String, Integer> scoreMap;
    
    private final static int DEFAULT_SCORE  = 0;
    
    
    public TeamInfo( boolean isHome, String teamName, int to, Map<String, Integer> scoreMap, String players ){
        this.isHome = isHome;
        this.teamAbbr  = teamName;
        this.to     = to;
        this.players= players;
        this.scoreMap = scoreMap;
    }

    
    public final boolean isHome( ) {
        return isHome;
    }
    

    public final String getTeamNameAbbr( ) {
        return teamAbbr;
    }

    public final int getTo( ) {
        return to;
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
    
    
    public final void toDisplayString( StringBuilder builder ){
        builder.append( teamAbbr ).append( SPACE )
        .append( getTotalScore( ) ).append( SPACE )
        .append( "[" )
        .append( get1QuarterScore( )).append( SPACE )
        .append( get2QuarterScore( )).append( SPACE )
        .append( get3QuarterScore( )).append( SPACE )
        .append( get4QuarterScore( )).append( SPACE )
        .append( getOvertimeScore( ))
        .append( "]" )
        .append( NEWLINE );
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
        builder.append( teamAbbr );
        
        builder.append( ", To=" ).append( to );
        builder.append( ", Score:" ). append( scoreMap );
        builder.append( ", Players=" ).append( players );           
        builder.append( "]" );
        
        return builder.toString( );
    }

}