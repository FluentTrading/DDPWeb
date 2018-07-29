package com.ddp.nfl.web.pickem;

import org.slf4j.*;
import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.schedule.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class PickManager{

    private final DDPMeta meta;
    private final int[] unpicked;
    private final DBService service;
    private final ScheduleManager schMan;
    
    private final static int NFL_TOTAL_PLAY_WEEK= 16;
    private final static Logger LOGGER          = LoggerFactory.getLogger( "PickManager" );
    
    
    public PickManager( DDPMeta meta, ScheduleManager schMan, DBService service ){
        this.meta       = meta;
        this.service    = service;
        this.schMan     = schMan;
        this.unpicked   = getUnpickedWeeks( meta.getWeek( ) );
    }
    
    
    public final int[] getUnpickedWeeks( ){
        return unpicked;
    }
    
    
    //TODO:
    //If the pick for next week has been made, display it on the form,
    //If the pick hasn't been made, then show the pick order
    public final Map<String, DDPPlayer> getAllPlayers( ){
        return service.getAllPlayers( );
    }
    

    public final Map<String, NFLTeam> getAllTeams( ) {
        return service.getAllTeams( );
    }
    

    public final Collection<DDPPick> getPickedTeamForWeek( int pickForWeek ){
        return service.loadPicks( pickForWeek, meta ).values( );        
    }
    
    
    public final Set<NFLTeam> getPlayingTeamsForWeek( int weekNumber ){
        return schMan.getPlayingTeams( weekNumber, meta, service.getAllTeams( ) );        
    }
        
        
    
    public final PickResult process( int pickForWeek, String player, String team1, String team2, String team3 ) {

        Collection<DDPPick> picks   = getPickedTeamForWeek( pickForWeek );
        Set<NFLTeam> playingTeams   = getPlayingTeamsForWeek( pickForWeek );
        
        PickResult result           = validatePick( pickForWeek, player, team1, team2, team3, picks, playingTeams );
        if( !result.isValid( ) ){
            return result;
        }
        
        int year                    = meta.getYear( );
        int pickOrder               = picks.size( ) + ONE;
        DDPPick ddpPick             = parse( pickOrder, pickForWeek, player, team1, team2, team3 );
        boolean storedCorrectly     = service.upsertPick( year, pickForWeek, pickOrder, ddpPick );
        if( !storedCorrectly ) {
            return PickResult.createInvalid( "FAILED to save picsk, Server on Fire Mon!" );
        }
        
        return PickResult.createValid( "" );
        
    }
    

    //1. Ensure every player only has picked once.
    //2. Ensure every team picked is playing the week for which the pick was made.        
    //3. Ensure every team was only picked once.    
    protected final PickResult validatePick( int pickForWeek, String player, String team1, String team2, String team3, Collection<DDPPick> picks, Set<NFLTeam> playingTeams ){
        
        DDPPick playerPicked        = hasPlayerPicked( player, picks );
        if( playerPicked != null ){
            return PickResult.createInvalid( "Player " + player + " had already picked " + playerPicked.toTeamString( ) );
        }
  
        //User hasn't selected teams twice
        boolean teamsNotUnique      = (team1.equals(team2) || team2.equals(team3) || team1.equals( team3));
        if( teamsNotUnique ){
            return PickResult.createInvalid( player + " can't pick same team multiple times! " + team1 + " " + team2 + " " + team3 );
        }
        
        
        boolean isTeamPlaying1      = isTeamPlaying( team1, playingTeams );
        if( !isTeamPlaying1 ){
            return PickResult.createInvalid( team1 + " are not playing in Week " + pickForWeek );
        }
        
        boolean isTeamPlaying2      = isTeamPlaying( team2, playingTeams );
        if( !isTeamPlaying2 ){
            return PickResult.createInvalid( team2 + " are not playing in Week " + pickForWeek );
        }
        
        boolean isTeamPlaying3      = isTeamPlaying( team3, playingTeams );
        if( !isTeamPlaying3 ){
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
                      
        return PickResult.createValid("");
   
    }
        
    
    public final boolean isTeamPlaying( String team, Set<NFLTeam> playingTeams ) {
        NFLTeam nflTeam = resolveTeam( team );
        boolean playing = playingTeams.contains( nflTeam );
        
        return playing;
    }


    public final boolean wasTeamPicked( String teamName, Collection<DDPPick> picks ) {
        
        for( DDPPick pick : picks ){
            
            for( NFLTeam nflTeam : pick.getTeams( ) ) {
                if( teamName.equalsIgnoreCase( nflTeam.getName( ) )){
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
         

    protected final DDPPick parse( int pickOrder, int pickForWeek, String playerStr, String team1Str, String team2Str, String team3Str ){

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
            LOGGER.warn("For Week [{}] [{}] picked {}", pickForWeek, playerStr, ddpPick );
            
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to parse picks made for [{}] [{}] [{}] [{}] [{}]", pickForWeek, playerStr, team1Str, team2Str, team3Str );
        }
        
        return ddpPick;
        
    }
           
    
    protected final DDPPlayer resolvePlayer( String playerName ){
        return getAllPlayers( ).get( playerName );
    }


    protected final NFLTeam resolveTeam( String teamName ){
        return getAllTeams( ).get( teamName );
    }


    //If week 6 is current, show option for week 6 as well
    protected final static int[] getUnpickedWeeks( int currWeek ){
        int pickCount   = (NFL_TOTAL_PLAY_WEEK + ONE - currWeek);
        int[] pickWeeks = new int[ pickCount];
        
        for( int i=0; i<pickCount; i++ ){
            pickWeeks[i] = currWeek + i;
        }
        
        return pickWeeks;
        
    }
               
    
    public final static String toMessageString( String header, Collection<DDPPick> teams ){
                
        StringBuilder builder       = new StringBuilder( 64 );
        builder.append( header ).append( NEWLINE ).append( NEWLINE );
        
        for( DDPPick pick : teams ){
            
            builder.append( pick.getPlayer( ).getName( ) ).append( "\t" );
            builder.append( pick.getTeams()[0].getName( ) ).append( " " );
            builder.append( pick.getTeams()[1].getName( ) ).append( " " );
            builder.append( pick.getTeams()[2].getName( ) ).append( " " );
            
            builder.append( NEWLINE );            
        }
                
        return builder.toString( );
        
    }
}
