package com.ddp.nfl.web.parser;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;

import java.time.*;

import com.ddp.nfl.web.core.*;



public final class LiveScore{
    
    private final String gameId;
    private final LocalDate gameDate;
    private final String gameDateTime;
        
    private final boolean notStarted;
    private final boolean isPlaying;
    private final boolean isFinished;
    
    private final NFLTeam home;
    private final int homeScore;
    
    private final NFLTeam away;
    private final int awayScore;
    
    private final String teamPossession;
    private final String timeRemaining;
    
    private final boolean isRedzone;
    private final String quarter;
    
    private final static int    DATE_LENGTH     = 8;
    private final static Logger LOGGER          = LoggerFactory.getLogger( "MatchScore" );
    
    public LiveScore(  String gameId, String gameEid, String gameDay, String gameTime,
                            boolean notStarted, boolean isPlaying, boolean isFinished, 
                            NFLTeam home, int homeScore, 
                            NFLTeam away, int awayScore,
                            String teamPossession, String timeRemaining, 
                            boolean isRedzone, String rawQuarterStr ){
        
        this.gameId         = gameId;
        this.gameDate       = parseGameDate( gameEid );
        this.gameDateTime   = createFormattedGameTime( gameDay, gameDate, gameTime );
        this.notStarted     = notStarted;
        this.isPlaying      = isPlaying;
        this.isFinished     = isFinished;
        this.home           = home;
        this.homeScore      = homeScore;
        this.away           = away;
        this.awayScore      = awayScore;
        this.timeRemaining  = timeRemaining;
        this.teamPossession = teamPossession;
        this.isRedzone      = isRedzone;
        this.quarter        = parseQuarter( notStarted, isFinished, rawQuarterStr, gameDay, gameTime, timeRemaining );
        
    }
    

    public final String getGameId( ) {
        return gameId;
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
    
    
    public final String getTimeRemaining( ){
        return timeRemaining;
    }
    
    
    public final String getPossessionTeam( ){
        return teamPossession;
    }
    
    
    public final String getQuarter( ){
        return quarter;
    }
       
  
    protected final String createFormattedGameTime( String gameDay, LocalDate gameDate, String gameTime ){
        
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
    
    
    public final static String parseQuarter( boolean notStarted, boolean isFinished, 
                                            String quarterStr, String gameDay, String gameTime, 
                                            String timeRemaining ) {

        //SHow the time when the game starts
        if( notStarted ){
            return gameDay + SPACE + gameTime;
        }

        if( isFinished ){
            return "Final";
        }

        if( quarterStr.equalsIgnoreCase("H") ){
            return "Half";
        }

        if( quarterStr.equalsIgnoreCase("1")){
            return quarterStr + " QT " + timeRemaining;

        }else if( quarterStr.equalsIgnoreCase("2")){
            return quarterStr + " QT " + timeRemaining;

        }else if( quarterStr.equalsIgnoreCase("3")){
            return quarterStr + " QT " + timeRemaining;

        }else if( quarterStr.equalsIgnoreCase("4")){            
            return quarterStr + " QT " + timeRemaining;

        }else{
            return "O.T";
        }

    }
    

    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( 128 );
        
        builder.append( "LiveScore[")
        .append( "GameId=" ).append( gameId )
        .append( ", Home=" ).append( home.getUpperCaseName( ) ).append( ", HomeScore=" ).append( homeScore )
        .append( ", Away=" ).append( away.getUpperCaseName( ) ).append( ", AwayScore=" ).append( awayScore );
        
        if(notStarted ) {
            builder.append( ", StartTime=" ).append( gameDateTime );
        }
        
        if( isPlaying ) {
            builder.append( ", Possession=" ).append( teamPossession )
            .append( ", quarter=" ).append( quarter )
            .append( ", TimeLeft=" ).append( timeRemaining )
            .append( ", isRedzone=" ).append( isRedzone );
        }
        
        if( isFinished ) {
            builder.append( ", quarter=" ).append( quarter );
        }
        
        builder.append( "]" );
        
        return builder.toString( );
    }
    
        


}

