package com.ddp.nfl.web.match;

import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.data.model.parser.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.winnings.*;


public final class EspnGameResultFactory{
    
    
    public final static EspnGameResultManager packData( DDPMeta meta, Map<NFLTeam, EspnLiveScore> scoreMap, DBService dbService, WinnerManager cashManager ){
        
        List<EspnGameResult> resultList = new LinkedList<>( );
        Map<Integer, DDPPick> pickMap   = dbService.getOrderedPicks( );
                
        //For Each DDP Player
        for( DDPPick pick : pickMap.values( ) ){
        	EspnGameResult result   = packData( meta.getGameWeek( ), pick, scoreMap, cashManager );
        	resultList.add( result );

        	if( pick.getPlayer().getName().equalsIgnoreCase("raj")) {
        		//System.out.println( "State 2: " + result.getGame2LiveScore().);
        		//System.out.println( "1: " + result.getGame1LiveScore().getSchedule() + ", " + result.getGame1Stadium() );
        		//System.out.println( "2: " + result.getGame2LiveScore().getSchedule() + ", " + result.isGame2NotStarted() + ", " + result.getGame2Stadium() );
        		
        		//System.out.println( "Bucs: " + result.getGame1LiveScore().getHomeTeamInfo().getRecord()  );
        		//System.out.println( "Cowboys: " + result.getGame1LiveScore().getAwayTeamInfo().getRecord()  );
        //		System.out.println( "1: NotStarted: " + result.isGame1NotStarted() + ", Fin: " + result.isGame1Finished() + ", Live: " + result.isGame1Playing() + ", " + result.getGame1LiveScore().getGameState());
        	}
        	
        	
        
        }
        
        resultList                      	= sortGameResult( resultList );
        EspnGameResultManager resultManager = EspnGameResultManager.createValid( meta, resultList );
        
        return resultManager;
                
    }
    
    
    public final static EspnGameResult packData( int weekNumber, DDPPick pick, Map<NFLTeam, EspnLiveScore> scoreMap, WinnerManager cashManager ){

        int index                   = 0;
        Set<String> uniqueTeamsSet  = new HashSet<>( );
        NFLTeam[] myPickedTeams     = pick.getTeams( );
        EspnLiveScore[] myScores    = new EspnLiveScore[ myPickedTeams.length ];
        
        for( NFLTeam myTeam : myPickedTeams ){
            
        	EspnLiveScore score     = scoreMap.get( myTeam );
            if( score != null ) {
                myScores[ index ]   = score;
                ++index;

                if( score.getHomeTeam() != null ) {
                	uniqueTeamsSet.add( score.getHomeTeam( ).getLowerCaseName( ) );
                }else {
                	uniqueTeamsSet.add( "Unknown");
                }
                
                if( score.getAwayTeam() != null ) {
                	uniqueTeamsSet.add( score.getAwayTeam( ).getLowerCaseName( ) );
                	uniqueTeamsSet.add( "Unknown");
                }
            }
                    
        }
        
        int weeklyTotalScore    = cashManager.getPlayTotalScoreMap( ).get( pick.getPlayer( ) );

        EspnGameResult gameResult   = new EspnGameResult( weekNumber, pick, myScores, weeklyTotalScore );
    
        return gameResult;
        
    }
    
    
   protected final static List<EspnGameResult> sortGameResult( List<EspnGameResult> unsortedList ){
        
        // Sorting the list based on player's combined score.
        Collections.sort( unsortedList, new Comparator<EspnGameResult>(){
           
            public int compare( EspnGameResult o1, EspnGameResult o2 ){
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
