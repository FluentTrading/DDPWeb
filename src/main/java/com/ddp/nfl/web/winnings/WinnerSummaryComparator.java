package com.ddp.nfl.web.winnings;

import java.util.*;

public final class WinnerSummaryComparator implements Comparator<WinnerSummary>{
    
    @Override
    public final int compare( WinnerSummary z1, WinnerSummary z2 ){

        if (z1.getTotalScore( ) > z2.getTotalScore( ) )
            return -1;
        
        if (z1.getTotalScore( ) < z2.getTotalScore( ) )
            return 1;
        
        //Else it is a tie, pick the person who has won mos tnumber of weeks.
        if( z1.getTotalScore( ) == z2.getTotalScore( ) ) {
            if( z1.getTotalWeeksWon( ) < z2.getTotalWeeksWon( ) ){
                return 1;
            }else {
                return -1;
            }
        }
        
    
        return 0;
    }
    

}
