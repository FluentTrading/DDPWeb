package com.ddp.nfl.web.match;

public enum ResultCode {
    
    DB_ERROR        ( true),
    PARSE_ERROR     ( true),
    META_MISSING    ( false),
    PICKS_NOT_MADE  ( false),
    SCHEDULE_MISSING( false),
    SUCCESS         ( false),
    UNAUTHORIZED    ( false),
    UNKNOWN         ( true);
    
    private final boolean isError;
        
    private ResultCode( boolean isError ){
        this.isError    = isError;
    }
    
    
    public final boolean isError( ){
        return isError;
    }
    
    
}

