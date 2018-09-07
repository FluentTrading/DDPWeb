package com.ddp.nfl.web.parser;

import org.slf4j.*;
import java.io.*;
import java.net.*;
import java.time.*;
import java.util.*;
import java.util.Map.*;

import com.ddp.nfl.web.analytics.home.*;
import com.ddp.nfl.web.analytics.summary.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.schedule.*;
import com.google.gson.*;

import static java.time.temporal.ChronoUnit.DAYS;
import static com.ddp.nfl.web.util.DDPUtil.*;


public final class LiteScoreParser{
    
    private final static String GAME_ID_KEY = "GAME_ID";
    private final static String URL_PREFIX  = "http://www.nfl.com/liveupdate/game-center/"+ GAME_ID_KEY + "/" + GAME_ID_KEY + "_gtd.json";
    private final static Logger LOGGER      = LoggerFactory.getLogger( "LiveScoreParser" );
    
       

    public final static Map<NFLTeam, LiveScore> parseLiveScore( Map<String, Schedule> gameScheduleMap ){
        
        Map<NFLTeam, LiveScore> scores = new HashMap<>();
        
        for( Entry<String, Schedule> entry: gameScheduleMap.entrySet( ) ){
            
            Schedule schedule   = entry.getValue( );
            boolean isPlaying   = isGameBeingPlayedNow( schedule );
            LiveScore liveScore = null;
            
            if( isPlaying ) {
                liveScore = parseLiveScore( entry.getKey( ), schedule ); 
            }else {
                liveScore = miniParse( entry.getKey( ), schedule );
            } 
            
            if( liveScore != null ) {
                LOGGER.info( "{}", liveScore );
                scores.put( liveScore.getHomeTeam( ), liveScore );
                scores.put( liveScore.getAwayTeam( ), liveScore );
            }
            
        }
    
        return scores;
        
    }
    

    //TODO: improve by checking time not just date
    protected final static boolean isGameBeingPlayedNow( Schedule schedule ) {
        LocalDate gameTime = schedule.getGameDate( );
        boolean gameToday  =  DAYS.between(LocalDate.now( ), gameTime) == ZERO;
        
        return gameToday;        
    }


    public final static LiveScore parseLiveScore( String gameId, Schedule schedule ){
        
        LiveScore liveScore     = null;
        
        try {
                
            String gameDataUrl  = URL_PREFIX.replaceAll( GAME_ID_KEY, gameId );
            LOGGER.info("Parsing live score from {}", gameDataUrl);
            
            String gameDayData  = readUrl( gameDataUrl );
            liveScore           = parseLiveScore( gameId, schedule, gameDayData );
                                    
        }catch( FileNotFoundException fe ) {
            //This will warn if the url has no data (if we run this before data has been published by NFL)
            //LOGGER.warn( "Game center data for GameId:[{}] is not available!", schedule.getGameId( ));
                
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to download game center data for GameId:[{}]", gameId, e);
        }
    
        return liveScore;

    }
    
    
    protected final static LiveScore parseLiveScore( String gameId, Schedule schedule, String jsonData ){
        
        LiveScore liveScore     = null;
        
        try{
            
            JsonElement topElem = JSON_PARSER.parse(jsonData);
            JsonObject jObject  = topElem.getAsJsonObject();
            
            //What does it mean
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
            String stadium      = safeParse( gameObj, "stadium" );
            
            int homeScore        = home.getTotalScore( );
            int awayScore        = away.getTotalScore( );
            
            boolean notStarted   = !isValid(rawQuarterStr) || "p".equalsIgnoreCase( rawQuarterStr );
            boolean isFinished   = rawQuarterStr.equalsIgnoreCase("F") || rawQuarterStr.equalsIgnoreCase("FO");
            boolean isPlaying    = !notStarted && !isFinished;
            
            liveScore            = new LiveScore( gameId, schedule, notStarted, isPlaying, isFinished, homeScore, 
                                                  awayScore, teamPossession, timeRemaining, isRedzone, rawQuarterStr,
                                                  yl, togo, down, note, stadium, summ);
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse NFL game center info", e );
        }
        
        return liveScore;
    }
    
    
    protected final static LiveScore miniParse( String gameId, Schedule schedule ){
    
        LiveScore   liveScore    = new LiveScore( gameId, schedule, true, false, false, 
                                                  ZERO, ZERO, 
                                                  EMPTY, EMPTY, false, EMPTY,
                                                  EMPTY, ZERO, ZERO, EMPTY, EMPTY, null);
            
        return liveScore;
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

