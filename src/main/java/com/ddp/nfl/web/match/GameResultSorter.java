package com.ddp.nfl.web.match;

import java.util.*;



public final class GameResultSorter {
    
    public final static List<GameResult> sortGameResult( List<GameResult> unsortedList ){
        
        // Sorting the list based on values
        Collections.sort( unsortedList, new Comparator<GameResult>(){
           
            public int compare( GameResult o1, GameResult o2 ){
                int points1 = o1.getHomeTotalScore( );
                int points2 = o2.getHomeTotalScore( );
                
                if( points1 != points2 ){
                    return ( points1 > points2) ? -1 : 1;

                }else {

                    if( o1.getPick( ).getPickOrder( ) > o2.getPick( ).getPickOrder( ) ){
                        return -1;
                    }else{
                        return 1;
                    }
                }
                
            }
        
        });

        return unsortedList;
        
    }
    
}
