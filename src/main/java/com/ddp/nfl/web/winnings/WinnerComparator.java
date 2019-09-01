package com.ddp.nfl.web.winnings;

import java.util.*;

public final class WinnerComparator implements Comparator<WinnerResult>{
    
    @Override
    public final int compare( WinnerResult z1, WinnerResult z2 ){

        if (z1.getTotalScore( ) > z2.getTotalScore( ))
            return -1;
        
        if (z1.getTotalScore( ) < z2.getTotalScore( ))
            return 1;
        
        //Else it is a tie, check the pick order
        if( z1.getTotalScore( ) == z2.getTotalScore( ) ) {
            if( z1.getDDPPick( ).getPickOrder( ) < z2.getDDPPick( ).getPickOrder( ) ) {
                return 1;
            }else {
                return -1;
            }
        }
        
    
        return 0;
    }
    

}
