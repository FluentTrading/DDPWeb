package com.ddp.nfl.web.pickem;


public final class PickResult{
    
    private final boolean isValid;
    private final String message;
        
    public PickResult( boolean isValid, String message ){
        this.isValid    = isValid;
        this.message    = message;
    }

    
    
    public final static PickResult createValid( String message ){
        return new PickResult( true, message );
    }
    
    
    public final static PickResult createInvalid( String message ){
        return new PickResult( false, message );
    }
  

    public final boolean isValid( ){
        return isValid;
    }    
    
    
    public final String getMessage( ) {
        return message;
    }


    @Override
    public String toString( ) {
        StringBuilder builder = new StringBuilder( );
        builder.append( "PickResult [isValid=" ).append( isValid ).append( ", message=" ).append( message ).append( "]" );
        return builder.toString( );
    }
    
    
   
    
}
