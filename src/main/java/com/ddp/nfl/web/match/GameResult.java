package com.ddp.nfl.web.match;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.parser.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class GameResult{
    
    private final boolean has6Teams;
    private final DDPPick pick;
    private final LiveScore[] myScores;
    private final NFLTeam[] myPickedTeams;
    
    private final static String WIN_BUTTON_CLASS     = "winButton";
    private final static String LOSS_BUTTON_CLASS    = "lossButton";
    private final static String TIED_BUTTON_CLASS    = "tiedButton";
    private final static String NO_START_BUTTON_CLASS= "nostartButton";
    
        
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
    
   
    public final String getMy1TeamName( ){
        return getMy1Team( ).getCamelCaseName( );
    }
        
    public final String getMy2TeamName( ){
        return getMy2Team( ).getCamelCaseName( );
    }
    
    public final String getMy3TeamName( ){
        return (getMy3Team( ) != null) ? getMy3Team( ).getCamelCaseName( ) : EMPTY;
    }
    
    
    public final int getMy1TeamScore( ){
        NFLTeam myTeam    = getMy1Team( );
        LiveScore info    = getMatch1Info( );
        int myTeamScore   = getScore( true, myTeam, info);
        
        return myTeamScore;
    }
    
    public final int getMy2TeamScore( ){
        NFLTeam myTeam    = getMy2Team( );
        LiveScore info    = getMatch2Info( );
        int myTeamScore   = getScore( true, myTeam, info);
        
        return myTeamScore;
    }
    
    public final int getMy3TeamScore( ){
        NFLTeam myTeam    = getMy3Team( );
        LiveScore info    = getMatch3Info( );
        int myTeamScore   = getScore( true, myTeam, info);
        
        return myTeamScore;
    }
    
    
    public final int getHomeTotalScore( ) {
        return getMy1TeamScore( ) + getMy2TeamScore( ) + getMy3TeamScore( );
    }
    
        
    //AWAY
    public final String getOpp1TeamName( ){
        return getOpp1Team( ).getCamelCaseName( );
    }
    
    public final String getOpp2TeamName( ){
        return getOpp2Team( ).getCamelCaseName( );
    }
    
    public final String getOpp3TeamName( ){
        return (getOpp3Team( ) != null) ? getOpp3Team( ).getCamelCaseName( ) : EMPTY;
    }
    
    
    public final int getOpp1TeamScore( ){
        LiveScore info   = getMatch1Info( );
        int myTeamScore  = getScore( false, getMy1Team( ), info);
        
        return myTeamScore;
    }
    
    public final int getOpp2TeamScore( ){
        LiveScore info   = getMatch2Info( );
        int myTeamScore  = getScore( false, getMy2Team( ), info);
        
        return myTeamScore;
    }
    
    public final int getOpp3TeamScore( ){
        LiveScore info   = getMatch3Info( );
        int myTeamScore  = getScore( false, getMy3Team( ), info);
        
        return myTeamScore;
    }
    

    public final int getAwayTotalScore( ) {
        return getOpp1TeamScore( ) + getOpp2TeamScore( ) + getOpp3TeamScore( );
    }
    
    
    public final String getGame1Quarter( ){
        return getGameQuarterButton( getMatch1Info( ), getMy1TeamScore( ), getOpp1TeamScore( ) );        
    }
    
    public final String getGame2Quarter( ){
        return getGameQuarterButton( getMatch2Info( ), getMy2TeamScore( ), getOpp2TeamScore( ) );        
    }
    
    public final String getGame3Quarter( ){
        
        if( getMatch3Info( ) == null ) {
            return EMPTY;
        }
        
        return getGameQuarterButton( getMatch3Info( ), getMy3TeamScore( ), getOpp3TeamScore( ) );    
    }
    

    public final String getGame1WinnerIcon( ){
        return getGameWinnerIcon( getMy1TeamScore( ), getMy1Team( ), getOpp1TeamScore( ), getOpp1Team( ) );
    }
    
    
    public final String getGame2WinnerIcon( ){
        return getGameWinnerIcon( getMy2TeamScore( ), getMy2Team( ), getOpp2TeamScore( ), getOpp2Team( ) );
    }
    
    
    public final String getGame3WinnerIcon( ){
        return getGameWinnerIcon( getMy3TeamScore( ), getMy3Team( ), getOpp3TeamScore( ), getOpp3Team( ) );
    }
      
    
    public final String getGameWinnerIcon( int homeScore, NFLTeam home, int awayScore, NFLTeam away ){
        
        boolean notStarted    = ( home == null || away == null );
        boolean gameTied      = ( homeScore == awayScore );
        boolean homeTeamLost  = ( homeScore < awayScore );
        
        if( notStarted || gameTied || homeTeamLost ){
            return NFLTeam.getMissingTeamLogo( );
        }
                
        return home.getRoundTeamIcon( );
        
    }
    

    //In order to color the quarter button, we need to determine the score from player's perspective
    //Can't just use the live score home and away score.
    public final String getGameQuarterButton( LiveScore liveScore, int homeScore, int awayScore ){
        
        StringBuilder builder   = new StringBuilder( );
        String unclickableLabel = ( liveScore == null ) ? EMPTY : liveScore.getGameDayTime( );
                
        //Game not started, not clickable & we show the time of the game
        if( liveScore.notStarted( ) ){
            builder.append("<div class=").append( NO_START_BUTTON_CLASS ).append(">");
            builder.append(  "<h4>" ).append( unclickableLabel ).append(  "</h4>" );
            builder.append( "</div>");
            return builder.toString( );
        }
        
        String gameId       = liveScore.getGameId( );
        String quarterString= (liveScore == null ) ? EMPTY : liveScore.getQuarter( );
        String redzoneBlink = liveScore.isRedzone( ) ? REDZONE_ANIMATION : EMPTY;
        String btnClassName = (homeScore == awayScore)? TIED_BUTTON_CLASS: (homeScore > awayScore ? WIN_BUTTON_CLASS : LOSS_BUTTON_CLASS);
        
        /*
         * 
        builder.append("<div class=\"").append( btnClassName ).append("\">");
        builder.append("<button class=\"").append( btnClassName ).append("\"");
        builder.append( " type=\"submit\"");
        builder.append( " name=" ).append( ANALYTICS_GAME_ID_KEY );
        builder.append( " value=\"" ).append( gameId ).append("\">");
        builder.append(  "<h4>" );
        builder.append( redzoneBlink );
        builder.append("&nbsp");
        builder.append( quarterString );
        builder.append(  "</h4>" );
        builder.append( "</button>");
        builder.append( "</div>");
        */
        
        
        //Unclickable for now
        builder.append("<div class=\"").append( btnClassName ).append("\">");
        builder.append(  "<h4>" );
        builder.append( redzoneBlink );
        builder.append("&nbsp");
        builder.append( quarterString );
        builder.append(  "</h4>" );
        builder.append( "</div>");
        
        return builder.toString( );
    
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
    
        
    //UI shouldn't directly invoke these methods.
    
    protected final NFLTeam getMy1Team( ){
        return myPickedTeams[0];
    }
    
    protected final NFLTeam getMy2Team( ){
        return myPickedTeams[1];
    }
    
    protected final NFLTeam getMy3Team( ){
        return (myPickedTeams.length == 3) ? myPickedTeams[2] : null;
    }
    
    protected final NFLTeam getOpp1Team( ){
        LiveScore info   = getMatch1Info( );
        NFLTeam oppTeam  = getOpponentTeam( getMy1Team( ), info );
        
        return oppTeam;
    }
    
    protected final NFLTeam getOpp2Team( ){
        LiveScore info   = getMatch2Info( );
        NFLTeam oppTeam  = getOpponentTeam( getMy2Team( ), info );
        
        return oppTeam;
    }
    
    protected final NFLTeam getOpp3Team( ){
        LiveScore info   = getMatch3Info( );
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
        
    protected final NFLTeam getOpponentTeam( NFLTeam myTeam, LiveScore info ){
        if( myTeam == null || info == null ) {
            return null;
        }
        
        return (myTeam.getId( ) == info.getHomeTeam( ).getId( )) ? info.getAwayTeam( ) : info.getHomeTeam( );
    }
    
    
    public final LiveScore getMatch1Info( ){
        return myScores[0];        
    }
    
    
    public final LiveScore getMatch2Info( ){
        return myScores[1];        
    }
    
    
    public final LiveScore getMatch3Info( ){
        if( !has6Teams ) return null;
        return myScores[2];        
    }
       
    
}
