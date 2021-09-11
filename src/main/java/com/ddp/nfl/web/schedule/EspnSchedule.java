package com.ddp.nfl.web.schedule;

import org.slf4j.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.data.model.Competitor;
import com.ddp.nfl.web.data.model.Odd;


public final class EspnSchedule{
    
    private final boolean isGameOver;
    private final String gameId;    
    private final LocalDate gameDate;
    private final String shortSchedule;
    private final String fullSchedule;
    
    private final Competitor home;
    private final NFLTeam homeTeam;
    private final int homeScore;
    
    private final Competitor away;
    private final NFLTeam awayTeam;
    private final int awayScore;
    private final Odd gameOdds;
    
    private final static DateTimeFormatter DATE_FMT	= DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static Logger LOGGER          	= LoggerFactory.getLogger( "EspnSchedule" );
    
    
    public EspnSchedule( String gameEid, boolean isGameOver, String gameDate, String shortSchedule, String fullSchedule, 
    		Competitor home, NFLTeam homeTeam, int homeScore, Competitor away, NFLTeam awayTeam, int awayScore, Odd gameOdds ){
        
        this.gameId         = gameEid;
        this.isGameOver     = isGameOver;
        this.gameDate       = parseGameDate( gameDate );
        this.shortSchedule  = shortSchedule;
        this.fullSchedule	= fullSchedule;
        this.home           = home;
        this.homeTeam		= homeTeam;
        this.homeScore      = homeScore;
        this.away           = away;
        this.awayTeam		= awayTeam;
        this.awayScore      = awayScore;
        this.gameOdds		= gameOdds;
        
        
    }

    
    public final boolean isGameOver( ){
        return isGameOver;
    }
    
    
    public final String getGameId( ){
        return gameId;
    }
    
    public final Odd getGameOdds( ){
        return gameOdds;
    }
    
    
    public final String getFullSchedule( ){
        return fullSchedule;
    }
    
    public final String getShortSchedule( ){
        return shortSchedule;
    }
     
    
    public final LocalDate getGameDate( ) {
        return gameDate;
    }
    
    
    public final Competitor getHomeCompetitor( ){
        return home;
    }
    
    
    public final NFLTeam getHomeTeam( ){
        return homeTeam;
    }
    
    
    public final int getHomeScore( ){
        return homeScore;
    }    
    
 

    public final Competitor getAwayCompetitor( ){
        return away;
    }
    
    
    public final NFLTeam getAwayTeam( ){
        return awayTeam;
    }

    
    public final int getAwayScore( ){
        return awayScore;
    }
     
        
    //2021-09-12T17:00Z
    protected final static LocalDate parseGameDate( String gameDateString ){
       
        LocalDate gameDate  = LocalDate.now( );
        
        try {
            gameDate        = LocalDate.parse( gameDateString.substring(0, gameDateString.indexOf("T")), DATE_FMT );
            
        }catch(Exception e ) {
            LOGGER.warn("FAILED to parse game date from [{}]", gameDateString, e);
        }
        
        return gameDate;
    
    }
       

    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( 32 );
        builder.append( "Schedule[")
        .append( "GameId=" ).append( gameId )
        .append( ", ScheduleTime=" ).append( fullSchedule )
        .append( ", Odds=" ).append( gameOdds )
        .append( ", Home=" ).append( home )
        .append( ", HomeScore=" ).append( homeScore )
        .append( ", Away=" ).append( away )
        .append( ", AwayScore=" ).append( awayScore )
        
        .append( "]" );
        
        return builder.toString( );
    }


}
