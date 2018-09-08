package com.ddp.nfl.web.analytics.core;

import org.slf4j.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import com.ddp.nfl.web.analytics.home.*;
import com.ddp.nfl.web.analytics.summary.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.parser.*;
import com.ddp.nfl.web.pickem.*;
import com.google.gson.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class GameAnalyticsManager{

    private ScheduledExecutorService executor;
    
    private final GameAnalyticsHtml gameHtml;
    private final GameAnalyticsThread gameThread;
    private final Map<String, String> analyticsMap;

    private final static int INTERVAL   = 45;
    private final static Logger LOGGER  = LoggerFactory.getLogger( "GameAnalyticsManager" );
    
          
    public GameAnalyticsManager( Map<String, LiveScore> map, PickManager pickManager ){
        this( false, map, pickManager );
    }
    
    
    public GameAnalyticsManager( boolean parseDrives, Map<String, LiveScore> liveScoreMap, PickManager pickManager ){
        this.analyticsMap= new ConcurrentHashMap<>( );
        this.gameHtml   = new GameAnalyticsHtml( pickManager );
        this.executor   = Executors.newSingleThreadScheduledExecutor( );
        this.gameThread = new GameAnalyticsThread( parseDrives, liveScoreMap );
        
    }

    
    public final void start( ){
        //TODO: STart this after we enable analytics
        // executor.scheduleAtFixedRate( gameThread, ZERO, INTERVAL, TimeUnit.SECONDS);
        LOGGER.info( "Successfully created analytics thread to run everuy {} seconds.", INTERVAL );
    }
    
    
    public final String getGameAnalytics( String gameId ){
        
        if( !isValid(gameId) ) {
            LOGGER.warn( "getGameAnalytics() was called without setting gameId!");
            return gameHtml.toErrorHtml( );
        }
            
        //TODO: Only for testing
        String htmlAnalytics    = analyticsMap.get( "2017101200" );
            
        //String htmlAnalytics= analyticsMap.get( gameId );
        htmlAnalytics           = !isValid(htmlAnalytics) ? gameHtml.toErrorHtml( ) : htmlAnalytics;
        
        return htmlAnalytics;
                
    }
    
    
    public final void stop( ){
        executor.shutdown( );
        executor = null;
        LOGGER.info( "Successfully shutdown analytics thread.");        
    }
    
    
    private final class GameAnalyticsThread implements Runnable{
        
        private final boolean parseDrives;
        private final Collection<LiveScore> liveScores;
                
        private final static String GAME_ID_KEY = "GAME_ID";
        private final static String URL_PREFIX  = "http://www.nfl.com/liveupdate/game-center/"+ GAME_ID_KEY + "/" + GAME_ID_KEY + "_gtd.json";
                
       
        public GameAnalyticsThread( boolean parseDrives, Map<String, LiveScore> liveScoreMap ){
            this.parseDrives    = parseDrives;
            this.liveScores     = liveScoreMap.values( );
        }
        
        
        @Override
        public final void run( ){
            downloadAnalyticsData( );
        }

        
        protected final void downloadAnalyticsData( ){
            
            try {
            
                long startTime  = System.nanoTime( );
                    
                for( LiveScore liveScore : liveScores ){
                    downloadAnalyticsData( liveScore );
                }       
                        
                long timeTaken  = TimeUnit.MILLISECONDS.convert( System.nanoTime( ) - startTime, TimeUnit.NANOSECONDS);
                LOGGER.info( "[{}] ms to parse game center data for [{}] games.", timeTaken, liveScores.size( ) );
            
            }catch (Exception e) {
                LOGGER.error( "Exception while retrieving game center data.", e );
            }
                
        }    
        
        
        protected final void downloadAnalyticsData( LiveScore liveScore ){
            
            try {
                
                //TODO: 
                //Remove this after testing
                String gameId       = "2017101200";
                //String gameId     = liveScore.getGameId( );
                
                //Since we get the livescore map at start time, we can't rely on this check below.
                //This wouldn't catch the condition where a game was not started when we started the web app.
                //boolean toDownload  = liveScore.isPlaying( ) || liveScore.isFinished( );
                //if( !toDownload ) return;
                
                String urlForGameId = URL_PREFIX.replaceAll( GAME_ID_KEY, gameId );
                String gameDayData  = readUrl( urlForGameId );
                GameAnalytics gameCenter= parseGameDay( gameId, gameDayData );
                if( gameCenter == null ) return;
                
                String analyticsHtml= gameHtml.toHtml( gameCenter );
                analyticsHtml       = !isValid( analyticsHtml ) ? gameHtml.toErrorHtml( ) : analyticsHtml;
                
                analyticsMap.put( gameId, analyticsHtml );
                LOGGER.info( "Stored html analytics data for [{}] [{}] vs [{}]", gameId, 
                        liveScore.getHomeTeam( ).getLowerCaseName( ), liveScore.getAwayTeam( ).getLowerCaseName( ) );
                        
            }catch( FileNotFoundException fe ) {
                //This will warn if the url has no data (if we run this before data has been published by NFL)
                //LOGGER.warn( "Game center data for GameId:[{}] is not available!", schedule.getGameId( ));
                
            }catch( Exception e ) {
                LOGGER.warn( "FAILED to download game center data for GameId:[{}]", liveScore.getGameId( ), e);
            }
        
        }


        protected final GameAnalytics parseGameDay( String gameId, String jsonData ){
            
            GameAnalytics gameCenter   = null;
            
            try{
                
                JsonElement topElem = JSON_PARSER.parse(jsonData);
                JsonObject jObject  = topElem.getAsJsonObject();
                
                //What does it mean
                int nextUpdate      = jObject.get( "nextupdate" ).getAsInt( );
                JsonObject gameObj  = jObject.getAsJsonObject( gameId );
                            
                TeamInfo home       = HomeFactory.createHome( gameObj );
                TeamInfo away       = HomeFactory.createAway( gameObj );
                SummaryManager summ = ScoreSummaryFactory.create( gameObj );
                               
                String weather      = safeParse( gameObj, "weather" );
                String media        = safeParse( gameObj, "media" );
                String yl           = safeParse( gameObj, "yl" );
                String qtr          = safeParse( gameObj, "qtr" );
                String note         = safeParse( gameObj, "note" );
                
                int down            = safeParseInt( gameObj, "down" );
                int togo            = safeParseInt( gameObj, "togo" );
                boolean redzone     = safeParseBoolean( gameObj, "redzone");
                
                String clock        = safeParse( gameObj, "clock" );;
                String posteam      = safeParse( gameObj, "posteam" );
                String stadium      = safeParse( gameObj, "stadium" );
                
                gameCenter          = new GameAnalytics(   gameId, nextUpdate, home, away, 
                                                        summ, weather, media, yl, qtr, note, down,
                                                        togo, redzone, clock, posteam, stadium );
                
            }catch(Exception e ){
                LOGGER.warn( "FAILED to parse NFL game center info", e );
            }
            
            return gameCenter;
        }
        
        
        protected final String readUrl( String urlString ) throws Exception{
            
            BufferedReader reader   = null;
            StringBuilder buffer    = new StringBuilder( 1024 );
            
            try{
                
                URL url             = new URL(urlString);
                reader              = new BufferedReader(new InputStreamReader(url.openStream()));
                
                int read;
                char[] chars        = new char[1024];
                while ((read = reader.read(chars)) != -1) {
                    buffer.append(chars, 0, read);
                }
                
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
            
            return buffer.toString();
            
        }
        
    }

       
}