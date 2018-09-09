package com.ddp.nfl.web.match;

import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.parser.*;


public final class GameResultFactory{
    
    
    public final static GameResultManager packData( DDPMeta meta, Map<NFLTeam, LiveScore> scoreMap, DBService service ){
        
        List<GameResult> resultList     = new LinkedList<>( );
        Map<Integer, DDPPick> pickMap   = service.getOrderedPicks( );
                
        //For Each DDP Player
        for( DDPPick pick : pickMap.values( ) ){
            GameResult result   = packData( pick, scoreMap );
            resultList.add( result );
        }
        
        resultList                      = sortGameResult( resultList );
        GameResultManager resultManager = GameResultManager.createValid( meta, resultList );
        
        return resultManager;
                
    }
    
    
    public final static GameResult packData( DDPPick pick, Map<NFLTeam, LiveScore> scoreMap ){

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

        boolean hasAll6Teams    = (uniqueTeamsSet.size( ) == (myPickedTeams.length * 2));
        GameResult gameResult   = new GameResult( hasAll6Teams, pick, myScores );
    
        return gameResult;
        
    }
    
    
   protected final static List<GameResult> sortGameResult( List<GameResult> unsortedList ){
        
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
   
    //Andy -> Saints, Chargers, Raiders
    //     -> Saints   21 vs Lions 7
    //     -> Chargers 14 vs Raiders 14
    
    /*
    public final static GameResult packData( DDPPick pick, Map<NFLTeam, NFLGameScore> scoreMap ){

        int index                       = 0;
        Set<String> uniqueTeamsSet      = new HashSet<>( );
        NFLTeam[] myPickedTeams         = pick.getTeams( );
        NFLSingleGameResult[] homeResult= new NFLSingleGameResult[ myPickedTeams.length ];
        NFLSingleGameResult[] awayResult= new NFLSingleGameResult[ myPickedTeams.length ];
        
        for( NFLTeam myTeam : myPickedTeams ){
            
            NFLGameScore score      = scoreMap.get( myTeam );
            if( score == null ) continue;
            
            if( myTeam.getId( ) == score.getHomeTeam( ).getId( ) ) {
                homeResult[index]   = new NFLSingleGameResult( myTeam, score.getHomeScore( ) );
                awayResult[index]   = getOtherTeamResult( myTeam, score );
            }
            
            if( myTeam.getId( ) == score.getAwayTeam( ).getId( ) ) {
                homeResult[index]   = new NFLSingleGameResult( score.getAwayTeam( ), score.getAwayScore( ) );
                awayResult[index]   = getOtherTeamResult( score.getAwayTeam( ), score );
            }

            ++index;
            uniqueTeamsSet.add( score.getHomeTeam( ).getName( ) );
            uniqueTeamsSet.add( score.getAwayTeam( ).getName( ) ); 
                    
        }

        boolean hasAll6Teams    = (uniqueTeamsSet.size( ) == (myPickedTeams.length * 2));
        GameResult gameResult   = new GameResult( hasAll6Teams, pick, homeResult, awayResult );
    
        return gameResult;
        
    }
    
    
    public final static NFLSingleGameResult getOtherTeamResult( NFLTeam myTeam, NFLGameScore score ){
        NFLTeam team = ( myTeam.getId( ) == score.getHomeTeam( ).getId( )) ? score.getAwayTeam( ) : score.getHomeTeam( );
        int teamScore= ( myTeam.getId( ) == score.getHomeTeam( ).getId( )) ? score.getAwayScore( ): score.getHomeScore( );
        
        return new NFLSingleGameResult( team, teamScore );
    }
    
    */
   
}