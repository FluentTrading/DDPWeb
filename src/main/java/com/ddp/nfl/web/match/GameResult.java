package com.ddp.nfl.web.match;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.parser.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class GameResult{
    
    private final boolean has6Teams;
    private final DDPPick pick;
    private final NFLTeam[] myTeams;
    private final MatchScore[] myScores;
        
    public GameResult( boolean hasAll6Teams, DDPPick pick, MatchScore[ ] myScores ){
        this.has6Teams      = hasAll6Teams;
        this.pick           = pick;
        this.myTeams        = pick.getTeams( );
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
        
    
    public final String getMy1TeamName( ){
        return getMy1Team( ).getDisplayName( );
    }
    
    public final String getMy2TeamName( ){
        return getMy2Team( ).getDisplayName( );
    }
    
    public final String getMy3TeamName( ){
        return (getMy3Team( ) != null) ? getMy3Team( ).getDisplayName( ) : EMPTY;
    }
    
    
    public final int getMy1TeamScore( ){
        NFLTeam myTeam      = getMy1Team( );
        MatchScore info   = getMatch1Info( );
        int myTeamScore     = getScore( true, myTeam, info);
        
        return myTeamScore;
    }
    
    public final int getMy2TeamScore( ){
        NFLTeam myTeam      = getMy2Team( );
        MatchScore info   = getMatch2Info( );
        int myTeamScore     = getScore( true, myTeam, info);
        
        return myTeamScore;
    }
    
    public final int getMy3TeamScore( ){
        NFLTeam myTeam      = getMy3Team( );
        MatchScore info   = getMatch3Info( );
        int myTeamScore     = getScore( true, myTeam, info);
        
        return myTeamScore;
    }
    
    
    public final int getHomeTotalScore( ) {
        return getMy1TeamScore( ) + getMy2TeamScore( ) + getMy3TeamScore( );
    }
    
        
    //AWAY
    public final String getOpp1TeamName( ){
        return getOpp1Team( ).getDisplayName( );
    }
    
    public final String getOpp2TeamName( ){
        return getOpp2Team( ).getDisplayName( );
    }
    
    public final String getOpp3TeamName( ){
        return (getOpp3Team( ) != null) ? getOpp3Team( ).getDisplayName( ) : EMPTY;
    }
    
    
    public final int getOpp1TeamScore( ){
        MatchScore info   = getMatch1Info( );
        int myTeamScore     = getScore( false, getMy1Team( ), info);
        
        return myTeamScore;
    }
    
    public final int getOpp2TeamScore( ){
        MatchScore info   = getMatch2Info( );
        int myTeamScore     = getScore( false, getMy2Team( ), info);
        
        return myTeamScore;
    }
    
    public final int getOpp3TeamScore( ){
        MatchScore info   = getMatch3Info( );
        int myTeamScore     = getScore( false, getMy3Team( ), info);
        
        return myTeamScore;
    }
    

    public final int getAwayTotalScore( ) {
        return getOpp1TeamScore( ) + getOpp2TeamScore( ) + getOpp3TeamScore( );
    }
    
    
    public final String getGame1Quarter( ){
        return getMatch1Info( ).getQuarter( );        
    }
    
    public final String getGame2Quarter( ){
        return getMatch2Info( ).getQuarter( );        
    }
    
    public final String getGame3Quarter( ){
        return ( getMatch3Info( ) != null ) ? getMatch3Info().getQuarter( ) : EMPTY;        
    }
    
    
    public final String getGame1QuarterColor( ){
        return getQuarterColor( getMy1TeamScore( ), getOpp1TeamScore( ) );        
    }
    
    public final String getGame2QuarterColor( ){
        return getQuarterColor( getMy2TeamScore( ), getOpp2TeamScore( ) );        
    }
    
    public final String getGame3QuarterColor( ){
        return getQuarterColor( getMy3TeamScore( ), getOpp3TeamScore( ) );
    }
    
               
    //Winner Icons
    public final String getGame1WinnerIcon( ){
        return getGameWinnerIcon( getMy1TeamScore( ), getMy1Team( ), getOpp1TeamScore( ), getOpp1Team( ) );
    }
    
    public final String getGame2WinnerIcon( ){
        return getGameWinnerIcon( getMy2TeamScore( ), getMy2Team( ), getOpp2TeamScore( ), getOpp2Team( ) );
    }
    
    public final String getGame3WinnerIcon( ){
        return getGameWinnerIcon( getMy3TeamScore( ), getMy3Team( ), getOpp3TeamScore( ), getOpp3Team( ) );
    }
    
    
    //If game hasn't started or the score is tied, display no icon
    public final String getGameWinnerIcon( int homeScore, NFLTeam home, int awayScore, NFLTeam away ){
        if( home == null || away == null ) return MISSING_TEAM_LOGO;
        if( homeScore == awayScore ) return MISSING_TEAM_LOGO;
         
        String winnerIcon= (homeScore > awayScore) ? home.getRoundTeamIcon( ) : MISSING_TEAM_LOGO;
        return winnerIcon;
    }
    
    
    //Team Bg Color
    protected final String getQuarterColor( int homeScore, int awayScore ){
        
        if( homeScore == awayScore) return "#a0a5a5";
        
        String tdBgColor    = ( homeScore > awayScore ) ? "#27AC44" : "#f46e5f";
        return tdBgColor;
        
    }
    
    /*
    //Quarter Color
    protected final String getQuarterColor( NFLGameScore home, String tdBgColor ){
        
        if( home.isFinished( ) ){
            return tdBgColor;
        }
        
        if( !home.isPlaying( ) ){
            return "#5c5e5a";
        }else{
            return "#7a8944";
        }
        
    }
    */
    
    protected final int calcTotalScore( boolean home, MatchScore[] scores ){
        int totalScore = 0;
        
        for( MatchScore score : scores ){
            if( score != null ) {
                totalScore += (home) ? score.getHomeScore( ) : score.getAwayScore( );
            }
        }
        
        return totalScore;
        
    }
    
        
    //UI shouldn't directly invoke these methods.
    
    protected final NFLTeam getMy1Team( ){
        return myTeams[0];
    }
    
    protected final NFLTeam getMy2Team( ){
        return myTeams[1];
    }
    
    protected final NFLTeam getMy3Team( ){
        return (myTeams.length == 3) ? myTeams[2] : null;
    }
    
    protected final NFLTeam getOpp1Team( ){
        MatchScore info   = getMatch1Info( );
        NFLTeam oppTeam   = getOpponentTeam( getMy1Team( ), info );
        
        return oppTeam;
    }
    
    protected final NFLTeam getOpp2Team( ){
        MatchScore info   = getMatch2Info( );
        NFLTeam oppTeam   = getOpponentTeam( getMy2Team( ), info );
        
        return oppTeam;
    }
    
    protected final NFLTeam getOpp3Team( ){
        MatchScore info   = getMatch3Info( );
        NFLTeam oppTeam   = getOpponentTeam( getMy3Team( ), info );
        
        return oppTeam;         
    }

    
    
    protected final int getScore( boolean forHome, NFLTeam myTeam, MatchScore info ){
        
        if( myTeam == null || info == null ) {
            return -1;
        }
        
        if( forHome ) {
            return (myTeam.getId( ) == info.getHomeTeam( ).getId( )) ? info.getHomeScore( ) : info.getAwayScore( );
        }
           
        return (myTeam.getId( ) == info.getHomeTeam( ).getId( )) ? info.getAwayScore( ) : info.getHomeScore( );
        
    }
    
    
    
    protected final NFLTeam getOpponentTeam( NFLTeam myTeam, MatchScore info ){

        if( myTeam == null || info == null ) {
            return null;
        }
        
        return (myTeam.getId( ) == info.getHomeTeam( ).getId( )) ? info.getAwayTeam( ) : info.getHomeTeam( );
    }
    
    
    public final MatchScore getMatch1Info( ){
        return myScores[0];        
    }
    
    
    public final MatchScore getMatch2Info( ){
        return myScores[1];        
    }
    
    
    public final MatchScore getMatch3Info( ){
        if( !has6Teams ) return null;
        return myScores[2];        
    }
    
    
}
