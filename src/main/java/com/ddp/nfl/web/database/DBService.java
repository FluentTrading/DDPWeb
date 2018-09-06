package com.ddp.nfl.web.database;

import java.sql.*;
import java.util.*;

import com.ddp.nfl.web.core.*;


public final class DBService{
   
    private final boolean isValid;
    private final DBConnection dbConnection;
    private final Map<String, NFLTeam> teamMap;
    private final Map<String, DDPPlayer> playerMap;
    private final Map<Integer, DDPPick> playerPickMap;
    
    public DBService( DDPMeta ddpMeta, String driverName, String dbHost, String dbPort, String dbName ){
        
        this.dbConnection   = new DBConnection( ddpMeta, driverName, dbHost, dbPort, dbName );
        this.teamMap        = dbConnection.loadTeams( );
        this.playerMap      = dbConnection.loadPlayers( );
        this.playerPickMap  = loadPicks( ddpMeta.getWeek( ),ddpMeta );
        this.isValid        = validate( );
        
    }


    public final boolean isValid( ) {
        return isValid;
    }
    
    
    public final boolean isPicksMade( ) {
        return !playerPickMap.isEmpty( );
    }
             

    public final DBConnection getConnection( ) {
        return dbConnection;
    }
    
    
    public final Map<String, NFLTeam> getAllTeams( ){
        return teamMap;
    }
    
    public final NFLTeam getTeam( String teamName ){
        return teamMap.get( teamName.toLowerCase( ) );
    }
    
    
    public final Map<String, DDPPlayer> getAllPlayers( ){
        return playerMap;
    }

    public final DDPPlayer getPlayer( String playerName ){
        return playerMap.get( playerName );
    }
    
    
    public final Map<Integer, DDPPick> getOrderedPicks( ){
        return playerPickMap;
    }
    
    
    public final Map<Integer, DDPPick> loadPicks( int week, DDPMeta meta ){
        return dbConnection.loadPicks( meta.getYear( ), week, teamMap, playerMap );
    }
        
    
    public final boolean upsertPick( int year, int pickForWeek, int pickOrder, DDPPick ddpPick  ){
        return dbConnection.upsertPick( year, pickForWeek, pickOrder, ddpPick );
    }
    
    protected final boolean validate( ){
        if( teamMap.isEmpty( ) ) return false;
        if( playerMap.isEmpty( ) ) return false;
                        
        return true;
    }    
    

    public final void close( ) throws SQLException {
        dbConnection.close( );
    }

    
}