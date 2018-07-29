package com.ddp.nfl.web.analytics.home;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;
import java.util.*;
import java.util.Map.*;
import com.google.gson.*;

public final class HomeFactory{
    
    private final static String HOME_KEY    = "home";
    private final static String AWAY_KEY    = "away";
    private final static Logger LOGGER      = LoggerFactory.getLogger( "HomeFactory" );
    
    
    public final static TeamInfo createHome( JsonObject gameObj ){
        return create( true, gameObj.getAsJsonObject(HOME_KEY) );
    }
        
    public final static TeamInfo createAway( JsonObject gameObj ){
        return create( false, gameObj.getAsJsonObject(AWAY_KEY) );
    }
    
    
    protected final static TeamInfo create( boolean isHome, JsonObject jObject ){
        
        TeamInfo team      = null; 
        
        try{
                        
            String abbr         = safeParse( jObject, "abbr" );
            int to              = safeParseInt( jObject, "to" );
            Map<String, Integer> sMap = parseScores( jObject );
            String players      = safeParse( jObject, "players" );
            StatsManager stats  = parseStats( jObject );
            
            team                = new TeamInfo( isHome, abbr, to, stats, sMap, players );
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse TeamAnalytics for [{}]", jObject, e );
        }
                
        return team;
    }

    
    protected final static Map<String, Integer> parseScores( JsonObject jObject ){
        Map<String, Integer> map = new HashMap<>( );
        
        try{
            
            JsonObject scoreObj = jObject.getAsJsonObject( "score" );
            for( Entry<String, JsonElement> scoreEntry : scoreObj.entrySet( ) ) {
                map.put( scoreEntry.getKey( ), scoreEntry.getValue( ).getAsInt( ) );
            }
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Scores", e );
        }
          
        return map;
    }

    protected final static StatsManager parseStats( JsonObject gameObj ){

        JsonObject statsObject      = gameObj.getAsJsonObject( "stats" );
        
        Map<String, Passing> paMap  = parsePassing( statsObject );
        Map<String, Rushing> ruMap  = parseRushing( statsObject );
        
        Map<String, Receiving> rcMap= parseReceiving( statsObject );
        Map<String, Fumbles> fmMap  = parseFumbles( statsObject );
        Map<String, Kicking> kcMap  = parseKicking( statsObject );
        Map<String, Punting> ptMap  = parsePunting( statsObject );
        Map<String, Kickret> krMap  = parseKickret( statsObject );
        Map<String, Puntret> prMap  = parsePuntret( statsObject );
        Map<String, Defense> dfMap  = parseDefense( statsObject );
        Team team                   = parseTeam( statsObject );
        
        StatsManager stats       = new StatsManager( paMap, ruMap, rcMap, fmMap, 
                                                    kcMap, ptMap, krMap, prMap, dfMap, team );
        return stats;
    }
    
    
    protected final static Map<String, Passing> parsePassing( JsonObject gameObj ){
        
        Map<String, Passing> map = new HashMap<>( );
        
        try{
            
            JsonObject passing  = gameObj.getAsJsonObject( "passing" );
            
            for( Entry<String, JsonElement> playerEntry : passing.entrySet( ) ) {
                Passing pass    = GSON_INSTANCE.fromJson( playerEntry.getValue( ), Passing.class );
                map.put( playerEntry.getKey( ), pass );
            }
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Passing", e );
        }
          
        return map;
    }

    
    protected final static Map<String, Rushing> parseRushing( JsonObject gameObj ){
        
        Map<String, Rushing> map = new HashMap<>( );
        
        try{
            
            JsonObject passing  = gameObj.getAsJsonObject( "rushing" );
            
            for( Entry<String, JsonElement> playerEntry : passing.entrySet( ) ) {
                map.put( playerEntry.getKey( ), GSON_INSTANCE.fromJson( playerEntry.getValue( ), Rushing.class ) );
            }
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Rushing", e );
        }
          
        return map;
    }
    

