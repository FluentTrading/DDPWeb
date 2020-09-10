package com.ddp.nfl.web.schedule;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;

import java.time.*;

import com.ddp.nfl.web.core.*;

public final class Schedule{
    
    private final boolean isGameOver;
    private final String gameId;
    private final String gameDay;
    private final String gameTime; 
    
    private final LocalDate gameDate;
            
    private final NFLTeam home;
    private final int homeScore;
    
    private final NFLTeam away;
    private final int awayScore;
        
    private final static int    DATE_LENGTH     = 8;
    private final static Logger LOGGER          = LoggerFactory.getLogger( "Schedule" );
    
    
    public Schedule( String gameEid, String gameDay, String gameTime, 
                     NFLTeam home, int homeScore,NFLTeam away, int awayScore ){
        
        this.gameId         = gameEid;
        this.gameDay        = gameDay;
        this.gameTime       = gameTime;
        this.gameDate       = parseGameDate( gameEid );
        this.home           = home;
        this.homeScore      = homeScore;
        this.away           = away;
        this.awayScore      = awayScore;
        this.isGameOver     = gameDate.isBefore( LocalDate.now( ) );
        
    }

    
    public final boolean isGameOver( ){
        return isGameOver;
    }
    
    
    public final String getGameId( ){
        return gameId;
    }
    
    
    public final String getGameDayTime( ){
        return gameDay + SPACE + gameTime;
    }
     
    
    public final LocalDate getGameDate( ) {
        return gameDate;
    }
    
    
    public final String getGameScheduleTime( ) {
        return createScheduleTime( gameDay, gameDate, gameTime );
    }
    
    
    public final NFLTeam getHomeTeam( ){
        return home;
    }
    
    
    public final int getHomeScore( ){
        return homeScore;
    }    
    
    public final NFLTeam getAwayTeam( ){
        return away;
    }

    
    public final int getAwayScore( ){
        return awayScore;
    }
    
  
    protected final String createScheduleTime( String gameDay, LocalDate gameDate, String gameTime ){
        
        StringBuilder builder= new StringBuilder( 32 );
        
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
            builder.append( dateValue );
        }
        
        builder.append( SPACE).append( gameTime );
        
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
        .append( ", ScheduleTime=" ).append( getGameScheduleTime( ) )
        .append( ", Home=" ).append( home )
        .append( ", HomeScore=" ).append( homeScore )
        .append( ", Away=" ).append( away )
        .append( ", AwayScore=" ).append( awayScore )
        .append( "]" );
        
        return builder.toString( );
    }


}
