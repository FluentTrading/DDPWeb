package com.ddp.nfl.web.match;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.parser.*;
import com.ddp.nfl.web.util.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class GameResult{
    
    private final boolean has6Teams;
    private final DDPPick pick;
    private final LiveScore[] myScores;
    private final NFLTeam[] myPickedTeams;
    
        
    public GameResult( boolean hasAll6Teams, DDPPick pick, LiveScore[ ] myScores ){
        this.has6Teams      = hasAll6Teams;
        this.pick           = pick;
        this.myPickedTeams  = pick.getTeams( );
        this.myScores       = myScores;
    }


    public final boolean hasAll6Teams( ){
        return has6Teams;
    }    
    
    
    public final DDPPick getPick( ) {
        return pick;
    }
    
    
    public final DDPPlayer getPlayer( ) {
        return pick.getPlayer( );
    }
    
    
    public final LiveScore[] getLiveScores( ) {
        return myScores;
    }
    
        
    public final String getMy1TeamName( ){
        return getHomeTeamDisplayName( getMy1Team( ), getMatch1Score( ) );
    }
    
        
    public final String getMy2TeamName( ){
        return getHomeTeamDisplayName( getMy2Team( ), getMatch2Score( ) );
    }
    
    
    public final String getMy3TeamName( ){
        return getHomeTeamDisplayName( getMy3Team( ), getMatch3Score( ) );
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
    
        
    //AWAY
    public final String getOpp1TeamName( ){
        return getHomeTeamDisplayName( getOpp1Team( ), getMatch1Score( ) );
    }
    
    public final String getOpp2TeamName( ){
        return getHomeTeamDisplayName( getOpp2Team( ), getMatch2Score( ) );
    }
    
    
    public final String getOpp3TeamName( ){
        return getHomeTeamDisplayName( getOpp3Team( ), getMatch3Score( ) );         
    }
    
    
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
        return getGameQuarterInfo( getMatch1Score( ) );        
    }
    
    public final String getGame2Quarter( ){
        return getGameQuarterInfo( getMatch2Score( ) );        
    }
    
    public final String getGame3Quarter( ){
        
        if( getMatch3Score( ) == null ) {
            return EMPTY;
        }
        
        return getGameQuarterInfo( getMatch3Score( ) );    
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
    
    
    public final String getGame1ScoreClass( ){
        return getScoreClassName( getMatch1Score( ), getMy1TeamScore( ), getOpp1TeamScore( ) );
    }
    
    public final String getGame2ScoreClass( ){
        return getScoreClassName( getMatch2Score( ), getMy2TeamScore( ), getOpp2TeamScore( ) );
    }
        
    public final String getGame3ScoreClass( ){
        return getScoreClassName( getMatch3Score( ), getMy3TeamScore( ), getOpp3TeamScore( ) );
    }   
    
        
    //UI shouldn't directly invoke these methods.
    //-----------------------------------------------------------
    
    //If home team has possession, adds a blinking green dot next to the name
    //
    protected final String getHomeTeamDisplayName( NFLTeam team, LiveScore liveScore ){
        
        if( team == null ) return EMPTY;
        
        String teamName         = team.getCamelCaseName( );
        GameState gameState     = liveScore.getGameState( );
        boolean delayedOrHalf   = ( GameState.DELAYED == gameState || GameState.HALFTIME == gameState );
        if( delayedOrHalf ) return teamName;
        
        boolean hasPossession   = teamHasPossession( team, liveScore );
        String displayName      = ( hasPossession ) ? (teamName + SPACE + BLINK_GREEN_DOT) : teamName;
        return displayName;
        
    }
    
    
    protected final String getGameWinnerIcon( LiveScore liveScore, int homeScore, NFLTeam home, int awayScore, NFLTeam away ){
        
        GameState gameState     = liveScore.getGameState( );
                
        if( GameState.NOT_STARTED == gameState ){
            return NFLTeam.getMissingTeamLogo( );
        }
        
        //Game is finished, if home team won, show the team logo, otherwise thumbs down
        if( GameState.FINISHED == gameState ) {
            String teamIcon   = ( homeScore > awayScore ) ? home.getSquareTeamIcon( ) : NFLTeam.getGameLostLogo( );
            return teamIcon;
        }        
        
        //Game is going on && Home team is in Redzone
        boolean hasPossession = teamHasPossession( home, liveScore );
        boolean homeRedzone   = isHomeTeamInRedzone( hasPossession, liveScore );
        if( homeRedzone ){
            return DDPUtil.RED_ZONE_ICON;
        }
                
        //Not in Redzone and tied
        boolean gameTied      = ( homeScore == awayScore );
        if( gameTied ){
            return NFLTeam.getMissingTeamLogo( );
        }
        
        //Not in Redzone, home team is winning and hasPossession
        boolean homeTeamWin  = ( homeScore > awayScore );
        if( homeTeamWin  ){
            return home.getSquareTeamIcon( );
        }
        
        return NFLTeam.getMissingTeamLogo( );
        
    }
    
    
    protected final String getMessageInfoClassName( LiveScore liveScore, int homeScore, int awayScore ){
    
        if( GameState.NOT_STARTED == liveScore.getGameState( ) ){
            return DivUtil.INFO_BAR_GAME_PENDING;
        }
        
        if( GameState.FINISHED == liveScore.getGameState( ) ){
            return DivUtil.INFO_BAR_GAME_FINISHED;
        }
        
        
        if( homeScore == awayScore ){
            return DivUtil.INFO_BAR_GAME_TIED;
        }
        
        if( homeScore > awayScore ){
            return DivUtil.INFO_BAR_GAME_WON;
        }
        
        return DivUtil.INFO_BAR_GAME_LOST;
        
    }
    
    
    protected final String getScoreClassName( LiveScore liveScore, int homeScore, int awayScore ){
        
        if( GameState.NOT_STARTED == liveScore.getGameState( ) ){
            return DivUtil.SCORE_BAR_GAME_PENDING;
        }
        
        if( homeScore == awayScore ){
            return DivUtil.SCORE_BAR_GAME_TIED;
        }
        
        if( homeScore > awayScore ){
            return DivUtil.SCORE_BAR_GAME_WON;
        }
        
        return DivUtil.SCORE_BAR_GAME_LOST;
        
    }
    

    //Show QT info + Time
    
    //In order to color the quarter button, we need to determine the score from player's perspective
    //Can't just use the live score home and away score.
    protected final String getGameQuarterInfo( LiveScore liveScore ){
        
        StringBuilder builder   = new StringBuilder( );
        
        GameState gameState     = liveScore.getGameState( );
        String quarterInfo      = liveScore.getFormattedQuarter( );

        if( GameState.NOT_STARTED == gameState ){
            builder.append( quarterInfo )
            .append( SPACE ).append( AT ).append( SPACE )
            .append( liveScore.getStadium( ) );
            
            return builder.toString( );
        }

        if( GameState.FINISHED == gameState ){
            return quarterInfo;
        }
        
        return quarterInfo;
        
    }
  
    
    protected final int calcTotalScore( boolean home, LiveScore[] scores ){
        int totalScore = 0;
        
        for( LiveScore score : scores ){
            if( score != null ) {
                totalScore += (home) ? score.getHomeScore( ) : score.getAwayScore( );
            }
        }
        
        return totalScore;
        
    }

    
    public final NFLTeam getMy1Team( ){
        return myPickedTeams[0];
    }
    
    public final NFLTeam getMy2Team( ){
        return myPickedTeams[1];
    }
    
    public final NFLTeam getMy3Team( ){
        return (myPickedTeams.length == 3) ? myPickedTeams[2] : null;
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
    
    
    //Game is still being played, redzone is on && myTeam is the team with possession of the ball
    protected final boolean isHomeTeamInRedzone( boolean homeHasPossession, LiveScore info ){
        //homeHasPossession() already check if we are playing //info.isPlaying( )
        return ( info.isRedzone( ) && homeHasPossession );
    }
    
    
    protected final boolean teamHasPossession( NFLTeam myTeam, LiveScore liveScore ){
        
        if( myTeam == null || liveScore == null ) {
            return false;
        }
        
        boolean isPlaying   = GameState.PLAYING == liveScore.getGameState( );
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
        if( !has6Teams ) return null;
        return myScores[2];        
    }
       
    
}
