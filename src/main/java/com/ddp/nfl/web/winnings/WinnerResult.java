package com.ddp.nfl.web.winnings;

import com.ddp.nfl.web.core.*;
import java.util.concurrent.atomic.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class WinnerResult{
    
    private final DDPPick winPick;
    private final int weekNumber;
    private final int totalScore;
    private final AtomicBoolean isWinner;
    
    private final NFLTeam team1;
    private final int _1Score;
    
    private final NFLTeam team2;
    private final int _2Score;
    
    private final NFLTeam team3;
    private final int _3Score;
    
    private final String cardLink;
    private final String cardName;
                
    private final static String WEEK_NAME   = "Week";
    private final static String CARD_PREFIX = "images/winnings/";
    private final static String CARD_SUFFIX = ".jpg";
    
    
    public WinnerResult( int weekNumber, DDPPick ddpPick, int totalPoints, NFLTeam[ ] teams, Integer[ ] scores ){

        this.isWinner       = new AtomicBoolean( );
        this.weekNumber     = weekNumber;
        this.winPick        = ddpPick;
        this.totalScore     = totalPoints;
        this.team1          = teams[0];
        this._1Score        = scores[0];
        this.team2          = teams[1];
        this._2Score        = scores[1];
        this.team3          = teams[2];
        this._3Score        = scores[2];
        
        this.cardLink       = CARD_PREFIX + WEEK_NAME + weekNumber + CARD_SUFFIX;
        this.cardName       = WEEK_NAME + weekNumber;
        
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


    public final NFLTeam getTeam1( ){
        return team1;
    }


    public final NFLTeam getTeam2( ){
        return team2;
    }


    public final NFLTeam getTeam3( ){
        return team3;
    }


    public final int get_1Score( ){
        return _1Score;
    }


    public final int get_2Score( ){
        return _2Score;
    }


    public final int get_3Score( ){
        return _3Score;
    }

    
    public final String getCardLink( ){
        return cardLink;
    }
    
    
    public final String getCardName( ){
        return cardName;
    }
    
    
    public final void markWinner( ){
        isWinner.set( true );
    }
        
    
    public final String getWeekNumberHtml( ){

        StringBuilder builder = new StringBuilder( );
        
        if( isWinner( ) ) {
            builder.append( "<a href=\"" ).append ( cardLink ).append( "\"");
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
