package com.ddp.nfl.web.database;

import org.slf4j.*;
import java.sql.*;
import java.util.*;

import com.mysql.cj.jdbc.*;
import com.ddp.nfl.web.core.*;

import static com.ddp.nfl.web.util.DDPUtil.*;
import static com.ddp.nfl.web.core.DDPTable.*;


public final class DBConnection{

    private final DDPMeta ddpMeta;
    private final boolean connected;
    private final Connection connection;
    private final PreparedStatement pickPrepStatement;
        
    private final static String NAME    = DBConnection.class.getSimpleName( );
    private final static Logger LOGGER  = LoggerFactory.getLogger( NAME );
    
    
    public DBConnection( DDPMeta ddpMeta, String driverName, String dbHost, String dbPort, String dbName ){
        this.ddpMeta            = ddpMeta;
        this.connection         = createConnection( driverName, dbHost, dbPort, dbName );
        this.connected          = ( connection != null );
        this.pickPrepStatement  = preparePickStatement( connection );
    }
    
    
    protected final Map<Integer, DDPPick> loadPicks( Map<String, NFLTeam> teamMap, Map<String, DDPPlayer> playerMap ){
        return loadPicks( ddpMeta.getYear( ), ddpMeta.getWeek( ), teamMap, playerMap );
        
    }
    
   
    
    public final Map<Integer, DDPPick> loadPicks( int nflYear, int nflWeek, Map<String, NFLTeam> teamMap, Map<String, DDPPlayer> playerMap ){
        
        Map<Integer, DDPPick> map   = new TreeMap<>( );
        
        try{
           
            String query            = "SELECT * from " + DDP_PICK.getTableName( ) + " WHERE Year=" + nflYear + " AND Week=" + nflWeek + " order by PickOrder";
            LOGGER.info( "Executing query {}", query );
            
            ResultSet result        = connection.createStatement( ).executeQuery( query );
            
            while( result.next( ) ){
                int pickOrder   = result.getInt( "PickOrder" );
                String name     = result.getString( "Player" );
                String team1    = result.getString( "Team1" );
                team1           = isValid(team1) ? team1.toLowerCase( ) : EMPTY;
                
                String team2    = result.getString( "Team2" );
                team2           = isValid(team2) ? team2.toLowerCase( ) : EMPTY;
                
                String team3    = result.getString( "Team3" );
                team3           = isValid(team3) ? team3.toLowerCase( ) : EMPTY;
                
                NFLTeam[] teams = new NFLTeam[]{ teamMap.get(team1), teamMap.get(team2), teamMap.get(team3) };
                DDPPlayer player= playerMap.get( name );
                DDPPick pick    = new DDPPick( pickOrder, player, teams );
                
                map.put( pickOrder,  pick );
                LOGGER.info( "[{}] Retrieved Pick {}", map.size( ), pick );                                                                                                                                                                                                                         
            }
                       
        }catch( SQLException e ) {
            LOGGER.warn( "Exception while getting Pick info from DB", e );   
        }

        return map;
        
    }
 
    
    
    
    protected final Map<String, DDPPlayer> loadPlayers( ){
     
        Map<String, DDPPlayer> map = new HashMap<>( );
        
        try{
            
            String query        = "SELECT * from " + DDP_PLAYER.getTableName( ) + " WHERE IsActive=1";
            LOGGER.info( "Executing query {}", query );
            
            ResultSet result    = connection.createStatement( ).executeQuery( query );
            
            while( result.next( ) ){
                int playerId    = result.getInt( "Id" );
                String name     = result.getString( "Name" );
                String nickName = result.getString( "NickName" );
                String email    = result.getString( "Email" );
                
                DDPPlayer player= new DDPPlayer( playerId, name, nickName, email );
                map.put( name,  player );
                
                LOGGER.info( "Retrieved Player {}", player );
            }
            
        }catch( SQLException e ) {
            LOGGER.warn( "Exception while getting player info from DB", e );   
        }

        return map;
    
    }


