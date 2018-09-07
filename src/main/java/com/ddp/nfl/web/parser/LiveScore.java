package com.ddp.nfl.web.parser;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;

import java.time.*;

import com.ddp.nfl.web.analytics.summary.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.schedule.*;



public final class LiveScore{
    
    private final String gameId;
    private final Schedule schedule;
            
    private final boolean notStarted;
    private final boolean isPlaying;
    private final boolean isFinished;
    
    private final int homeScore;
    private final int awayScore;
    
    private final String teamPossession;
    private final String timeRemaining;
    
    private final boolean isRedzone;
    private final String quarter;
    
    private final String yl;
    private final int togo;
    private final int down;
    private final String note;
    private final SummaryManager summary; 
    
    private final static int    DATE_LENGTH     = 8;
    private final static Logger LOGGER          = LoggerFactory.getLogger( "LiveScore" );
    
    public LiveScore(  String gameId, Schedule schedule,
                            boolean notStarted, boolean isPlaying, boolean isFinished, 
                            int homeScore, 
                            int awayScore,
                            String teamPossession, String timeRemaining, 
                            boolean isRedzone, String rawQuarterStr,
                            String yl, int togo, int down, String  note, SummaryManager summary ){
        
        this.gameId         = gameId;
        this.schedule       = schedule;
        
        this.notStarted     = notStarted;
        this.isPlaying      = isPlaying;
        this.isFinished     = isFinished;
        this.homeScore      = homeScore;
        this.awayScore      = awayScore;
        this.timeRemaining  = timeRemaining;
        this.teamPossession = teamPossession;
        this.isRedzone      = isRedzone;
        this.quarter        = parseQuarter( notStarted, isFinished, schedule, rawQuarterStr, timeRemaining );
        
        this.yl             = yl;
        this.togo           = togo;
        this.down           = down;
        this.note           = note;
        this.summary        = summary;
    }
    

    public final String getGameId( ) {
        return gameId;
    }

      
    public final String getGameScheduleTime( ) {
        return schedule.getGameScheduleTime( );
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
        return schedule.getHomeTeam( );
    }

    
    public final int getHomeScore( ){
        return homeScore;
    }

    
    public final NFLTeam getAwayTeam( ){
        return schedule.getAwayTeam( );
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
       

    public final boolean isNotStarted( ) {
        return notStarted;
    }


    public final String getTeamPossession( ) {
        return teamPossession;
    }


    public final String getYl( ) {
        return yl;
    }


    public final int getTogo( ) {
        return togo;
    }


    public final int getDown( ) {
        return down;
    }


    public final String getNote( ) {
        return note;
    }


    public final SummaryManager getSummary( ) {
        return summary;
    }

    
  
    protected final static String createFormattedGameTime( String gameDay, LocalDate gameDate, String gameTime ){
        
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
    

    
    public final static String parseQuarter( boolean notStarted, boolean isFinished, Schedule schedule,
                                            String quarterStr, String timeRemaining ) {

        //SHow the time when the game starts
        if( notStarted ){
            return schedule.getGameDayTime( );
        }

        if( isFinished ){
            return "Final";
        }

        if( quarterStr.equalsIgnoreCase("H") ){
            return "Half";
        }

        if( quarterStr.equalsIgnoreCase("1")){
            return "Q" + quarterStr + SPACE_DASH + timeRemaining;

        }else if( quarterStr.equalsIgnoreCase("2")){
            return "Q" + quarterStr + SPACE_DASH + timeRemaining;

        }else if( quarterStr.equalsIgnoreCase("3")){
            return "Q" + quarterStr + SPACE_DASH + timeRemaining;

        }else if( quarterStr.equalsIgnoreCase("4")){            
            return "Q" + quarterStr + SPACE_DASH + timeRemaining;

        }else{
            return "O.T";
        }

    }


    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( 256 );
        
        builder.append( "LiveScore[")
        .append( "GameId=" ).append( gameId );
                        
        if(notStarted ) {
            builder.append( ", NotStarted=" ).append( notStarted ).append( ", StartTime=" ).append( getGameScheduleTime( ) )
            .append( ", Home=" ).append( getHomeTeam( ).getCamelCaseName( ) )
            .append( ", Away=" ).append( getAwayTeam( ).getCamelCaseName( ) );
        }

        builder.append( ", IsFinished=" ).append( isFinished )
        .append( ", IsPlaying=" ).append( isPlaying )
        .append( ", Home=" ).append( getHomeTeam( ).getCamelCaseName( )).append( ", HomeScore=" ).append( homeScore )
        .append( ", Away=" ).append( getAwayTeam( ).getCamelCaseName( ) ).append( ", AwayScore=" ).append( awayScore )
        .append( ", Possession=" ).append( teamPossession )
        .append( ", quarter=" ).append( quarter )
        .append( ", TimeLeft=" ).append( timeRemaining )
        .append( ", isRedzone=" ).append( isRedzone )
        .append( ", yl=" ).append( yl ).append( ", togo=" ).append( togo )
        .append( ", down=" ).append( down ).append( ", note=" ).append( note );
            
        if( summary != null ) {
            builder.append( summary );
        }
             
        builder.append( "]" );
            
        return builder.toString( );
        
    }
    

}

