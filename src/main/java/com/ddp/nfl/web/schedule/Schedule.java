package com.ddp.nfl.web.schedule;

import org.slf4j.*;
import java.time.*;
import com.ddp.nfl.web.core.*;

import static com.ddp.nfl.web.util.DDPUtil.*;

public final class Schedule{
    
    private final String gameId;
    private final String gameDayTime;
    private final LocalDate gameDate;
    private final String gameSchedTime;
        
    private final NFLTeam home;
    private final NFLTeam away;
    
    private final static int    DATE_LENGTH     = 8;
    private final static Logger LOGGER          = LoggerFactory.getLogger( "MatchScore" );
    
    public Schedule( String gameEid, String gameDay, 
                     String gameTime, NFLTeam home, NFLTeam away ){
        
        this.gameId         = gameEid;
        this.gameDayTime    = createGameDayTime( gameDay, gameTime );
        this.gameDate       = parseGameDate( gameEid );
        this.gameSchedTime   = createScheduleTime( gameDay, gameDate, gameTime );
        this.home           = home;
        this.away           = away;
        
    }
    

    public final String getGameId( ){
        return gameId;
    }
    
    
    public final String getGameDayTime( ){
        return gameDayTime;
    }
     
    
    public final LocalDate getGameDate( ) {
        return gameDate;
    }
    
    
    public final String getGameScheduleTime( ) {
        return gameSchedTime;
    }
    
    
    public final NFLTeam getHomeTeam( ){
        return home;
    }

    
    public final NFLTeam getAwayTeam( ){
        return away;
    }

    
    protected final String createGameDayTime( String gameDay, String gameTime ){
        return gameDay + SPACE + gameTime;
    }
  
    protected final String createScheduleTime( String gameDay, LocalDate gameDate, String gameTime ){
        
        StringBuilder builder   = new StringBuilder( 32 );
        
        int monthValue       = gameDate.getMonthValue( );
        int dateValue        = gameDate.getDayOfMonth( );
        
        builder.append( gameDay ).append( ", " );
        
        if( monthValue < TEN ) {
            builder.append( ZERO ).append( monthValue ).append( "/" );
        }else {
            builder.append( monthValue ).append( "/" );
        }
        
        if( dateValue < TEN ) {
            builder.append( ZERO ).append( dateValue ).append( SPACE );
        }else {
            builder.append( dateValue ).append( SPACE);
        }
        
        builder.append( gameTime );    
                 
        return builder.toString( );
    }
    
        
    protected final static LocalDate parseGameDate( String eidString ){
       
        LocalDate gameDate  = LocalDate.now( );
        
        try {
        
            String onlyDate = eidString.substring( 0, DATE_LENGTH );
            gameDate        = LocalDate.parse( onlyDate, _YYYYMMDDD );
            
        }catch(Exception e ) {
            LOGGER.warn("FAILED to parse game date from EID [{}]", eidString, e);
        }
        
        return gameDate;
    
    }
       

    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( 32 );
        
        builder.append( "Schedule[")
        .append( "GameId=" ).append( gameId )
        .append( ", ScheduleTime=" ).append( gameSchedTime )
        .append( ", Home=" ).append( home.getUpperCaseName( ) )
        .append( ", Away=" ).append( away.getUpperCaseName( ) )
        .append( "]" );
        
        return builder.toString( );
    }


}
