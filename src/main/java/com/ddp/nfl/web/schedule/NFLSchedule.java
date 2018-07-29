package com.ddp.nfl.web.schedule;

import org.slf4j.*;
import java.text.*;
import java.util.*;
import com.ddp.nfl.web.core.*;

import static com.ddp.nfl.web.util.DDPUtil.*;

public final class NFLSchedule{
    
    private final String gameId;
    private final Date gameDate;
    private final String gameDay;
    private final String gameTime;
    private final String fGameDate;
    private final NFLTeam homeTeam;
    private final int homeScore;
    private final NFLTeam awayTeam;
    private final int awayScore;
    
    private final static int    DATE_LENTH  = 8;
    private final static Logger LOGGER      = LoggerFactory.getLogger( "NFLSchedule" );
    
        
    public NFLSchedule( String gameId, String gameEid, String gameDay, String gameTime, 
                        NFLTeam homeTeam, int homeScore, NFLTeam awayTeam, int awayScore ) throws ParseException{
        
        this.gameId     = gameId;
        this.gameDate   = parseGameDateFromEid( gameEid );
        this.gameDay    = gameDay;
        this.gameTime   = gameTime;
        this.fGameDate  = createFormattedGameTime( gameDate, gameDay, gameTime );
        this.homeTeam   = homeTeam;
        this.homeScore  = homeScore;
        this.awayTeam   = awayTeam;
        this.awayScore  = awayScore;
                
    }
    

    public final String getGameId( ) {
        return gameId;
    }
    
    public final Date getGameDate( ) {
        return gameDate;
    }


    public final String getGameDay( ) {
        return gameDay;
    }


    public final String getGameTime( ) {
        return gameTime;
    }


    public final NFLTeam getHomeTeam( ) {
        return homeTeam;
    }
    
    public final int getHomeScore( ) {
        return homeScore;
    }

    
    public final NFLTeam getAwayTeam( ) {
        return awayTeam;
    }
              
    public final int getAwayScore( ) {
        return awayScore;
    }
    
    
    public final String getFormattedGameTime( ) {
        return fGameDate;
    }
    
        
    protected final static String createFormattedGameTime( Date gameDate, String gameDay, String gameTime ) {
        StringBuilder builder= new StringBuilder( 32 );
        builder.append( gameDate.getMonth( ) ).append( "/" );
        builder.append( gameDate.getDate( ) ).append( " " );
        builder.append( gameDay ).append( " " );
        builder.append( gameTime ).append( " ET" );
        
        return builder.toString( );
    }
    
    
    
    protected final static Date parseGameDateFromEid( String eidString ){
       
        Date gameDate       = TODAY_DATE;
        
        try {
        
            String onlyDate = eidString.substring( 0, DATE_LENTH );
            gameDate        = NFL_EID_FORMAT.parse(onlyDate);
            
        }catch(Exception e ) {
            LOGGER.warn("FAILED to parse game date from EID [{}]", eidString, e);
        }
        
        return gameDate;
    
    }
    
    
    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( 64 );
        builder.append( "NFLSchedule [GgameId=" ).append( gameId );
        builder.append( ", Day=" ).append( gameDay );
        builder.append( ", Time=" ).append( gameTime );
        builder.append( ", HomeTeam=" ).append( homeTeam.getName( ) );
        builder.append( ", HomeScore=" ).append( homeScore );
        builder.append( ", AwayTeam=" ).append( awayTeam.getName( ) );
        builder.append( ", AwayScore=" ).append( awayScore );
        builder.append( "]" );
        
        return builder.toString( );
    }
    

}
