package com.ddp.nfl.web.parser;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;
import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.schedule.*;
import com.ddp.nfl.web.util.*;
import com.google.gson.*;


public final class NFLDataParser{
    
    private final static Logger LOGGER      = LoggerFactory.getLogger( "NFLDataParser" );
    
    
    public final static Map<NFLTeam, NFLMatchInfo> parseFromURL( String nflDataUrl, ScheduleManager schMan, DBService service ){
        
        Map<NFLTeam, NFLMatchInfo> map  = Collections.emptyMap( );
        
        try{
            
            String jsonData = readUrl(nflDataUrl );
            map             = parseGameInfo( jsonData, schMan, service );
                
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse NFL data from URL: [{}]", nflDataUrl, e );
        }
        
        return map;
        
    }

    
    public final static Map<NFLTeam, NFLMatchInfo> parseGameInfo( String jsonData, ScheduleManager schMan, DBService service ){
        
        Map<NFLTeam, NFLMatchInfo> map  = new HashMap<>( );
        
        try{
            
            JsonElement elem            = JSON_PARSER.parse( jsonData );
            JsonObject jObject          = elem.getAsJsonObject();
            JsonArray games             = jObject.get("gms").getAsJsonArray();
        
            for( JsonElement gElement : games ){
                
                JsonObject gObject  = gElement.getAsJsonObject();
                NFLMatchInfo game   = parseGameScore( gObject, schMan, service );
                
                map.put(game.getHomeTeam( ), game );
                map.put(game.getAwayTeam( ), game );
            }
                    
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse NFL game info", e );
        }
        
        return map;
        
    }
    
    
    public final static NFLMatchInfo parseGameScore( JsonElement gElement, ScheduleManager schMan, DBService service ){
        
        NFLMatchInfo gameScore  = null;
                
        try{
            
            JsonObject gObject  = gElement.getAsJsonObject();
            
            String gameId       = DDPUtil.safeParse( gObject, "gsis" );
            NFLSchedule schedule= schMan.get( gameId );
            if( schedule == null ) {
                LOGGER.warn("FAILED to find schedule for GameId: ", gameId );
                return null;                
            }
            
            String homeTeamStr  = DDPUtil.safeParse( gObject, "hnn" );
            NFLTeam homeTeam    = service.getTeam(  homeTeamStr );
            int homeScore       = DDPUtil.safeParseInt( gObject, "hs" );
            
            String awayTeamStr  = DDPUtil.safeParse( gObject, "vnn" );
            NFLTeam awayTeam    = service.getTeam( awayTeamStr );
            int awayScore       = DDPUtil.safeParseInt( gObject, "vs" );
            
            String quarterStr   = DDPUtil.safeParse( gObject, "q" );
            boolean isFinished  = quarterStr.equalsIgnoreCase("F") || quarterStr.equalsIgnoreCase("FO");
            boolean isPlaying   = quarterStr.equalsIgnoreCase("P") || quarterStr.equalsIgnoreCase("H") ||  quarterStr.equalsIgnoreCase("F") ;
            String quarter      = parseQuarter( isFinished, quarterStr, schedule );
            
            gameScore           = new NFLMatchInfo( isPlaying, isFinished, homeTeam, homeScore, awayTeam, awayScore, quarter, schedule );
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse NFLGameScore for [{}]", gElement, e );           
        }
                
        return gameScore;
        
    }
    
               
    
    protected final static String parseQuarter( boolean isFinished, String quarterStr, NFLSchedule schedule ) {
                
        if( isFinished ){
            return "Final";
        }
            
        if( quarterStr.equalsIgnoreCase("P") ){
            return schedule.getGameTime( );
        }
        
        if( quarterStr.equalsIgnoreCase("H") ){
            return "Half";
        }
                
        if( quarterStr.equalsIgnoreCase("1")){
            return quarterStr + " qt";
        
        }else if( quarterStr.equalsIgnoreCase("2")){
            return quarterStr + " qt";
                                
        }else if( quarterStr.equalsIgnoreCase("3")){
            return quarterStr + " qt";
        
        }else if( quarterStr.equalsIgnoreCase("4")){            
            return quarterStr + " qt";
        
        }else{
            return "OT";
        }
                
    }
    
    
    
    public final static int parseWeekNumber( String nflDataUrl ){
        
        int weekNumber          = -1;
        
        try{
            
            String jsonData     = readUrl( nflDataUrl );
            JsonElement elem    = JSON_PARSER.parse(jsonData);
            JsonObject jObject  = elem.getAsJsonObject();
            weekNumber          = jObject.get( "w" ).getAsInt( );
        
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse NFL data from [{}] to determine week number", nflDataUrl, e );
        }
        
        return weekNumber;
    
    }
    
    
}
