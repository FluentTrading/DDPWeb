package com.ddp.nfl.web.parser;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;

import java.time.*;

import com.ddp.nfl.web.core.*;


//Will contain all data for MatchScore  + Schedule class
//Then schedule class will only do lookups based on this data
//Rename it to MatchScore or something better (LiveMatchScore0

public final class LiveScore{
    
    private final String gameId;
    private final LocalDate gameDate;
    private final String gameDateTime;
    private final String gameDayTime;
    
    private final boolean notStarted;
    private final boolean isPlaying;
    private final boolean isFinished;
    
    private final NFLTeam home;
    private final int homeScore;
    
    private final NFLTeam away;
    private final int awayScore;
    
    private final boolean isRedzone;
    private final String quarter;
    
    private final static int    DATE_LENGTH     = 8;
    private final static Logger LOGGER          = LoggerFactory.getLogger( "MatchScore" );
    
    public LiveScore(  String gameId, String gameEid, String gameDay, String gameTime,
                            boolean notStarted, boolean isPlaying, boolean isFinished, 
                            NFLTeam home, int homeScore, 
                            NFLTeam away, int awayScore, 
                            boolean isRedzone, String quarter ){
        
        this.gameId     = gameId;
        this.gameDate   = parseGameDate( gameEid );
        this.gameDateTime= createFormattedGameTime( gameDay, gameDate, gameTime );
        this.gameDayTime= gameDay + COLON + SPACE + gameTime;
        this.notStarted = notStarted;
        this.isPlaying  = isPlaying;
        this.isFinished = isFinished;
        this.home       = home;
        this.homeScore  = homeScore;
        this.away       = away;
        this.awayScore  = awayScore;
        this.isRedzone  = isRedzone;
        this.quarter    = quarter;
        
    }
    

    public final String getGameId( ) {
        return gameId;
    }
        

    public final LocalDate getGameDate( ) {
        return gameDate;
    }

  
    public final String getGameDayTime( ) {
        return gameDayTime;
    }
    
    
    public final String getGameDateTime( ) {
        return gameDateTime;
    }
    

    public final boolean notStarted( ) {
        return notStarted;
    }
    

    public final boolean isPlaying( ) {
        return isPlaying;
    }

    
    public final boolean isFinished( ){
        return isFinished;
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
    
    
    public final boolean isRedzone( ){
        return isRedzone;
    }
    
    
    public final String getQuarter( ){
        return quarter;
    }
    
  
        
    protected final String createFormattedGameTime( String gameDay, LocalDate gameDate, String gameTime ){
        StringBuilder builder= new StringBuilder( 32 );
                
        builder.append( gameDay.toUpperCase( ) ).append( ", " );
        builder.append( gameDate.getMonthValue( ) ).append( "/" );
        builder.append( gameDate.getDayOfMonth( ) ).append( " " );
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
        
        StringBuilder builder = new StringBuilder( 128 );
        
        builder.append( "LiveScore[")
        .append( "GameId=" ).append( gameId )
        .append( ", Time=" ).append( gameDateTime )
        .append( ", NotStarted=" ).append( notStarted )
        .append( ", isPlaying=" ).append( isPlaying )
        .append( ", isFinished=" ).append( isFinished )
        .append( ", isRedzone=" ).append( isRedzone )
        .append( ", quarter=" ).append( quarter )
        .append( ", Home=" ).append( home.getUpperCaseName( ) ).append( ", HomeScore=" ).append( homeScore )
        .append( ", Away=" ).append( away.getUpperCaseName( ) ).append( ", AwayScore=" ).append( awayScore )
        .append( "]" );
        
        return builder.toString( );
    }
    
        


}

