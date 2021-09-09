package com.ddp.nfl.web.database;

import org.slf4j.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

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
    
    
    public DBConnection( DDPMeta ddpMeta ){
        this.ddpMeta            = ddpMeta;
        this.connection         = createConnection( ddpMeta );
        this.connected          = validateConnection( );
        this.pickPrepStatement  = preparePickStatement( connection );
    }

    
    public final Map<Integer, DDPPick> loadPicks( int nflYear, int nflWeek, Map<String, NFLTeam> teamMap, Map<String, DDPPlayer> playerMap ){
        
        Map<Integer, DDPPick> map   = new TreeMap<>( );
        
        try{
           
            LOGGER.info( "Loading all team picks stored in the database." );
            
            String query            = "SELECT * from " + DDP_PICK.getTableName( ) + " WHERE Year=" + nflYear + " AND Week=" + nflWeek + " order by PickOrder";
            LOGGER.info( "Executing query [{}]", query );
            
            ResultSet result        = connection.createStatement( ).executeQuery( query );
            
            while( result.next( ) ){
                int pickOrder   = result.getInt( "PickOrder" );
                String name     = result.getString( "Player" );
                String team1    = NFLTeam.resolveOverriddenName( result.getString( "Team1" ));
                team1           = isValid(team1) ? team1.toLowerCase( ) : EMPTY;
                
                String team2    = NFLTeam.resolveOverriddenName( result.getString( "Team2" ));
                team2           = isValid(team2) ? team2.toLowerCase( ) : EMPTY;

                NFLTeam[] teams = new NFLTeam[]{ teamMap.get(team1), teamMap.get(team2) };
                DDPPlayer player= playerMap.get( name );
                DDPPick pick    = new DDPPick( pickOrder, player, teams );
                
                map.put( pickOrder,  pick );
                LOGGER.info( "Retrieved {}", pick );                                                                                                                                                                                                                         
            }
                       
        }catch( SQLException e ) {
            LOGGER.warn( "Exception while getting Pick info from DB", e );   
        }

        LOGGER.info( "Successfully loaded [{}] DDP picks{}", map.size( ), PRINT_NEWLINE );
        
        return map;
        
    }
 
    
    
    
    protected final Map<String, DDPPlayer> loadPlayers( ){
     
        Map<String, DDPPlayer> map = new HashMap<>( );
        
        try{
            
            LOGGER.info( "Loading all DDP players stored in the database." );
            
            String query        = "SELECT * from " + DDP_PLAYER.getTableName( );
            LOGGER.info( "Executing query [{}]", query );
            
            ResultSet result    = connection.createStatement( ).executeQuery( query );
            
            while( result.next( ) ){
                int playerId    = result.getInt( "Id" );
                String name     = result.getString( "Name" );
                String nickName = result.getString( "NickName" );                
                int deposit     = result.getInt( "Deposit" );
                
                DDPPlayer player= new DDPPlayer( playerId, name, nickName, deposit );
                map.put( name,  player );
                
                LOGGER.info( "Retrieved {}", player );
            }
            
        }catch( SQLException e ) {
            LOGGER.warn( "Exception while getting player info from DB", e );   
        }

        LOGGER.info( "Successfully loaded [{}] players{}", map.size( ), PRINT_NEWLINE );
        
        return map;
    
    }


    protected final Map<String, NFLTeam> loadTeams( ) {
        
        Map<String, NFLTeam> map = new HashMap<>( );
        
        try{
            
            LOGGER.info( "Loading all NFL teams stored in the database." );
            
            String query        = "SELECT * from " + NFL_TEAM.getTableName( );
            LOGGER.info( "Executing query [{}]", query );
            
            ResultSet result    = connection.createStatement( ).executeQuery( query );
            
            while( result.next( ) ){
                int teamId      = result.getInt( "Id" );
                String name     = result.getString( "Team" );
                String newName  = NFLTeam.resolveOverriddenName( name );
                String nickName = result.getString( "NickName" );
                String division = result.getString( "Division" );
                String conf     = result.getString( "Conference" );
                String city     = result.getString( "City" );
                
                NFLTeam team    = new NFLTeam( teamId, newName, nickName, division, conf, city );
                map.put( team.getLowerCaseName( ),  team );
                LOGGER.info( "Retrieved {}", team );
            }
            
        }catch( SQLException e ) {
            LOGGER.warn( "Exception while getting Team info from DB", e );   
        }

        LOGGER.info( "Successfully loaded [{}] teams{}", map.size( ), PRINT_NEWLINE );
        
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
            pickPrepStatement.setString ( startColumnIndex++, pick.getTeams( )[0].getLowerCaseName( ) );
            pickPrepStatement.setString ( startColumnIndex++, pick.getTeams( )[1].getLowerCaseName( ) );

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
            builder.append( "( Year, Week, PickOrder, Player, Team1, Team2 )" );
            builder.append( " VALUES " );
            builder.append( "( ?, ?, ?, ?, ?, ? )" );
                        
            String query    = builder.toString( );
            pStatement      = connection.prepareStatement(query);
                        
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to created prepared statement.", e );
        }
    
        return pStatement;
    
    }

    
    
    protected final static Connection createConnection( DDPMeta ddpMeta ){

        LOGGER.info( "Attempting to create DB connection.");

        Connection connection   = null;
        String connectionURL    = ddpMeta.getDBConnectionURL();
        try {
            Class.forName("org.postgresql.Driver");
            connection          = DriverManager.getConnection(connectionURL);
            LOGGER.info("Successfully connected to [{}]", connectionURL);

        }catch( Exception e) { 
            LOGGER.error("FAILED to connect to DB at", connectionURL, e );
        }
        
        return connection;
        
    }    

    
    protected final boolean validateConnection( ) {
        if( connection == null ) {
            throw new RuntimeException("Exiting as we FAILED to connect to database!"); 
        }
        
        return true;
    }
    
    
    public final void close( ) throws SQLException {
        if( connection != null ){
            connection.close();
        }

        LOGGER.info("Successfully closed connection to DB.");
        
    }

    public static void main( String[] args ){
        DDPMeta ddpMeta         = new DDPMeta("3.0", false, "REG", LocalDate.now(), 1, 40);
        DBConnection connection = new DBConnection(ddpMeta);
        System.out.println( connection.loadPlayers().values() );
    }

}