package com.ddp.nfl.web.pickem;

import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.util.*;

public final class PickResult{
    
    private final boolean isValid;
    private final String message;
    private final Collection<DDPPick> picks;
        
    public PickResult( boolean isValid, String message, Collection<DDPPick> picks ){
        this.isValid    = isValid;
        this.message    = message;
        this.picks      = picks;
    }
    
    
    public final static PickResult createValid( String message, Collection<DDPPick> picks ){
        return new PickResult( true, message, picks );
    }
    
    
    public final static PickResult createInvalid( String message ){
        return new PickResult( false, message, null );
    }
  

    public final boolean isValid( ){
        return isValid;
    }    
    
    
    public final String getMessage( ) {
        return message;
    }
    
    
    public final Collection<DDPPick> getPicks( ) {
        return picks;
    }
    
    
    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( );
        builder.append( "PickSaveResult [isValid=" ).append( isValid ).append( ", message=" ).append( message ).append( "]" );
        return builder.toString( );
    }
       
    
}
