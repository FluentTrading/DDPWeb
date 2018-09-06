package com.ddp.nfl.web.analytics.core;

import java.util.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.pickem.*;
import com.ddp.nfl.web.analytics.home.*;
import com.ddp.nfl.web.analytics.summary.*;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;


public final class GameAnalyticsHtml{
    
    private final String toErrorHtml;
    private final Map<String, NFLTeam> teamsMap;
    
    private final static String DRIVE_SPLIT_KEY = "Drive";
    private final static String ERROR_MSG       = "Game Center is unavailable at this time.";
    private final static Logger LOGGER          = LoggerFactory.getLogger( "GameAnalyticsHtml" );
    
    
    public GameAnalyticsHtml( PickManager pickManager ){
        this.toErrorHtml= createErrorHtml( );
        this.teamsMap   = pickManager.getAllTeamsByNickName( );
    }
  
    
    public final String toErrorHtml(  ){
        return toErrorHtml;
    }
    

    public final String toHtml( GameAnalytics game ){
        
       StringBuilder builder = new StringBuilder( 1024 );
        
       try {
           
           toHtmlHeader( builder );
           toHtmlScoreHeader( builder, game );
           toHtmlTeamInfo( builder, game.getHome( )  );
           toHtmlTeamInfo( builder, game.getAway( ) );
           toHtmlGameInfo( builder, game );
           toHtmlFooter( builder );
       
           toHtmlHeader( builder );
           toHtmlSummary( builder, game.getSummaryManager( ) );
           toHtmlFooter( builder );
           
       }catch( Exception e ) {
           LOGGER.warn( "FAILED to convert analytics to html.", e );
       }
       
       return builder.toString( );

    }
       
    
    protected final void toHtmlScoreHeader( StringBuilder builder, GameAnalytics game ){
        
        builder.append( "<tr>" );
        builder.append( "<th width=\"15%\" align=\"center\">" ).append( EMPTY ).append( "</td>" );
        
        builder.append( "<th width=\"10%\" align=\"center\">Clock: " ).append( game.getClock( ) ).append( "</td>" );
        builder.append( "<th width=\"10%\">" ).append( "1" ).append( "</th>" );
        builder.append( "<th width=\"10%\">" ).append( "2" ).append( "</th>" );
        builder.append( "<th width=\"10%\">" ).append( "3" ).append( "</th>" );
        builder.append( "<th width=\"10%\">" ).append( "4" ).append( "</th>" );
        builder.append( "<th width=\"10%\">" ).append( "OT" ).append( "</th>" );
        builder.append( "<th width=\"10%\">" ).append( "TOTAL" ).append( "</th>" );
        
        builder.append( "<th width=\"15%\" align=\"center\">" ).append( EMPTY ).append( "</td>" );
        builder.append( "</tr>" );
        
    }
    
    protected final void toHtmlTeamInfo( StringBuilder builder, TeamInfo teamInfo ){
        
        String teamName = teamInfo.getTeamNameAbbr( );
        NFLTeam nflTeam = teamsMap.get( teamName );
        String teamImage= createImage( teamName, nflTeam); 
        
        String quarter1Score = zeroToEmpty( teamInfo.get1QuarterScore() );
        String quarter2Score = zeroToEmpty( teamInfo.get2QuarterScore() );
        String quarter3Score = zeroToEmpty( teamInfo.get3QuarterScore() );
        String quarter4Score = zeroToEmpty( teamInfo.get4QuarterScore() );
        String overtimeScore = zeroToEmpty( teamInfo.getOvertimeScore() );
        String totalScore   = zeroToEmpty( teamInfo.getTotalScore() );
        
        builder.append( "<tr>" );
            
            builder.append( "<td align=\"center\">" );
            builder.append( teamImage );
            builder.append( "</td>" );
            
            builder.append( "<td align=\"center\">" );
            builder.append( nflTeam.getNickName( ) );
            builder.append( NEWLINE );
            builder.append( "3-0" );
            builder.append( "</td>" );
            
            builder.append( "<td align=\"center\"> <h5>" ).append( quarter1Score ).append( "</h5></td>" );
            builder.append( "<td align=\"center\"> <h5>" ).append( quarter2Score ).append( "</h5></td>" );
            builder.append( "<td align=\"center\"> <h5>" ).append( quarter3Score ).append( "</h5></td>" );
            builder.append( "<td align=\"center\"> <h5>" ).append( quarter4Score ).append( "</h5></td>" );
            builder.append( "<td align=\"center\"> <h5>" ).append( overtimeScore ).append( "</h5></td>" );
            builder.append( "<td align=\"center\"> <h4>" ).append( totalScore ).append( "</h4></td>" );
        builder.append( "</tr>" );
        
    }
    
