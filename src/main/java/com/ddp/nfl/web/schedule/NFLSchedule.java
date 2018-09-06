package com.ddp.nfl.web.schedule;

import org.slf4j.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import com.ddp.nfl.web.core.*;


public final class NFLSchedule{
    
    private final String gameId;
    private final LocalDate gameDate;
    private final String gameDay;
    private final String gameTime;
    private final String fGameDate;
    private final NFLTeam homeTeam;
    private final int homeScore;
    private final NFLTeam awayTeam;
    private final int awayScore;
    
    private final static int    DATE_LENTH      = 8;
    private final static DateTimeFormatter EID  = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final static Logger LOGGER          = LoggerFactory.getLogger( "NFLSchedule" );
    
        
    public NFLSchedule( String gameId, String gameEid, String gameDay, String gameTime, 
                        NFLTeam homeTeam, int homeScore, NFLTeam awayTeam, int awayScore ) throws ParseException{
        
        this.gameId     = gameId;
        this.gameDate   = parseGameDate( gameEid );
        this.gameDay    = gameDay;
        this.gameTime   = gameTime;
        this.fGameDate  = createFormattedGameTime( );
        this.homeTeam   = homeTeam;
        this.homeScore  = homeScore;
        this.awayTeam   = awayTeam;
        this.awayScore  = awayScore;
                
    }
    

    public final String getGameId( ) {
        return gameId;
    }
    
    public final LocalDate getGameDate( ) {
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
    
        
    protected final String createFormattedGameTime( ){
        StringBuilder builder= new StringBuilder( 32 );
        
        builder.append( gameDay ).append( ", " );
        builder.append( gameDate.getMonthValue( ) ).append( "/" );
        builder.append( gameDate.getDayOfMonth( ) ).append( " " );
        builder.append( gameTime );
        
        return builder.toString( );
    }
    
    
    
    protected final static LocalDate parseGameDate( String eidString ){
       
        LocalDate gameDate  = LocalDate.now( );
        
        try {
        
            String onlyDate = eidString.substring( 0, DATE_LENTH );
            gameDate        = LocalDate.parse( onlyDate, EID );
            
        }catch(Exception e ) {
            LOGGER.warn("FAILED to parse game date from EID [{}]", eidString, e);
        }
        
        return gameDate;
    
    }
        
    
    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( 64 );
        builder.append( "NFLSchedule [GameId=" ).append( gameId );
        builder.append( ", Day=" ).append( gameDay );
        builder.append( ", Date=" ).append( gameDate );
        builder.append( ", Time=" ).append( gameTime );
        builder.append( ", HomeTeam=" ).append( homeTeam.getName( ) );
        builder.append( ", HomeScore=" ).append( homeScore );
        builder.append( ", AwayTeam=" ).append( awayTeam.getName( ) );
        builder.append( ", AwayScore=" ).append( awayScore );
        builder.append( "]" );
        
        return builder.toString( );
    }
    

}
