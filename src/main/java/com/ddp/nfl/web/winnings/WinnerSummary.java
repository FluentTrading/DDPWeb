package com.ddp.nfl.web.winnings;

import java.util.*;
import com.ddp.nfl.web.core.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class WinnerSummary{
    
    private final int totalScore;
    private final int totalCash;
    private final DDPPlayer player;
    private final String weeksWon;
    private final List<WinnerResult> results;
    
    public WinnerSummary( DDPPlayer player, int totalScore, Set<Integer> weeksWon, List<WinnerResult> results ){
        this.totalScore = totalScore;
        this.player = player;
        this.totalCash = weeksWon.size( ) * 40;
        this.weeksWon = formatWeeksWon(weeksWon);
        this.results = results;
    }

    
    public final int getTotalScore( ) {
        return totalScore;
    }

    public final DDPPlayer getPlayer( ) {
        return player;
    }


    public final String getWeeksWon( ) {
        return weeksWon;
    }
    

    public final int getTotalCash( ) {
        return totalCash;
    }
    
    
    public final List<WinnerResult> getResults( ) {
        return results;
    }
    
        
    private final String formatWeeksWon( Set<Integer> weeksWon ){
        StringBuilder builder = new StringBuilder( );
        for( Integer week : weeksWon ) {
            builder.append(  week ).append( SPACE );
        }
        
        return builder.toString( );
    }
        

}
