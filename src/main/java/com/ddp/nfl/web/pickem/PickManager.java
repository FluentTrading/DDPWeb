package com.ddp.nfl.web.pickem;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;
import java.util.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.schedule.*;


public final class PickManager{

    private final DDPMeta ddpMeta;
    private final int pickWeek;
    private final DBService service;
    private final ScheduleManager scheduleManager;
    
    private final Set<String> allTeamsForWeek; 
    private final Map<String, DDPPlayer> allPlayers;
    
    private final Collection<DDPPick> currentWeekPick; 
    
    private final static Logger LOGGER  = LoggerFactory.getLogger( "PickManager" );
    
    
    public PickManager( DDPMeta ddpMeta, ScheduleManager scheduleManager, DBService service ){
        this.ddpMeta        = ddpMeta;
        this.service        = service;
        this.scheduleManager= scheduleManager;
        this.pickWeek       = ddpMeta.getGameWeek( );
        this.allTeamsForWeek= populateTeamForThisWeek( );
        this.allPlayers     = new TreeMap<>( service.getAllPlayers( ) );
        this.currentWeekPick= loadTeamsPicked( ddpMeta.getGameWeek( ) );
                        
    }
   
    
    public final int getPickWeek( ){
        return pickWeek;
    }  
    
    
    public final Map<String, DDPPlayer> getAllPlayers( ){
        return allPlayers;
    }  
  
    
    public final Set<String> getTeamsPlaying( ){
        return allTeamsForWeek;
    }  
        
    
    public final Collection<DDPPick> getCurrentWeekPicks( ){
        return currentWeekPick;
    }
    
        
    public final Collection<DDPPick> loadTeamsPicked( int pickWeek ){
        return service.loadPicks( pickWeek, ddpMeta ).values( );
    }
    
    
    public final PickResult savePicks( int pickForWeek, String player, String team1, String team2 ) {

        Collection<DDPPick> picks   = loadTeamsPicked( pickForWeek );
        PickResult result           = validatePick( pickForWeek, player, team1, team2, picks );
        if( !result.isValid( ) ){
            return result;
        }
        
        int year                    = ddpMeta.getGameYear( );
        int pickOrder               = picks.size( ) + ONE;
        DDPPick ddpPick             = createDDPPick( pickOrder, pickForWeek, player, team1, team2 );
        boolean storedCorrectly     = service.upsertPick( year, pickForWeek, pickOrder, ddpPick );
        if( !storedCorrectly ) {
            return PickResult.createInvalid( "FAILED to save picks! Server on Fire Mon!" );
        }
        
        cleanupSelection( player, team1, team2 );
        return PickResult.createValid( "", picks );
        
    }


    //1. Ensure every player only has picked once.
    //2. Ensure every team picked is playing the week for which the pick was made.        
    //3. Ensure every team was only picked once.    (Relaxing this rule for 2020 )
    protected final PickResult validatePick( int pickForWeek, String player, String team1, String team2, Collection<DDPPick> picks ){
        
        Collection<Schedule> scheuleMap = scheduleManager.getSchedules( ).values( );
        DDPPick playerPicked        = hasPlayerPicked( player, picks );
        if( playerPicked != null ){
            return PickResult.createInvalid( "Player " + player + " had already picked " + playerPicked.toTeamString( ) );
        }
  
        //User hasn't selected teams twice
        boolean teamsNotUnique      = team1.equals(team2);
        if( teamsNotUnique ){
            return PickResult.createInvalid( player + " can't pick " + team1 + " twice!");
        }
        
        boolean[] allTeamsPlaying   = allTeamsPlaying( team1, team2 );
        if( !allTeamsPlaying[ZERO] ){
            return PickResult.createInvalid( team1 + " are not playing in Week " + pickForWeek );
        }
        
        if( !allTeamsPlaying[ONE] ){
            return PickResult.createInvalid( team2 + " are not playing in Week " + pickForWeek );
        }

        return PickResult.createValid("", null);
   
    }
        
           
    protected final boolean[] allTeamsPlaying( String team1, String team2 ){
        Set<String> teamsPlaying = getTeamsPlaying( );
        return new boolean[]{ teamsPlaying.contains(team1), teamsPlaying.contains(team2) };
    }
    

    protected final DDPPick createDDPPick( int pickOrder, int pickForWeek, String playerStr, String team1Str, String team2Str ){

        DDPPick ddpPick     = null;
        
        try{
            
            DDPPlayer player= resolvePlayer( playerStr );
            if( player == null ){
                LOGGER.warn( "Discarding pick as we failed to look up Player using [{}]", playerStr );
                return null;
            }
                
            NFLTeam team1   = resolveTeam( team1Str );
            if( team1 == null ) {
                LOGGER.warn( "Discarding pick as we failed to look up Team1 using [{}]", team1Str );
                return null;
            }
                
            
            NFLTeam team2   = resolveTeam( team2Str );
            if( team2 == null ) {
                LOGGER.warn( "Discarding pick as we failed to look up Team2 using [{}]", team2Str );
                return null;
            }

            ddpPick         = new DDPPick( pickOrder, player, new NFLTeam[]{ team1, team2 } );
            LOGGER.info("For Week [{}] [{}] picked {}", pickForWeek, playerStr, ddpPick );
            
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to parse picks made for [{}] [{}] [{}] [{}]", pickForWeek, playerStr, team1Str, team2Str );
        }
        
        return ddpPick;
        
    }


    public final DDPPick hasPlayerPicked( String playerName, Collection<DDPPick> picks ){
        
        for( DDPPick pick : picks ){
            String pickPlayer = pick.getPlayer( ).getName( );
            boolean hasPicked = pickPlayer.equalsIgnoreCase( playerName );
            if( hasPicked ){
                return pick;
            }
        }
        
        return null;
        
    }
    
    
    protected final DDPPlayer resolvePlayer( String playerName ){
        return service.getAllPlayers( ).get( playerName );
    }


    protected final NFLTeam resolveTeam( String teamName ){
        return service.getAllTeams( ).get( teamName.toLowerCase( ) );
    }

   
    protected final Set<String> populateTeamForThisWeek( ){
        Set<String> teamWeek = new TreeSet<>();
        for( String team : scheduleManager.getTeamsPlaying( ) ) {
            teamWeek.add( team.toUpperCase( ) );
        }
        
        return teamWeek;
    }
    

    private final void cleanupSelection( String player, String team1, String team2 ) {
        try {
            allPlayers.remove( player );
            allTeamsForWeek.remove( team1 );
            allTeamsForWeek.remove( team2 );

        }catch(Exception e ) {
            LOGGER.error( "FAILED to clean up player and teams after selection", e );
        }
    }
    
}
