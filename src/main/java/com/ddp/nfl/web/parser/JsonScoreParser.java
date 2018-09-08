package com.ddp.nfl.web.parser;

import org.slf4j.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.*;
import java.util.concurrent.*;

import com.ddp.nfl.web.analytics.home.*;
import com.ddp.nfl.web.analytics.summary.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.schedule.*;
import com.google.gson.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class JsonScoreParser{
    
    private final Map<String, Schedule> scheduleMap;
        
    private final static String JSON_SCORE_URL  = "http://www.nfl.com/liveupdate/scores/scores.json";
    private final static Logger LOGGER          = LoggerFactory.getLogger( "JsonScoreParser" );
    
       
    public JsonScoreParser( ScheduleManager schManager ){
        this.scheduleMap    = schManager.getSchedules( );
    }
    
    
    public final Map<NFLTeam, LiveScore> parseLiveScore( ){
               
        Map<NFLTeam, LiveScore> scores = new HashMap<>();
        
        try {
        
            long startTime          = System.nanoTime( );
            String jsonGameData     = readUrl( JSON_SCORE_URL );
            JsonElement topElement  = JSON_PARSER.parse( jsonGameData );
        
            for( Entry<String, JsonElement> entry : topElement.getAsJsonObject( ).entrySet( ) ) {
        
                String gameId       = entry.getKey( );
                JsonElement element = entry.getValue( );
                
                LiveScore liveScore = parseLiveScore( gameId, element );
                LOGGER.info( "{}", liveScore );
                
                if( liveScore != null ){
                    scores.put( liveScore.getHomeTeam( ), liveScore );
                    scores.put( liveScore.getAwayTeam( ), liveScore );
                }
                            
            }
            
            long timeTaken          = System.nanoTime( ) - startTime;
            LOGGER.warn( "Time taken to parse live score [{}] ms", TimeUnit.MILLISECONDS.convert( timeTaken, TimeUnit.NANOSECONDS ));
            
            
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to download game center data from:[{}]", JSON_SCORE_URL, e);
        }
    
        return scores;
        
    }



    protected final LiveScore parseLiveScore( String gameId, JsonElement element ){
        
        Schedule schedule   = scheduleMap.get( gameId );

        JsonObject gameObj  = element.getAsJsonObject( );
        TeamInfo home       = create( true, gameObj );
        TeamInfo away       = create( false, gameObj );
        SummaryManager summ = null; //ScoreSummaryFactory.create( gameObj );
           
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
            
        LiveScore liveScore  = new LiveScore( gameId, schedule, notStarted, isPlaying, isFinished, homeScore, 
                                                  awayScore, teamPossession, timeRemaining, isRedzone, rawQuarterStr,
                                                  yl, togo, down, note, summ);

        return liveScore;

    }
    
       
    
   
    protected final static boolean parseGameNotStarted( String rawQuarterStr ){
        return EMPTY.equals( rawQuarterStr ) || "p".equalsIgnoreCase( rawQuarterStr );
    }
    
    
    protected final static boolean parseGameFinished( String rawQuarterStr ){
        return "F".equals( rawQuarterStr ) || "FO".equals( rawQuarterStr )
                || "Final".equals( rawQuarterStr );
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
    
    
    protected final static TeamInfo create( boolean isHome, JsonObject jObject ){
        
        TeamInfo team           = null; 
        
        try{
                        
            String teamName     = safeParse( jObject, "abbr" );
            teamName            = isValid( teamName ) ? teamName.toLowerCase( ) : EMPTY;
            int to              = safeParseInt( jObject, "to" );
            Map<Quarter, Integer> sMap = HomeFactory.parseScores( isHome, jObject );
            team                = new TeamInfo( isHome, teamName, to, sMap, null );
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse TeamAnalytics for [{}]", jObject, e );
        }
                
        return team;
    }
       
    
    
    public final static void main( String[] args ) {
        JsonScoreParser parser = new JsonScoreParser(  null  );
        parser.parseLiveScore( );
    }
    
    
}

