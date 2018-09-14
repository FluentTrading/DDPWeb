package com.ddp.nfl.web.analytics.core;

import java.util.*;
import java.util.Map.*;
import com.google.gson.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class StatsManager{
    
    public final static String PASSING_KEY  = "passing";
    public final static String RUSHING_KEY  = "rushing";
    public final static String RECEIVING_KEY= "receiving";
    
    private final static String STATS_KEY   = "stats";
    private final static String[] TEAM_KEY  = new String[] {"home", "away"};
        
   
    protected final static Map<String, String> parse( JsonObject gameObj ){
        
        //Reverse order so that we display Quarter 4 info before Quarter 1
        Map<String, String> statsMap        = new HashMap<>( );
    
        for( String firstKey : TEAM_KEY ) {
            
            StringBuilder builder   = new StringBuilder( );
            
            JsonObject homeObj      = gameObj.getAsJsonObject( firstKey );
            String teamName         = homeObj.get( "abbr" ).getAsString( );
            JsonObject statsObj     = homeObj.getAsJsonObject( STATS_KEY );
            
            parsePassing( parseFirstPlay( PASSING_KEY, statsObj ), builder );
            parseRushing( parseFirstPlay( RUSHING_KEY, statsObj ), builder );
            parseReceiving( parseFirstPlay( RECEIVING_KEY, statsObj ), builder );
                        
            statsMap.put( teamName, builder.toString( ) );
                        
        }
        
        return statsMap;
        
    }
    
    
    protected final static JsonElement parseFirstPlay( String playType, JsonObject statsObj  ){
        
        JsonElement element = null;
        for( Entry<String, JsonElement> entry : statsObj.get(playType).getAsJsonObject( ).entrySet( ) ){
            element = entry.getValue( );
            break;
        }
                
        return element;
        
    }
    
    
    protected final static void parsePassing( JsonElement element, StringBuilder builder ){
        
        String name = safeParse(element.getAsJsonObject( ), "name" );
        int yards   = negativeToZero(safeParseInt( element.getAsJsonObject( ), "yds" ));
        int tds     = negativeToZero(safeParseInt( element.getAsJsonObject( ), "tds" ));
        int ints    = negativeToZero(safeParseInt( element.getAsJsonObject( ), "ints" ));
        
        builder.append( "Pass: " ).append( name ).append( padRight( HTML_SPACE, 25 ) );
        builder.append( yards ).append( " yds" ).append( COMMA ).append(padRight( HTML_SPACE, 5 ));
        builder.append( tds ).append( " tds" ).append( COMMA ).append(padRight( HTML_SPACE, 5 ));
        builder.append( ints ).append( " int" );
        builder.append( NEWLINE );
                
    }
    
    
    protected final static void parseRushing( JsonElement element, StringBuilder builder ){
        
        String name = safeParse(element.getAsJsonObject( ), "name" );
        int attempts= negativeToZero(safeParseInt( element.getAsJsonObject( ), "att" ));
        int yards   = negativeToZero(safeParseInt( element.getAsJsonObject( ), "yds" ));
        int tds     = negativeToZero(safeParseInt( element.getAsJsonObject( ), "tds" ));
        
        builder.append( "Rush: " ).append( name ).append( padRight( HTML_SPACE, 25 ) );
        builder.append( attempts ).append( " att" ).append( COMMA ).append( padRight( HTML_SPACE, 5 ) );
        builder.append( yards ).append( " yds" ).append( COMMA ).append( padRight( HTML_SPACE, 5 ));
        builder.append( tds ).append( " tds" );
        builder.append( NEWLINE );
        
    }
    

    protected final static void parseReceiving( JsonElement element, StringBuilder builder ){
        
        String name = safeParse(element.getAsJsonObject( ), "name" );
        int recv    = negativeToZero(safeParseInt( element.getAsJsonObject( ), "rec" ));
        int yards   = negativeToZero(safeParseInt( element.getAsJsonObject( ), "yds" ));
        int tds     = negativeToZero(safeParseInt( element.getAsJsonObject( ), "tds" ));
        
        builder.append( "Recv: " ).append( name ).append( padRight( HTML_SPACE, 25 ) );
        builder.append( recv ).append( " rec" ).append( COMMA ).append( padRight( HTML_SPACE, 5 ) );
        builder.append( yards ).append( " yds" ).append( COMMA ).append(padRight( HTML_SPACE, 5 ));
        builder.append( tds ).append( " tds" );
                
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
