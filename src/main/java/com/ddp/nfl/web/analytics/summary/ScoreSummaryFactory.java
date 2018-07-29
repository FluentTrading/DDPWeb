package com.ddp.nfl.web.analytics.summary;

import org.slf4j.*;
import java.util.*;
import java.util.Map.*;
import com.google.gson.*;


public final class ScoreSummaryFactory{
    
    private final static String SUMMARY_KEY = "scrsummary";
    private final static Logger LOGGER      = LoggerFactory.getLogger( "ScoreSummaryFactory" );
    
    
    public final static SummaryManager create( JsonObject gameObj ){
        
        JsonElement scoreElement        = gameObj.get( SUMMARY_KEY );
        Map<Integer, ScoreSummary> map  = parseScoreSummary( scoreElement);
        SummaryManager manager          = new SummaryManager( map );
        
        return manager;
    }
    
    
    
    
    protected final static Map<Integer,ScoreSummary> parseScoreSummary( JsonElement scoreElement ){
        
        Map<Integer, ScoreSummary> map   = new TreeMap<>( );
                
        try {
        
            
            for( Entry<String, JsonElement> entry : scoreElement.getAsJsonObject( ).entrySet( ) ){
                
                int scoreTime    = Integer.parseInt( entry.getKey( ) );
                JsonObject sumObject= entry.getValue( ).getAsJsonObject( );
                String type = sumObject.get( "type" ).getAsString( );
                String desc = sumObject.get( "desc" ).getAsString( );
                int quarter = sumObject.get( "qtr" ).getAsInt( );
                String team = sumObject.get( "team" ).getAsString( );
                
                Map<String, String> players   = new HashMap<>( 32 );        
                for( Entry<String, JsonElement> playerEntry : sumObject.get("players").getAsJsonObject( ).entrySet( ) ) {
                    String playerName = playerEntry.getKey( );
                    String playerValue= playerEntry.getValue( ).getAsString( );
                    players.put( playerName, playerValue );
                }
                
                ScoreSummary summary = new ScoreSummary( type, desc, quarter, team, players );
                map.put( scoreTime, summary );
            }
            
        }catch( Exception e ){
            LOGGER.warn( "FAILED to parse {}", scoreElement, e );
        }
      
        return map;
    }
    
  
}