    protected final String createImage( String teamName, NFLTeam team ) {
        return (team != null) ? "<img src=" + team.getSquareTeamIcon( ) + " height=\"52\" width=\"82\">" : teamName; 
    }
    
    
    protected final void toHtmlGameInfo( StringBuilder builder, GameAnalytics game ){
        
        builder.append( "<tr>" );
            builder.append( "<td>Quarter: " ).append( game.getQtr( ) ).append( "</td>" );
            builder.append( "<td>Down: " ).append( game.getDown( ) ).append( "</td>" );
            builder.append( "<td>ToGo" ).append( game.getTogo( ) ).append( "</td>" );
            
            if( game.isRedzone( ) ) {
                builder.append( "<td><font-color=\"red\">" ).append( game.getPosteam( ) ).append( "</td>" );    
            }else {
                builder.append( "<td>" ).append( game.getPosteam( ) ).append( "</td>" );
            }
                        
        builder.append( "</tr>" );
        
    }

    
    
    protected final void toHtmlSummary( StringBuilder builder, SummaryManager summaryManager ){
        
        for( ScoreSummary summary : summaryManager.getScoreSummaryMap( ).values( ) ) {
            
            String quarter      = formatQuarter( summary.getQtr( ));
            String scoreType    = formatType( summary.getType( ) );
            
            String description  = summary.getDesc( );
            String[] descSplit  = splitByDrive( description );
            String descFirst    = ( descSplit != null && descSplit.length == TWO ) ? descSplit[0] : description;
            String descSecond   = ( descSplit != null && descSplit.length == TWO ) ? descSplit[1] : EMPTY;
            
            builder.append( "<tr>" );
            builder.append( "<td align=\"left\">" );
            builder.append( scoreType ).append( NEWLINE ).append(quarter);
            builder.append( "</td>" );
                        
            builder.append( "<td align=\"left\">" );
            builder.append( descFirst );
            if( !descSecond.isEmpty( ) ) {
                builder.append( NEWLINE );
                builder.append( DRIVE_SPLIT_KEY ).append( SPACE );
                builder.append( descSecond );
            }
            
            builder.append( "</td>" );
            builder.append( "</tr>" );
            
        }
        
    }
    
    protected final static String formatQuarter( int qtr ){
        return L_BRACKET + "Q" + qtr + R_BRACKET;
    }

    protected final static String formatType( String type ){
        return L_BRACKET + type + R_BRACKET;
    }
    
    
    //Quarter 2 TD C.Newton 16 yd. run (G.Gano kick is good) Drive: 4 plays, 43 yards in 1:59 
    protected final static String[ ] splitByDrive( String desc ){
        String[ ] values = null;
        try {
            values=  desc.split( DRIVE_SPLIT_KEY );
        }catch( Exception e ) {}
                 
        return values;
    }


    protected final void toHtmlHeader( StringBuilder builder ){
        builder.append( "<table class=\"gamecenter\">");
    }
    
    protected final void toHtmlFooter( StringBuilder builder ){
        builder.append( "</table>");
    }
    
    
    protected final String createErrorHtml( ){
        StringBuilder builder = new StringBuilder( 64 ); 
        
        toHtmlHeader( builder );
        
        builder.append( "<tr>" );
        builder.append( "<td width=\"100%\" align=\"center\">" );
        builder.append( ERROR_MSG );
        builder.append( "</td>" );
        builder.append( "</tr>" );
        
        toHtmlFooter( builder );
        
        return builder.toString( );
    }
    
    protected final static String zeroToEmpty( int value ){
        return ( value == 0 ) ?  EMPTY : String.valueOf( value );
    }

        
        
}