package com.ddp.nfl.web.analytics.core;

import org.apache.commons.collections4.map.*;
import org.slf4j.*;
import java.util.*;
import java.util.concurrent.*;

import com.ddp.nfl.web.analytics.summary.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.match.*;
import com.ddp.nfl.web.parser.*;
import com.google.gson.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class AnalyticsManager{

    private ScheduledExecutorService executor;
    
    private final Set<String> gameIdSet;
    private final DownloadAnalyticsThread gameThread;
    private final Map<String, MultiKeyMap<String, String>> analyticsMap;

    private final static int INTERVAL       = TWO;
    private final static Logger LOGGER      = LoggerFactory.getLogger( "AnalyticsManager" );
    
    
    public AnalyticsManager(  ){
        this.gameIdSet      = ConcurrentHashMap.newKeySet( );
        this.analyticsMap   = new ConcurrentHashMap<>( );
        this.executor       = Executors.newSingleThreadScheduledExecutor( );
        this.gameThread     = new DownloadAnalyticsThread( gameIdSet );
        
    }

    
    public final void start( ){
        executor.scheduleAtFixedRate( gameThread, ZERO, INTERVAL, TimeUnit.MINUTES);
        LOGGER.info( "Successfully created analytics thread to run everuy {} minutes.", INTERVAL );
    }
    
     
    public final String getGameOneSummary( GameResult result ){
        return getGameSummary( result.getGame1Quarter( ), result.getMy1Team( ), result.getMatch1Score( ) );
    }
    
    
    public final String getGameTwoSummary( GameResult result ){
        return getGameSummary( result.getGame2Quarter( ), result.getMy2Team( ), result.getMatch2Score( ) );
    }

    
    public final String getGameThreeSummary( GameResult result ){
        return getGameSummary( result.getGame3Quarter( ), result.getMy3Team( ), result.getMatch3Score( ) );
    }
    

    public final String getGameOneDrive( GameResult result ){
        return result.getMatch1Score( ).getDriveInfo( );
    }
    
    
    public final String getGameTwoDrive( GameResult result ){
        return result.getMatch2Score( ).getDriveInfo( );
    }

    
    public final String getGameThreeDrive( GameResult result ){
        return result.getMatch3Score( ).getDriveInfo( );
    }
    
    
    public final String getGameOneQuarter( GameResult result ){
        return result.getMatch1Score( ).getFormattedQuarter( );
    }
    
    
    public final String getGameTwoQuarter( GameResult result ){
        return result.getMatch2Score( ).getFormattedQuarter( );
    }

    
    public final String getGameThreeQuarter( GameResult result ){
        return result.getMatch3Score( ).getFormattedQuarter( );
    }
    
    
    //Display the summary for the home team.
    public final String getGameSummary( String fmtQuarter, NFLTeam homeTeam, LiveScore liveScore ){
        
        String gameSummary     = EMPTY;
        
        try {
            
            boolean isPlaying   = (GameState.PLAYING == liveScore.getGameState( ));
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
    
     
    public final void gameStatusUpdate( Map<NFLTeam, LiveScore> scoreMap ){
        
        for( LiveScore score : scoreMap.values( ) ){
            if( score == null ) continue;
            
            String gameId   = score.getGameId( );
            boolean exists  = gameIdSet.contains( gameId );
            GameState state = score.getGameState( );
            
            if( !exists && (GameState.PLAYING == state) ){
                gameIdSet.add( gameId );
                //LOGGER.info( "Added GameId: {} for analytics.", gameId );
            }
        
            if( exists && (GameState.FINISHED == state) ){
                boolean removed = gameIdSet.remove( gameId );
                if( removed ){
                    //LOGGER.info( "GameId: {} ended, won't poll for analytics.", gameId );
                }
            }
        
        }
        
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
                
       
        public DownloadAnalyticsThread( Set<String> currentGameIdSet ){
            this.currentGameIdSet  = currentGameIdSet;
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
                downloadAnalyticsData( gameId, fullGameUrl );
            
            }catch( Exception e ){
                LOGGER.warn( "FAILED to download game center data for GameId:[{}]", gameId, e);
            }
        
        }
    
        
        
        protected final void downloadAnalyticsData( String gameId, String fullGameUrl ) throws Exception{
            
            String gameDayData  = LiveScoreParser.readUrl( fullGameUrl );
            JsonElement topElem = JSON_PARSER.parse( gameDayData );
            boolean isJsonObj   = topElem.isJsonObject( );
            if( !isJsonObj ) return;
            
            JsonObject jObject  = topElem.getAsJsonObject( );
            boolean isNull      = jObject.isJsonNull( );
            if( isNull ) return;
            
            JsonObject gameObj  = jObject.getAsJsonObject( gameId );
            MultiKeyMap<String, String> summMap = SummaryManager.parseScoreSummary( gameObj );
            if( !summMap.isEmpty( ) ) {
                analyticsMap.put( gameId, summMap );
            }
            
        }
        
   
    }    
            
    
    public final static void main( String[] args ) throws Exception{
        
        AnalyticsManager test = new AnalyticsManager( );
        
        long startTimeNanos   = System.nanoTime( );
        test.gameThread.downloadAnalyticsData( "2018090908" ); 
        
        MultiKeyMap summMap = test.analyticsMap.get( "2018090908" );
        
        System.out.println( summMap );
        
        long timeTakenNanos   = System.nanoTime( ) - startTimeNanos;
        LOGGER.info("Time Taken to prepare game summary [{} millis]", TimeUnit.MILLISECONDS.convert(timeTakenNanos, TimeUnit.NANOSECONDS) );
     
    }
    
    
}