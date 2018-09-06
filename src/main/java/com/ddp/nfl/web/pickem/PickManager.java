package com.ddp.nfl.web.pickem;

import org.slf4j.*;
import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.parser.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class PickManager{

    private final DDPMeta meta;
    private final int[] unpicked;
    private final DBService service;
    private final Map<String, NFLTeam> nflTeams;
    private final Map<String, NFLTeam> nflTeamsByNickName;
    private final Map<String, DDPPlayer> players;
    
    private final static int NFL_TOTAL_PLAY_WEEK= 16;
    private final static Logger LOGGER          = LoggerFactory.getLogger( "PickManager" );
    
    
    public PickManager( DDPMeta meta, DBService service ){
        this.meta       = meta;
        this.service    = service;
        this.nflTeams   = new TreeMap<>( service.getAllTeams( ));
        this.nflTeamsByNickName = createMapping( nflTeams );
        this.players    = new TreeMap<>( service.getAllPlayers( ));
        this.unpicked   = getUnpickedWeeks( meta.getWeek( ) );
    }

    
    public final int[] getUnpickedWeeks( ){
        return unpicked;
    }
        
    
    public final Map<String, DDPPlayer> getAllPlayers( ){
        return players;
    }
    

    public final Map<String, NFLTeam> getAllTeams( ) {
        return nflTeams;
    }
    
    
    public final Map<String, NFLTeam> getAllTeamsByNickName( ) {
        return nflTeamsByNickName;
    }    
    

    public final Collection<DDPPick> getPickedTeamForWeek( int pickForWeek ){
        return service.loadPicks( pickForWeek, meta ).values( );        
    }
    
    
    public final Set<NFLTeam> getPlayingTeamsForWeek( int weekNumber ){
        return getPlayingTeams( weekNumber, meta, service.getAllTeams( ) );        
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
                      
        return PickResult.createValid("", null);
   
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
        return getAllPlayers( ).get( playerName );
    }


    protected final NFLTeam resolveTeam( String teamName ){
        return getAllTeams( ).get( teamName );
    }

    
    protected final Map<String, NFLTeam> createMapping( Map<String, NFLTeam> nflTeams ) {
        Map<String, NFLTeam> nickMap = new HashMap<>();
        for( NFLTeam team : nflTeams.values( ) ) {
            nickMap.put( team.getNickName( ), team );
        }
        
        return nickMap;
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
               

    //When we pick teams, we need to know the teams that are playing for that week
    protected final Set<NFLTeam> getPlayingTeams( int pickForWeek, DDPMeta meta, Map<String, NFLTeam> teamMap ){
        
        Set<NFLTeam> playingTeams  = new HashSet<>( );
        
        try {
        
            String liveScoreUrlForWeek= LiveScoreParser.createLiveScoreUrl( meta.getSeasonType( ), meta.getYear( ), pickForWeek );
            Collection<LiveScore> map= LiveScoreParser.parseLiveScore( liveScoreUrlForWeek, teamMap ).values( );
            
            for( LiveScore score : map ) {
                playingTeams.add( score.getHomeTeam( ) );
                playingTeams.add( score.getAwayTeam( ) );
            }
            
        }catch( Exception e ) {
            LOGGER.warn("FAILED to get playing teams for week [{}]", pickForWeek, e );
        }
        
        return playingTeams;
        
    }
    
}
