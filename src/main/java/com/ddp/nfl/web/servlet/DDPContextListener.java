package com.ddp.nfl.web.servlet;

import org.slf4j.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.pickem.*;
import com.ddp.nfl.web.schedule.*;
import com.ddp.nfl.web.winnings.*;
import com.ddp.nfl.web.analytics.core.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


@WebListener
public final class DDPContextListener implements ServletContextListener{
    
    private final static String APP_VERSION_KEY = "APP_VERSION";
    private final static String NFL_SEASON_KEY  = "NFL_SEASON_TYPE";
    private final static String NFL_YEAR_KEY    = "NFL_YEAR";
    private final static String NFL_WEEK_NUMBER = "NFL_WEEK_NUMBER";
    private final static String CASH_PER_WEEK_KEY= "CASH_PER_WEEK";
    private final static String RDS_DRIVER_TAG  = "RDS_DRIVER";
    private final static String RDS_HOST_TAG    = "RDS_HOST";
    private final static String RDS_PORT_TAG    = "RDS_PORT";
    private final static String RDS_DB_NAME_TAG = "RDS_DB_NAME";
    private final static String LOGGER_CFG_TAG  = "LOGGER_CONFIG";
    
    private final static Logger LOGGER          = LoggerFactory.getLogger( "DDPContextListener" );

    
    @Override
    public final void contextInitialized( ServletContextEvent event ){
        LOGGER.info( "Initializing DDP NFL Servlet context.");
        
        ServletContext context  = event.getServletContext();
        loadLogger( context );
                
        DDPMeta ddpMeta         = createMetaData( context );
        DBService service       = createDBService( ddpMeta, context );
        ScheduleManager schMan  = parseSchedule( ddpMeta, service, context );
        
        createPickManager( ddpMeta, service, context );
        prepareWinningsCash( ddpMeta, service, context );
        createGameCenter( schMan, context );
                        
        LOGGER.info( "DDP NFL Servlet context initialized!");
        
    }



    protected final void createPickManager( DDPMeta meta, DBService service, ServletContext context ){
        PickManager pick    = new PickManager( meta, service );
        context.setAttribute( PICK_MANAGER_KEY, pick );
        LOGGER.info("Successfully created PickManager with key [{}]{}", PICK_MANAGER_KEY, PRINT_NEWLINE);
        
    }
    
    
    protected final void prepareWinningsCash( DDPMeta ddpMeta, DBService service, ServletContext context ){
        LOGGER.info("Attempting to calculate cash winnngs.");
        
        CashManager cashManager     = new CashManager( ddpMeta, service );
        Map<Integer, CashWin> winMap= cashManager.getWinnings( );
        boolean isWinningsValid     = (winMap != null && !winMap.isEmpty( ));
        if( isWinningsValid ){
            context.setAttribute( WINNINGS_MAP_KEY, winMap );
            LOGGER.info("Successfully stored winnings with key [{}] {}{}", WINNINGS_MAP_KEY, winMap.values( ), PRINT_NEWLINE);
        }
    }


    protected final void createGameCenter( ScheduleManager schMan, ServletContext context ) {
        PickManager pickManager     = (PickManager) context.getAttribute( PICK_MANAGER_KEY );
        GameAnalyticsManager manager= new GameAnalyticsManager( schMan.getSchedules( ), pickManager );
        manager.start( );
        
        context.setAttribute( GAME_ANALYTICS_KEY, manager );
        LOGGER.info("Successfully stored game center manager with key [{}] {}", GAME_ANALYTICS_KEY, PRINT_NEWLINE);        
    }
    

    protected final DBService createDBService( DDPMeta ddpMeta, ServletContext context ){
        
        String dbDriver     = context.getInitParameter(RDS_DRIVER_TAG);
        String dbHost       = context.getInitParameter(RDS_HOST_TAG);
        String dbPort       = context.getInitParameter(RDS_PORT_TAG);
        String dbName       = context.getInitParameter(RDS_DB_NAME_TAG);

        DBService service   = new DBService( ddpMeta, dbDriver, dbHost, dbPort, dbName );
        if( service != null ) {
            context.setAttribute( DB_SERVICE_KEY, service );
            LOGGER.info("Successfully created DBService & stored with key [{}]{}", DB_SERVICE_KEY, PRINT_NEWLINE);
        }
        
        return service;
    
    }    
    

    protected final ScheduleManager parseSchedule( DDPMeta ddpMeta, DBService service, ServletContext context ){
        
        ScheduleManager manager = new ScheduleManager( ddpMeta, service );
        context.setAttribute( SCHEDULE_KEY, manager );
        
        LOGGER.info( "Stored [{}] game schedule for Week [{}] with Key: [{}]{}", manager.getScheduleCount( ), ddpMeta.getWeek( ), SCHEDULE_KEY, PRINT_NEWLINE );
     
        return manager;
    
    }
    
    
    protected final int parseYear( ServletContext context ) {
        int yearNumber          = 0;
        
        try {
       
            String yearString   = context.getInitParameter( NFL_YEAR_KEY );
            yearNumber          = Integer.parseInt( yearString );
            
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to parse param [{}] from context to get NFL year", NFL_YEAR_KEY, e );
        }
        
        return yearNumber;
    }
    
        
    protected final DDPMeta createMetaData( ServletContext context ){
        
        DDPMeta metaData        = null;
        
        try {
            
            String version      = context.getInitParameter( APP_VERSION_KEY );
            String nflSeason    = context.getInitParameter( NFL_SEASON_KEY );
            int nflYear         = Integer.parseInt( context.getInitParameter(NFL_YEAR_KEY) );
            int nflWeek         = Integer.parseInt( context.getInitParameter( NFL_WEEK_NUMBER ) );
            int cashPerWeek     = Integer.parseInt( context.getInitParameter( CASH_PER_WEEK_KEY ) );
            
            metaData            = new DDPMeta( version, nflSeason, nflYear, nflWeek, cashPerWeek );
            
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
                        
            GameAnalyticsManager gameManager = (GameAnalyticsManager) ctx.getAttribute( GAME_ANALYTICS_KEY );
            if( gameManager != null ) {
                gameManager.stop();
            }
            
            LOGGER.info( "Successfully destroyed DDP Servlet context.");
            
        } catch (SQLException e) {
            LOGGER.warn("FAILED to destroy DDP Servlet context.", e);
        }
    
    }
    
}