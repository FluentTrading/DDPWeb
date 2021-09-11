package com.ddp.nfl.web.analytics.core;

import org.apache.commons.collections4.map.*;
import org.slf4j.*;

import java.util.*;
import java.util.concurrent.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.data.model.parser.*;
import com.ddp.nfl.web.match.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class EspnAnalyticsManager{

    private final Map<String, Map<String,Analytics>> statsMap;
    private final Map<String, MultiKeyMap<String, String>> analyticsMap;
    
    private final static Logger LOGGER      = LoggerFactory.getLogger( "AnalyticsManager" );
    
    
    public EspnAnalyticsManager(  ){
        this.statsMap       = new ConcurrentHashMap<>( );
        this.analyticsMap   = new ConcurrentHashMap<>( );
    }
   
    
    public final String getGame1Drive( EspnGameResult result ){
        return getPlayDrive( result.getMatch1Score( ) );
    }
    
    
    public final String getGame2Drive( EspnGameResult result ){
        return getPlayDrive( result.getMatch2Score( ) );
    }

    
    //-------- Game Quarter --------
    public final String getGame1Quarter( EspnGameResult result ){
        return result.getMatch1Score( ).getDisplayableQuarter( );
    }
    
    
    public final String getGame2Quarter( EspnGameResult result ){
        return result.getMatch2Score( ).getDisplayableQuarter( );
    }
    
    //-------- Game Summary --------
    public final String getGame1Summary( EspnGameResult result ){
        return getGameSummary( result.getGame1Quarter( ), result.getMy1Team( ), result.getMatch1Score( ) );
    }
    
    public final String getGame2Summary( EspnGameResult result ){
        return getGameSummary( result.getGame2Quarter( ), result.getMy2Team( ), result.getMatch2Score( ) );
    }
    
    
    //-------- Game Stats --------
    public final Analytics getGame1Stats( EspnGameResult result ){
        return getGameStat( result.getMy1Team( ), result.getMatch1Score( ) );
    }
    
    public final Analytics getGame2Stats( EspnGameResult result ){
        return getGameStat( result.getMy2Team( ), result.getMatch2Score( ) );
    }

    protected final String getPlayDrive( EspnLiveScore liveScore ){
        if( liveScore == null ) return EMPTY;
        
        boolean notStarted  = EspnGameState.isNotStarted( liveScore );
        String driveInfo    = (notStarted)? liveScore.getStadium( ) : liveScore.getDriveInfo( ); 
        
        return driveInfo;
    }
    
    
    //Display the summary for the home team.
    protected final String getGameSummary( String fmtQuarter, NFLTeam homeTeam, EspnLiveScore liveScore ){
        
        String gameSummary     = EMPTY;
        
        try {
            
            boolean isPlaying   = EspnGameState.isPlaying( liveScore );
            if( !isPlaying ) return EMPTY;
         
            String gameId       = liveScore.getGameId( );
            MultiKeyMap summMap = analyticsMap.get( gameId );
            if( summMap == null ) return EMPTY;
            
            String homeNickName = homeTeam.getNickName( );
            String rawQuarter   = String.valueOf(liveScore.getQuarter());
            gameSummary         = (String) summMap.get( rawQuarter, homeNickName );
            gameSummary         = !isValid(gameSummary) ? EMPTY : gameSummary;       
        
        }catch (Exception e) {
            LOGGER.warn("Exception while looking up game summary", e);
        }
                
        return gameSummary; 
                        
    }
    
    
    
    protected final Analytics getGameStat( NFLTeam homeTeam, EspnLiveScore liveScore ) {
        
        Analytics gameStat      = null;
        
        try {
            
            boolean isPlaying   = EspnGameState.isPlaying( liveScore );
            if( !isPlaying ) return null;
         
            String gameId       = liveScore.getGameId( );
            Map<String,Analytics> stats   = statsMap.get( gameId );
            if( stats == null ) return null;
            
            String homeNickName = homeTeam.getNickName( );
            gameStat            = stats.get( homeNickName );
            
        }catch (Exception e) {
            LOGGER.warn("Exception while looking up game stats", e);
        }
                
        return gameStat; 
        
    }
        
    
    
}