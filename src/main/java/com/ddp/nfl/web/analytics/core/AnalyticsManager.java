package com.ddp.nfl.web.analytics.core;

import org.apache.commons.collections4.map.*;
import org.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.match.*;
import com.ddp.nfl.web.parser.*;
import com.google.gson.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class AnalyticsManager{

    private ScheduledExecutorService executor;
    
    private final DownloadAnalyticsThread gameThread;
    private final Map<String, Map<String,Analytics>> statsMap;
    private final Map<String, MultiKeyMap<String, String>> analyticsMap;
    
    private final static int INTERVAL       = ONE;
    private final static Logger LOGGER      = LoggerFactory.getLogger( "AnalyticsManager" );
    
    
    public AnalyticsManager(  ){
        this.statsMap       = new ConcurrentHashMap<>( );
        this.analyticsMap   = new ConcurrentHashMap<>( );
        this.gameThread     = new DownloadAnalyticsThread( );
        this.executor       = Executors.newSingleThreadScheduledExecutor( );
    }

    
    public final void start( ){
      //executor.scheduleAtFixedRate( gameThread, ZERO, INTERVAL, TimeUnit.MINUTES);
      //LOGGER.info( "Successfully created analytics thread to run every {} minute.", INTERVAL );
    }
    
    
    public final String getGame1Drive( GameResult result ){
        return getPlayDrive( result.getMatch1Score( ) );
    }
    
    
    public final String getGame2Drive( GameResult result ){
        return getPlayDrive( result.getMatch2Score( ) );
    }

    
    public final String getGame3Drive( GameResult result ){
        return getPlayDrive( result.getMatch3Score( ) );
    }
    
    
    //-------- Game Quarter --------
    public final String getGame1Quarter( GameResult result ){
        return result.getMatch1Score( ).getDisplayableQuarter( );
    }
    
    
    public final String getGame2Quarter( GameResult result ){
        return result.getMatch2Score( ).getDisplayableQuarter( );
    }

    
    public final String getGame3Quarter( GameResult result ){
        if( result == null || result.getMatch3Score( ) == null ) return EMPTY;
        return result.getMatch3Score( ).getDisplayableQuarter( );
    }
    
    
    //-------- Game Summary --------
    public final String getGame1Summary( GameResult result ){
        return getGameSummary( result.getGame1Quarter( ), result.getMy1Team( ), result.getMatch1Score( ) );
    }
    
    public final String getGame2Summary( GameResult result ){
        return getGameSummary( result.getGame2Quarter( ), result.getMy2Team( ), result.getMatch2Score( ) );
    }
    
    public final String getGame3Summary( GameResult result ){
        if( result == null ) return EMPTY;
        return getGameSummary( result.getGame3Quarter( ), result.getMy3Team( ), result.getMatch3Score( ) );
    }
    
    
    //-------- Game Stats --------
    public final Analytics getGame1Stats( GameResult result ){
        return getGameStat( result.getMy1Team( ), result.getMatch1Score( ) );
    }
    
    public final Analytics getGame2Stats( GameResult result ){
        return getGameStat( result.getMy2Team( ), result.getMatch2Score( ) );
    }
        
    public final Analytics getGame3Stats( GameResult result ){
        return getGameStat( result.getMy3Team( ), result.getMatch3Score( ) );
    }


    protected final String getPlayDrive( LiveScore liveScore ){
        if( liveScore == null ) return EMPTY;
        
        boolean notStarted  = GameState.isNotStarted( liveScore );
        String driveInfo    = (notStarted)? liveScore.getStadium( ) : liveScore.getDriveInfo( ); 
        
        return driveInfo;
    }
    
    
    //Display the summary for the home team.
    protected final String getGameSummary( String fmtQuarter, NFLTeam homeTeam, LiveScore liveScore ){
        
        String gameSummary     = EMPTY;
        
        try {
            
            boolean isPlaying   = GameState.isPlaying( liveScore );
            if( !isPlaying ) return EMPTY;
         
            String gameId       = liveScore.getGameId( );
            MultiKeyMap summMap = analyticsMap.get( gameId );
            if( summMap == null ) return EMPTY;
            
            String homeNickName = homeTeam.getNickName( );
            String rawQuarter   = liveScore.getRawQuarter( );
            gameSummary         = (String) summMap.get( rawQuarter, homeNickName );
            gameSummary         = !isValid(gameSummary) ? EMPTY : gameSummary;       
        
        }catch (Exception e) {
            LOGGER.warn("Exception while looking up game summary", e);
        }
                
        return gameSummary; 
                        
    }
    
    
    
    protected final Analytics getGameStat( NFLTeam homeTeam, LiveScore liveScore ) {
        
        Analytics gameStat      = null;
        
        try {
            
            boolean isPlaying   = GameState.isPlaying( liveScore );
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
    
    
    public final void gameStatusUpdate( Map<NFLTeam, LiveScore> scoreMap ){
        gameThread.gameStatusUpdate( scoreMap );
    }

    
    public final void stop( ){
        executor.shutdown( );
        executor = null;
        LOGGER.info( "Successfully shutdown analytics thread.");        
    }
    
    
    
    private final class DownloadAnalyticsThread implements Runnable{
        
        private final Set<String> currentGameIdSet;
                        
        private final static String GAME_ID_KEY = "GAME_ID";
        private final static String URL_PREFIX  = "http://www.nfl.com/liveupdate/game-center/"+ GAME_ID_KEY + "/" + GAME_ID_KEY + "_gtd.json";
         
        
        public DownloadAnalyticsThread(  ){
            this.currentGameIdSet   = ConcurrentHashMap.newKeySet( );
        }
        
        
        @Override
        public final void run( ){
            downloadAnalyticsData( );
        }

        
        protected final void downloadAnalyticsData( ){
            
            try {
            
                //long startTime  = System.nanoTime( );
                for( String gameId : currentGameIdSet ){
                    downloadAnalyticsData( gameId );
                }
                //long timeTaken  = TimeUnit.MILLISECONDS.convert( System.nanoTime( ) - startTime, TimeUnit.NANOSECONDS);
                //LOGGER.info( "[{}] ms to parse game center data for [{}] games.", timeTaken, currentGameIdSet.size( ) );
            
            }catch (Exception e) {
                LOGGER.error( "Exception while retrieving game center data.", e );
            }
                
        }    
        
        
        protected final void downloadAnalyticsData( String gameId ){
            
            try {
                                
                String fullGameUrl = URL_PREFIX.replaceAll( GAME_ID_KEY, gameId );
                
                String gameDayData  = LiveScoreParser.readUrl( fullGameUrl );
                JsonElement topElem = JSON_PARSER.parse( gameDayData );
                boolean isJsonObj   = topElem.isJsonObject( );
                if( !isJsonObj ) return;
                
                JsonObject jObject  = topElem.getAsJsonObject( );
                boolean isNull      = jObject.isJsonNull( );
                if( isNull ) return;
                
                JsonObject gameObj  = jObject.getAsJsonObject( gameId );
                if( gameObj.isJsonNull( ) ) return;
                
                MultiKeyMap<String, String> summMap = SummaryManager.parse( gameObj );
                if( !summMap.isEmpty( ) ) {
                    analyticsMap.put( gameId, summMap );
                }
                
                Map<String, Analytics> stats = StatsManager.parse( gameObj );
                if( !stats.isEmpty( ) ) {
                    statsMap.put( gameId, stats);
                }               
            
            }catch( EOFException e ){
                LOGGER.warn( "Garbled message from game center for GameId:[{}]", gameId, e);
                
            }catch( Exception e ){
                LOGGER.warn( "FAILED to download game center data for GameId:[{}]", gameId, e);
            }
        
        }
        
        
        protected final void gameStatusUpdate( Map<NFLTeam, LiveScore> scoreMap ){
            
            for( LiveScore score : scoreMap.values( ) ){
                if( score == null ) continue;
                
                String gameId   = score.getGameId( );
                boolean exists  = currentGameIdSet.contains( gameId );
                GameState state = score.getGameState( );
                
                if( !exists && GameState.isPlaying(state) ){
                    currentGameIdSet.add( gameId );
                    LOGGER.info( "Added GameId: {} for analytics.", gameId );
                }
            
                if( exists && (GameState.FINISHED == state) ){
                    boolean removed = currentGameIdSet.remove( gameId );
                    if( removed ){
                        LOGGER.info( "GameId: {} ended, won't poll for analytics.", gameId );
                    }
                }
                
            }
            
        }
   
    }    
    
    public static void main( String[] args ) {
        AnalyticsManager manager = new AnalyticsManager( );
        manager.start( );
    }
    
    
}