package com.ddp.nfl.web.analytics.summary;

import org.slf4j.*;
import java.util.Map.*;
import com.google.gson.*;
import org.apache.commons.collections4.map.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class SummaryManager{
    
    
    private final static String DRIVE_KEY   = "Drive:";
    private final static String SUMMARY_KEY = "scrsummary";
    private final static String[] EMPTY_ARR = new String[] {EMPTY, EMPTY};
    private final static Logger LOGGER      = LoggerFactory.getLogger( "SummaryManager" );
    
          
    
    //Quarter   -> Team, ScoreSummary
    //1         -> PHI, ScoreSummary
    @SuppressWarnings( "unchecked" )
    public final static MultiKeyMap<String, String> parseScoreSummary( JsonObject gameObj ){
        
        //Reverse order so that we display Quarter 4 info before Quarter 1
        //Map<Integer, Map<String, String>> summaryMap   = new TreeMap<>( Collections.reverseOrder() );
        MultiKeyMap summaryMap      = MultiKeyMap.multiKeyMap( new LinkedMap(32) );
        
        try {        
            
            JsonElement scoreElement= gameObj.get( SUMMARY_KEY );
            
            for( Entry<String, JsonElement> entry : scoreElement.getAsJsonObject( ).entrySet( ) ){
                
                //int scoreTime       = Integer.parseInt( entry.getKey( ) );
                JsonObject sumObject= entry.getValue( ).getAsJsonObject( );
                String type         = sumObject.get( "type" ).getAsString( );
                String desc         = sumObject.get( "desc" ).getAsString( );
                String quarter      = sumObject.get( "qtr" ).getAsString( );
                String team         = sumObject.get( "team" ).getAsString( );
                String[] playDrive  = parseDescription( desc );
                
                String displayText  = toDisplayString( quarter, type, team, playDrive );
                summaryMap.put( quarter, team, displayText );
               // LOGGER.info( "Stored {} {} {}", quarter, team, displayText );
            }
            
        }catch( Exception e ){
            LOGGER.warn( "FAILED to parse summary object", gameObj, e );
        }
      
        return summaryMap;
    }


    protected final static String toDisplayString( String quarter, String scoreType, String team, String[] playDrive ){
        
        StringBuilder builder = new StringBuilder( 32 );
        
        builder.append( NEWLINE )
        .append( team ).append( SPACE ).append( scoreType ).append( NEWLINE )
        .append( playDrive[ZERO] );
        
        return builder.toString( );

    }
    
    protected final static String[ ] parseDescription( String desc ) {
    
        String[] descArray  = EMPTY_ARR;
        
        try {
            descArray       = desc.split( DRIVE_KEY );
        }catch(Exception e ){}
                     
        return descArray;
    
    }    
  
}
