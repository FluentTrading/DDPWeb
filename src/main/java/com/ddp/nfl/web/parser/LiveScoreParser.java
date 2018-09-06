package com.ddp.nfl.web.parser;

import org.slf4j.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.namespace.*;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import com.ddp.nfl.web.core.*;


import static com.ddp.nfl.web.util.DDPUtil.*;


public final class LiveScoreParser{
    
    private final static String URL_PREFIX  = "http://www.nfl.com/ajax/scorestrip?season=";
    private final static String SEASON_PART = "&seasonType=";
    private final static String WEEK_PART   = "&week=";
    private final static Logger LOGGER      = LoggerFactory.getLogger( "LiveScoreParser" );
    
       
    public final static Map<NFLTeam, LiveScore> parseLiveScore( String scheduleUrl, Map<String, NFLTeam> teamMap ){
    
        LOGGER.info("Parsing live score from {}", scheduleUrl);
        
        Map<NFLTeam, LiveScore> scores = new HashMap<>();
                        
        try{
            
            InputStream inStream            = new URL( scheduleUrl ).openStream( );
            
            XMLInputFactory inputFactory    = XMLInputFactory.newInstance( );
            XMLEventReader xmlEventReader   = inputFactory.createXMLEventReader(inStream);
        
            while( xmlEventReader.hasNext() ){
                
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
            
                if( xmlEvent.isStartElement() ){
                   StartElement startElement= xmlEvent.asStartElement( );
                   
                   if( startElement.getName().getLocalPart().equals("g") ){
                       LiveScore score = parseLiveScore( startElement, teamMap );
                       if( score != null ){
                           //TODO: Can we use team name as key??
                           scores.put(score.getHomeTeam( ), score );
                           scores.put(score.getAwayTeam( ), score );
                       }
                   }
                }
            }
                     
        } catch( Exception e ){
            LOGGER.warn( "FAILED to parse NFL live score data", e );
        }
                        
        return scores;

    }
    
    
    
    protected final static LiveScore parseLiveScore( StartElement startElement, Map<String, NFLTeam> teamMap ){
        
        LiveScore liveScore= null;
        
        try{               
            
           String gameId        = safeParse( startElement, "gsis", "gidMissing");
           String gameEid       = safeParse( startElement, "eid", "EidMissing"); 
           String gameDay       = safeParse( startElement, "d", EMPTY);
           String gameTime      = safeParse( startElement, "t", EMPTY);

           String homeTeam      = safeParse( startElement, "hnn", EMPTY);
           NFLTeam home         = teamMap.get( homeTeam.toLowerCase( ) );
           int homeScore        = safeParse( startElement, "hs", ZERO);
           
           String awayTeam      = safeParse( startElement, "vnn", EMPTY);
           NFLTeam away         = teamMap.get( awayTeam.toLowerCase( ) );
           int awayScore        = safeParse( startElement, "vs", ZERO);
           
           String rawQuarterStr = safeParse( startElement, "q", EMPTY );
           boolean notStarted   = !isValid(rawQuarterStr) || "p".equalsIgnoreCase( rawQuarterStr );
           boolean isFinished   = rawQuarterStr.equalsIgnoreCase("F") || rawQuarterStr.equalsIgnoreCase("FO");
           boolean isPlaying    = !notStarted && !isFinished;
           boolean isRedzone    = parseRedZone( isPlaying, startElement, "rz");
           
           String teamPossession= safeParse( startElement, "p", EMPTY);
           String timeRemaining = safeParse( startElement, "k", EMPTY);
           
           liveScore            = new LiveScore( gameId, gameEid, gameDay, gameTime,
                                                 notStarted, isPlaying, isFinished, home, homeScore, 
                                                 away, awayScore, teamPossession, timeRemaining, isRedzone, rawQuarterStr );
           //TODO: Muting it for now
           //LOGGER.info( "{}", liveScore );
                   
        }catch( Exception e ){
            LOGGER.warn("FAILED to parse live game score {}", startElement, e);
        }

        return liveScore;

    }

    
    private final static boolean parseRedZone( boolean isPlaying, StartElement startElement, String string ) {
        
        if( !isPlaying ) return false;
        
        int redZoneCode     = safeParse( startElement, "rz", ZERO );
        boolean isRedZone   = ( redZoneCode > ZERO );
        
        return isRedZone;
    }


    protected final static String safeParse( StartElement startElement, String key, String defaultVal ){
        Attribute attr     = startElement.getAttributeByName(new QName(key));
        String value       = ( attr != null ) ? attr.getValue() : defaultVal;
        
        return value;
    }
    
    
    protected final static int safeParse( StartElement startElement, String key, int defaultVal ){
        
        int value          = defaultVal;
        Attribute attr     = startElement.getAttributeByName(new QName(key));
        
        try {
            value = Integer.parseInt(attr.getValue() );
        }catch( Exception e ) {}
                 
        return value;
    }
    
    
   
    public final static String createLiveScoreUrl(  String seasonType, int year, int week ){
        
        StringBuilder builder = new StringBuilder( );
        builder.append( URL_PREFIX ).append( year );
        builder.append( SEASON_PART ).append( seasonType );
        builder.append( WEEK_PART ).append( week );
        
        return builder.toString( );
        
    }
    
}
