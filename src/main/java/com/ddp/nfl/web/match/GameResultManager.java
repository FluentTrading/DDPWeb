package com.ddp.nfl.web.match;

import java.util.*;
import com.ddp.nfl.web.core.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class GameResultManager{
       
    private final DDPMeta meta;
    private final ResultCode code;
    private final String message;
    private final List<GameResult> resultList;
    
    
    public GameResultManager( DDPMeta meta, ResultCode code, String message, List<GameResult> resultList ){
        this.meta       = meta;
        this.code       = code;
        this.message    = message;
        this.resultList = resultList;
    }
    
    
    public final static GameResultManager createValid( DDPMeta meta, List<GameResult> infoList ){
        return new GameResultManager( meta, ResultCode.SUCCESS, EMPTY, infoList );
    }

    public final static GameResultManager createInvalid( DDPMeta meta, ResultCode code, String message ){
        return new GameResultManager( meta, code, message, null );
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
    

    public final List<GameResult> getResultList( ){
        return resultList;
    }
   

    @Override
    public final String toString( ) {
        
        StringBuilder builder = new StringBuilder( );
        builder.append( "DDPGameResult [code=" );
        builder.append( code );
        builder.append( ", message=" );
        builder.append( message );
        builder.append( ", ResultList=" );
        builder.append( resultList );
        builder.append( "]" );
        
        return builder.toString( );
    
    }


}