    protected final Map<String, NFLTeam> loadTeams( ) {
        
        Map<String, NFLTeam> map = new HashMap<>( );
        
        try{
            
            String query        = "SELECT * from " + NFL_TEAM.getTableName( );
            LOGGER.info( "Executing query {}", query );
            
            ResultSet result    = connection.createStatement( ).executeQuery( query );
            
            while( result.next( ) ){
                int teamId      = result.getInt( "Id" );
                String name     = result.getString( "Team" ).toLowerCase( );
                String nickName = result.getString( "NickName" );
                String division = result.getString( "Division" );
                String conf     = result.getString( "Conference" );
                String city     = result.getString( "City" );
                String state    = result.getString( "State" );
                String roster   = result.getString( "Roster" );
                
                NFLTeam team    = new NFLTeam( teamId, name, nickName, division, conf, city, state, roster );
                map.put( name,  team );
                LOGGER.info( "Retrieved Team {}", team );
            }
            
        }catch( SQLException e ) {
            LOGGER.warn( "Exception while getting Team info from DB", e );   
        }

        return map;
        
    }

    
    protected final boolean upsertPick( int year, int pickForWeek, int pickOrder, DDPPick pick ){

        int startColumnIndex    = 1;
        boolean picksUpdated    = false;
        
        try{ 

            pickPrepStatement.setInt    ( startColumnIndex++, year );
            pickPrepStatement.setInt    ( startColumnIndex++, pickForWeek );
            pickPrepStatement.setInt    ( startColumnIndex++, pickOrder );
            pickPrepStatement.setString ( startColumnIndex++, pick.getPlayer( ).getName( ) );
            pickPrepStatement.setString ( startColumnIndex++, pick.getTeams( )[0].getName( ) );
            pickPrepStatement.setString ( startColumnIndex++, pick.getTeams( )[1].getName( ) );
            pickPrepStatement.setString ( startColumnIndex++, pick.getTeams( )[2].getName( ) );
             
            int updateResult    = pickPrepStatement.executeUpdate( );
            picksUpdated        = ( updateResult > -1 );
       
        }catch( SQLException e ){
            LOGGER.warn( "FAILED to update picks for week {}", pickForWeek, e );        
        }

        return picksUpdated;

    }

    
    protected final PreparedStatement preparePickStatement( Connection connection ){
        
        if( !connected ) {
            LOGGER.warn( "Won't attempt to create prepared statement as we aren't connected to DB." );
            return null;
        }
        
        PreparedStatement pStatement= null;
        
        try {
            
            StringBuilder builder   = new StringBuilder( 128 );
            
            builder.append( "INSERT INTO " ).append( DDP_PICK.getTableName( ) );
            builder.append( "( Year, Week, PickOrder, Player, Team1, Team2, Team3 )" );
            builder.append( " VALUES " );
            builder.append( "( ?, ?, ?, ?, ?, ?, ? )" );
                        
            String query    = builder.toString( );
            pStatement      = connection.prepareStatement(query);
                        
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to created prepared statement.", e );
        }
    
        return pStatement;
    
    }

    
    
    protected final static Connection createConnection( String driverName, String hostname, String port, String dbName ){

        LOGGER.info( "Attempting to create DB connection.");

        Connection connection   = null;
        String userName         = System.getProperty("RDS_USERNAME");
        String password         = System.getProperty("RDS_PASSWORD");
        
        try {
            Class.forName( driverName );
            
            String jdbcUrl      = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName;
            LOGGER.info("Connecting to DB at [{}], User: [{}] ", jdbcUrl, userName );

            connection          = DriverManager.getConnection(jdbcUrl, userName, password);
            LOGGER.info("Successfully connected to database: [{}]", dbName);
            
        }catch( Exception e) { 
            LOGGER.error("FAILED to connect to DB [{}:{}] [{}] [{}]", hostname, port, dbName, userName, e );
        }
        
        return connection;
        
    }
    

    
    public final void close( ) throws SQLException {
        
        if( connection != null ){
            connection.close();
        }
        
        AbandonedConnectionCleanupThread.checkedShutdown( );
        LOGGER.info("Successfully closed connection to DB.");
        
    }
   
    
}