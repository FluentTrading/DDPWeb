package com.ddp.nfl.web.analytics.plays;

import static com.ddp.nfl.web.util.DDPUtil.*;

import java.util.*;
import java.util.Map.*;

public final class PlayManager{

    private final Map<Integer, Play> playMap;

    public PlayManager( Map<Integer, Play> playMap ){
        this.playMap = playMap;
    }
    
    public final Play get( int playIndex ) {
        return playMap.get( playIndex );
    }  
    

    public final Map<Integer, Play> getPlayMap( ) {
        return playMap;
    }

    
    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 64);
        builder.append( "PlayManager [" ).append( PRINT_NEWLINE);
        for( Entry<Integer, Play> entry : playMap.entrySet( ) ) {
            builder.append( entry.getKey( ) ).append(  " ==> " );
            builder.append( entry.getValue( ) ).append(  PRINT_NEWLINE );
        }
        
        return builder.toString( );
        
    }  

    
    
}