    protected final static Map<String, Receiving> parseReceiving( JsonObject gameObj ){
        
        Map<String, Receiving> map = new HashMap<>( );
        
        try{
            
            JsonObject receiving  = gameObj.getAsJsonObject( "receiving" );
            
            for( Entry<String, JsonElement> playerEntry : receiving.entrySet( ) ) {
                map.put( playerEntry.getKey( ), GSON_INSTANCE.fromJson( playerEntry.getValue( ), Receiving.class ) );
            }
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Receiving", e );
        }
          
        return map;
    }
    
    
    protected final static Map<String, Fumbles> parseFumbles( JsonObject gameObj ){
        
        Map<String, Fumbles> map = new HashMap<>( );
        
        try{
            
            JsonObject receiving  = gameObj.getAsJsonObject( "fumbles" );
            
            for( Entry<String, JsonElement> playerEntry : receiving.entrySet( ) ) {
                map.put( playerEntry.getKey( ), GSON_INSTANCE.fromJson( playerEntry.getValue( ), Fumbles.class ) );
            }
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Fumbles", e );
        }
          
        return map;
    }
    

    protected final static Map<String, Kicking> parseKicking( JsonObject gameObj ){
        
        Map<String, Kicking> map = new HashMap<>( );
        
        try{
            
            JsonObject receiving  = gameObj.getAsJsonObject( "kicking" );
            
            for( Entry<String, JsonElement> playerEntry : receiving.entrySet( ) ) {
                map.put( playerEntry.getKey( ), GSON_INSTANCE.fromJson( playerEntry.getValue( ), Kicking.class ) );
            }
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Kicking", e );
        }
          
        return map;
    }
    
    
    protected final static Map<String, Punting> parsePunting( JsonObject gameObj ){
        
        Map<String, Punting> map = new HashMap<>( );
        
        try{
            
            JsonObject receiving  = gameObj.getAsJsonObject( "punting" );
            
            for( Entry<String, JsonElement> playerEntry : receiving.entrySet( ) ) {
                map.put( playerEntry.getKey( ), GSON_INSTANCE.fromJson( playerEntry.getValue( ), Punting.class ) );
            }
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Punting", e );
        }
          
        return map;
    }
    
    
    protected final static Map<String, Kickret> parseKickret( JsonObject gameObj ){
        
        Map<String, Kickret> map = new HashMap<>( );
        
        try{
            
            JsonObject receiving  = gameObj.getAsJsonObject( "kickret" );
            
            for( Entry<String, JsonElement> playerEntry : receiving.entrySet( ) ) {
                map.put( playerEntry.getKey( ), GSON_INSTANCE.fromJson( playerEntry.getValue( ), Kickret.class ) );
            }
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Kickret", e );
        }
          
        return map;
    }
    
    
    protected final static Map<String, Puntret> parsePuntret( JsonObject gameObj ){
        
        Map<String, Puntret> map = new HashMap<>( );
        
        try{
            
            JsonObject receiving  = gameObj.getAsJsonObject( "puntret" );
            
            for( Entry<String, JsonElement> playerEntry : receiving.entrySet( ) ) {
                map.put( playerEntry.getKey( ), GSON_INSTANCE.fromJson( playerEntry.getValue( ), Puntret.class ) );
            }
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Puntret", e );
        }
          
        return map;
    }

    
    protected final static Map<String, Defense> parseDefense( JsonObject gameObj ){
        
        Map<String, Defense> map = new HashMap<>( );
        
        try{
            
            JsonObject receiving  = gameObj.getAsJsonObject( "defense" );
            
            for( Entry<String, JsonElement> playerEntry : receiving.entrySet( ) ) {
                map.put( playerEntry.getKey( ), GSON_INSTANCE.fromJson( playerEntry.getValue( ), Defense.class ) );
            }
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Defense", e );
        }
          
        return map;
    }

    
    protected final static Team parseTeam( JsonObject gameObj ){
        
        Team team                = null;
        
        try{
            
            JsonObject teamJson  = gameObj.getAsJsonObject( "team" );
            team = GSON_INSTANCE.fromJson( teamJson, Team.class );
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Team", e );
        }
          
        return team;
    }

}
