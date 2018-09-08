package com.ddp.nfl.web.pickem;

import org.slf4j.*;
import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.schedule.*;

import static com.ddp.nfl.web.util.DDPUtil.*;

public final class PickManager{

    private final DDPMeta meta;
    private final int pickWeek;
    private final DBService service;
    private final ScheduleManager schMan;
    private final Map<String, NFLTeam> teamsByNickName;
    private final static Logger LOGGER  = LoggerFactory.getLogger( "PickManager" );
    
    
    public PickManager( DDPMeta meta, ScheduleManager schMan, DBService service ){
        this.meta       = meta;
        this.schMan     = schMan;
        this.service    = service;
        this.pickWeek   = meta.getWeek( );
        this.teamsByNickName = createTeamsByNickName( service );
        
    }


    public final int getPickWeek( ){
        return pickWeek;
    }  
    
    
    public final Map<String, DDPPlayer> getAllPlayers( ){
        return service.getAllPlayers( );
    }  
  
    
    public final Set<String> getTeamsPlaying( ){
        return schMan.getTeamsPlaying( );
    }  
    
    
    public final Map<String, NFLTeam> getTeamsByNickName( ){
        return teamsByNickName;
    }  
    
    
        
    public final Collection<DDPPick> loadTeamsPicked( int pickWeek ){
        return service.loadPicks( pickWeek, meta ).values( );
    }
    
    
    public final PickResult savePicks( int pickForWeek, String player, String team1, String team2, String team3 ) {

        Collection<DDPPick> picks   = loadTeamsPicked( pickForWeek );
        PickResult result           = validatePick( pickForWeek, player, team1, team2, team3, picks );
        if( !result.isValid( ) ){
            return result;
        }
        
        int year                    = meta.getYear( );
        int pickOrder               = picks.size( ) + ONE;
        DDPPick ddpPick             = createDDPPick( pickOrder, pickForWeek, player, team1, team2, team3 );
        boolean storedCorrectly     = service.upsertPick( year, pickForWeek, pickOrder, ddpPick );
        if( !storedCorrectly ) {
            return PickResult.createInvalid( "FAILED to save picks! Server on Fire Mon!" );
        }
        
        return PickResult.createValid( "", picks );
        
    }
    

    //1. Ensure every player only has picked once.
    //2. Ensure every team picked is playing the week for which the pick was made.        
    //3. Ensure every team was only picked once.    
    protected final PickResult validatePick( int pickForWeek, String player, String team1, 
                                             String team2, String team3, Collection<DDPPick> picks ){
        
        Collection<Schedule> scheuleMap = schMan.getSchedules( ).values( );
        DDPPick playerPicked        = hasPlayerPicked( player, picks );
        if( playerPicked != null ){
            return PickResult.createInvalid( "Player " + player + " had already picked " + playerPicked.toTeamString( ) );
        }
  
        //User hasn't selected teams twice
        boolean teamsNotUnique      = (team1.equals(team2) || team2.equals(team3) || team1.equals( team3));
        if( teamsNotUnique ){
            return PickResult.createInvalid( player + " can't pick same team multiple times! " + team1 + " " + team2 + " " + team3 );
        }
        
        boolean[] allTeamsPlaying   = allTeamsPlaying( team1, team2, team3, scheuleMap );
        if( !allTeamsPlaying[ZERO] ){
            return PickResult.createInvalid( team1 + " are not playing in Week " + pickForWeek );
        }
        
        if( !allTeamsPlaying[ONE] ){
            return PickResult.createInvalid( team2 + " are not playing in Week " + pickForWeek );
        }
        
        if( !allTeamsPlaying[TWO] ){
            return PickResult.createInvalid( team3 + " are not playing in Week " + pickForWeek );
        }
        
        
        boolean teamWasPicked1      = wasTeamPicked( team1, picks );
        if( teamWasPicked1 ){
            return PickResult.createInvalid( team1 + " was already picked!" );
        }
        
        boolean teamWasPicked2      = wasTeamPicked( team2, picks );
        if( teamWasPicked2 ){
            return PickResult.createInvalid( team2 + " was already picked!" );
        }
        
        boolean teamWasPicked3      = wasTeamPicked( team3, picks );
        if( teamWasPicked3 ){
            return PickResult.createInvalid( team3 + " was already picked!" );
        }
                      
        return PickResult.createValid("", null);
   
    }
        
           
    protected final boolean[] allTeamsPlaying( String team1, String team2, String team3, Collection<Schedule> scheuleMap ) {
        Set<String> teamsPlaying = getTeamsPlaying( );
        return new boolean[]{ teamsPlaying.contains(team1), teamsPlaying.contains(team1), teamsPlaying.contains(team1) };
    }
    

    protected final DDPPick createDDPPick( int pickOrder, int pickForWeek, String playerStr, String team1Str, String team2Str, String team3Str ){

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
                LOGGER.warn( "Discarding pick as we failed to look up Team1 using [{}]", team2Str );
                return null;
            }
                
            NFLTeam team3   = resolveTeam( team3Str );
            if( team3 == null ) {
                LOGGER.warn( "Discarding pick as we failed to look up Team1 using [{}]", team3Str );
                return null;
            }
                
            ddpPick         = new DDPPick( pickOrder, player, new NFLTeam[]{ team1, team2, team3 } );
            LOGGER.info("For Week [{}] [{}] picked {}", pickForWeek, playerStr, ddpPick );
            
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to parse picks made for [{}] [{}] [{}] [{}] [{}]", pickForWeek, playerStr, team1Str, team2Str, team3Str );
        }
        
        return ddpPick;
        
    }
           
        
    public final boolean isTeamPlaying( String team, Set<NFLTeam> playingTeams ) {
        NFLTeam nflTeam = resolveTeam( team );
        boolean playing = playingTeams.contains( nflTeam );
        
        return playing;
    }


    public final boolean wasTeamPicked( String teamName, Collection<DDPPick> picks ) {
        
        for( DDPPick pick : picks ){
            
            for( NFLTeam nflTeam : pick.getTeams( ) ) {
                if( teamName.equalsIgnoreCase( nflTeam.getLowerCaseName( ) )){
                    return true;
                }
            }
            
        }
        
        return false;
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
        return service.getAllTeams( ).get( teamName );
    }

    protected final Map<String, NFLTeam> createTeamsByNickName( DBService service ){
        Map<String, NFLTeam> nflTeams = service.getAllTeams( );
        Map<String, NFLTeam> nickMap = new HashMap<>();
        for( NFLTeam team : nflTeams.values( ) ) {
            nickMap.put( team.getNickName( ), team );
        }
        
        return nickMap;
    }

    
}
