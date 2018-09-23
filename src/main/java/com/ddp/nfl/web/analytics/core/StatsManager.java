package com.ddp.nfl.web.analytics.core;

import java.util.*;
import java.util.Map.*;
import com.google.gson.*;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;


public final class StatsManager{
    
    public final static String PASSING_KEY  = "passing";
    public final static String RUSHING_KEY  = "rushing";
    public final static String RECEIVING_KEY= "receiving";
    
    private final static String STATS_KEY   = "stats";
    private final static String[] EMPTY_ARRAY= new String[] {"", ""};
    private final static String[] TEAM_KEY  = new String[] {"home", "away"};
    private final static Logger LOGGER      = LoggerFactory.getLogger( "StatsManager" );
    
   
    protected final static Map<String, Analytics> parse( JsonObject gameObj ){
        
        Map<String, Analytics> statsMap        = new HashMap<>( );
    
        for( String firstKey : TEAM_KEY ) {
            
            JsonObject homeObj      = gameObj.getAsJsonObject( firstKey );
            String teamName         = homeObj.get( "abbr" ).getAsString( );
            JsonObject statsObj     = homeObj.getAsJsonObject( STATS_KEY );
            
            String[] passingArray   = parsePassing( parseFirstPlay( PASSING_KEY, statsObj ) );
            String[] rushingArray   = parseRushing( parseFirstPlay( RUSHING_KEY, statsObj ) );
            String[] recvingArray   = parseReceiving( parseFirstPlay( RECEIVING_KEY, statsObj ) );
                   
            Analytics analytics     = new Analytics( passingArray, rushingArray, recvingArray );
            statsMap.put( teamName, analytics );
                        
        }
        
        return statsMap;
        
    }
    
    
    protected final static JsonElement parseFirstPlay( String playType, JsonObject statsObj  ){
        
        JsonElement playTypeElement = statsObj.get(playType);
        if( playTypeElement == null ) return null;
        
        JsonElement element = null;
        for( Entry<String, JsonElement> entry : playTypeElement.getAsJsonObject( ).entrySet( ) ){
            element = entry.getValue( );
            break;
        }
                
        return element;
        
    }
    
    
    protected final static String[] parsePassing( JsonElement element ){
        
        if( element == null ) return EMPTY_ARRAY;
        
        String[] result = EMPTY_ARRAY;
        
        try {
            String name = safeParse(element.getAsJsonObject( ), "name" );
            int yards   = negativeToZero(safeParseInt( element.getAsJsonObject( ), "yds" ));
            int tds     = negativeToZero(safeParseInt( element.getAsJsonObject( ), "tds" ));
            int ints    = negativeToZero(safeParseInt( element.getAsJsonObject( ), "ints" ));
        
            StringBuilder builder = new StringBuilder( );
            builder.append( yards ).append( " yds" ).append( COMMA ).append( SPACE );
            builder.append( tds ).append( " tds" ).append( COMMA ).append( SPACE );
            builder.append( ints ).append( " int" );
            
            result      = new String[] { name, builder.toString( ) };
            
        }catch( Exception e ) {
            LOGGER.warn("FAILED to parse passing for {}", element, e );
        }
        
        return result;
         
    }
    
    
    protected final static String[] parseRushing( JsonElement element ){
        
        if( element == null ) return EMPTY_ARRAY;
        
        String[] result = EMPTY_ARRAY;
        
        try {
            String name = safeParse(element.getAsJsonObject( ), "name" );
            int attempts= negativeToZero(safeParseInt( element.getAsJsonObject( ), "att" ));
            int yards   = negativeToZero(safeParseInt( element.getAsJsonObject( ), "yds" ));
            int tds     = negativeToZero(safeParseInt( element.getAsJsonObject( ), "tds" ));
        
            StringBuilder builder = new StringBuilder( );
            builder.append( yards ).append( " yds" ).append( COMMA ).append( SPACE );
            builder.append( tds ).append( " tds" ).append( COMMA ).append( SPACE );
            builder.append( attempts ).append( " att" );
                
            result      = new String[] { name, builder.toString( ) };
            
         }catch( Exception e ) {
             LOGGER.warn("FAILED to parse rushing for {}", element, e);
         }
            
         return result;
        
    }
    

    protected final static String[] parseReceiving( JsonElement element ){
        
        if( element == null ) return EMPTY_ARRAY;
        
        String[] result = EMPTY_ARRAY;
        
        try {
            String name = safeParse(element.getAsJsonObject( ), "name" );
            int recv    = negativeToZero(safeParseInt( element.getAsJsonObject( ), "rec" ));
            int yards   = negativeToZero(safeParseInt( element.getAsJsonObject( ), "yds" ));
            int tds     = negativeToZero(safeParseInt( element.getAsJsonObject( ), "tds" ));
        
            StringBuilder builder = new StringBuilder( );
            builder.append( yards ).append( " yds" ).append( COMMA ).append( SPACE );
            builder.append( tds ).append( " tds" ).append( COMMA ).append( SPACE );
            builder.append( recv ).append( " rec" );
            
            result      = new String[] { name, builder.toString( ) };
            
        }catch( Exception e ) {
            LOGGER.warn("FAILED to parse receiving for {}", element, e);
        }
           
        return result;
        
    
    }
    
    
    protected final static int negativeToZero( int number ) {
        return (number == NEGATIVE_ONE) ? ZERO : number;
    }
    

    /*
    public final static void main( String[] args ) throws Exception{
        
        String gameId       = "2018091000";
        String fullGameUrl  = "C:\\temp\\Full_Game_Center_Week1.json";
        
        StringBuilder buffer    = new StringBuilder( 2048 );
        
        try (BufferedReader br = new BufferedReader(new FileReader(fullGameUrl))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                if(!line.isEmpty()){
                    buffer.append( line );
                }
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        
        JsonElement topElem = JSON_PARSER.parse( buffer.toString( ) );
        boolean isJsonObj   = topElem.isJsonObject( );
        if( !isJsonObj ) return;
        
        JsonObject jObject  = topElem.getAsJsonObject( );
        boolean isNull      = jObject.isJsonNull( );
        if( isNull ) return;
        
        JsonObject gameObj  = jObject.getAsJsonObject( gameId );
        if( gameObj.isJsonNull( ) ) return;
        
        JsonElement passing    = parsePlayStat( true, "passing", gameObj );
        String passingValue     = parsePassing( passing );
        System.out.println( passingValue );
        
        JsonElement rushing    = parsePlayStat( true, "rushing", gameObj );
        String rushingValue     = parsePassing( rushing );
        System.out.println( rushingValue );
        
        JsonElement receiving   = parsePlayStat( true, "receiving", gameObj );
        String receivingValue     = parsePassing( receiving );
        System.out.println( receivingValue );
                
     }
    */
}
