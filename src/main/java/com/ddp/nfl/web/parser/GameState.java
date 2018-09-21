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
    public final static String TH    = "th";
        
    
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
       
    
    public final static boolean isNotStarted( LiveScore score ){
        return ( GameState.NOT_STARTED == score.getGameState( ) );
    }
         
    
    public final static boolean isFinished( LiveScore score ) {
        return ( GameState.FINISHED == score.getGameState( ) );
    }
    
    
    public final static boolean isPlaying( LiveScore score ) {
        return ( GameState.PLAYING == score.getGameState( ) );
    }

    
    public final static boolean isPlaying( GameState state ) {
        return ( GameState.PLAYING == state );
    }
    
    
    public final static boolean isPlayingNotHalfTime( GameState state ){
        return ( isPlaying(state) && !(GameState.HALFTIME == state));
    }
    
  
    public final static boolean isDelayed( LiveScore score ) {
        return ( GameState.DELAYED == score.getGameState( ) );
    }
    
    
    public final static boolean isHalftime( LiveScore score ) {
        return ( GameState.HALFTIME == score.getGameState( ) );
    }
    
    
    protected final static boolean parseGameNotStarted( String rawQuarterStr ){
        return !isValid(rawQuarterStr ) || "p".equalsIgnoreCase( rawQuarterStr ) || "Pregame".equalsIgnoreCase( rawQuarterStr );
    }
    
    
    protected final static boolean parseGameFinished( String rawQuarterStr ){
        return "F".equals( rawQuarterStr ) || "FO".equals( rawQuarterStr )
                || "Final".equals( rawQuarterStr )
                || "final overtime".equalsIgnoreCase( rawQuarterStr );
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
        
        }else if( down == FOUR ) {
            return TH;
            
        }else {
            return EMPTY;
        }
        
    }




            
}
