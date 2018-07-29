package com.ddp.nfl.web.schedule;

import org.slf4j.*;

import java.util.*;

import com.ddp.nfl.web.core.*;


public final class ScheduleManager{

    private final boolean isValid;
    private final Map<String, NFLSchedule> schMap;
    
    private final static Logger LOGGER  = LoggerFactory.getLogger( "ScheduleManager" );
    
    
    public ScheduleManager( Map<String, NFLSchedule> schMap ){
        this.schMap  = schMap;
        this.isValid = ( schMap != null && !schMap.isEmpty( ) );
    }
    
    
    public final boolean isValid( ){
        return isValid;
    }
   
    
    public final NFLSchedule get( String gameId ) {
        return schMap.get( gameId );
    }

    
    public final Map<String, NFLSchedule> getSchedules( ) {
        return schMap;
    }
    
    
    public final Set<NFLTeam> getPlayingTeams( int pickForWeek, DDPMeta meta, Map<String, NFLTeam> teamMap ){
        
        Set<NFLTeam> playingTeams  = new HashSet<>( );
        try {
        
            String scheduleUrl         = ScheduleParser.prepareUrl( meta.getSeasonType( ), meta.getYear( ), pickForWeek );
            Collection<NFLSchedule> schs= ScheduleParser.parseSchedule( scheduleUrl, teamMap ).values( );
            for( NFLSchedule schedule : schs ) {
                playingTeams.add( schedule.getHomeTeam( ) );
                playingTeams.add( schedule.getAwayTeam( ) );
            }
            
        }catch( Exception e ) {
            LOGGER.warn("FAILED to get playing teams for week [{}]", pickForWeek, e );
        }
        
        return playingTeams;
        
    }
    

    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 64 );
        builder.append( "ScheduleManager [isValid=" ).append( isValid ).append( ", schMap=" ).append( schMap ).append( "]" );
        return builder.toString( );
    }



}
