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
    	
    	String name		= status.getType() != null ? status.getType().getName() : "UNKNOWN";
    	    	
    	if( "STATUS_IN_PROGRESS".equals(name) ){
    		return PLAYING;
    	}
    	    	
    	if( "STATUS_FINAL".equals(name) ){
    		return FINISHED;
    	}
    	
    	if( "STATUS_SCHEDULED".equals(name) ){
    		 return NOT_STARTED;
    	}
    	
        if( "STATUS_HALFTIME".equals(name)) {
        	return HALFTIME;
        }
        
        if( "STATUS_DELAYED".equals(name)) {
        	return DELAYED;
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
