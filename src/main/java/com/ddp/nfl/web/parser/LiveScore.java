package com.ddp.nfl.web.parser;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;
import java.time.*;
import com.ddp.nfl.web.analytics.core.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.schedule.*;



public final class LiveScore{
    
    private final String gameId;
    private final Schedule schedule;
            
    private final GameState gameState;
    
    private final int homeScore;
    private final int awayScore;
    
    private final String teamPossession;
    private final String timeRemaining;
    
    private final boolean isRedzone;
    private final String rawQuarterStr;
    
    private final String driveInfo;
    private final String formattedQuarter;
    
    private final String stadium;
    private final String note;
    
    private final SummaryManager summary; 
    
    private final static int    DATE_LENGTH     = 8;
    private final static Logger LOGGER          = LoggerFactory.getLogger( "LiveScore" );
    
   
    public LiveScore(  String gameId, Schedule schedule, GameState gameState,
                            int homeScore, int awayScore,
                            String teamPossession, String timeRemaining, 
                            boolean isRedzone, String rawQuarterStr,
                            String yl, int togo, int down, int bp, String stadium, String note, SummaryManager summary ){
        
        this.gameId         = gameId;
        this.schedule       = schedule;
        this.gameState      = gameState;
                
        this.homeScore      = homeScore;
        this.awayScore      = awayScore;
        this.timeRemaining  = timeRemaining;
        this.teamPossession = teamPossession;
        this.isRedzone      = isRedzone;
        this.rawQuarterStr  = rawQuarterStr;
        this.driveInfo      = parseDrive( gameState, down, togo, yl );
        this.formattedQuarter= formatQuarter( gameState, schedule, rawQuarterStr, stadium, timeRemaining );
        
        this.stadium        = NFLStadium.getFormattedName(stadium);
        this.note           = note;
        this.summary        = summary;
    }
    


    public final String getGameId( ) {
        return gameId;
    }

      
    public final String getGameScheduleTime( ) {
        return schedule.getGameScheduleTime( );
    }
    

    public final GameState getGameState( ) {
        return gameState;
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
    
    
    public final String getDisplayableQuarter( ){
        return formattedQuarter;
    }
     
    
    public final String getRawQuarter( ){
        return rawQuarterStr;
    }

    
    public final String getTeamPossession( ) {
        return teamPossession;
    }


    public final String getDriveInfo( ) {
        return driveInfo;
    }
    
    
    public final String getStadium( ) {
        return stadium;
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


    protected final static String parseDrive( GameState state, int down, int togo, String yard ){

        boolean isPlaying     = GameState.isPlayingNotHalfTime(state);
        if( !isPlaying ) return EMPTY;
        
        StringBuilder builder = new StringBuilder( );
        String downSuffix     = GameState.getDownSuffix( down );
        
        builder.append( down ).append( downSuffix )
        .append( SPACE ).append( AMPERSAND ).append( SPACE )
        .append( togo ).append( SPACE ).append( AT ).append( SPACE )
        .append( yard );
        
        return builder.toString( );
    }

    
    
    protected final static String formatQuarter( GameState state, Schedule schedule, String quarterStr, String stadium, String timeRemaining ){

        
        switch( state ) {
            
            case NOT_STARTED:
                return schedule.getGameDayTime( );
                
            case FINISHED:
                return "Final";
                
            case DELAYED:
                return "Delayed";
                
            case HALFTIME:
                return "Halftime";
                
            case PLAYING:{
                
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
            
            default:
                return "Unknown";
        }
        
    }


    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( 256 );
        
        builder.append( "LiveScore[")
        .append( "GameId=" ).append( gameId );
                        
        if( GameState.NOT_STARTED == gameState ) {
            builder.append( "StartTime=" ).append( getGameScheduleTime( ) )
            .append( ", Home=" ).append( getHomeTeam( ).getCamelCaseName( ) )
            .append( ", Away=" ).append( getAwayTeam( ).getCamelCaseName( ) );
        }

        builder.append( ", State=" ).append( gameState.name( ) )
        .append( ", Home=" ).append( getHomeTeam( ).getCamelCaseName( )).append( ", HomeScore=" ).append( homeScore )
        .append( ", Away=" ).append( getAwayTeam( ).getCamelCaseName( ) ).append( ", AwayScore=" ).append( awayScore )
        .append( ", Possession=" ).append( teamPossession )
        .append( ", quarter=" ).append( formattedQuarter )
        .append( ", TimeLeft=" ).append( timeRemaining )
        .append( ", isRedzone=" ).append( isRedzone )
        .append( ", Drive=" ).append( driveInfo ).append( ", stadium=" ).append( stadium )
        .append( ", note=" ).append( note );
            
        if( summary != null ) {
            builder.append( summary );
        }
             
        builder.append( "]" );
            
        return builder.toString( );
        
    }
    

}

