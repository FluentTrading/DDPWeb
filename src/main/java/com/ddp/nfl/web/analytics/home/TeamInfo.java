package com.ddp.nfl.web.analytics.home;

import java.util.*;
import com.ddp.nfl.web.data.model.Record;

import static com.ddp.nfl.web.util.DDPUtil.*;


public class TeamInfo{

    private final boolean isHome;
    private final String teamAbbr;    
    private final String players;
    private final Map<Quarter, Integer> scoreMap;
    private final Map<String, Record> recordMap;
        
    private final static int DEFAULT_SCORE  = 0;
    
    
    public TeamInfo( boolean isHome, String teamName, Map<Quarter, Integer> scoreMap, String players, List<Record> records ){
        this.isHome     = isHome;
        this.teamAbbr   = teamName;        
        this.players    = players;
        this.scoreMap   = scoreMap;
        this.recordMap	= parseRecord(records);
    }

    
	public final boolean isHome( ) {
        return isHome;
    }
    

    public final String getTeamNameAbbr( ) {
        return teamAbbr;
    }

    public final String getPlayers( ) {
        return players;
    }
    
    
    //YTD, Home or Away
    public final String getRecord( ){
    	Record record = recordMap.get("YTD");
    	return (record != null) ? record.getSummary() : "0-0";
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
    	int sum = 0;
    	for( Integer i : scoreMap.values() ) {
    		sum += i;
    	}

    	return sum;
       // return getScoreWithDefault( Quarter.TOTAL );        
    }
    
    
    public final String getQuarterScoreWithPadding( Quarter quarter ){
        int score = getScoreWithDefault( quarter );        
        return ( score < 10 ) ? "0" + score : String.valueOf(score);
    }
    
        
    protected final int getScoreWithDefault( Quarter qEnum ){
        Integer score = scoreMap.get( qEnum );
        return ( score != null ) ? score.intValue( ) : DEFAULT_SCORE;   
    }
    

    private Map<String, Record> parseRecord( List<Record> records ){
		Map<String, Record> map = new HashMap<>();
		for( Record r : records ) {
			map.put( r.getName(), r);
		}
		
		return map;
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
        builder.append( ", Records: " ).append( recordMap );        
        builder.append( ", Score:" ). append( scoreMap );
        builder.append( ", Players=" ).append( players );           
        builder.append( "]" );
        
        return builder.toString( );
    }

}