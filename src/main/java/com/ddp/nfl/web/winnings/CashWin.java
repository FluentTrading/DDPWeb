package com.ddp.nfl.web.winnings;

import com.ddp.nfl.web.core.*;


public final class CashWin{
    
    private final DDPPick winPick;
    private final int weekNumber;
    private final int cashWon;
    private final int totalScore;
        
    private final NFLTeam team1;
    private final int _1Score;
    
    private final NFLTeam team2;
    private final int _2Score;
    
    private final NFLTeam team3;
    private final int _3Score;
    
    private final String cardLink;
    private final String cardName;
    
    private final static String WEEK_NAME   = "Week";
    private final static String CARD_PREFIX = "images/cash/";
    private final static String CARD_SUFFIX = ".jpg";
    
        
    public CashWin( int weekNumber, DDPPick winPick, int cashWon, int totalScore, 
                    NFLTeam team1, int _1Score, NFLTeam team2, int _2Score, NFLTeam team3, int _3Score ){
        
        this.weekNumber     = weekNumber;
        this.winPick        = winPick;
        this.cashWon        = cashWon;
        this.totalScore     = totalScore;
        this.team1          = team1;
        this._1Score        = _1Score;
        this.team2          = team2;
        this._2Score        = _2Score;
        this.team3          = team3;
        this._3Score        = _3Score;
        this.cardLink       = CARD_PREFIX + WEEK_NAME + weekNumber + CARD_SUFFIX;
        this.cardName       = WEEK_NAME + weekNumber;
        
    }

    
    public final int getWeekNumber( ){
        return weekNumber;
    }
    
    
    public final DDPPick getWinPick( ){
        return winPick;
    }

    
    public final DDPPlayer getPlayer( ){
        return winPick.getPlayer( );
    }
     
    
    public final int getCashWon( ){
        return cashWon;
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
    

    @Override
    public final String toString( ){
        StringBuilder builder = new StringBuilder( 128 );
        builder.append( "CashWin [winPick=" ).append( winPick.getPlayer( ).getName( ) ).append( ", Week=" ).append( weekNumber );
        builder.append( ", CashWon=" ).append( cashWon ).append( ", TotalScore=" ).append( totalScore );
        builder.append( ", Team1=" ).append( team1.getLowerCaseName( ) );
        builder.append( ", Score=" ).append( _1Score );
        builder.append( ", Team2=" ).append( team2.getLowerCaseName( ) );
        builder.append( ", Score=" ).append( _2Score );
        builder.append( ", Team3=" ).append( team3.getLowerCaseName( ) );
        builder.append( ", Score=" ).append( _3Score );
        
        return builder.toString( );
    }


}
