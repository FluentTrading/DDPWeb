package com.ddp.nfl.web.parser;

import org.slf4j.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.*;

import com.ddp.nfl.web.analytics.core.*;
import com.ddp.nfl.web.analytics.home.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.schedule.*;
import com.google.gson.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class LiveScoreParser{
    
    private final Map<String, Schedule> scheduleMap;
        
    private final static Logger LOGGER = LoggerFactory.getLogger( "LiveScoreParser" );
    
       
    public LiveScoreParser( ScheduleManager schManager ){
        this.scheduleMap    = schManager.getSchedules( );
    }
    

    public final Map<NFLTeam, LiveScore> parseLiveScore( ){
               
        Map<NFLTeam, LiveScore> scores = new HashMap<>();
        
        try {
        
            //long startTime          = System.nanoTime( );
            String jsonGameData     = readUrl( JSON_MINI_SCORE_URL );
            JsonElement topElement  = JSON_PARSER.parse( jsonGameData );
        
            for( Entry<String, JsonElement> entry : topElement.getAsJsonObject( ).entrySet( ) ) {
        
                String gameId       = entry.getKey( );
                JsonElement element = entry.getValue( );
                
                LiveScore liveScore = parseLiveScore( gameId, element );
                //LOGGER.info( "{}", liveScore );
                
                if( liveScore != null ){
                    scores.put( liveScore.getHomeTeam( ), liveScore );
                    scores.put( liveScore.getAwayTeam( ), liveScore );
                }
                            
            }
            
            //long timeTaken          = System.nanoTime( ) - startTime;
            //LOGGER.info( "Time taken to parse live score [{}] ms", TimeUnit.MILLISECONDS.convert( timeTaken, TimeUnit.NANOSECONDS ));
            
            
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to parse live score from:[{}]", JSON_MINI_SCORE_URL, e);
        }
    
        return scores;
        
    }



    protected final LiveScore parseLiveScore( String gameId, JsonElement element ){
        
        Schedule schedule   = scheduleMap.get( gameId );

        JsonObject gameObj  = element.getAsJsonObject( );
        TeamInfo home       = createTeamInfo( true, gameObj );
        TeamInfo away       = createTeamInfo( false, gameObj );
        SummaryManager summ = null;
        
        int bp              = safeParseInt( gameObj, "bp" );
        String yl           = safeParse( gameObj, "yl" );
        String rawQuarterStr= safeParse( gameObj, "qtr" );
        String note         = safeParse( gameObj, "note" );
        String stadium      = safeParse( gameObj, "stadium" );
        int down            = safeParseInt( gameObj, "down" );
        int togo            = safeParseInt( gameObj, "togo" );
        boolean isRedzone   = safeParseBoolean( gameObj, "redzone");
        String timeRemaining= safeParse( gameObj, "clock" );;
        String teamPossession= safeParse( gameObj, "posteam" );
                        
        int homeScore        = home.getTotalScore( );
        int awayScore        = away.getTotalScore( );
            
        GameState gameState  = GameState.parseState( rawQuarterStr );
        LiveScore liveScore  = new LiveScore( gameId, schedule, gameState, homeScore, 
                                                  awayScore, teamPossession, timeRemaining, isRedzone, rawQuarterStr,
                                                  yl, togo, down, bp, stadium, note, summ);

        return liveScore;

    }
   
    
          
    public final static String readUrl( String urlString ) throws Exception{
        
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
    
    
    protected final static TeamInfo createTeamInfo( boolean isHome, JsonObject jObject ){
        
        TeamInfo team           = null; 
        
        try{
                        
            String teamName     = safeParse( jObject, "abbr" );
            teamName            = isValid( teamName ) ? teamName.toLowerCase( ) : EMPTY;
            int to              = safeParseInt( jObject, "to" );
            Map<Quarter, Integer> sMap = HomeFactory.parseScores( isHome, jObject );
            team                = new TeamInfo( isHome, teamName, to, sMap, null );
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse TeamInfo for [{}]", jObject, e );
        }
                
        return team;
    }
       
    
    
    public final static void main( String[] args ) {
        LiveScoreParser parser = new LiveScoreParser(  null  );
        parser.parseLiveScore( );
    }
    
    
}

