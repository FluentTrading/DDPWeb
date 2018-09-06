package com.ddp.nfl.web.analytics.summary;

import java.util.*;
import java.util.Map.*;

import static com.ddp.nfl.web.util.DDPUtil.*;

public final class SummaryManager{
    
    //TODO:What does the key represent here, is it time?
    private final Map<Integer, ScoreSummary> map;
        
    public SummaryManager( Map<Integer, ScoreSummary> map ){
        this.map    = map;
    }
    
    public final ScoreSummary get( int key ){
        return map.get( key );
    }
    
    public final Map<Integer, ScoreSummary> getScoreSummaryMap( ) {
        return map;
    }
       
    
    @Override
    public final String toString(  ){
        StringBuilder builder = new StringBuilder( 64 );

        for( Entry<Integer, ScoreSummary> entry : map.entrySet( ) ) {
            builder.append( entry.getKey( ) );
            builder.append( " => ");
            builder.append( entry.getValue( ) );
            builder.append( PRINT_NEWLINE );
        }
   
        return builder.toString( );
    }
    
  
}
