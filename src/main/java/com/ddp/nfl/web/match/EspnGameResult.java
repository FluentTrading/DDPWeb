package com.ddp.nfl.web.match;

import com.ddp.nfl.web.analytics.home.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.data.model.parser.*;
import com.ddp.nfl.web.util.*;

import static com.ddp.nfl.web.util.DDPUtil.*;

import java.util.Arrays;


public final class EspnGameResult{
    
    private final DDPPick pick;
    private final EspnLiveScore[] myScores;
    private final NFLTeam[] myPickedTeams;
    private final int allTotalScore;
    
    public EspnGameResult( int weekNumber, DDPPick pick, EspnLiveScore[ ] myScores, int weeklyTotalScore ){
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
    
    public final String getGame1HomeRecord( ) {
        return getGame1LiveScore().getHomeTeamInfo().getRecord();
    }
    
    public final String getGame1AwayRecord( ) {
        return getGame1LiveScore().getAwayTeamInfo().getRecord();
    }
        
    public final String getGame2HomeRecord( ) {
        return getGame2LiveScore().getHomeTeamInfo().getRecord();
    }
    
    public final String getGame2AwayRecord( ) {
        return getGame2LiveScore().getAwayTeamInfo().getRecord();
    }
    
    
    public final EspnLiveScore getGame1LiveScore( ) {
        return myScores[0];
    }
    
    public final EspnLiveScore getGame2LiveScore( ) {
        return myScores[1];
    }
        
        
    public final String getMy1TeamName( ){
        return getTeamWithPossessionBlinker( getMy1Team( ), getMatch1Score( ) );
    }
    
        
    public final String getMy2TeamName( ){
        return getTeamWithPossessionBlinker( getMy2Team( ), getMatch2Score( ) );
    }

    
    public final String getOpp1TeamName( ){
        return getTeamWithPossessionBlinker( getOpp1Team( ), getMatch1Score( ) );
    }
    
    public final String getOpp2TeamName( ){
        return getTeamWithPossessionBlinker( getOpp2Team( ), getMatch2Score( ) );
    }
    
    
    public final String getMy1TeamIcon( ){
        return getTeamIcon( getMy1Team( ));
    }

    
    public final String getMy2TeamIcon( ){
        return getTeamIcon( getMy2Team( ));
    }


    public final String getOpp1TeamIcon( ){
        return getTeamIcon( getOpp1Team( ));
    }

        
    public final String getOpp2TeamIcon( ){
        return getTeamIcon( getOpp2Team( ));
    }

        
    public final int getMy1TeamScore( ){
        NFLTeam myTeam    = getMy1Team( );
        EspnLiveScore info= getMatch1Score( );
        int myTeamScore   = getScore( true, myTeam, info );
        
        return myTeamScore;
    }
        
    
    public final int getMy2TeamScore( ){
        NFLTeam myTeam    = getMy2Team( );
        EspnLiveScore info    = getMatch2Score( );
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
    
    
    public final String getMy1TeamScorePerQuarter( boolean isHome ){        
        return getTeamScorePerQuarter( isHome, getMy1Team( ), getMatch1Score() );
    }
    
    
    public final String getMy2TeamScorePerQuarter( boolean isHome ){
        return getTeamScorePerQuarter( isHome, getMy2Team( ), getMatch2Score() );
    }
       
    
    //Total for a given week  
    public final int getHomeTotalScore( ) {
        return getMy1TeamScore( ) + getMy2TeamScore( );
    }
      
   
    //Cumulative total for all weeks
    public final int getAllTotalHomeScore( ) {
        return allTotalScore;
    }   
    
        
    //AWAY
    //----------------------------------------------------------------------
    
    public final int getOpp1TeamScore( ){
        EspnLiveScore info   = getMatch1Score( );
        int myTeamScore  = getScore( false, getMy1Team( ), info);
        
        return myTeamScore;
    }
    
    public final int getOpp2TeamScore( ){
        EspnLiveScore info   = getMatch2Score( );
        int myTeamScore  = getScore( false, getMy2Team( ), info);
        
        return myTeamScore;
    }


    public final int getAwayTotalScore( ) {
        return getOpp1TeamScore( ) + getOpp2TeamScore( );
    }
    
    
    public final String getGame1NotStartedMessage( ){
        return getGameNotStartedMessage( getMatch1Score( ) );
    }
    
    
    public final String getGame2NotStartedMessage( ){
        return getGameNotStartedMessage( getMatch2Score( ) );
    }

    
    protected final String getGameNotStartedMessage( EspnLiveScore score ){
        if( score == null || score.getHomeTeam( ) == null || score.getAwayTeam( ) == null ) {
        	return "Missing";
        }else {
        	return score.getAwayTeam( ).getNickName( ) + " at " + score.getHomeTeam( ).getNickName( ) + ", " + score.getStadium( );
        }
    }
    
    
    public final String getGame1Stadium( ){
        return ( getMatch1Score( ) == null ? "" : getMatch1Score( ).getStadium( ));
    }
    
    
    public final String getGame2Stadium( ){
        return ( getMatch2Score( ) == null ? "" : getMatch2Score( ).getStadium( ));
    }
    
    
    public final String getGame1FinishedMessage( ){
        return getGameFinishedMessage( getMy1Team( ), getMy1TeamScore( ), getOpp1TeamScore( ) );
    }
    
    
    public final String getGame2FinishedMessage( ){
        return getGameFinishedMessage( getMy2Team( ), getMy2TeamScore( ), getOpp2TeamScore( ) );
    }
    
    
    public final String getGameFinishedMessage( NFLTeam myTeam, int myScore, int awayScore ){
                
        if( myScore > awayScore ){
            return myTeam.getCamelCaseName( ) + " won by " + (myScore-awayScore);
        }else if( myScore == awayScore ){
            return  "Game tied";
        }else {
            return myTeam.getCamelCaseName( ) + " lost by " + Math.abs(myScore-awayScore);
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
    

    public final String getGame1WinnerIcon( ){
        return getGameWinnerIcon( getMatch1Score( ), getMy1TeamScore( ), getMy1Team( ), getOpp1TeamScore( ), getOpp1Team( ) );
    }
    
    
    public final String getGame2WinnerIcon( ){
        return getGameWinnerIcon( getMatch2Score( ), getMy2TeamScore( ), getMy2Team( ), getOpp2TeamScore( ), getOpp2Team( ) );
    }
    
    
    public final boolean isGame1Finished( ){
        return EspnGameState.isFinished(getMatch1Score( ));
    }
    
    public final boolean isGame2Finished( ){
        return EspnGameState.isFinished(getMatch2Score( ));
    }
        
    
    public final boolean isGame1NotStarted( ){
        return EspnGameState.isNotStarted(getMatch1Score( ));
    }
    
    public final boolean isGame2NotStarted( ){
        return EspnGameState.isNotStarted(getMatch2Score( ));
    }
    

    
    public final boolean isGame1Playing( ){
        return EspnGameState.isPlaying(getMatch1Score( ));
    }
    
    public final boolean isGame2Playing( ){
        return EspnGameState.isPlaying(getMatch2Score( ));
    }
    
    
      
    //-----------------------   
    
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
    
    
    
    public final String getGame1ResultDivClasss( ){
        return getResultCellDivClassName( getMatch1Score( ), getMy1TeamScore( ), getOpp1TeamScore( ) );
    }
    
    public final String getGame2ResultDivClasss( ){
        return getResultCellDivClassName( getMatch2Score( ), getMy2TeamScore( ), getOpp2TeamScore( ) );
    }
       
    
        
    //UI shouldn't directly invoke these methods.
    //-----------------------------------------------------------
    
    protected final String getTeamIcon( NFLTeam team ){
        if( team == null ) return EMPTY;
        return team.getSquareTeamIcon( );        
    }

    
    protected final String getMyGameScoreWithPossssion( int teamScore, EspnLiveScore score, NFLTeam myTeam ){
        
        if( teamHasPossession(myTeam, score) ){            
            if( score.isRedzone( ) ) {
                return "<div class=\"blink_me\"><u> " + teamScore + "</u></div>";        
            }else {
                return "<u>" + teamScore + "</u>";
            }                
        }else {
            return String.valueOf(  teamScore );    
        }
                                
    }
    
    
    //If home team has possession, adds a blinking green dot next to the name
    protected final String getTeamWithPossessionBlinker( NFLTeam team, EspnLiveScore liveScore ){
        
        if( team == null ) return EMPTY;
        if( liveScore == null ) return EMPTY;
        
        //String teamName         = team.getCamelCaseName( );
        String teamName         = team.getNickName( );
        boolean delayedOrHalf   = ( EspnGameState.isDelayed(liveScore) || EspnGameState.isHalftime(liveScore) );
        if( delayedOrHalf ) return teamName;
        
        boolean hasPossession   = teamHasPossession( team, liveScore );
        String displayName      = ( hasPossession ) ? (teamName + SPACE + BLINK_GREEN_DOT) : teamName;
        
        return displayName;
        
    }
   
    
    protected final String getGameWinnerIcon( EspnLiveScore liveScore, int homeScore, NFLTeam home, int awayScore, NFLTeam away ){
        
        if( EspnGameState.isNotStarted(liveScore) ){
            return NFLTeam.getMissingTeamLogo( );
        }
        
        //Game is finished, if home team won, show the team logo, otherwise game lost logo
        if( EspnGameState.isFinished(liveScore) ){
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
   
    
    protected final String getScoreDivClassName( EspnLiveScore liveScore, int homeScore, int awayScore ){
    
        if( EspnGameState.isNotStarted( liveScore ) ){
            return "score-game-not-started";
        
        }else if( EspnGameState.isFinished( liveScore ) ){
            return "score-game-finished";
            
        }else if( homeScore == awayScore ){
            return "score-game-tied";
        
        }else if( homeScore > awayScore ){
            return "score-game-won";
        
        }else {        
            return "score-game-lost";
        }
        
    }
    
    
    protected final String getResultCellDivClassName( EspnLiveScore liveScore, int homeScore, int awayScore ){
        
        if( EspnGameState.isNotStarted( liveScore ) ){
            return "result-cell-not-started";
            
        }else if( EspnGameState.isFinished( liveScore ) ){
            return "result-cell-finished";
             
        }else if( homeScore == awayScore ){
            return "result-cell-tied";
        
        }else if( homeScore > awayScore ){
            return "result-cell-won";
            
        }else {
            return "result-cell-lost";
        }
        
    }
    
    
    protected final String getTeamScorePerQuarter( boolean isHome, NFLTeam myTeam, EspnLiveScore score ){
    
        TeamInfo teamInfo           = getTeamInfo( isHome, myTeam, score );
        if( teamInfo == null ){
            return "";
        }
        
        StringBuilder builder       = new StringBuilder();
        boolean isFinishedOrPlaying = EspnGameState.isPlaying( score ) || EspnGameState.isFinished( score ) || EspnGameState.isHalftime( score );
        
        if( isFinishedOrPlaying ){
            builder.append( teamInfo.getQuarterScoreWithPadding( Quarter.FIRST ) )
            .append(DDPUtil.SPACE)
            .append(teamInfo.getQuarterScoreWithPadding( Quarter.SECOND ) )
            .append(DDPUtil.SPACE)
            .append(teamInfo.getQuarterScoreWithPadding( Quarter.THIRD ) )
            .append(DDPUtil.SPACE)
            .append(teamInfo.getQuarterScoreWithPadding( Quarter.FOURTH) );
            
            /*
            if( teamInfo.getOvertimeScore( ) > 0 ){
                builder.append(DDPUtil.SPACE)
                .append(teamInfo.getQuarterScoreWithPadding( Quarter.OVERTIME) );        
            } 
            */               
        }
         
        return builder.toString();
                
    }
    
   
    public final NFLTeam getMy1Team( ){
        return myPickedTeams[ZERO];
    }
    
    
    public final NFLTeam getMy2Team( ){
        return myPickedTeams[ONE];
    }
    
   
    
    protected final NFLTeam getOpp1Team( ){
    	EspnLiveScore info   = getMatch1Score( );
        NFLTeam oppTeam  = getOpponentTeam( getMy1Team( ), info );
        
        return oppTeam;
    }
    
    protected final NFLTeam getOpp2Team( ){
    	EspnLiveScore info   = getMatch2Score( );
        NFLTeam oppTeam  = getOpponentTeam( getMy2Team( ), info );
        
        return oppTeam;
    }
    
        
    protected final int getScore( boolean forHome, NFLTeam myTeam, EspnLiveScore info ){
        
        if( myTeam == null || info == null ) {
            return NEGATIVE_ONE;
        }
        
        if( forHome ) {
        	if( info.getHomeTeam() != null ) {
        		return (myTeam.getId( ) == info.getHomeTeam( ).getId( )) ? info.getHomeScore( ) : info.getAwayScore( );
        	}else {
        		return 0;
        	}
        }
           
        if( info.getHomeTeam() != null ) {
        	return (myTeam.getId( ) == info.getHomeTeam( ).getId( )) ? info.getAwayScore( ) : info.getHomeScore( );
        }else {
        	return 0;
        }
        
    }
    
    
    
    protected final TeamInfo getTeamInfo( boolean forHome, NFLTeam myTeam, EspnLiveScore info ){
        
        if( myTeam == null || info == null ) {
            return null;
        }
        
        if( forHome ) {
        	if( info.getHomeTeam() != null ) {
        		return (myTeam.getId( ) == info.getHomeTeam( ).getId( )) ? info.getHomeTeamInfo( ) : info.getAwayTeamInfo( );
        	}else {
        		return null;
        	}
        }
           
        if( info.getHomeTeam() != null ) {
        	return (myTeam.getId( ) == info.getHomeTeam( ).getId( )) ? info.getAwayTeamInfo( ) : info.getHomeTeamInfo( );
        }else {
        	return null;
        }
        
    }
    
    
    
    protected final boolean teamHasPossession( NFLTeam myTeam, EspnLiveScore liveScore ){
        
        if( myTeam == null || liveScore == null ) {
            return false;
        }
        
        boolean isPlaying   = EspnGameState.isPlaying( liveScore );
        boolean hasPossess  = myTeam.getNickName( ).equals(liveScore.getPossessionTeam( ));
        if( isPlaying && hasPossess ){
            return true;
        }
           
        return false;
        
    }
    
        
    protected final NFLTeam getOpponentTeam( NFLTeam myTeam, EspnLiveScore info ){
        if( myTeam == null || info == null ) {
            return null;
        }
        
        if( info.getHomeTeam() != null && info.getAwayTeam() != null ){
        	return (myTeam.getId( ) == info.getHomeTeam( ).getId( )) ? info.getAwayTeam( ) : info.getHomeTeam( );	
        }else {
        	return null;
        }
        
    }
    
    
    public final EspnLiveScore getMatch1Score( ){
        return myScores[0];        
    }
    
    
    public final EspnLiveScore getMatch2Score( ){
        return myScores[1];        
    }
    
        
    protected final EspnLiveScore[] getLiveScores( ){
        return myScores;        
    }
    
    
    private final int calculateAllTotal( int weekNumber, int weeklyTotalScore ){
        return ( weekNumber == 1 ) ? (getHomeTotalScore( ) ) : (getHomeTotalScore( ) + weeklyTotalScore);
    }


	@Override
	public String toString() {
		return "EspnGameResult [pick=" + pick + ", myScores=" + Arrays.toString(myScores) + ", myPickedTeams="
				+ Arrays.toString(myPickedTeams) + ", allTotalScore=" + allTotalScore + "]";
	}

    
    
}
