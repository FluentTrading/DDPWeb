package com.ddp.nfl.web.schedule;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.namespace.*;
import javax.xml.stream.*;
import javax.xml.stream.events.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;


public final class ScheduleManager{

    private final boolean isValid;
    private final Map<String, Schedule> scheduleMap;
    
    private final static String URL_PREFIX  = "http://www.nfl.com/ajax/scorestrip?season=";
    private final static String SEASON_PART = "&seasonType=";
    private final static String WEEK_PART   = "&week=";
    private final static Logger LOGGER      = LoggerFactory.getLogger( "ScheduleManager" );
    
    
    public ScheduleManager( DDPMeta ddpMeta, DBService service ){
        this.scheduleMap    = parse( ddpMeta, service );
        this.isValid        = ( !scheduleMap.isEmpty( ) );
    }
    

    public final boolean isValid( ){
        return isValid;
    }
   
    
    public final Schedule get( String gameId ) {
        return scheduleMap.get( gameId );
    }

    
    public final int getScheduleCount( ) {
        return scheduleMap.size( );
    }
    
    
    public final Map<String, Schedule> getSchedules( ) {
        return scheduleMap;
    }
    

    //Used to display data on the schedules page
   //We use this map to display schedule where earliest game (smallest gid) is at first
    protected final Map<String, Schedule> parse( DDPMeta meta, DBService service ) {
        
        String liveScoreUrl              = createScheduleUrl( meta );
        Map<String, Schedule> scheduleMap= parseSchedule( liveScoreUrl, service.getAllTeams( ) );
    
        return scheduleMap;
    }
    
    
    public final static Map<String, Schedule> parseSchedule( String scheduleUrl, Map<String, NFLTeam> teamMap ){
    
        LOGGER.info("Parsing Schedule from {}", scheduleUrl);
        
        Map<String, Schedule> scheduleMap   = new TreeMap<>( );
                        
        try{
            
            InputStream inStream            = new URL( scheduleUrl ).openStream( );
            
            XMLInputFactory inputFactory    = XMLInputFactory.newInstance( );
            XMLEventReader xmlEventReader   = inputFactory.createXMLEventReader(inStream);
        
            while( xmlEventReader.hasNext() ){
                
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
            
                if( xmlEvent.isStartElement() ){
                   StartElement startElement= xmlEvent.asStartElement( );
                   
                   if( startElement.getName().getLocalPart().equals("g") ){
                       Schedule schedule    = parseSchedule( startElement, teamMap );
                       if( schedule != null ){
                           scheduleMap.put(schedule.getGameId( ), schedule );
                       }
                   }
                }
            }
                     
        } catch( Exception e ){
            LOGGER.warn( "FAILED to parse schedule.", e );
        }
                        
        return scheduleMap;

    }
    
    
    
    protected final static Schedule parseSchedule( StartElement startElement, Map<String, NFLTeam> teamMap ){
        
        Schedule schedule   = null;
        
        try{               
            
           String gameEid   = safeParse( startElement, "eid", "EidMissing"); 
           String gameDay   = safeParse( startElement, "d", EMPTY);
           String gameTime  = safeParse( startElement, "t", EMPTY);

           String homeTeam  = safeParse( startElement, "hnn", EMPTY);
           NFLTeam home     = teamMap.get( homeTeam.toLowerCase( ) );
                      
           String awayTeam  = safeParse( startElement, "vnn", EMPTY);
           NFLTeam away     = teamMap.get( awayTeam.toLowerCase( ) );
           
           schedule        = new Schedule( gameEid, gameDay, gameTime, home, away );
           LOGGER.info( "{}", schedule );
                   
        }catch( Exception e ){
            LOGGER.warn("FAILED to parse schedule {}", startElement, e);
        }

        return schedule;

    }
    
    
    protected final static String createScheduleUrl( DDPMeta meta ) {
        return createScheduleUrl( meta.getSeasonType( ), meta.getYear( ), meta.getWeek( ) );
    }
    
    public final static String createScheduleUrl(  String seasonType, int year, int week ){
        
        StringBuilder builder = new StringBuilder( );
        builder.append( URL_PREFIX ).append( year );
        builder.append( SEASON_PART ).append( seasonType );
        builder.append( WEEK_PART ).append( week );
        
        return builder.toString( );
        
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


}
