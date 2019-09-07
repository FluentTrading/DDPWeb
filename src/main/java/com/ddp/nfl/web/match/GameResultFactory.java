package com.ddp.nfl.web.match;

import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.parser.*;
import com.ddp.nfl.web.winnings.*;


public final class GameResultFactory{
    
    
    public final static GameResultManager packData( DDPMeta meta, Map<NFLTeam, LiveScore> scoreMap, DBService dbService, WinnerManager cashManager ){
        
        List<GameResult> resultList     = new LinkedList<>( );
        Map<Integer, DDPPick> pickMap   = dbService.getOrderedPicks( );
                
        //For Each DDP Player
        for( DDPPick pick : pickMap.values( ) ){
            GameResult result   = packData( meta.getGameWeek( ), pick, scoreMap, cashManager );
            resultList.add( result );
        }
        
        resultList                      = sortGameResult( resultList );
        GameResultManager resultManager = GameResultManager.createValid( meta, resultList );
        
        return resultManager;
                
    }
    
    
    public final static GameResult packData( int weekNumber, DDPPick pick, Map<NFLTeam, LiveScore> scoreMap, WinnerManager cashManager ){

        int index                   = 0;
        Set<String> uniqueTeamsSet  = new HashSet<>( );
        NFLTeam[] myPickedTeams     = pick.getTeams( );
        LiveScore[] myScores        = new LiveScore[ myPickedTeams.length ];
        
        for( NFLTeam myTeam : myPickedTeams ){
            
            LiveScore score         = scoreMap.get( myTeam );
            if( score != null ) {
                myScores[ index ]   = score;
                ++index;

                uniqueTeamsSet.add( score.getHomeTeam( ).getLowerCaseName( ) );
                uniqueTeamsSet.add( score.getAwayTeam( ).getLowerCaseName( ) );
            }
                    
        }
        
        int weeklyTotalScore    = cashManager.getPlayTotalScoreMap( ).get( pick.getPlayer( ) );

        GameResult gameResult   = new GameResult( weekNumber, pick, myScores, weeklyTotalScore );
    
        return gameResult;
        
    }
    
    
   protected final static List<GameResult> sortGameResult( List<GameResult> unsortedList ){
        
        // Sorting the list based on player's combined score.
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
