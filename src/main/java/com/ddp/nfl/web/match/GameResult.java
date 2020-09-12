package com.ddp.nfl.web.match;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.parser.*;
import com.ddp.nfl.web.util.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class GameResult{
    
    private final DDPPick pick;
    private final LiveScore[] myScores;
    private final NFLTeam[] myPickedTeams;
    private final int allTotalScore;
    
    public GameResult( int weekNumber, DDPPick pick, LiveScore[ ] myScores, int weeklyTotalScore ){
        this.pick           = pick;
        this.myPickedTeams  = pick.getTeams( );
        this.myScores       = myScores;
        this.allTotalScore  = calculateAllTotal( weekNumber, weeklyTotalScore );
    }


    public final DDPPick getPick( ) {
        return pick;
    }
    
    
    public final DDPPlayer getPlayer( ) {
        return pick.getPlayer( );
    }
        
        
    public final String getMy1TeamName( ){
        return getTeamWithPossessionBlinker( getMy1Team( ), getMatch1Score( ) );
    }
    
        
    public final String getMy2TeamName( ){
        return getTeamWithPossessionBlinker( getMy2Team( ), getMatch2Score( ) );
    }
    
    
    public final String getMy3TeamName( ){
        return getTeamWithPossessionBlinker( getMy3Team( ), getMatch3Score( ) );
    }
    
    
    public final String getOpp1TeamName( ){
        return getTeamWithPossessionBlinker( getOpp1Team( ), getMatch1Score( ) );
    }
    
    public final String getOpp2TeamName( ){
        return getTeamWithPossessionBlinker( getOpp2Team( ), getMatch2Score( ) );
    }
    
    
    public final String getOpp3TeamName( ){
        return getTeamWithPossessionBlinker( getOpp3Team( ), getMatch3Score( ) );         
    }
    
    
    
    public final String getMy1TeamIcon( ){
        return getTeamIcon( getMy1Team( ));
    }

    
    public final String getMy2TeamIcon( ){
        return getTeamIcon( getMy2Team( ));
    }

    
    public final String getMy3TeamIcon( ){
        return getTeamIcon( getMy3Team( ));
    }
    


    public final String getOpp1TeamIcon( ){
        return getTeamIcon( getOpp1Team( ));
    }

        
    public final String getOpp2TeamIcon( ){
        return getTeamIcon( getOpp2Team( ));
    }


    public final String getOpp3TeamIcon( ){
        return getTeamIcon( getOpp3Team( ));
    }
        
        
    public final int getMy1TeamScore( ){
        NFLTeam myTeam    = getMy1Team( );
        LiveScore info    = getMatch1Score( );
        int myTeamScore   = getScore( true, myTeam, info );
        
        return myTeamScore;
    }
        
    
    public final int getMy2TeamScore( ){
        NFLTeam myTeam    = getMy2Team( );
        LiveScore info    = getMatch2Score( );
        int myTeamScore   = getScore( true, myTeam, info);
        
        return myTeamScore;
    }
    
    public final int getMy3TeamScore( ){
        NFLTeam myTeam    = getMy3Team( );
        LiveScore info    = getMatch3Score( );
        int myTeamScore   = getScore( true, myTeam, info);
        
        return myTeamScore;
    }
    
    
    public final String getMyGame1ScoreWithPossssion( boolean isHome ){
        int teamScore   = ( isHome ) ? getMy1TeamScore( ) : getOpp1TeamScore( );
        NFLTeam myTeam  = ( isHome ) ? getMy1Team( ) : getOpp1Team( ); 
        
        return getMyGameScoreWithPossssion( teamScore, getMatch1Score( ), myTeam );
    }
    
    

    public final String getMyGame2ScoreWithPossssion( boolean isHome ){
        int teamScore   = ( isHome ) ? getMy2TeamScore( ) : getOpp2TeamScore( );
        NFLTeam myTeam  = ( isHome ) ? getMy2Team( ) : getOpp2Team( ); 
        
        return getMyGameScoreWithPossssion( teamScore, getMatch2Score( ), myTeam );
    }
    
    

    public final String getMyGame3ScoreWithPossssion( boolean isHome ){
        int teamScore   = ( isHome ) ? getMy3TeamScore( ) : getOpp3TeamScore( );
        NFLTeam myTeam  = ( isHome ) ? getMy3Team( ) : getOpp3Team( ); 
        
        return getMyGameScoreWithPossssion( teamScore, getMatch3Score( ), myTeam );
    }
    
        
    public final int getHomeTotalScore( ) {
        return getMy1TeamScore( ) + getMy2TeamScore( ) + getMy3TeamScore( );
    }
    
   
    //Cumulative total
    public final int getAllTotalHomeScore( ) {
        return allTotalScore;
    }   
    
        
    //AWAY
    //----------------------------------------------------------------------
    
    public final int getOpp1TeamScore( ){
        LiveScore info   = getMatch1Score( );
        int myTeamScore  = getScore( false, getMy1Team( ), info);
        
        return myTeamScore;
    }
    
    public final int getOpp2TeamScore( ){
        LiveScore info   = getMatch2Score( );
        int myTeamScore  = getScore( false, getMy2Team( ), info);
        
        return myTeamScore;
    }
    
    public final int getOpp3TeamScore( ){
        LiveScore info   = getMatch3Score( );
        int myTeamScore  = getScore( false, getMy3Team( ), info);
        
        return myTeamScore;
    }
    

    public final int getAwayTotalScore( ) {
        return getOpp1TeamScore( ) + getOpp2TeamScore( ) + getOpp3TeamScore( );
    }
    
    
    public final String getGame1NotStartedMessage( ){
        return getGameNotStartedMessage( getMatch1Score( ) );
    }
    
    
    public final String getGame2NotStartedMessage( ){
        return getGameNotStartedMessage( getMatch2Score( ) );
    }
    
    
    public final String getGame3NotStartedMessage( ){
        return getGameNotStartedMessage( getMatch3Score( ) );
    }
    
    
    public final String getGameNotStartedMessage( LiveScore score ){
        if( score == null ) return "Missing";
        return score.getAwayTeam( ).getNickName( ) + " at " +
                score.getHomeTeam( ).getNickName( ) + " " +
                score.getGameTime( );

    }
    
    
    public final String getGame1FinishedMessage( ){
        return getGameFinishedMessage( getMy1TeamScore( ), getOpp1TeamScore( ), getMatch1Score( ) );
    }
    
    
    public final String getGame2FinishedMessage( ){
        return getGameFinishedMessage(  getMy2TeamScore( ), getOpp2TeamScore( ),getMatch2Score( ) );
    }
    
    
    public final String getGame3FinishedMessage( ){
        return getGameFinishedMessage(  getMy3TeamScore( ), getOpp3TeamScore( ),getMatch3Score( ) );
    }
    
    public final String getGameFinishedMessage( int myScore, int awayScore, LiveScore score ){
        if( score == null ) return "Missing";
                
        if( myScore > awayScore ){
            return score.getHomeTeam( ).getCamelCaseName( ) + " won by " + (myScore-awayScore);
        }else if( myScore == awayScore ){
            return  "Game tied";
        }else {
            return score.getAwayTeam( ).getCamelCaseName( ) + " lost by " + Math.abs(myScore-awayScore);
        }
    }
    
    
    public final boolean didMyTeamWin( int myScore, int awayScore ){
        return ( myScore > awayScore );
    }
    
    
    public final String getGame1Quarter( ){
        return getMatch1Score( ).getDisplayableQuarter( );        
    }
    
    public final String getGame2Quarter( ){
        return getMatch2Score( ).getDisplayableQuarter( );        
    }
    
    
    public final String getGame3Quarter( ){
        return ( getMatch3Score( ) == null ) ? EMPTY : getMatch1Score( ).getDisplayableQuarter( );
    }
    

    public final String getGame1WinnerIcon( ){
        return getGameWinnerIcon( getMatch1Score( ), getMy1TeamScore( ), getMy1Team( ), getOpp1TeamScore( ), getOpp1Team( ) );
    }
    
    
    public final String getGame2WinnerIcon( ){
        return getGameWinnerIcon( getMatch2Score( ), getMy2TeamScore( ), getMy2Team( ), getOpp2TeamScore( ), getOpp2Team( ) );
    }
    
    
    public final String getGame3WinnerIcon( ){
        return getGameWinnerIcon( getMatch3Score( ), getMy3TeamScore( ), getMy3Team( ), getOpp3TeamScore( ), getOpp3Team( ) );
    }
    
    
    public final boolean isGame1Finished( ){
        return GameState.isFinished(getMatch1Score( ));
    }
    
    public final boolean isGame2Finished( ){
        return GameState.isFinished(getMatch2Score( ));
    }
    
    public final boolean isGame3Finished( ){
        return GameState.isFinished(getMatch3Score( ));
    }
    
    
    public final boolean isGame1NotStarted( ){
        return GameState.isNotStarted(getMatch1Score( ));
    }
    
    public final boolean isGame2NotStarted( ){
        return GameState.isNotStarted(getMatch2Score( ));
    }
    
    public final boolean isGame3NotStarted( ){
        return GameState.isNotStarted(getMatch3Score( ));
    }

    
    public final boolean isGame1Playing( ){
        return GameState.isPlaying(getMatch1Score( ));
    }
    
    public final boolean isGame2Playing( ){
        return GameState.isPlaying(getMatch2Score( ));
    }
    
    public final boolean isGame3Playing( ){
        return GameState.isPlaying(getMatch3Score( ));
    }
    
    
      
    //-----------------------

    public final String getMatch1TvStation( ){
        return getMatch1Score().getTVStation( );
    }
    
    public final String getMatch2TvStation( ){
        return getMatch2Score().getTVStation( );
    }
    
    public final String getMatch3TvStation( ){
        return getMatch3Score().getTVStation( );
    }
        
    
    public final String getGame1ScoreDivClass( boolean isHome ){
        if( isHome ) {
            return getScoreDivClassName( getMatch1Score( ), getMy1TeamScore( ), getOpp1TeamScore( ) );
        }else {
            return getScoreDivClassName( getMatch1Score( ), getOpp1TeamScore( ), getMy1TeamScore( ) );
        }
    }
    
    
    public final String getGame2ScoreDivClass( boolean isHome ){
        if( isHome ) {
            return getScoreDivClassName( getMatch2Score( ), getMy2TeamScore( ), getOpp2TeamScore( ) );
        }else {
            return getScoreDivClassName( getMatch2Score( ), getOpp2TeamScore( ), getMy2TeamScore( ) );
        }
    }
    
    
    public final String getGame3ScoreDivClass( boolean isHome ){
        if( isHome ) {
            return getScoreDivClassName( getMatch3Score( ), getMy3TeamScore( ), getOpp3TeamScore( ) );
        }else {
            return getScoreDivClassName( getMatch3Score( ), getOpp3TeamScore( ), getMy3TeamScore( ) );
        }
    }
    
    
    public final String getGame1ResultDivClasss( ){
        return getResultCellDivClassName( getMatch1Score( ), getMy1TeamScore( ), getOpp1TeamScore( ) );
    }
    
    public final String getGame2ResultDivClasss( ){
        return getResultCellDivClassName( getMatch2Score( ), getMy2TeamScore( ), getOpp2TeamScore( ) );
    }
        
    public final String getGame3ResultDivClasss( ){
        return getResultCellDivClassName( getMatch3Score( ), getMy3TeamScore( ), getOpp3TeamScore( ) );
    }  
    
        
    //UI shouldn't directly invoke these methods.
    //-----------------------------------------------------------
    
    protected final String getTeamIcon( NFLTeam team ){
        if( team == null ) return EMPTY;
        return team.getSquareTeamIcon( );        
    }
    
    
    protected final String getMyGameScoreWithPossssion( int teamScore, LiveScore score, NFLTeam myTeam ){
        if( GameState.isPlaying(score) ){
            if( teamHasPossession( myTeam, score ) ) {
                if( score.isRedzone( ) ) {
                    return "<div class=\"blink_me\"><i class=\"fas fa-football-ball fa-xs\"> </i> " + teamScore + "</div>";        
                }else {
                    return "<i class=\"fas fa-football-ball fa-xs\"> </i> " + teamScore;
                }
                
            //Playing but do not have possession.    
            }else {
                return String.valueOf(  teamScore );    
            }
            
        }else {
            return String.valueOf(  teamScore );
        }
                
    }
    
    
    //If home team has possession, adds a blinking green dot next to the name
    protected final String getTeamWithPossessionBlinker( NFLTeam team, LiveScore liveScore ){
        
        if( team == null ) return EMPTY;
        if( liveScore == null ) return EMPTY;
        
        //String teamName         = team.getCamelCaseName( );
        String teamName         = team.getNickName( );
        boolean delayedOrHalf   = ( GameState.isDelayed(liveScore) || GameState.isHalftime(liveScore) );
        if( delayedOrHalf ) return teamName;
        
        boolean hasPossession   = teamHasPossession( team, liveScore );
        String displayName      = ( hasPossession ) ? (teamName + SPACE + BLINK_GREEN_DOT) : teamName;
        
        return displayName;
        
    }
    
   
    
    
    protected final String getGameWinnerIcon( LiveScore liveScore, int homeScore, NFLTeam home, int awayScore, NFLTeam away ){
        
        if( GameState.isNotStarted(liveScore) ){
            return NFLTeam.getMissingTeamLogo( );
        }
        
        //Game is finished, if home team won, show the team logo, otherwise game lost logo
        if( GameState.isFinished(liveScore) ){
            return ( homeScore > awayScore ) ? home.getSquareTeamIcon( ) : NFLTeam.getGameLostLogo( );
        }        
        
        //Game is going on && Home team is in Redzone
        boolean hasPossession = teamHasPossession( home, liveScore );
        boolean homeRedzone   = ( hasPossession && liveScore.isRedzone( ) );
        if( homeRedzone ){
            return DDPUtil.RED_ZONE_ICON;
        }
                
        //Not in Redzone and tied
        if( homeScore == awayScore ){
            return NFLTeam.getMissingTeamLogo( );
        }
        
        //Not in Redzone, home team is winning
        boolean homeTeamWin  = ( homeScore > awayScore );
        return ( homeTeamWin  ) ? home.getSquareTeamIcon( ) : NFLTeam.getMissingTeamLogo( );
                
    }
    
    
    

    
    
    protected final String getScoreDivClassName( LiveScore liveScore, int homeScore, int awayScore ){
    
        if( GameState.isNotStarted( liveScore ) ){
            return "score-game-not-started";
        
        }else if( homeScore == awayScore ){
            return "score-game-tied";
        
        }else if( homeScore > awayScore ){
            return "score-game-won";
        
        }else {        
            return "score-game-lost";
        }
        
    }
    
    
    protected final String getResultCellDivClassName( LiveScore liveScore, int homeScore, int awayScore ){
        
        if( GameState.isNotStarted( liveScore ) ){
            return "result-cell-not-started";
        
        }else if( homeScore == awayScore ){
            return "result-cell-tied";
        
        }else if( homeScore > awayScore ){
            return "result-cell-won";
            
        }else {
            return "result-cell-lost";
        }
        
    }
    
  
   
    public final NFLTeam getMy1Team( ){
        return myPickedTeams[ZERO];
    }
    
    
    public final NFLTeam getMy2Team( ){
        return myPickedTeams[ONE];
    }
    
    
    public final NFLTeam getMy3Team( ){
        return myPickedTeams[TWO];
    }
    
    
    protected final NFLTeam getOpp1Team( ){
        LiveScore info   = getMatch1Score( );
        NFLTeam oppTeam  = getOpponentTeam( getMy1Team( ), info );
        
        return oppTeam;
    }
    
    protected final NFLTeam getOpp2Team( ){
        LiveScore info   = getMatch2Score( );
        NFLTeam oppTeam  = getOpponentTeam( getMy2Team( ), info );
        
        return oppTeam;
    }
    
    protected final NFLTeam getOpp3Team( ){
        LiveScore info   = getMatch3Score( );
        NFLTeam oppTeam  = getOpponentTeam( getMy3Team( ), info );
        
        return oppTeam;         
    }

        
    protected final int getScore( boolean forHome, NFLTeam myTeam, LiveScore info ){
        
        if( myTeam == null || info == null ) {
            return NEGATIVE_ONE;
        }
        
        if( forHome ) {
            return (myTeam.getId( ) == info.getHomeTeam( ).getId( )) ? info.getHomeScore( ) : info.getAwayScore( );
        }
           
        return (myTeam.getId( ) == info.getHomeTeam( ).getId( )) ? info.getAwayScore( ) : info.getHomeScore( );
        
    }
    
    
    protected final boolean teamHasPossession( NFLTeam myTeam, LiveScore liveScore ){
        
        if( myTeam == null || liveScore == null ) {
            return false;
        }
        
        boolean isPlaying   = GameState.isPlaying( liveScore );
        boolean hasPossess  = myTeam.getNickName( ).equals(liveScore.getPossessionTeam( ));
        if( isPlaying && hasPossess ){
            return true;
        }
           
        return false;
        
    }
    
        
    protected final NFLTeam getOpponentTeam( NFLTeam myTeam, LiveScore info ){
        if( myTeam == null || info == null ) {
            return null;
        }
        
        return (myTeam.getId( ) == info.getHomeTeam( ).getId( )) ? info.getAwayTeam( ) : info.getHomeTeam( );
    }
    
    
    public final LiveScore getMatch1Score( ){
        return myScores[0];        
    }
    
    
    public final LiveScore getMatch2Score( ){
        return myScores[1];        
    }
    
    
    public final LiveScore getMatch3Score( ){
        return myScores[2];        
    }
    
    
    protected final LiveScore[] getLiveScores( ){
        return myScores;        
    }
    
    
    private final int calculateAllTotal( int weekNumber, int weeklyTotalScore ){
        return ( weekNumber == 1 ) ? (getHomeTotalScore( ) ) : (getHomeTotalScore( ) + weeklyTotalScore);
    }

    
    
}
