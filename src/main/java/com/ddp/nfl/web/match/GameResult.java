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
    
    public GameResult( DDPPick pick, LiveScore[ ] myScores, int weeklyTotalScore ){
        this.pick           = pick;
        this.myPickedTeams  = pick.getTeams( );
        this.myScores       = myScores;
        this.allTotalScore  = getHomeTotalScore( ) + weeklyTotalScore;
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
    
    
    
    public final int getMy1TeamScore( ){
        NFLTeam myTeam    = getMy1Team( );
        LiveScore info    = getMatch1Score( );
        int myTeamScore   = getScore( true, myTeam, info);
        
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
    
    
    public final int getHomeTotalScore( ) {
        return getMy1TeamScore( ) + getMy2TeamScore( ) + getMy3TeamScore( );
    }
    
   
    //Cumulative total
    public final int getAllTotalHomeScore( ) {
        return allTotalScore;
    }   
    
        
    //AWAY

    
    
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
      
    //-----------------------
    
    public final String getGame1MsgInfoClass( ){
        return getMessageInfoClassName( getMatch1Score( ), getMy1TeamScore( ), getOpp1TeamScore( ) );
    }
    
    public final String getGame2MsgInfoClass( ){
        return getMessageInfoClassName( getMatch2Score( ), getMy2TeamScore( ), getOpp2TeamScore( ) );
    }
        
    public final String getGame3MsgInfoClass( ){
        return getMessageInfoClassName( getMatch3Score( ), getMy3TeamScore( ), getOpp3TeamScore( ) );
    }   
      
        
    //UI shouldn't directly invoke these methods.
    //-----------------------------------------------------------
    
    //If home team has possession, adds a blinking green dot next to the name
    protected final String getTeamWithPossessionBlinker( NFLTeam team, LiveScore liveScore ){
        
        if( team == null ) return EMPTY;
        if( liveScore == null ) return EMPTY;
        
        String teamName         = team.getCamelCaseName( );
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
    
    
    protected final String getMessageInfoClassName( LiveScore liveScore, int homeScore, int awayScore ){
    
        if( GameState.isNotStarted( liveScore ) ){
            return INFO_BAR_GAME_PENDING;
        }
        
        if( GameState.isFinished( liveScore ) ){
            return INFO_BAR_GAME_FINISHED;
        }
                
        if( homeScore == awayScore ){
            return INFO_BAR_GAME_TIED;
        }
        
        if( homeScore > awayScore ){
            return INFO_BAR_GAME_WON;
        }
        
        return INFO_BAR_GAME_LOST;
        
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
    
}
