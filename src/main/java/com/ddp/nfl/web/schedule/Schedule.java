package com.ddp.nfl.web.schedule;

import org.slf4j.*;
import java.time.*;
import com.ddp.nfl.web.core.*;

import static com.ddp.nfl.web.util.DDPUtil.*;

public final class Schedule{
    
    private final boolean isGameOver;
    private final String gameId;
    private final String gameDayTime;
    private final LocalDate gameDate;
    private final String gameSchedTime;
        
    private final NFLTeam home;
    private final int homeScore;
    private final TeamRecord homeRecord;
    
    private final NFLTeam away;
    private final int awayScore;
    private final TeamRecord awayRecord;
    
    private final static int    DATE_LENGTH     = 8;
    private final static Logger LOGGER          = LoggerFactory.getLogger( "Schedule" );
    
    
    public Schedule( String gameEid, String gameDay, String gameTime, 
                     NFLTeam home, int homeScore, TeamRecord homeRecord, 
                     NFLTeam away, int awayScore, TeamRecord awayRecord ){
        
        this.gameId         = gameEid;
        this.gameDayTime    = createGameDayTime( gameDay, gameTime );
        this.gameDate       = parseGameDate( gameEid );
        this.gameSchedTime  = createScheduleTime( gameDay, gameDate, gameTime );
        this.home           = home;
        this.homeScore      = homeScore;
        this.homeRecord     = homeRecord;
        this.away           = away;
        this.awayScore      = awayScore;
        this.awayRecord     = awayRecord;                
        this.isGameOver     = gameDate.isBefore( LocalDate.now( ) );
        
    }

    
    public final boolean isGameOver( ){
        return isGameOver;
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
    
    
    public final int getHomeScore( ){
        return homeScore;
    }    

    
    public final TeamRecord getHomeRecord( ){
        return homeRecord;
    } 
    
    
    public final NFLTeam getAwayTeam( ){
        return away;
    }

    
    public final int getAwayScore( ){
        return awayScore;
    }
    
    
    public final TeamRecord getAwayRecord( ){
        return awayRecord;
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
        .append( ", HomeScore=" ).append( homeScore )
        .append( ", HomeRecord=" ).append( homeRecord )
        .append( ", Away=" ).append( away.getUpperCaseName( ) )
        .append( ", AwayScore=" ).append( awayScore )
        .append( ", AwayRecord=" ).append( awayRecord )
        .append( "]" );
        
        return builder.toString( );
    }


}
