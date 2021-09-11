package com.ddp.nfl.web.schedule;

import org.slf4j.*;
import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.data.model.Competition;
import com.ddp.nfl.web.data.model.Competitor;
import com.ddp.nfl.web.data.model.EspnData;
import com.ddp.nfl.web.data.model.Event;
import com.ddp.nfl.web.data.model.Odd;
import com.ddp.nfl.web.data.model.Team;
import com.ddp.nfl.web.data.model.parser.EspnDataManager;
import com.ddp.nfl.web.database.*;


public final class EspnScheduleManager{

    private final boolean isValid;
    private final Set<String> teamsPlaying;
    private final Map<String, EspnSchedule> scheduleMap;

    private final static Logger LOGGER      = LoggerFactory.getLogger( "ScheduleManager" );
    
    
    public EspnScheduleManager( DDPMeta ddpMeta, DBService service ){
        this.scheduleMap    = parseSchedule( ddpMeta, service );
        this.isValid        = ( !scheduleMap.isEmpty( ) );
        this.teamsPlaying   = createTeamsPlayingSet( scheduleMap );
    }
    

    public final boolean isValid( ){
        return isValid;
    }
   
    
    public final int getScheduleCount( ) {
        return scheduleMap.size( );
    }
    
    
    public final Map<String, EspnSchedule> getSchedules( ) {
        return scheduleMap;
    }
    
    
    public final Set<String> getTeamsPlaying( ){
        return teamsPlaying;
    }
            

    protected final Set<String> createTeamsPlayingSet( Map<String, EspnSchedule> scheduleMap ){
        Set<String> teamsSet = new HashSet<>( );
        
        for( EspnSchedule scheule : scheduleMap.values( ) ){
            teamsSet.add( scheule.getHomeTeam( ) == null ? "" : scheule.getHomeTeam( ).getLowerCaseName( ) );
            teamsSet.add( scheule.getAwayTeam( ) == null ? "" : scheule.getAwayTeam( ).getLowerCaseName( ) );
        }
        
        return teamsSet;
        
    }
    
    
    //TODO: SORT Schedules by Time

    //Used to display data on the schedules page
    //We use this map to display schedule where earliest game (smallest gid) is at first
    public static final Map<String, EspnSchedule> parseSchedule( DDPMeta ddpMeta, DBService service ){

    	Map<String, EspnSchedule> scheduleMap= new TreeMap<>();
                
        try{               
                    	
        	EspnData dataManager 		= EspnDataManager.loadData( ddpMeta, service );
        	Map<String, NFLTeam> teamMap= service.getAllTeams();
        	
        	for( Event event : dataManager.getEvents() ){
       		
        		for( Competition competition : event.getCompetitions() ) {
        		
        			String gameEid  	= competition.getId();
            		String gameDate 	= competition.getDate();        		
            		String fullDetail	= competition.getStatus().getType().getDetail();
            		String shortDetail	= competition.getStatus().getType().getShortDetail();
            		boolean isOver		= competition.getStatus().getType().isCompleted();
            		        			
        			Competitor home 	= competition.getHomeCompetitor();        			
        	        NFLTeam homeTeam    = getTeam( home, teamMap );        	        
        	        int homeScore       = Integer.parseInt(home.getScore());
        	        
        	        Competitor away 	= competition.getAwayCompetitor();
        	        NFLTeam awayTeam    = getTeam( away, teamMap );        	                	          
        	        int awayScore       = Integer.parseInt(away.getScore());
        			
        			Odd gameOdds 		= parseGameOdds(competition);

        			EspnSchedule schedule = new EspnSchedule(gameEid, isOver, gameDate, shortDetail, fullDetail, home, homeTeam, homeScore, away, awayTeam, awayScore, gameOdds);
        			scheduleMap.put(gameEid, schedule);
        			
        			LOGGER.info( "{}", schedule );
        		}  
        	}
        	
        }catch( Exception e ){
            LOGGER.warn("FAILED to populate schedule", e);
        }
        
        return scheduleMap;

    }
    
    private static final Odd parseGameOdds( Competition competition ) {
    	if( competition.getOdds() == null || competition.getOdds().isEmpty() ) {
    		return new Odd();
    	}else {
    		return competition.getOdds().get(0);
    	}    	
		
    }
    
    
    public final static NFLTeam getTeam( Competitor competitor, Map<String, NFLTeam> teamMap ){    	
    	Team team 		= competitor.getTeam();
		String teamName = ( team.getName() != null ) ? team.getName() : team.getDisplayName(); 
  		return teamMap.get(teamName.toLowerCase());
    }
      

    


}
