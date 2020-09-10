package com.ddp.nfl.web.winnings;

import com.ddp.nfl.web.core.*;
import java.util.concurrent.atomic.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class WinnerResult{
    
    private final DDPPick winPick;
    private final int weekNumber;
    private final int totalScore;
    private final String totalFormatted;
    private final AtomicBoolean isWinner;
        
    private final NFLTeam team1;
    private final String _1Score;
    
    private final NFLTeam team2;
    private final String _2Score;
    
    private final NFLTeam team3;
    private final String _3Score;
    
    private final String winImageLink;
    private final String winImageName;
                
    private final static String WEEK_NAME        = "Week";
    private final static String WIN_IMAGE_PREFIX = "images/winnings/";
    private final static String WIN_IMAGE_SUFFIX = ".jpg";
    
    
    public WinnerResult( int weekNumber, DDPPick ddpPick, int totalPoints, NFLTeam[ ] teams, Integer[ ] scores ){

        this.isWinner       = new AtomicBoolean( );        
        this.weekNumber     = weekNumber;
        this.winPick        = ddpPick;
        this.totalScore     = totalPoints;
        this.totalFormatted = formatTotalScore(totalPoints);
        this.team1          = (teams!= null && teams.length >= 1) ? teams[0] : null;
        this._1Score        = formatWeeklyScore( scores, 0 );
        this.team2          = (teams!= null && teams.length >= 2) ? teams[1] : null;
        this._2Score        = formatWeeklyScore( scores, 1 );
        this.team3          = (teams!= null && teams.length >= 3) ? teams[2] : null;
        this._3Score        = formatWeeklyScore( scores, 2 );
        
        this.winImageLink   = WIN_IMAGE_PREFIX + WEEK_NAME + weekNumber + WIN_IMAGE_SUFFIX;
        this.winImageName   = WEEK_NAME + weekNumber;
        
    }


    public final boolean isWinner( ){
        return isWinner.get( );
    }
    
    
    public final int getWeekNumber( ){
        return weekNumber;
    }
    
    
    public final DDPPick getDDPPick( ){
        return winPick;
    }

    
    public final DDPPlayer getPlayer( ){
        return winPick.getPlayer( );
    }
     
    
    public final int getTotalScore( ){
        return totalScore;
    }

    
    public final String getTotalFormattedScore( ){
        return totalFormatted;
    }
    

    public final NFLTeam getTeam1( ){
        return team1;
    }


    public final NFLTeam getTeam2( ){
        return team2;
    }


    public final NFLTeam getTeam3( ){
        return team3;
    }


    public final String get_1Score( ){
        return _1Score;
    }


    public final String get_2Score( ){
        return _2Score;
    }


    public final String get_3Score( ){
        return _3Score;
    }

    
    public final String getCardLink( ){
        return winImageLink;
    }
    
    
    public final String getCardName( ){
        return winImageName;
    }
    
    
    public final void markWinner( ){
        isWinner.set( true );
    }
        

    private final String formatWeeklyScore( Integer[ ] scores, int index ) {
        if( scores == null || scores.length < (index+1) ) {
            return "";
        }
        
        int score = scores[index];
        return ( score < 10) ? "0"+score : String.valueOf(score);
    }
    
    
    private static final String formatTotalScore( int totalPoints ){
        
        if( totalPoints < 10 ) {
            return "00" + totalPoints; 
                    
        }else if( totalPoints > 10 && totalPoints < 100 ) {
            return "0" + totalPoints;
        }
        
        return String.valueOf(totalPoints);
    }
    
    
    public final String getWeekNumberHtml( ){

        StringBuilder builder = new StringBuilder( );
        
        if( isWinner( ) ){
            builder.append( "<a href=\"" ).append ( winImageLink ).append( "\"");
            builder.append( " class=\"cashLink\">" );
            builder.append( " Week " ).append( weekNumber ).append( COLON );
            builder.append( "</a>" );
            
        }else {
            builder.append( "Week " ).append(  weekNumber ).append( COLON );
        }
           
        return builder.toString( );
            
    }
    
    

    @Override
    public final String toString( ){
        StringBuilder builder = new StringBuilder( 128 );
        builder.append( "WinnerResult [Player=" ).append( winPick.getPlayer( ).getName( ) ).append( ", Week=" ).append( weekNumber );
        builder.append( ", isWinner=" ).append( isWinner( ) );        
        builder.append( ", TotalScore=" ).append( totalScore );
        builder.append( ", Team1=" ).append( team1.getLowerCaseName( ) );
        builder.append( ", Score=" ).append( _1Score );
        builder.append( ", Team2=" ).append( team2.getLowerCaseName( ) );
        builder.append( ", Score=" ).append( _2Score );
        builder.append( ", Team3=" ).append( team3.getLowerCaseName( ) );
        builder.append( ", Score=" ).append( _3Score );
        
        return builder.toString( );
    }



}
