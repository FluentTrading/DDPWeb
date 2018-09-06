package com.ddp.nfl.web.analytics.core;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;
import java.util.*;
import java.util.Map.*;

import com.ddp.nfl.web.analytics.drives.*;
import com.ddp.nfl.web.analytics.home.*;
import com.ddp.nfl.web.analytics.summary.*;
import com.google.gson.*;


public final class GameCenterManager{

    private final Map<String, GameCenter> gameCenterMap;
    
    private final static String GAME_ID_KEY = "GAME_ID";
    private final static String URL_PREFIX  = "http://www.nfl.com/liveupdate/game-center/"+ GAME_ID_KEY + "/" + GAME_ID_KEY + "_gtd.json";
    private final static Logger LOGGER      = LoggerFactory.getLogger( "GameCenterManager" );
    
    
    public GameCenterManager(  ){
        this.gameCenterMap  = new HashMap<>( );
    }
    
    
    public final GameCenter create( String gameId ){
        
        GameCenter gCenter      = null;
        
        try {
            
            String urlForGameId = URL_PREFIX.replaceAll( GAME_ID_KEY, gameId );
            LOGGER.info( "Attempting to get GameCenter data for [{}] from [{}]", gameId, urlForGameId );
            
            String jsonGameDay  = readUrl( urlForGameId );
            gCenter             = parseGameDay( gameId, jsonGameDay );
            if( gCenter != null ) {
                gameCenterMap.put( gameId, gCenter );
            }
        
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to create GameCenter for GameId:[{}]", gameId, e );
        }
        
        return gCenter;
    }
    
    
    public final GameCenter get( String gameId ){
        return gameCenterMap.get( gameId );
    }
    
    
    protected final static GameCenter parseGameDay( String gameId, String jsonData ){
        
        GameCenter gameCenter   = null;
        
        try{
            
            JsonElement topElem = JSON_PARSER.parse(jsonData);
            JsonObject jObject  = topElem.getAsJsonObject();
            
            //What does it mean
            int nextUpdate      = jObject.get( "nextupdate" ).getAsInt( );
            JsonObject gameObj  = jObject.getAsJsonObject( gameId );
                        
            TeamInfo home       = HomeFactory.createHome( gameObj );
            TeamInfo away       = HomeFactory.createAway( gameObj );
            SummaryManager summ = ScoreSummaryFactory.create( gameObj );
            DriveManager drives = DriveFactory.create( gameObj );
           
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
            
            gameCenter          = new GameCenter(   gameId, nextUpdate, home, away, drives, 
                                                    summ, weather, media, yl, qtr, note, down,
                                                    togo, redzone, clock, posteam, stadium );
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse NFL game center info", e );
        }
        
        return gameCenter;
        
    }
    
    public final String toPrettyPrint( ) {
        return GSON_INSTANCE.toJson(this);        
    }
    
    @Override
    public final String toString( ){
    
        StringBuilder builder = new StringBuilder( 1024 );
        builder.append( "GameCenterManager:").append(  NEWLINE );
        
        for( Entry<String, GameCenter> entry : gameCenterMap.entrySet( ) ) {
            builder.append( entry.getKey( ) ).append( "===>" );
            builder.append( entry.getValue( ) ).append( NEWLINE );
        }
        
        builder.append( "]" );
        return builder.toString( );
        
    }
    

    //"eid": 2017101200,
    public static void main( String[] args ) {
        String gameId = "2017101200";
        GameCenterManager man = new GameCenterManager( );
        GameCenter center = man.create( gameId );
        
        System.out.println(  center.toString( ) );
    }
}