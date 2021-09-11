package com.ddp.nfl.web.match;

import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.data.model.parser.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class EspnGameResultManager{
       
    private final DDPMeta meta;
    private final ResultCode code;
    private final String message;
    private final boolean noGamesStarted;
    private final List<EspnGameResult> resultList;
    
    
    public EspnGameResultManager( DDPMeta meta, ResultCode code, String message, List<EspnGameResult> resultList ){
        this.meta       = meta;
        this.code       = code;
        this.message    = message;
        this.resultList = resultList;
        this.noGamesStarted = noGamesStarted( resultList );
    }
    
    
    public final static EspnGameResultManager createValid( DDPMeta meta, List<EspnGameResult> resultList ){
        return new EspnGameResultManager( meta, ResultCode.SUCCESS, EMPTY, resultList );
    }
    

    public final static EspnGameResultManager createInvalid( DDPMeta meta, ResultCode code, String message ){
        return new EspnGameResultManager( meta, code, message, null );
    }
    
    
    public final DDPMeta getMeta( ){
        return meta;
    }
    
    
    public final boolean isValid( ){
        return (ResultCode.SUCCESS == code );
    }
     
        
    public final boolean isMissingPicks( ){
        return (ResultCode.PICKS_NOT_MADE == code );
    }
        

    public final ResultCode getResultCode( ){
        return code;
    }
    
        
    public final String getMessage( ){
        return message;
    }
    
    
    public final String getDisplayMessage( ){
        return getMessage( );
    }
    

    public final List<EspnGameResult> getResultList( ){
        return resultList;
    }
    
    
    public final boolean noGamesStarted( ){
        return noGamesStarted;
    } 
    

    private final boolean noGamesStarted( List<EspnGameResult> resultList ){
        
        if( resultList == null || resultList.isEmpty( ) ) return true;
        
        int totalGamesCount = 0;
        int notStartedCount = 0;
        
        for( EspnGameResult result : resultList ){
            for( EspnLiveScore score : result.getLiveScores( ) ){
                ++totalGamesCount;
                if( score != null ) {
                   notStartedCount  = (EspnGameState.isNotStarted(score)) ? ++notStartedCount : notStartedCount;
                }
            }
        }
        
        boolean noGamesStarted  = ( totalGamesCount ==  notStartedCount);
        return noGamesStarted;
    
    }
  

    @Override
    public final String toString( ) {
        
        StringBuilder builder = new StringBuilder( );
        builder.append( "GameResultManager [code=" );
        builder.append( code );
        builder.append( ", message=" );
        builder.append( message );
        builder.append( ", ResultList=" );
        builder.append( resultList );
        builder.append( "]" );
        
        return builder.toString( );
    
    }


}
