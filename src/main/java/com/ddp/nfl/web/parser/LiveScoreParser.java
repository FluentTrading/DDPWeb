package com.ddp.nfl.web.parser;

import org.slf4j.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.*;

import com.ddp.nfl.web.analytics.home.*;
import com.ddp.nfl.web.analytics.summary.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.schedule.*;
import com.google.gson.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class LiveScoreParser{
    
    private final Map<String, Long> downloadErrMap;
    private final Map<String, Schedule> scheduleMap;
    private final Map<String, LiveScore> gameOverMap;
        
    private final static String GAME_ID_KEY     = "GAME_ID";
    private final static long CHECK_TIME_MILLIS = 1 * 60 * 60 * 1000;
    private final static String URL_PREFIX      = "http://www.nfl.com/liveupdate/game-center/"+ GAME_ID_KEY + "/" + GAME_ID_KEY + "_gtd.json";
    private final static Logger LOGGER          = LoggerFactory.getLogger( "LiveScoreParser" );
    
       
    public LiveScoreParser( ScheduleManager schManager ){
        this.scheduleMap    = schManager.getSchedules( );
        this.downloadErrMap = new HashMap<>();
        this.gameOverMap    = new HashMap<>();
    }
    
    
    public final Map<NFLTeam, LiveScore> parseLiveScore( ){
        return parseLiveScore( scheduleMap );
    }
    
    
    public final Map<NFLTeam, LiveScore> parseLiveScore( Map<String, Schedule> scheduleMap ){
        
        Map<NFLTeam, LiveScore> scores = new HashMap<>();
        
        for( Entry<String, Schedule> entry: scheduleMap.entrySet( ) ){
            
            String gameId       = entry.getKey( );
            Schedule schedule   = entry.getValue( );
            LiveScore liveScore = parseLiveScore( gameId, schedule );
            
            //Game may be not publishing data as it is not live
            //But we still need to show it on the app.
            if( liveScore == null ){
                liveScore = createFutureLiveScore( entry.getKey( ), schedule );
            } 
            
            //Muting for now
            //LOGGER.info( "{}", liveScore );
            scores.put( liveScore.getHomeTeam( ), liveScore );
            scores.put( liveScore.getAwayTeam( ), liveScore );
                        
        }
    
        return scores;
        
    }



    protected final LiveScore parseLiveScore( String gameId, Schedule schedule ){
        
        LiveScore liveScore     = null;
        
        try {
                
            //If the game has been done, no need to parse it again as nothing will change.
            LiveScore gameDone  = gameOverMap.get( gameId);
            if( gameDone != null ) return gameDone;
            
            
            //If the game didn't have published data before, we try to download every hour
            String gameDataUrl  = URL_PREFIX.replaceAll( GAME_ID_KEY, gameId );
            boolean shouldCheck = shouldDownload( gameId, gameDataUrl );
            if( !shouldCheck ) return null;
            
            //TODO: Muting for now
            //LOGGER.info("Parsing live score from {}", gameDataUrl);
            String jsonGameData = readUrl( gameDataUrl );
            liveScore           = parseLiveScore( gameId, schedule, jsonGameData );
            boolean isFinished  = (liveScore != null && liveScore.isFinished( ));
            if( isFinished ) {
                gameOverMap.put( gameId, liveScore );
            }
                                    
        }catch( FileNotFoundException fe ) {
            //This will warn if the url has no data (if we run this before data has been published by NFL)
            //LOGGER.warn( "Game center data for GameId:[{}] is not available!", schedule.getGameId( ));
            populateErrorMap( gameId );
                
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to download game center data for GameId:[{}]", gameId, e);
        }
    
        return liveScore;

    }
    
       
    
    protected final LiveScore parseLiveScore( String gameId, Schedule schedule, String jsonData ){
        
        LiveScore liveScore     = null;
        
        try{
            
            JsonElement topElem = JSON_PARSER.parse(jsonData);
            JsonObject jObject  = topElem.getAsJsonObject();
            
            JsonObject gameObj  = jObject.getAsJsonObject( gameId );
            TeamInfo home       = HomeFactory.createHome( gameObj );
            TeamInfo away       = HomeFactory.createAway( gameObj );
            SummaryManager summ = ScoreSummaryFactory.create( gameObj );
            
            String yl           = safeParse( gameObj, "yl" );
            String rawQuarterStr= safeParse( gameObj, "qtr" );
            String note         = safeParse( gameObj, "note" );
            int down            = safeParseInt( gameObj, "down" );
            int togo            = safeParseInt( gameObj, "togo" );
            boolean isRedzone   = safeParseBoolean( gameObj, "redzone");
            String timeRemaining= safeParse( gameObj, "clock" );;
            String teamPossession= safeParse( gameObj, "posteam" );
                        
            int homeScore        = home.getTotalScore( );
            int awayScore        = away.getTotalScore( );
            
            boolean notStarted   = parseGameNotStarted( rawQuarterStr );
            boolean isFinished   = parseGameFinished( rawQuarterStr );
            boolean isPlaying    = !notStarted && !isFinished;
            
            liveScore            = new LiveScore( gameId, schedule, notStarted, isPlaying, isFinished, homeScore, 
                                                  awayScore, teamPossession, timeRemaining, isRedzone, rawQuarterStr,
                                                  yl, togo, down, note, summ);
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse NFL game center info", e );
        }
        
        return liveScore;
    }
    
    
    protected final static LiveScore createFutureLiveScore( String gameId, Schedule schedule ){
    
        LiveScore   liveScore    = new LiveScore( gameId, schedule, true, false, false, 
                                                  ZERO, ZERO, 
                                                  EMPTY, EMPTY, false, EMPTY,
                                                  EMPTY, ZERO, ZERO, EMPTY, null);
            
        return liveScore;
    }
    

    protected final static boolean parseGameNotStarted( String rawQuarterStr ){
        return EMPTY.equals( rawQuarterStr ) || "p".equalsIgnoreCase( rawQuarterStr );
    }
    
    
    protected final static boolean parseGameFinished( String rawQuarterStr ){
        return "F".equals( rawQuarterStr ) || "FO".equals( rawQuarterStr )
                || "Final".equals( rawQuarterStr );
    }
    
 

    protected final boolean shouldDownload( String gameId, String gameDataUrl ){
        
        Long errorTime  = downloadErrMap.get( gameId );
        if( errorTime == null ) return true;
        
        //GameId in the error map, has it been more than 1 hour.
        long elapsedTime = System.currentTimeMillis( ) - errorTime.longValue( );
        boolean toDownload= elapsedTime > CHECK_TIME_MILLIS;
        
        //We have waited for more than CHECK_TIME_MILLIS, will try again.
        if( toDownload ) {
            downloadErrMap.remove( gameId );
        }
        
        return toDownload;
        
    }
    
    
    //If we got a FileNotFoundException, we will check that URL every hour and not evry 45 seconds.
    protected final void populateErrorMap( String gameId ){
        downloadErrMap.put( gameId, System.currentTimeMillis( ));
    }
    
          
    protected final static String readUrl( String urlString ) throws Exception{
        
        BufferedReader reader   = null;
        StringBuilder buffer    = new StringBuilder( 2048 );
        
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

