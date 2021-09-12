package com.ddp.nfl.web.data.model.parser;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;
import java.time.*;

import com.ddp.nfl.web.analytics.home.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.data.model.Situation;
import com.ddp.nfl.web.schedule.*;



public final class EspnLiveScore{
    
    private final String gameId;
    private final EspnSchedule schedule;
            
    private final EspnGameState gameState;
    
    private final int homeScore;
    private final TeamInfo homeTeamInfo;
    
    private final int awayScore;
    private final TeamInfo awayTeamInfo;
            
    private final String teamPossession;
    private final String timeRemaining;
    
    private final boolean isRedzone;
    private final int quarter;
    
    private final String driveInfo;
    private final String formattedQuarter;
    
    private final String stadium;
    private final String note;
    
    private final Situation summary; 
    
    private final static int    DATE_LENGTH     = 8;
    private final static Logger LOGGER          = LoggerFactory.getLogger( "LiveScore" );
    
   
    public EspnLiveScore(  String gameId, EspnSchedule schedule, EspnGameState gameState,
                            int homeScore, TeamInfo homeTeamInfo,  
                            int awayScore, TeamInfo awayTeamInfo, 
                            String teamPossession, String timeRemaining, 
                            boolean isRedzone, int quarter,
                            String yl, int togo, int down, String stadium, String note, Situation summary ){
        
        this.gameId         = gameId;
        this.schedule       = schedule;
        this.gameState      = gameState;
                
        this.homeScore      = homeScore;
        this.homeTeamInfo   = homeTeamInfo;
        this.awayScore      = awayScore;
        this.awayTeamInfo   = awayTeamInfo;
        this.timeRemaining  = timeRemaining;
        this.teamPossession = teamPossession;
        this.isRedzone      = isRedzone;
        this.quarter  		= quarter;
        this.driveInfo      = parseDrive( gameState, down, togo, yl );
        this.formattedQuarter= formatQuarter( gameState, schedule, quarter, stadium, timeRemaining );
        
        this.stadium        = NFLStadium.getFormattedName(stadium);        
        this.note           = note;
        this.summary        = summary;
    }
    

    public final String getGameId( ) {
        return gameId;
    }

    
    public final EspnSchedule getSchedule( ){
        return schedule;
    }
    
      
    public final String getGameScheduleTime( ) {
        return schedule.getFullSchedule( );
    }
    
    public final String getGameTime( ) {
        return schedule.getShortSchedule( );
    }
    

    public final EspnGameState getGameState( ) {
        return gameState;
    }
    
    
    public final NFLTeam getHomeTeam( ){
        return schedule.getHomeTeam( );
    }

    
    public final int getHomeScore( ){
        return homeScore;
    }

    
    public final TeamInfo getHomeTeamInfo( ){
        return homeTeamInfo;
    }
    
        
    public final int getAwayScore( ){
        return awayScore;
    }
    
    
    public final TeamInfo getAwayTeamInfo( ){
        return awayTeamInfo;
    }
    
    
    public final NFLTeam getAwayTeam( ){
        return schedule.getAwayTeam( );
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
         
    
    public final int getQuarter( ){
        return quarter;
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


    public final Situation getSituation( ) {
        return summary;
    }


	public final String getGamePlayingInfo(){
		String info = summary.getDownDistanceText();
		if( info == null || info.length() ==0 ) {
			return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		}else {
			return info;
		}
	}
	
	    
    
    //Game not started
    //Show odds if not available, show time
    public final String getGameNotStartedInfo( ) {
    	if( schedule.getGameOdds() != null ) {
    		return schedule.getGameOdds().toLiteString();
    	}else {
    		return schedule.getFullSchedule();
    	}
    	
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


    protected final static String parseDrive( EspnGameState state, int down, int togo, String yard ){

        boolean isPlaying     = EspnGameState.isPlayingNotHalfTime(state);
        if( !isPlaying ) return EMPTY;
        
        StringBuilder builder = new StringBuilder( );
        String downSuffix     = EspnGameState.getDownSuffix( down );
        
        builder.append( down ).append( downSuffix )
        .append( SPACE ).append( AMPERSAND ).append( SPACE )
        .append( togo ).append( SPACE ).append( AT ).append( SPACE ).append( yard );
        
        return builder.toString( );
    }

    
    
    protected final static String formatQuarter( EspnGameState state, EspnSchedule schedule, int quarter, String stadium, String timeRemaining ){

        if( schedule == null ) {
            return "Unknown";
        }
        
        switch( state ) {
            
            case NOT_STARTED:
                return schedule.getFullSchedule( );
                
            case FINISHED:
                return "Final";
                
            case DELAYED:
                return "Delayed";
                
            case HALFTIME:
                return "Halftime";
                
            case PLAYING:{
                
                if( quarter == 1){
                    return "Q1" + SPACE_DASH + timeRemaining;

                }else if( quarter == 2 ){
                    return "Q2" + SPACE_DASH + timeRemaining;

                }else if( quarter == 3){
                    return "Q3" + SPACE_DASH + timeRemaining;

                }else if( quarter == 4){            
                    return "Q4" + SPACE_DASH + timeRemaining;

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
                        
        if( EspnGameState.NOT_STARTED == gameState ) {
            builder.append( ", StartTime=" ).append( getGameScheduleTime( ) )
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

