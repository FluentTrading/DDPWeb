package com.ddp.nfl.web.schedule;

import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.parser.*;


public final class ScheduleManager{

    private final boolean isValid;
    private final Map<String, LiveScore> gameIdMap;
    
    public ScheduleManager( DDPMeta ddpMeta, DBService service ){
        this.gameIdMap  = parse( ddpMeta, service );
        this.isValid    = ( !gameIdMap.isEmpty( ) );
    }
    

    public final boolean isValid( ){
        return isValid;
    }
   
    
    public final LiveScore get( String gameId ) {
        return gameIdMap.get( gameId );
    }

    
    public final int getScheduleCount( ) {
        return gameIdMap.size( );
    }
    
    
    public final Map<String, LiveScore> getSchedules( ) {
        return gameIdMap;
    }
    

    //Used to display data on the schedules page
   //We use this map to display schedule where earliest game (smallest gid) is at first
    protected final Map<String, LiveScore> parse( DDPMeta meta, DBService service ) {
        
        Map<String, LiveScore> gameIdMap= new TreeMap<>( );
        String liveScoreUrl             = LiveScoreParser.createLiveScoreUrl( meta.getSeasonType( ), meta.getYear( ), meta.getWeek( ) );
        Map<NFLTeam, LiveScore> scoreMap= LiveScoreParser.parseLiveScore( liveScoreUrl, service.getAllTeams( ) );
        
        for( LiveScore score : scoreMap.values( ) ) {
            gameIdMap.put( score.getGameId( ), score );
        }
    
        return gameIdMap;
    }


    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 64 );
        builder.append( "ScheduleManager [isValid=" ).append( isValid ).append( ", gameIdMap=" ).append( gameIdMap ).append( "]" );
        return builder.toString( );
    }

}
