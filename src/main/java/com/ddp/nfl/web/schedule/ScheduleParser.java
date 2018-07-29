package com.ddp.nfl.web.schedule;

import org.slf4j.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.stream.*;
import javax.xml.namespace.*;
import javax.xml.stream.events.*;

import com.ddp.nfl.web.core.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class ScheduleParser{
    
    private final static String UREL_PREFIX = "http://www.nfl.com/ajax/scorestrip?season=";
    private final static String SEASON_PART = "&seasonType=";
    private final static String WEEK_PART   = "&week=";
    
    private final static Logger LOGGER      = LoggerFactory.getLogger( "ScheduleParser" );
    
    
    public final static Map<String, NFLSchedule> parseSchedule( DDPMeta ddpMeta, Map<String, NFLTeam> teamMap ){
        String scheduleUrl                  = prepareUrl( ddpMeta );
        Map<String, NFLSchedule> schedules  = parseSchedule( scheduleUrl, teamMap );
        
        return schedules;
    }
    

    public final static Map<String, NFLSchedule> parseSchedule( String scheduleUrl, Map<String, NFLTeam> teamMap ){
        
        Map<String, NFLSchedule> schedules  = new TreeMap<>();
                        
        try{
            
            InputStream inStream            = new URL( scheduleUrl ).openStream( );
            
            XMLInputFactory inputFactory    = XMLInputFactory.newInstance( );
            XMLEventReader xmlEventReader   = inputFactory.createXMLEventReader(inStream);
        
            while( xmlEventReader.hasNext() ){
                
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
            
                if( xmlEvent.isStartElement() ){
                   StartElement startElement= xmlEvent.asStartElement( );
                   
                   if( startElement.getName().getLocalPart().equals("g") ){
                       NFLSchedule schedule = parseSchedule( startElement, teamMap );
                       if( schedule != null ){
                           schedules.put( schedule.getGameId( ), schedule );
                       }
                   }
                   
                }
                
            }
                     
            
        } catch( Exception e ){
            LOGGER.warn("FAILED to parse NFLSchedule for {}", scheduleUrl, e);
        }
        
        return schedules;

    }
  

    protected final static NFLSchedule parseSchedule( StartElement startElement, Map<String, NFLTeam> teamMap ){
            
        NFLSchedule schedule = null;
        
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
           
           boolean isValid      = (home != null && away != null);
           if( !isValid ) {
               LOGGER.warn("Discarding GameId [{}] as we failed to look up Home or Away Team {} {}", gameId, homeTeam, awayTeam);
               return null;
           }
           
           schedule             = new NFLSchedule( gameId, gameEid, gameDay, gameTime, home, homeScore, away, awayScore );
                
        }catch( Exception e ){
            LOGGER.warn("FAILED to parse NFLSchedule {}", startElement, e);
        }

        return schedule;

    }

    
    protected final static String safeParse( StartElement startElement, String key, String defaultVal ){
        Attribute attr     = startElement.getAttributeByName(new QName(key));
        String value       = ( attr != null ) ? attr.getValue() : defaultVal;
        
        return value;
    }
    
    protected final static int safeParse( StartElement startElement, String key, int defaultVal ){
        Attribute attr     = startElement.getAttributeByName(new QName(key));
        int value          = ( attr != null ) ? Integer.parseInt(attr.getValue()) : defaultVal;
        
        return value;
    }
    
        
    
    protected final static String prepareUrl( DDPMeta ddpMeta ){
        return prepareUrl( ddpMeta.getSeasonType( ), ddpMeta.getYear( ), ddpMeta.getWeek( ) );        
    }

    
    public final static String prepareUrl( String seasonType, int year, int week ){
        
        StringBuilder builder = new StringBuilder( );
        builder.append( UREL_PREFIX ).append( year );
        builder.append( SEASON_PART ).append( seasonType );
        builder.append( WEEK_PART ).append( week );
        
        return builder.toString( );
        
    }
    


}
