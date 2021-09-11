package com.ddp.nfl.web.data.model.parser;

import static com.ddp.nfl.web.util.DDPUtil.*;

import java.util.Set;

import com.ddp.nfl.web.data.model.Status;


public enum EspnGameState{
    
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
    
    public final static Set<Integer> quarterSet = Set.of(1, 2, 3, 4);
        
    
    public final static EspnGameState parseState( Status status ) {
    	
    	int quarter 	= status.getPeriod();
    	String name		= status.getType().getName();
    	boolean done	= status.getType().isCompleted();
    	
    	if( !done && quarterSet.contains(quarter) ){
    		return PLAYING;
    	}
    	
    	if( done ){
    		return FINISHED;
    	}
    	
    	if( !done && !quarterSet.contains(quarter) ) {
    		 return NOT_STARTED;
    	}
    	
    	if( !done && name.contains("DELAY") ){
   		 	return DELAYED;
    	}
        

    	if( !done && name.contains("HALF") ){
   		 	return HALFTIME;
    	}
      
        
        return EspnGameState.UNKNOWN;
     
    }
       
        
    
    public final static boolean isNotStarted( EspnLiveScore score ){
        if( score == null ) return true;
        return ( EspnGameState.NOT_STARTED == score.getGameState( ) );
    }
         
    
    public final static boolean isFinished( EspnLiveScore score ) {
        return ( EspnGameState.FINISHED == score.getGameState( ) );
    }
    
    
    public final static boolean isPlaying( EspnLiveScore score ) {
        if( score == null ) return false;
        return ( EspnGameState.PLAYING == score.getGameState( ) );
    }

    
    public final static boolean isPlaying( EspnGameState state ) {
        return ( EspnGameState.PLAYING == state );
    }
    
    
    public final static boolean isPlayingNotHalfTime( EspnGameState state ){
        return ( isPlaying(state) && !(EspnGameState.HALFTIME == state));
    }
    
  
    public final static boolean isDelayed( EspnLiveScore score ) {
        return ( EspnGameState.DELAYED == score.getGameState( ) );
    }
    
    
    public final static boolean isHalftime( EspnLiveScore score ) {
        return ( EspnGameState.HALFTIME == score.getGameState( ) );
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
