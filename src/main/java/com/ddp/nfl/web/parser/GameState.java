package com.ddp.nfl.web.parser;

import static com.ddp.nfl.web.util.DDPUtil.*;


public enum GameState{
    
    NOT_STARTED,
    DELAYED,
    FINISHED,
    PLAYING,
    HALFTIME,
    UNKNOWN;

    public final static String ST    = "st";
    public final static String ND    = "nd";
    public final static String RD    = "rd";
        
    
    public final static GameState parseState( String rawQuarter ) {
        
        boolean notStarted  = parseGameNotStarted( rawQuarter );
        if( notStarted ) return NOT_STARTED;
        
        
        boolean isFinished  = parseGameFinished( rawQuarter );
        if( isFinished ) return FINISHED;
        
        
        boolean isDelayed   = parseGameDelayed( rawQuarter );
        if( isDelayed ) return DELAYED;
        
        
        boolean isHalftime  = parseGameHalftime( rawQuarter );
        if( isHalftime ) return HALFTIME;
        
        
        boolean isPlaying   = !notStarted && !isFinished;
        if( isPlaying ) return PLAYING;
        
        
        return GameState.UNKNOWN;
     
    }
   
    
    
    protected final static boolean parseGameNotStarted( String rawQuarterStr ){
        return !isValid(rawQuarterStr ) || "p".equalsIgnoreCase( rawQuarterStr ) || "Pregame".equalsIgnoreCase( rawQuarterStr );
    }
    
    
    protected final static boolean parseGameFinished( String rawQuarterStr ){
        return "F".equals( rawQuarterStr ) || "FO".equals( rawQuarterStr )
                || "Final".equals( rawQuarterStr );
    }
    
    
    protected final static boolean parseGameDelayed( String rawQuarterStr ){
        return rawQuarterStr.equalsIgnoreCase("Delayed") || rawQuarterStr.equalsIgnoreCase("Suspended");
    }
    
    
    protected final static boolean parseGameHalftime( String rawQuarterStr ){
        return rawQuarterStr.equalsIgnoreCase("H") || rawQuarterStr.equalsIgnoreCase("Halftime");
    }
    
    protected final static String getDownSuffix( int down ) {
    
        if( down == ONE ) {
            return ST;
        
        }else if( down == TWO ) {
            return ND;
        
        }else if( down == THREE ) {
            return RD;
        
        }else {
            return EMPTY;
        }
        
    }
            
}
