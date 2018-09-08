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
                        
            String teamName     = safeParse( jObject, "abbr" );
            teamName            = isValid( teamName ) ? teamName.toLowerCase( ) : EMPTY;
            int to              = safeParseInt( jObject, "to" );
            Map<Quarter, Integer> sMap = parseScores( isHome, jObject );
            String players      = safeParse( jObject, "players" );
            team                = new TeamInfo( isHome, teamName, to, sMap, players );
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse TeamAnalytics for [{}]", jObject, e );
        }
                
        return team;
    }


    public final static Map<Quarter, Integer> parseScores( boolean isHome, JsonObject jObject ){

        String scoreKey     = (isHome) ? "home" : "away";
        JsonObject keyObject= jObject.getAsJsonObject( scoreKey );
        if( keyObject == null ) return  Collections.emptyMap( );
        
        JsonObject scoreObj = keyObject.getAsJsonObject( "score" );
        if( scoreObj == null ) return  Collections.emptyMap( );
        
        
        Map<Quarter, Integer> map = new HashMap<>( );
        
        try{
            
            for( Entry<String, JsonElement> scoreEntry : scoreObj.entrySet( ) ) {
                Quarter quarter = Quarter.get( scoreEntry.getKey( ) );
                JsonElement elem= scoreEntry.getValue( );
                int quarterScore= ( elem.isJsonNull( ) ? ZERO : elem.getAsInt( ) );
                
                map.put( quarter, quarterScore );
            }
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Scores", e );
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
