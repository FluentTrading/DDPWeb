package com.ddp.nfl.web.winnings;

import java.util.*;
import com.ddp.nfl.web.core.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class WinnerSummary{
    
    private final int totalScore;
    private final String fmtTotalCash;
    private final DDPPlayer player;
    private final int totalWeeksWon;
    private final String fmtWeeksWon;
    private final List<WinnerResult> results;
    
    public WinnerSummary( DDPMeta ddpMeta, DDPPlayer player, int totalScore, Set<Integer> weeksT1Won, Set<Integer> weeksT2Won, List<WinnerResult> results ){
        this.totalScore     = totalScore;
        this.player         = player;
        this.results        = results;
        this.totalWeeksWon  = weeksT1Won.size( ) + weeksT2Won.size( );
        this.fmtWeeksWon    = formatWeeksWon( weeksT1Won, weeksT2Won );
        this.fmtTotalCash   = formatTotalCash( ddpMeta, player, weeksT1Won, weeksT2Won );
    
    }

    
    public final int getTotalScore( ) {
        return totalScore;
    }

    public final DDPPlayer getPlayer( ) {
        return player;
    }

    public final int getTotalWeeksWon( ) {
        return totalWeeksWon;
    }
    
    public final String getFmtWeeksWon( ) {
        return fmtWeeksWon;
    }
    

    public final String getTotalCashFormatted( ) {
        return fmtTotalCash;
    }
    
    
    public final List<WinnerResult> getResults( ) {
        return results;
    }
    
    
    private final String formatTotalCash( DDPMeta ddpMeta, DDPPlayer player, Set<Integer> weeksT1Won, Set<Integer> weeksT2Won ){
        
        int totalCashEarned     = 0;
        
        if( player.isTier1( ) ) {
            totalCashEarned     += weeksT1Won.size( ) * (ddpMeta.getCashPerWeekT1( ) + ddpMeta.getCashPerWeekT2( ));
            totalCashEarned     += weeksT2Won.size( ) * (ddpMeta.getCashPerWeekT1( ));
        }else {
            totalCashEarned     += weeksT2Won.size( ) * (ddpMeta.getCashPerWeekT2( ));
        }
        
        String fmtCash          = (totalCashEarned == ZERO) ? "$00" : "$"+totalCashEarned;
        
        return fmtCash;
    }
    
        
    private final String formatWeeksWon( Set<Integer> weeksT1Won, Set<Integer> weeksT2Won ){
        StringBuilder builder = new StringBuilder( );
        for( Integer week : weeksT1Won ) {
            builder.append(  week ).append( SPACE );
        }
        
        for( Integer week : weeksT2Won ) {
            builder.append(  week ).append( SPACE );
        }
        
        return builder.toString( );
    }


    @Override
    public String toString( ) {
        StringBuilder builder = new StringBuilder( );
        builder.append( "WinnerSummary [totalScore=" ).append( totalScore ).append( ", fmtTotalCash=" ).append( fmtTotalCash ).append( ", player=" ).append( player ).append( ", weeksWon=" ).append( fmtWeeksWon ).append( ", results=" ).append( results ).append( "]" );
        return builder.toString( );
    }
        

}
