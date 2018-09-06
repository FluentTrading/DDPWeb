package com.ddp.nfl.web.match;

import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.parser.*;


public final class MatchManager{
    

    public final static GameResultManager packData( DDPMeta meta, Map<NFLTeam, MatchScore> scoreMap, DBService service ){
        
        List<GameResult> resultList     = new LinkedList<>( );
        Map<Integer, DDPPick> pickMap   = service.getOrderedPicks( );
                
        //For Each DDP Player
        for( DDPPick pick : pickMap.values( ) ){
            GameResult result   = packData( pick, scoreMap );
            resultList.add( result );
        }
        
        resultList                      = GameResultSorter.sortGameResult( resultList );
        GameResultManager resultManager = GameResultManager.createValid( meta, resultList );
        
        return resultManager;
                
    }
    
    public final static GameResult packData( DDPPick pick, Map<NFLTeam, MatchScore> scoreMap ){

        int index                       = 0;
        Set<String> uniqueTeamsSet      = new HashSet<>( );
        NFLTeam[] myPickedTeams         = pick.getTeams( );
        MatchScore[] myScores         = new MatchScore[ myPickedTeams.length ];
        
        for( NFLTeam myTeam : myPickedTeams ){
            
            MatchScore score      = scoreMap.get( myTeam );
            if( score != null ) {
                myScores[ index ]   = score;
                ++index;

                uniqueTeamsSet.add( score.getHomeTeam( ).getName( ) );
                uniqueTeamsSet.add( score.getAwayTeam( ).getName( ) );
            }
                    
        }

        boolean hasAll6Teams    = (uniqueTeamsSet.size( ) == (myPickedTeams.length * 2));
        GameResult gameResult   = new GameResult( hasAll6Teams, pick, myScores );
    
        return gameResult;
        
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
