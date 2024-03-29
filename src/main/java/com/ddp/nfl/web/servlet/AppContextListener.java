package com.ddp.nfl.web.servlet;

import org.slf4j.*;
import java.io.*;
import java.sql.*;
import java.time.*;

import javax.servlet.*;
import javax.servlet.annotation.*;

import com.ddp.nfl.web.analytics.core.*;
import com.ddp.nfl.web.archive.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.pickem.*;
import com.ddp.nfl.web.schedule.EspnScheduleManager;
import com.ddp.nfl.web.winnings.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


@WebListener
public final class AppContextListener implements ServletContextListener{
    
    private final static String APP_VERSION_KEY     = "APP_VERSION";
    private final static String NFL_SEASON_OVER_KEY = "NFL_SEASON_OVER";
    private final static String NFL_SEASON_KEY      = "NFL_SEASON_TYPE";
    private final static String NFL_START_DATE_KEY  = "NFL_START_DATE";
    private final static String NFL_WEEK_NUMBER     = "NFL_WEEK_NUMBER";
    private final static String CASH_PER_WEEK_KEY   = "CASH_PER_WEEK";
    private final static String LOGGER_CFG_TAG      = "LOGGER_CONFIG";
    
    private final static Logger LOGGER              = LoggerFactory.getLogger( "AppContextListener" );

    
    @Override
    public final void contextInitialized( ServletContextEvent event ){
        LOGGER.info( "Initializing DDP NFL Servlet context.");
        
        ServletContext context  = event.getServletContext();
        loadLogger( context );
                
        DDPMeta ddpMeta         = createMetaData( context );
        DBService service       = createDBService( ddpMeta, context );
                
        storeScheduleManager( ddpMeta, service, context );
        createPickManager( ddpMeta, service, context );
        createGameCenter( context );
        prepareWinningsCash( ddpMeta, service, context );
        prepareArchiveReader( ddpMeta, context );
                                
        LOGGER.info( "DDP NFL Servlet context initialized!");
        
    }


    protected final void storeScheduleManager( DDPMeta meta, DBService service, ServletContext context ){
    	context.setAttribute( ESPN_SCHEDULE_KEY, new EspnScheduleManager(meta, service) );        
        LOGGER.info("Successfully created EspnScheduleManager with key [{}]{}", ESPN_SCHEDULE_KEY, PRINT_NEWLINE);    
    }
    

    protected final EspnPickManager createPickManager( DDPMeta meta, DBService service, ServletContext context ){
    	EspnScheduleManager sch =(EspnScheduleManager) context.getAttribute(ESPN_SCHEDULE_KEY);
    	EspnPickManager pick    = new EspnPickManager( meta, sch, service );
        context.setAttribute( ESPN_PICK_MANAGER_KEY, pick );
        LOGGER.info("Successfully created EspnPickManager with key [{}]{}", ESPN_PICK_MANAGER_KEY, PRINT_NEWLINE);
        
        return pick;
    }
    
    
    protected final void createGameCenter( ServletContext context ) {
    	context.setAttribute( GAME_ANALYTICS_KEY, new EspnAnalyticsManager() );
        LOGGER.info("Successfully stored game center manager with key [{}] {}", GAME_ANALYTICS_KEY, PRINT_NEWLINE);        
    }
    
    
    protected final void prepareWinningsCash( DDPMeta ddpMeta, DBService service, ServletContext context ){
        LOGGER.info("Attempting to calculate cash winnngs.");
        
        WinnerManager cashManager   = new WinnerManager( ddpMeta, service );
        boolean isWinningsValid     = !cashManager.getWinSummary( ).isEmpty( );
        if( isWinningsValid ){
            context.setAttribute( CASH_MANAGER_KEY, cashManager );
            LOGGER.info("Successfully stored winnings with key [{}] {}", CASH_MANAGER_KEY, PRINT_NEWLINE);
        }

    }
    
    protected final void prepareArchiveReader( DDPMeta ddpMeta, ServletContext context ) {
        LOGGER.info("Attempting to read archived images.");
        
        ArchiveManager archiveManager= new ArchiveManager( );
        context.setAttribute( ARCHIVE_MANAGER_KEY, archiveManager );
                
    }
       

    protected final DBService createDBService( DDPMeta ddpMeta, ServletContext context ){
        DBService service   = new DBService( ddpMeta );
        if( service != null ) {
            context.setAttribute( DB_SERVICE_KEY, service );
            LOGGER.info("Successfully created DBService & stored with key [{}]{}", DB_SERVICE_KEY, PRINT_NEWLINE);
        }
        
        return service;
    
    }    
    
   
        
    protected final DDPMeta createMetaData( ServletContext context ){
        
        DDPMeta metaData        = null;
        
        try {

            String version      = context.getInitParameter( APP_VERSION_KEY );
            boolean seasonOver  = Boolean.parseBoolean( context.getInitParameter( NFL_SEASON_OVER_KEY ) );
            String nflSeason    = context.getInitParameter( NFL_SEASON_KEY );
            LocalDate startDate = LocalDate.parse( context.getInitParameter( NFL_START_DATE_KEY ) );
            int nflWeek         = Integer.parseInt( context.getInitParameter( NFL_WEEK_NUMBER ) );
            int cashPerWeek     = Integer.parseInt( context.getInitParameter( CASH_PER_WEEK_KEY ) );
                        
            metaData            = new DDPMeta( version, seasonOver, nflSeason, startDate, nflWeek, cashPerWeek );
            
            context.setAttribute( META_INFO_KEY, metaData );
            LOGGER.info( "Successfully created {} {}", metaData, PRINT_NEWLINE );
        
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to parse DDP Meta Data", e );
        }
        
        return metaData;

    }

    
    
    protected final void loadLogger( ServletContext context ){
        
        String loggerCfgFile    = context.getInitParameter(LOGGER_CFG_TAG);
        
        try {
            
            if( loggerCfgFile == null ){
                System.err.println("Error: Logger config file is unspecified as context-param [" + LOGGER_CFG_TAG + "] in web.xml!");
                return;
            }
            
            String fullPath     = context.getRealPath("") + File.separator + loggerCfgFile;
            System.setProperty( "logback.configurationFile", fullPath );
            LOGGER.info( "Successfully loaded logger config from [{}]", loggerCfgFile );
        
        }catch( Exception e ) {
            System.err.println("FAILED to load logger config from " + loggerCfgFile);
            e.printStackTrace( );
        }
         
    }
    
           
    @Override
    public final void contextDestroyed( ServletContextEvent event ){
        
        try {
            
            ServletContext ctx  = event.getServletContext();
            DBService service   = (DBService) ctx.getAttribute( DB_SERVICE_KEY );
            if( service != null ){
                service.close( );
            }
                        
            LOGGER.info( "Successfully destroyed DDP Servlet context.");
            
        } catch (SQLException e) {
            LOGGER.warn("FAILED to destroy DDP Servlet context.", e);
        }
    
    }
    
}